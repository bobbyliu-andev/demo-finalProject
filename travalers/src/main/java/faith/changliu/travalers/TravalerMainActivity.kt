package faith.changliu.travalers

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.WindowManager
import faith.changliu.base.BaseActivity
import faith.changliu.base.data.AppRepository
import faith.changliu.base.data.firebase.FireAuth
import faith.changliu.base.data.firebase.firestore.FireDB
import faith.changliu.base.data.models.RequestApplication
import faith.changliu.base.data.preferences.UserPref
import faith.changliu.base.data.viewmodels.RequestViewModel
import faith.changliu.base.utils.no
import faith.changliu.base.utils.snackConfirm
import faith.changliu.base.utils.tryBlock
import faith.changliu.base.utils.yes
import kotlinx.android.synthetic.main.activity_travaler_main.*
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.clearTop
import org.jetbrains.anko.toast
import java.util.*

class TravalerMainActivity : BaseActivity() {

	private val mViewModel by lazy { RequestViewModel() }
	private lateinit var mRequestAdapter: TravalerRequestAdapter


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		FireAuth.isLoggedIn().no {
			val intent = Intent(this, TravalerLoginActivity::class.java).clearTop()
			startActivity(intent)
			finish()
		}.yes {
			window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
			setContentView(R.layout.activity_travaler_main)

			initViews()

			this.tryBlock {
				AppRepository.syncAllRequests()
			}
		}
	}

	private fun initViews() {
		mRcvAllRequests.layoutManager = LinearLayoutManager(this)
		mRequestAdapter = TravalerRequestAdapter(arrayListOf()) { request ->
			// todo: apply request with detail view
			snackConfirm(mRcvAllRequests, "Confirm Apply") { _ ->

				// todo: check if user already applied
				// get new application
				val userId = UserPref.getId()
				val uuid = UUID.randomUUID().toString()
				val newApplication = RequestApplication(uuid, request.id, userId, Date())

				// todo: use app repo and cache in room
				tryBlock {
					async(CommonPool) {
						FireDB.saveApplication(newApplication)
					}.await()
					toast("Applied Successfully")
				}
			}
		}
	}

	override fun onResume() {
		super.onResume()
		mViewModel.requests.observe(this, Observer { requests ->
			requests?.let { requests ->
				mRequestAdapter.requests.apply {
					// todo: opt delete/insert
					clear()
					addAll(requests)
				}
				mRcvAllRequests.adapter = mRequestAdapter
			}
		})
	}
}

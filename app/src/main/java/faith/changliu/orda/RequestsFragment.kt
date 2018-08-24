package faith.changliu.orda

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import faith.changliu.base.BaseFragment
import faith.changliu.base.data.AppRepository
import faith.changliu.base.data.viewmodels.RequestViewModel
import faith.changliu.base.utils.tryBlock
import kotlinx.android.synthetic.main.fragment_requests.*
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.support.v4.toast


class RequestsFragment : BaseFragment(), View.OnClickListener {

	private val mViewModel by lazy { RequestViewModel() }
	private lateinit var mRequestAdapter: RequestsAdapter
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		arguments?.let {
		}
	}
	
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
							  savedInstanceState: Bundle?): View? {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_requests, container, false)
	}
	
	override fun onAttach(context: Context) {
		super.onAttach(context)
		// todo: listener
	}
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		mFabAddRequest.setOnClickListener(this)

		mRcvRequests.layoutManager = LinearLayoutManager(context)
		// todo: implement onUpdate, onDelete
		mRequestAdapter = RequestsAdapter(arrayListOf(), {toast("Feature to be added")}, {
			request ->
			this.tryBlock {
				async(CommonPool) {
					AppRepository.deleteRequest(request.id)
				}.await()
				toast("Deleted")
			}
		})
	}

	override fun onResume() {
		super.onResume()
		mViewModel.requests.observe(this, Observer { requests ->
			requests?.let { requests ->
				mRequestAdapter.requests.apply {
					clear()
					addAll(requests)
				}
				mRcvRequests.adapter = mRequestAdapter
			}
		})
	}

	override fun onClick(v: View) {
		when (v.id) {
			R.id.mFabAddRequest -> {
				val intent = Intent(context, AddRequestActivity::class.java)
				intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
				startActivity(intent)
			}
		}
	}
}

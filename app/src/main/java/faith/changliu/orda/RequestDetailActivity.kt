package faith.changliu.orda

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.WindowManager
import faith.changliu.base.BaseActivity
import faith.changliu.base.data.AppRepository
import faith.changliu.base.data.firebase.firestore.FireDB
import faith.changliu.base.data.models.Request
import faith.changliu.base.data.models.RequestStatus
import faith.changliu.base.utils.KEY_REQUEST
import faith.changliu.base.utils.tryBlock
import kotlinx.android.synthetic.main.request_detail.*
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.toast

class RequestDetailActivity : BaseActivity() {

	private lateinit var mAdapter: ApplicationsAdapter
	private var request: Request? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.request_detail)
		window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

		mAdapter = ApplicationsAdapter(arrayListOf()) { assignedTo ->
			// todo: update request with assignedTo, status
			request?.let { request ->
				request.apply {
					this.assignedTo = assignedTo
					status = RequestStatus.ASSIGNED
				}

				tryBlock {
					async(CommonPool) {
						AppRepository.insertRequest(request)
					}.await()
					toast("Request Assigned")
					finish()
				}
			}
		}

		mRcvApplications.layoutManager = LinearLayoutManager(this)

		request = intent?.getSerializableExtra(KEY_REQUEST) as? Request

		request?.id?.let { requestId ->
			// load applications
			tryBlock {
				val applications = async(CommonPool) {
					FireDB.readAllApplicationsWithRequestId(requestId)
				}.await()
				mAdapter.applications.addAll(applications)
				mRcvApplications.adapter = mAdapter
			}
		}
	}
}


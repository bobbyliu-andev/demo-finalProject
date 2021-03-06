package faith.changliu.orda

import android.os.Bundle
import faith.changliu.base.BaseActivity
import faith.changliu.base.data.AppRepository
import faith.changliu.base.data.models.Request
import faith.changliu.base.data.models.RequestStatus
import faith.changliu.base.data.preferences.UserPref
import faith.changliu.base.utils.getDouble
import faith.changliu.base.utils.getString
import faith.changliu.base.utils.tryBlock
import kotlinx.android.synthetic.main.activity_add_request.*
import kotlinx.android.synthetic.main.content_add_request.*
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.toast
import java.util.*

class AddRequestActivity : BaseActivity() {

	private var deadline: Date? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_add_request)
		setSupportActionBar(toolbar)
		mBtnSubmitRequest.setOnClickListener {
			val title = mEtRequestTitle.getString() ?: return@setOnClickListener
			val address = mEtRequestAddress.getString() ?: return@setOnClickListener
			val city = mEtRequestCity.getString() ?: return@setOnClickListener
			val country = mEtRequestCountry.getString() ?: return@setOnClickListener
			val weight = mEtRequestWeight.getDouble() ?: return@setOnClickListener
			val volume = mEtRequestVolume.getDouble() ?: return@setOnClickListener
			val compensation = mEtRequestCompensation.getDouble() ?: return@setOnClickListener
			val description = mEtRequestDescription.getString() ?: return@setOnClickListener
			if (deadline == null) {
				toast("Deadline null")
				return@setOnClickListener
			}

			val userId = UserPref.getId()
			val requestId = UUID.randomUUID().toString()

			// todo: get user email
			val email = "bobbyliu117@gmail.com"

			val newRequest = Request(requestId, title, RequestStatus.PENDING, "", deadline!!, country, city, address, weight, volume, compensation, description, Date(), userId, email)
			tryBlock {
				async(CommonPool) {
					AppRepository.insertRequest(newRequest)
				}.await()
				finish()
			}
		}

		mBtnPickDeadline.setOnClickListener {
			// todo: pick date
			deadline = Date()
			mBtnPickDeadline.text = deadline.toString()
		}
	}
}

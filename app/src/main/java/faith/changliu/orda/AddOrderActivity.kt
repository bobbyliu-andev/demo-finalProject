package faith.changliu.orda

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import faith.changliu.base.BaseActivity
import faith.changliu.base.data.AppRepository
import faith.changliu.base.data.models.Order
import faith.changliu.base.data.preferences.UserPref
import faith.changliu.base.utils.getDouble
import faith.changliu.base.utils.getString
import faith.changliu.base.utils.tryBlock
import kotlinx.android.synthetic.main.activity_add_order.*
import kotlinx.android.synthetic.main.content_add_order.*
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import java.util.*

class AddOrderActivity : BaseActivity() {
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_add_order)
		setSupportActionBar(toolbar)
		mBtnSubmit.setOnClickListener { view ->
			
			// validation
			val barcode = mEtBarcode.getString() ?: return@setOnClickListener
			val title = mEtTitle.getString() ?: return@setOnClickListener
			val price = mEtPrice.getDouble() ?: return@setOnClickListener
			val weight = mEtWeight.getDouble() ?: return@setOnClickListener
			val description = mEtDescription.text.toString()
			
			val userId = UserPref.getId()
			
			// todo: remove ph agent test
			val newOrder = Order(barcode, title, weight, price, Date(), Date(), userId, description)
			tryBlock {
				async(CommonPool) {
					AppRepository.insertOrder(newOrder)
				}.await()
				finish()
			}
		}
		supportActionBar?.setDisplayHomeAsUpEnabled(true)
	}
}

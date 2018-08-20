package faith.changliu.orda

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import faith.changliu.base.data.AppRepository
import faith.changliu.base.data.models.Order
import kotlinx.android.synthetic.main.activity_add_order.*

class AddOrderActivity : AppCompatActivity() {
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_add_order)
		setSupportActionBar(toolbar)
		fab.setOnClickListener { view ->
			val newOrder = Order()
			AppRepository.insertOrder(newOrder)
			finish()
		}
		supportActionBar?.setDisplayHomeAsUpEnabled(true)
	}
}

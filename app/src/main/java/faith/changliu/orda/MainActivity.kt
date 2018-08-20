package faith.changliu.orda

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.WindowManager
import faith.changliu.base.BaseActivity
import faith.changliu.base.data.models.Order
import faith.changliu.base.data.viewmodels.MainViewModel
import faith.changliu.base.utils.no
import faith.changliu.base.utils.yes
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.toast

class MainActivity : BaseActivity() {

	private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
		when (item.itemId) {
			navigation.selectedItemId -> false
			R.id.navigation_shipping -> {
				mLoading.startLoading()
				switchFragment(mShippingFragment)
				return@OnNavigationItemSelectedListener true
			}
			R.id.navigation_dashboard -> {
				switchFragment(mRequestsFragment)
				return@OnNavigationItemSelectedListener true
			}
			R.id.navigation_notifications -> {
				switchFragment(mAccountFragment)
				return@OnNavigationItemSelectedListener true
			}
		}
		false
	}

	private val mShippingFragment by lazy { ShippingFragment() }
	private val mRequestsFragment by lazy { RequestsFragment() }
	private val mAccountFragment by lazy { AccountFragment() }

	private val mViewModel by lazy { MainViewModel() }

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		supportActionBar?.hide()
		mLoading.startLoading()

		launch(UI) {
			delay(2000)
			initViews()
			mLoading.stopLoading()
		}
	}

	override fun onResume() {
		super.onResume()
		mViewModel.orders.observe(this, Observer<List<Order>> {
			it?.isEmpty()?.no {
				toast(it?.get(0).toString())
			}
		})
	}

	private fun initViews() {
		window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN) // show status
		supportActionBar?.show()
		navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
		switchFragment(mShippingFragment)
	}

	private fun switchFragment(newFragment: Fragment) {
		supportFragmentManager.beginTransaction()
				.replace(R.id.mFragContainer, newFragment)
				.commit()
	}
	
}


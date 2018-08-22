package faith.changliu.orda

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.WindowManager
import faith.changliu.base.BaseActivity
import faith.changliu.base.data.AppRepository
import faith.changliu.base.data.firebase.FireAuth
import faith.changliu.base.data.viewmodels.MainViewModel
import faith.changliu.base.utils.no
import faith.changliu.base.utils.tryBlock
import faith.changliu.base.utils.yes
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.clearTop

class MainActivity : BaseActivity() {

	private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
		when (item.itemId) {
			navigation.selectedItemId -> false
			R.id.navigation_shipping -> {
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
		
		FireAuth.isLoggedIn().no {
			val intent = Intent(this, LoginActivity::class.java).clearTop()
			startActivity(intent)
			finish()
		}.yes {
			window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN) // show status
			setContentView(R.layout.activity_main)
			supportActionBar?.hide()
			
			tryBlock {
				AppRepository.syncOrders()
				initViews()
			}
		}
	}

	private fun initViews() {
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


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
import faith.changliu.base.data.models.Order
import faith.changliu.base.data.viewmodels.MainViewModel
import faith.changliu.base.utils.no
import faith.changliu.base.utils.tryBlock
import kotlinx.android.synthetic.main.fragment_shipping.*
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.toast

class ShippingFragment : BaseFragment(), View.OnClickListener {
	
	private val mViewModel by lazy { MainViewModel() }
	private lateinit var mOrderAdapter: OrdersAdapter
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		arguments?.let {
		}
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
							  savedInstanceState: Bundle?): View? {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_shipping, container, false)
	}

	override fun onAttach(context: Context) {
		super.onAttach(context)
		// todo: listener
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		mFabAddOrder.setOnClickListener(this)
		
		mRcvOrders.layoutManager = LinearLayoutManager(context)
		// todo: implement onUpdate, onDelete
		mOrderAdapter = OrdersAdapter(arrayListOf(), {toast("Feature to be added")}, {
			order ->
			tryBlock {
				async(CommonPool) {
					AppRepository.deleteOrder(order.id)
				}.await()
				toast("Deleted")
			}
		})
	}
	
	override fun onResume() {
		super.onResume()
		mViewModel.orders.observe(this, Observer<List<Order>> { orders ->
			orders?.let { orders ->
				mOrderAdapter.orders.apply {
					// todo: opt delete/insert
					clear()
					addAll(orders)
				}
				mRcvOrders.adapter = mOrderAdapter
			}
		})
	}
	
	override fun onClick(v: View) {
		when (v.id) {
			R.id.mFabAddOrder -> {
				val intent = Intent(context, AddOrderActivity::class.java)
				intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
				startActivity(intent)
			}
		}
	}
}


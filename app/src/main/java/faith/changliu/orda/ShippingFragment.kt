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
import faith.changliu.base.data.models.Order
import faith.changliu.base.data.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.fragment_shipping.*
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.toast

class ShippingFragment : Fragment(), View.OnClickListener {
	
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
		mOrderAdapter = OrdersAdapter(arrayListOf(), {toast(it.id)}, {toast(it.id)})
	}
	
	override fun onResume() {
		super.onResume()
		mViewModel.orders.observe(this, Observer<List<Order>> { orders ->
			orders?.let {
				val order = orders?.get(0).toString()
				println(order)
				toast(order)
				mOrderAdapter.orders.apply {
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


package faith.changliu.orda

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_requests.view.*
import kotlinx.android.synthetic.main.fragment_shipping.*
import kotlinx.android.synthetic.main.fragment_shipping.view.*

class ShippingFragment : Fragment(), View.OnClickListener {
	
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

class RequestsFragment : Fragment() {


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
		view.mTv01.text = "Requests"
	}
}

class AccountFragment : Fragment() {


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
		view.mTv01.text = "Account"
	}
}

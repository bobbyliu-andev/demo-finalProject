package faith.changliu.orda

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import faith.changliu.base.data.models.Order
import faith.changliu.base.utils.snackConfirm
import kotlinx.android.synthetic.main.cell_order.view.*

class OrdersAdapter(
		var orders: ArrayList<Order>,
		private val onUpdate: (Order) -> Unit,
		private val onDelete: (Order) -> Unit
) : RecyclerView.Adapter<OrdersAdapter.ViewHolder>() {
	
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val view = LayoutInflater.from(parent.context).inflate(R.layout.cell_order, parent, false)
		return ViewHolder(view)
	}
	
	override fun getItemCount(): Int {
		return orders.size
	}
	
	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		holder.bind(orders[position])
	}
	
	inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
		
		fun bind(order: Order) {
			itemView.apply {
				mTvTitle.text = order.id
				mTvMemo.text = order.description
				mBtnDelete.setOnClickListener {
					snackConfirm(it, "Confirm to delete") {
						onDelete(order)
					}
				}
				mBtnEdit.setOnClickListener {
					onUpdate(order)
				}
			}
		}
	}
}
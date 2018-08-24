package faith.changliu.orda

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import faith.changliu.base.data.models.RequestApplication
import faith.changliu.base.utils.snackConfirm
import kotlinx.android.synthetic.main.cell_application.view.*

class ApplicationsAdapter(
		val applications: ArrayList<RequestApplication>,
		private val onAssign: (assignedTo: String) -> Unit
) : RecyclerView.Adapter<ApplicationsAdapter.ViewHolder>() {
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val v = LayoutInflater.from(parent.context).inflate(R.layout.cell_application, parent, false)
		return ViewHolder(v)
	}

	override fun getItemCount(): Int = applications.size

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		holder.bind(applications[position])
	}


	inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {

		fun bind(application: RequestApplication) {
			itemView.apply {
				mTvName.text = application.appliedBy
				setOnClickListener {
					snackConfirm(this, "Assign Confirm") {
						onAssign(application.appliedBy)
					}
				}
			}
		}
	}
}
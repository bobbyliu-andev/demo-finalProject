package faith.changliu.travalers

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import faith.changliu.base.AppContext
import faith.changliu.base.data.models.Request
import kotlinx.android.synthetic.main.cell_request.view.*
import org.jetbrains.anko.email

class TravalerRequestAdapter(
		var requests: ArrayList<Request>,
		private val onClick: (Request) -> Unit
) : RecyclerView.Adapter<TravalerRequestAdapter.ViewHolder>() {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val view = LayoutInflater.from(parent.context).inflate(R.layout.cell_request, parent, false)
		return ViewHolder(view)
	}

	override fun getItemCount(): Int {
		return requests.size
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		holder.bind(requests[position])
	}

	inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {

		fun bind(request: Request) {
			itemView.apply {
				mTvTitle.text = request.title
				mTvMemo.text = request.description
				setOnClickListener{ onClick(request) }
				mBtnEmailAgent.setOnClickListener {
					AppContext.email(request.agentEmail, "About Request: ${request.title}", "May I ask something about the request ${request.title}?\n")
				}
			}
		}
	}
}
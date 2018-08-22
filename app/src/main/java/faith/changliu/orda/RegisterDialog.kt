package faith.changliu.orda

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import faith.changliu.base.utils.getEmail
import faith.changliu.base.utils.getString
import faith.changliu.base.utils.isConnected
import faith.changliu.base.widgets.LoadingDialog
import kotlinx.android.synthetic.main.register_dialog.*
import org.jetbrains.anko.toast

class RegisterDialog(context: Context) : Dialog(context) {
	
	private val mLoading: LoadingDialog by lazy { LoadingDialog(this.context) }
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.register_dialog)
		
		mRegSubmit.setOnClickListener {
			val name = mRegName.getString() ?: return@setOnClickListener
			val email = mRegEmail.getEmail() ?: return@setOnClickListener
			val zipcode = mRegZip.getString() ?: return@setOnClickListener
			if (zipcode.length != 5) {
				mRegZip.error = "Zip code should be 5 digits long"
				return@setOnClickListener
			}
			val phone = mRegPhone.text.toString()
			
			if (isConnected()) {
				// todo: save pending agent
				context.toast("Hello")
			} else {
				context.toast("Error: No internet")
			}
		}
	}
}
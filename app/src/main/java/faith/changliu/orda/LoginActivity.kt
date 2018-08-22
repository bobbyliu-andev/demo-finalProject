package faith.changliu.orda

import android.content.Intent
import android.os.Bundle
import android.view.View
import faith.changliu.base.BaseActivity
import faith.changliu.base.data.firebase.FireAuth
import faith.changliu.base.data.preferences.UserPref
import faith.changliu.base.utils.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.toast

class LoginActivity : BaseActivity(), View.OnClickListener {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_login)
		
		mBtnLogin.setOnClickListener(this)
		mBtnRegister.setOnClickListener(this)
		mBtnResetPwd.setOnClickListener(this)
	}
	
	override fun onClick(v: View) {
		when (v.id) {
			R.id.mBtnLogin -> login()
			R.id.mBtnRegister -> register()
			R.id.mBtnResetPwd -> resetPwd()
		}
	}
	
	private fun resetPwd() {
		val email = mEtLoginEmail.getEmail() ?: return
	}
	
	private fun register() {
		val dialog = RegisterDialog(this)
		dialog.show()
	}
	
	private fun login() {
		val email = mEtLoginEmail.getEmail() ?: return
		val pwd = mEtPwd.getString() ?: return
		
		if(isConnected().not()) {
			toast(getString(R.string.no_internet))
			return
		}
		
		tryBlock {
			val userId = async(CommonPool) {
				FireAuth.login(email, pwd)
			}.await()
			
			UserPref.setId(userId)
			toMain()
		}
	}
	
	private fun toMain() {
		val intent = Intent(this, MainActivity::class.java)
		intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
		startActivity(intent)
		finish()
	}
}
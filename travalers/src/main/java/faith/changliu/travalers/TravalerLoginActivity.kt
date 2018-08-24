package faith.changliu.travalers

import android.content.Intent
import android.os.Bundle
import android.view.View
import faith.changliu.base.BaseActivity
import faith.changliu.base.data.firebase.FireAuth
import faith.changliu.base.data.preferences.UserPref
import faith.changliu.base.utils.getEmail
import faith.changliu.base.utils.getString
import faith.changliu.base.utils.isConnected
import faith.changliu.base.utils.tryBlock
import kotlinx.android.synthetic.main.activity_travaler_login.*
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.toast

class TravalerLoginActivity : BaseActivity(), View.OnClickListener {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_travaler_login)

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
		// todo: register
		toast("To be added")
//		val dialog = RegisterDialog(this)
//		dialog.show()
	}

	// todo: diff agent and traveler
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
		val intent = Intent(this, TravalerMainActivity::class.java)
		intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
		startActivity(intent)
		finish()
	}
}

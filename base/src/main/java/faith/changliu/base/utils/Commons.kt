package faith.changliu.base.utils

import android.content.Context
import android.net.ConnectivityManager
import android.widget.EditText
import faith.changliu.base.AppContext
import faith.changliu.base.BaseActivity
import faith.changliu.base.widgets.LoadingDialog
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.toast

private val cm = AppContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

fun isConnected(): Boolean {
	val activeNetwork = cm.activeNetworkInfo ?: return false
	return activeNetwork.isConnected
}

fun Boolean.yes(yes: () -> Unit): Boolean {
	if (this) yes()
	return this
}

fun Boolean.no(no: () -> Unit): Boolean {
	if (this.not()){
		no()
	}
	return this
}



// ---------- Edit Ext ----------

fun EditText.getDouble(): Double? {
	val stringText = getString() ?: return null
	return stringText.toDouble()
}

fun EditText.getString(): String? {
	if (text.isNullOrEmpty()) {
		this.error = "Input is required"
		return null
	}
	
	return text.toString()
}

fun EditText.getEmail(): String? {
	val email = getString() ?: return null
	if (email.isEmail().not()) {
		error = "Not an email "
		return null
	}
	
	return email
}

/**
 * Email format check
 */
fun String.isEmail(): Boolean {
	val p = "^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)\$".toRegex()
	return matches(p)
}

// Coroutine
fun BaseActivity.tryBlock(block: suspend () -> Unit) {
	launch(UI) {
		try {
			mLoading.startLoading()
			block()
		} catch (ex: NullPointerException) {
			ex.printStackTrace()
		} catch (ex: Exception) {
			ex.printStackTrace()
		} finally {
			mLoading.stopLoading()
		}
	}
}
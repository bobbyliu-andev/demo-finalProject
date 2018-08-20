package faith.changliu.base.utils

import android.content.Context
import android.net.ConnectivityManager
import faith.changliu.base.AppContext

private val cm = AppContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

fun isConnected(): Boolean {
	val activeNetwork = cm.activeNetworkInfo ?: return false
	return activeNetwork.isConnected
}

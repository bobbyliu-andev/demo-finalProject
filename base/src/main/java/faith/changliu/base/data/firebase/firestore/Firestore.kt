package faith.changliu.base.data.firebase.firestore

import com.google.firebase.firestore.FirebaseFirestore
import faith.changliu.base.data.models.Order
import faith.changliu.base.data.preferences.UserPref
import kotlinx.coroutines.experimental.suspendCancellableCoroutine

object FireDB {
	private val mFirestore: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

	suspend fun saveOrder(order: Order) {
		mFirestore.saveOrder(order)
	}
	
	suspend fun readAllOrders(): ArrayList<Order> {
		return mFirestore.readAllOrders()
	}
	
	suspend fun deleteOrder(orderId: String) {
		mFirestore.deleteOrder(orderId)
	}
}

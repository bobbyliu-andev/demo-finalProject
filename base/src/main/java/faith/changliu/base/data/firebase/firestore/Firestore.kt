package faith.changliu.base.data.firebase.firestore

import com.google.firebase.firestore.FirebaseFirestore
import faith.changliu.base.data.models.Order
import faith.changliu.base.data.models.Request

object FireDB {
	private val mFirestore: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

	// region { Orders }

	suspend fun saveOrder(order: Order) {
		mFirestore.saveOrder(order)
	}
	
	suspend fun readAllOrders(): ArrayList<Order> {
		return mFirestore.readAllOrders()
	}
	
	suspend fun deleteOrder(orderId: String) {
		mFirestore.deleteOrder(orderId)
	}

	// endregion

	// region { Requests }

	suspend fun saveRequest(request: Request) {
		mFirestore.saveRequest(request)
	}

	suspend fun readAllRequests(): ArrayList<Request> {
		return mFirestore.readAllRequests()
	}

	suspend fun deleteRequest(requestId: String) {
		mFirestore.deleteRequest(requestId)
	}

	// endregion
}

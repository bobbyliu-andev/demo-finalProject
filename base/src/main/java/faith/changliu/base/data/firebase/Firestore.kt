package faith.changliu.base.data.firebase

import com.google.firebase.firestore.FirebaseFirestore
import faith.changliu.base.data.models.Order
import kotlinx.coroutines.experimental.suspendCancellableCoroutine

object FireDB {
	private val mFirestore: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

	suspend fun saveOrder(order: Order) {
		mFirestore.saveOrder(order)
	}
}

suspend fun FirebaseFirestore.saveOrder(order: Order) = suspendCancellableCoroutine<Unit> { cont ->
	collection(Collection.ORDERS).document(order.id)
			.set(order)
			.addOnSuccessListener {
				cont.resume(Unit)
			}.addOnFailureListener { exception ->
				cont.resumeWithException(exception)
			}.addOnCanceledListener {
				cont.cancel(Throwable("Cancelled"))
			}
}

object Collection {
	const val ORDERS = "orders"
}
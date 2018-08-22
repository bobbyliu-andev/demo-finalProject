package faith.changliu.base.data.firebase.firestore

import com.google.firebase.firestore.FirebaseFirestore
import faith.changliu.base.data.models.Order
import faith.changliu.base.data.preferences.UserPref
import kotlinx.coroutines.experimental.suspendCancellableCoroutine

/*
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insertAll(orders: List<Order>)

	@Query("select * from orders where id = :id")
	fun getOrderById(id: String): Order

	@Query("delete from orders")
	fun deleteAll()

	@Query("select count(*) from orders")
	fun getCount(): Int
 */

private const val ORDERS = "orders"

suspend fun FirebaseFirestore.saveOrder(order: Order) = suspendCancellableCoroutine<Unit> { cont ->
	collection(ORDERS).document(order.id)
			.set(order)
			.addOnSuccessListener {
				cont.resume(Unit)
			}.addOnFailureListener { exception ->
				cont.resumeWithException(exception)
			}.addOnCanceledListener {
				cont.cancel(Throwable("Cancelled"))
			}
}

suspend fun FirebaseFirestore.readAllOrders() = suspendCancellableCoroutine<ArrayList<Order>> { cont ->
	val userId = UserPref.getId()
	val orders = arrayListOf<Order>()
	collection(ORDERS).whereEqualTo("createdBy", userId).get().addOnSuccessListener { snapshot ->
		snapshot.forEach { doc ->
			doc?.toObject(Order::class.java)?.let { order ->
				orders.add(order)
			}
		}
		cont.resume(orders)
	}.addOnFailureListener { ex ->
		ex.printStackTrace()
		cont.resumeWithException(ex)
	}.addOnCanceledListener { cont.cancel() }
}

suspend fun FirebaseFirestore.deleteOrder(orderId: String) = suspendCancellableCoroutine<Unit> { cont ->
	collection(ORDERS).document(orderId).delete().addOnSuccessListener {
		cont.resume(Unit)
	}.addOnFailureListener { ex ->
		cont.resumeWithException(ex)
	}.addOnCanceledListener {
		cont.cancel()
	}
}
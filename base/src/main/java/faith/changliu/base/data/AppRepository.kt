package faith.changliu.base.data

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Room
import faith.changliu.base.AppContext
import faith.changliu.base.R
import faith.changliu.base.data.firebase.firestore.FireDB
import faith.changliu.base.data.models.Order
import faith.changliu.base.data.models.Request
import faith.changliu.base.data.room.RoomDB
import faith.changliu.base.utils.isConnected
import faith.changliu.base.utils.toastExt
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.toast
import java.util.concurrent.Executors

object AppRepository {

	private val roomDB = Room.databaseBuilder(AppContext, RoomDB::class.java, RoomDB.name).build()
	private val executor = Executors.newSingleThreadExecutor()

	suspend fun syncAll() {
		syncOrders()
		syncRequests()
	}

	// region { Orders }

	suspend fun syncOrders() {
		if (isConnected().not()) {
			AppContext.toast("No internet")
			return
		}
		// orders
		val orders = async(CommonPool) {
			FireDB.readAllOrders()
		}.await()
		
		async(CommonPool) {
			with(roomDB.orderDao) {
				deleteAll()
				insertAll(orders)
			}
		}.await()
	}

	suspend fun insertOrder(order: Order) {
		if (isConnected()) {
			// todo: upload
			FireDB.saveOrder(order)
			// todo: add to room
			executor.execute { roomDB.orderDao.insertOrder(order) }
		} else {
			toastExt()
		}
	}

	fun getAllOrders(): LiveData<List<Order>> {
		return roomDB.orderDao.getAll()
	}
	
	suspend fun deleteOrder(orderId: String) {
		if (!isConnected()) {
			toastExt()
		} else {
			FireDB.deleteOrder(orderId)
			roomDB.orderDao.deleteOrderById(orderId)
		}
	}

	// endregion

	// region { Requests }

	suspend fun syncRequests() {
		if (isConnected().not()) {
			AppContext.toast("No internet")
			return
		}

		val requests = async(CommonPool) {
			FireDB.readAllRequests()
		}.await()

		async(CommonPool) {
			with(roomDB.requestDao) {
				deleteAll()
				insertAll(requests)
			}
		}.await()
	}

	suspend fun insertRequest(request: Request) {
		if (isConnected()) {
			// todo: upload
			FireDB.saveRequest(request)
			// todo: add to room
			executor.execute { roomDB.requestDao.insertRequest(request) }
		} else {
			toastExt()
		}
	}

	fun getAllRequests(): LiveData<List<Request>> {
		return roomDB.requestDao.getAll()
	}

	suspend fun deleteRequest(requestId: String) {
		if (!isConnected()) {
			toastExt()
		} else {
			FireDB.deleteRequest(requestId)
			roomDB.requestDao.deleteRequestById(requestId)
		}
	}

	// endregion
}
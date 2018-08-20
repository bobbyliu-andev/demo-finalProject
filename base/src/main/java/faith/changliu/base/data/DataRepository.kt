package faith.changliu.base.data

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Room
import faith.changliu.base.AppContext
import faith.changliu.base.data.models.Order
import faith.changliu.base.data.room.RoomDB
import faith.changliu.base.utils.isConnected
import java.util.concurrent.Executors

object AppRepository {

	private val roomDB = Room.databaseBuilder(AppContext, RoomDB::class.java, RoomDB.name).build()

	private val executor = Executors.newSingleThreadExecutor()

	fun insertOrder(order: Order) {
		if (isConnected()) {
			// todo: upload
			// todo: add to room
			executor.execute { roomDB.orderDao.insertOrder(order) }
		} else {
			// todo: prompt user no connection
		}
	}

	fun getAllOrders(): LiveData<List<Order>> {
		return roomDB.orderDao.getAll()
	}
}
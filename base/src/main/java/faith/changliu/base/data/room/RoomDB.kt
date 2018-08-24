package faith.changliu.base.data.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import faith.changliu.base.data.models.Order
import faith.changliu.base.data.models.Request
import faith.changliu.base.data.room.dao.OrderDao
import faith.changliu.base.data.room.dao.RequestDao

@Database(entities = [Order::class, Request::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class RoomDB : RoomDatabase() {

	companion object {
		const val name = "orda_room.db"
	}

	abstract val orderDao: OrderDao
	abstract val requestDao: RequestDao
}
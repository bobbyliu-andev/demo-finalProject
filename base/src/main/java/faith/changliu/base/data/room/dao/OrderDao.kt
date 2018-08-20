package faith.changliu.base.data.room.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import faith.changliu.base.data.models.Order

@Dao
interface OrderDao {
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insertOrder(order: Order)

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insertAll(orders: List<Order>)

	@Delete
	fun deleteOrder(order: Order)

	@Query("select * from orders where id = :id")
	fun getOrderById(id: String): Order

	@Query("select * from orders order by createdAt desc")
	fun getAll(): LiveData<List<Order>>

	@Query("delete from orders")
	fun deleteAll()

	@Query("select count(*) from orders")
	fun getCount(): Int
}
package faith.changliu.base.data.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import java.util.*

abstract class User(val id: String,
					val email: String,
					val phone: String,
					val name: String,
					val zipcode: Int,
					tokenId: Long,
					createdAt: Date,
					status: Int)

class Agent

// rating
class Traveler

@Entity(tableName = "orders")
data class Order(@PrimaryKey val id: String, // barcode
			var title: String,
			var weight: Double,
			var price: Double,
			val createdAt: Date,
			val pickedAt: Date,
			val createdBy: String,
			var description: String) {
	@Ignore
	constructor() : this("", "", 0.0, 0.0, Date(), Date(), "", "")
}

class Request(val id: String,
			  val title: String,
			  var status: Int,
			  var assignedTo: String,
			  val deadline: Date,
			  val country: String,
			  val city: String,
			  val address: String,
			  val weight: Double,
			  val volume: Double,
			  val compensation: Double,
			  val description: String,
			  val createdAt: Date,
			  val postedBy: String // agent id
)

class RequestApplication(val id: String,
			val requestId: String,
			val appliedBy: String,
			val createdAt: Date)

class Rating(val id: String,
			 var fromId: String,
			 var toId: String,
			 var rate: Double,
			 val createdAt: Date,
			 var comment: String)

object UserStatus {
	const val PENDING = 0
	const val ACTIVE = 1
	const val INACTIVE = 2
	const val BANNED = 3
}

object RequestStatus {
	const val PENDING = 0
	const val ASSIGNED =1
	const val CLOSED =2
}

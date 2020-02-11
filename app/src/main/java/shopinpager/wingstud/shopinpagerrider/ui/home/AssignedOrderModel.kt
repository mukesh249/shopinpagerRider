package shopinpager.wingstud.shopinpagerrider.ui.home

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AssignedOrderModel(
        @SerializedName("order_id")
        val orderId: String,
        @SerializedName("order_no")
        val orderNo: String,
        val amount: String,
        @SerializedName("payment_type")
        val paymentType: String,
        val address: String,
        @SerializedName("user_name")
        val username: String,
        @SerializedName("restaurant_name")
        val restaurantName: String,
        @SerializedName("restaurant_address")
        val restaurantAddress: String,
        @SerializedName("delivery_datetime")
        val deliveryDatetime: String,
        @SerializedName("user_lat")
        val userLat: String,
        @SerializedName("user_lng")
        val userLng: String,

        @SerializedName("restaurant_lat")
        val restaurantLat: String,

        @SerializedName("restaurant_lng")
        val restaurantLng: String,

        @SerializedName("user_mobile")
        val userMobile: String,

        @SerializedName("restaurant_mobile")
        val restaurantMobile: String,
        @SerializedName("pickup_code")
        val pickupCode: String,
        val image: String,
        val items: String,
        @SerializedName("order_status")
        val orderStatus: String
) : Serializable
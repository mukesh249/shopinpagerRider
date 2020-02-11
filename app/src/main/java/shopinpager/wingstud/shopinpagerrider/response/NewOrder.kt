package shopinpager.wingstud.shopinpagerrider.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class NewOrder(
        @SerializedName("order_id")
        val orderId: String,

        @SerializedName("seller_id")
        val seller_id: String,

        @SerializedName("order_no")
        val orderNo: String,

        @SerializedName("amount")
        val amount: String,

        @SerializedName("payment_mode")
        val paymentType: String,

        @SerializedName("user_mobile")
        val userMobile: String,

        @SerializedName("user_address")
        val address: String,

        @SerializedName("user_name")
        val username: String,

        @SerializedName("seller_name")
        val seller_name: String,

        @SerializedName("seller_address")
        val seller_address: String,

        @SerializedName("delivery_date")
        val deliveryDatetime: String,
        @SerializedName("user_lat")
        val userLat: String,
        @SerializedName("user_long")
        val userLng: String,
        @SerializedName("seller_lat")
        val seller_lat: String,
        @SerializedName("seller_long")
        val seller_long: String,
        @SerializedName("count")
        val count: String
) : Serializable {

}
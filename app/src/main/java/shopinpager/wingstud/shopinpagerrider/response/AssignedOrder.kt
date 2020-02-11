package shopinpager.wingstud.shopinpagerrider.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AssignedOrder<T>(
        @SerializedName("id")
        val id: String,
        @SerializedName("order_id")
        val order_id: String,
        @SerializedName("amount")
        val amount: String,
        @SerializedName("payment_mode")
        val payment_mode: String,
        @SerializedName("delivery_date")
        val delivery_date: String,
        @SerializedName("delivery_time")
        val delivery_time: String,
        @SerializedName("seller_name")
        val seller_name: String,
        @SerializedName("pickup_lat")
        val pickup_lat: String,
        @SerializedName("pickup_long")
        val pickup_long: String,
        @SerializedName("seller_lat")
        val seller_lat: String,
        @SerializedName("seller_long")
        val seller_long: String,
        @SerializedName("user_mobile")
        val user_mobile: String,
        @SerializedName("pickup_name")
        val pickup_name: String,
        @SerializedName("pickup_address")
        val pickup_address: String,
        @SerializedName("seller_id")
        val seller_id: String,
        @SerializedName("type")
        val type: String,
        @SerializedName("pickup_mobile")
        val pickup_mobile: String,
        @SerializedName("status")
        val status: String,
        @SerializedName("seller_address")
        val seller_address: String ,
        val data: List<T>?
):Serializable
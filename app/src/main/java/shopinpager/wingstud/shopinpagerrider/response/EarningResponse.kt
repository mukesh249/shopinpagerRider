package shopinpager.wingstud.shopinpagerrider.response

import com.google.gson.annotations.SerializedName

class EarningResponse<T> (
        val status_code : Int,
        val message : String,
        val data : List<T>,
        val error_message : String,
        val total_earning : String
)
class DataEarning(
        val id : String,
        val order_id : String,
        val payment_mode : String,
        val delivery_date : String,
        val delivery_time : String,
        val seller_name : String,
        val seller_lat : String,
        val seller_address : String,
        val seller_long : String,
        val user_mobile : String,
        val user_name : String,
        val earning : String,
        val distance : String,
        val bonus : String,
        @SerializedName("pickup_name")
        val pickup_name: String,
        @SerializedName("pickup_address")
        val pickup_address: String,
        @SerializedName("pickup_lat")
        val pickup_lat: String,
        @SerializedName("pickup_long")
        val pickup_long: String
)
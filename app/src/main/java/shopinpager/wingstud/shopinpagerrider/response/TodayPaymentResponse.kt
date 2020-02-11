package shopinpager.wingstud.shopinpagerrider.response

import com.google.gson.annotations.SerializedName

data class TodayPaymentResponse(
        val `data`: List<Data>,
        val error_message: String,
        val message: String,
        val status_code: Int
) {
    data class Data(
            val delivery_date: String,
            val delivery_time: String,
            val id: Int,
            val item_details: List<ItemDetail>,
            val order_id: String,
            val payment_mode: String,
            val seller_address: String,
            val seller_id: Int,
            val seller_lat: String,
            val seller_long: String,
            val seller_name: String,
            @SerializedName("pickup_name")
            val pickup_name: String,
            @SerializedName("pickup_address")
            val pickup_address: String,
            @SerializedName("pickup_lat")
            val pickup_lat: String,
            @SerializedName("pickup_long")
            val pickup_long: String,
            val user_mobile: String,
            val commission: Double,
            val payment_amount: Double,
            val amount: Double
    ) {
        data class ItemDetail(
                val product_image: String,
                val product_name: String,
                val qty: Int
        )
    }
}
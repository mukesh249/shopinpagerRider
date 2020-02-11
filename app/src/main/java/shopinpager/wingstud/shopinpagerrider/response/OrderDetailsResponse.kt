package shopinpager.wingstud.shopinpagerrider.response

data class OrderDetailsResponse(
        val `data`: List<Data>,
        val error_message: String,
        val img_path: String,
        val message: String,
        val order_info: OrderInfo,
        val status_code: Int,
        val item_count: Int,
        val return_count: Int,
        val name: String,
        val mobile: String,
        val shipping_charge: String,
        val wallet_amount: String,
        val total_amount: String,
        val address: String
)
data class Data(
        val image: String,
        val name: String,
        val price: Double,
        val qty: Int,
        val weight: String,
        val return_status: Int
//        val exchange_status: Int
)
data class OrderInfo(
        val address_2: String,
        val city: String,
        val delivery_time: String,
        val f_name: String,
        val house: String,
        val id: Int,
        val l_name: String,
        val mobile: String,
        val name: String,
        val order_id: String,
        val payment_mode: String,
        val pincode: String,
        val seller_lat: String,
        val seller_long: String,
        val state: String,
        val street: String,
        val user_lat: String,
        val user_long: String
)
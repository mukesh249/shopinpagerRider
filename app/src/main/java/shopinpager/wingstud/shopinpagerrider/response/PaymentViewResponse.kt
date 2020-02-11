package shopinpager.wingstud.shopinpagerrider.response

data class PaymentViewResponse(

        val `data`: List<Data>,
        val img_path: String,
        val message: String,
        val order_amount: Double,
        val status: Int,
        val slip: String,
        val base_income: Double,
        val bonus_amount: Double,
        val transaction_id: String

) {
    data class Data(

        val bonus: Int,
        val date: String,
        val distance: String,
        val id: String,
        val job_id: String,
        val my_payment: String,
        val order_amount: String,
        val order_id: String,
        val payment_mode: String,
        val seller_id: String,
        val type: String
    )
}
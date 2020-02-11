package shopinpager.wingstud.shopinpagerrider.response

data class PaidResponse(
        val `data`: List<Data>,
        val message: String,
        val status_code: Int
) {
    data class Data(

            val amount_per_km: Int,
            val base_income: Int,
            val bonus: Int,
            val cod_total: Double,
            val from_date: String,
            val payable: Double,
            val payment_id: String,
            val to_date: String,
            val total: Double,
            val total_count: Int,
            val total_days: Int,
            val total_distance: Double

    )
}
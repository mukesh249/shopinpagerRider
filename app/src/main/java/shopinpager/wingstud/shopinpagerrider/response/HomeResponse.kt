package shopinpager.wingstud.shopinpagerrider.response;

import com.google.gson.annotations.SerializedName

data class HomeResponse(
        val status_code: Int,
        val status: Int,
        val login_time: String?,
        val message: String?,
        val totay_order_count: String?,
        val totay_payment: String?,
        val cod_amount: String?,
        @SerializedName("current_completed_order")
        val last_ride: LastRide
)

class LastRide(
        val total_amount: String,
        val estimated_distance: String?
)


package shopinpager.wingstud.shopinpagerrider.rest.requests

class OrderListResponse<T>(
        val status_code : Int,
        val message : String?,
        val error_message : String?,
        val data: List<T>?
)
class HistoryOrder (
        val user_id : Int,
        val order_id : String?,
        val total_amount : String?,
        val payment_mode : String?,
        val delivery_type : String?,
        val delivery_time : String?,
        val delivery_date : String?,
        val shipped_date : String?,
        val from_address : String?,
        val to_address : String?
)

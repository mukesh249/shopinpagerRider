package shopinpager.wingstud.shopinpagerrider.rest.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import shopinpager.wingstud.shopinpagerrider.response.*
import shopinpager.wingstud.shopinpagerrider.rest.requests.LoginRequest
import shopinpager.wingstud.shopinpagerrider.rest.requests.OrderListResponse
import shopinpager.wingstud.shopinpagerrider.rest.responses.LoginResponse
import shopinpager.wingstud.shopinpagerrider.ui.cod.CodResponse

interface ApiInterface {


    @POST("r-api-login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @POST("rider-order-details")
    fun orderDetails(@Body param: HashMap<String, String>): Call<OrderDetailsResponse>

    @POST("cod-list")
    fun codList(@Body body: HashMap<String, String>): Call<CodResponse>

    //
    @POST("today-payment")
    fun todayPayment(@Body hashmap: HashMap<String, String>): Call<TodayPaymentResponse>

    @POST("r-forgot-password")
    fun forgotPassword(@Body body: HashMap<String, String>): Call<CommanResponse>
    //
//    @POST("change_password")
//    fun changePassword(@Body data: HashMap<String, String>): Call<BaseResponse>

    @POST("rider-logout")
    fun logout(@Body hashmap: HashMap<String, String>): Call<SessionResponse>
//
    @POST("change-status")
    fun changeOnlineStatus(@Body data: HashMap<String, String>): Call<ChangeStatusResponse>

    //    @POST("new_orders")
//    fun newOrders(@Body delivery_boy_id: String, auth_key: String): Call<OrderListResponse<NewOrder>>
//
//    @POST("assigned_orders")
//    fun assignedOrder(@Body riderRequest: RiderRequest): Call<OrderListResponse<AssignedOrder>>
//
    @POST("dashboard")
    fun dashBoadData(@Body hashmap: HashMap<String, String>): Call<HomeBoradResponse>

    //
    @POST("get-order-history")
    fun completed(@Body hashmap: HashMap<String, String>): Call<OrderHistoryResponse>

    //
//    @POST("rejected_orders")
//    fun rejectedOrders(@Body riderRequest: RiderRequest): Call<OrderListResponse<HistoryOrder>>
//
//    //   http://ss.roketvending.com/demonew/hudibaba/Hudibaba_delivery_boy/delivery_boy_device_fcm
//
    @POST("delivery_boy_device_fcm")
    fun updateGCMToken(@Body hashmap: HashMap<String, String>): Call<SessionResponse>

    //
//    @POST("food_delivery_code")
//    fun foodDeliveryCode(@Body hashmap: HashMap<String, String>): Call<BaseResponse>
//
//
    @POST("accept-order")
    fun acceptNewOrder(@Body hashmap: HashMap<String, String>): Call<AcceptResponse>

    @POST("assigned-order")
    fun assignedorderList(@Body hashmap: HashMap<String, String>): Call<OrderListResponse<AssignedOrder<ItemDetails>>>

    //
    @POST("deliver-to-customer")
    fun completeDelivery(@Body hashmap: HashMap<String, String>): Call<CommanResponse>

    //
//    @POST("status")
//    fun checkOnlineStatus(@Body hashmap: HashMap<String, String>): Call<StatusResponse>
//
    @POST("your-earning")
    fun earningDetails(@Body hashmap: HashMap<String, String>): Call<EarningResponse<DataEarning>>

    //
//    @POST("todays_orders")
//    fun todayOrder(@Body hashmap: HashMap<String, String>): Call<TodayOrderResponse>
//
    @POST("get-paid-payment")
    fun paidPayment(@Body hashmap: HashMap<String, String>): Call<PaidResponse>

    //
    @POST("get-paid-payment-details")
    fun paidPaymentDetails(@Body hashmap: HashMap<String, String>): Call<PaymentViewResponse>

    //
    @POST("order-list")
    fun orderlist(@Body hashmap: HashMap<String, String>): Call<PaymentViewResponse>

    //
//    @POST("order_details")
//    fun orderDetails(@Body hashmap: HashMap<String, String>): Call<JsonObject>
//
    @POST("get-unpaid-payment")
    fun unpaidPayment(@Body hashmap: HashMap<String, String>): Call<PaidResponse>

//    @POST("todays_orders")
//    fun todayPayment(@Body hashmap: HashMap<String, String>): Call<TodayOrderResponse>
//
//    @POST("free_hours")
//    fun freeHours(@Body hashmap: HashMap<String, String>): Call<BaseResponse>


    @POST("app_session_status")
    fun session(@Body hashmap: HashMap<String, String>): Call<SessionResponse>


}
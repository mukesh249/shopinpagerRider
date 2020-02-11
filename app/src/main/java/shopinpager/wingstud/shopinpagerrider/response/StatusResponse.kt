package shopinpager.wingstud.shopinpagerrider.response

import com.google.gson.annotations.SerializedName

class StatusResponse(
    val status_code:String,
    val status:String,
    val message:String,
    @SerializedName("online_status")
    val onlineStatus : String
)
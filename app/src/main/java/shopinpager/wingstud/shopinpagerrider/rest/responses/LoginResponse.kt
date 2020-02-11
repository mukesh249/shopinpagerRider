package shopinpager.wingstud.shopinpagerrider.rest.responses

import com.google.gson.annotations.SerializedName
import shopinpager.wingstud.shopinpagerrider.account.UserAccount

class LoginResponse(
        val status_code: Int,
        val message: String?,
        val error_message: String?,
        val img_path: String?,
        val data: DataO
)class DataO (
        val username: String,
        val device_token: String?,
        @SerializedName("id")
        val deliverBoyId: String?,
        val mobile: String?,
        val employee_no: String?,
        val email: String?,
        val profile_image: String?,
        val city_id: String?
)
{
    fun toAccount(deviceId: String) = UserAccount(device_token!!, username, mobile!!, profile_image!!, city_id!!, employee_no!!, deliverBoyId!!, email!!, deviceId)
}


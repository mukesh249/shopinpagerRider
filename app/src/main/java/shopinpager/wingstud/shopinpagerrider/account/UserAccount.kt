package shopinpager.wingstud.shopinpagerrider.account
import com.google.gson.annotations.SerializedName

class UserAccount(
        @SerializedName("device_token")
        val device_token: String,
        val username: String,
        val mobile: String,
        val profile_image: String,
        @SerializedName("city_id")
        val cityId: String,
        @SerializedName("employee_no")
        val employeeNo: String,
        @SerializedName("delivery_boy_id")
        val deliverBoyId: String,
        val email:String,
        val deviceId:String
)
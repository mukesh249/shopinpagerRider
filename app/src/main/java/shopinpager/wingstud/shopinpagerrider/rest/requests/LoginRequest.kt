package shopinpager.wingstud.shopinpagerrider.rest.requests

import com.google.gson.annotations.SerializedName

class LoginRequest(
        val email: String,
        val password: String,
        @SerializedName("device_token")
        val device_token: String?=null
)
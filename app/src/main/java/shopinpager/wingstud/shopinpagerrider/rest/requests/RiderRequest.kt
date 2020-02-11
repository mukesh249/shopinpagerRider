package shopinpager.wingstud.shopinpagerrider.rest.requests

import com.google.gson.annotations.SerializedName

class RiderRequest(
        @SerializedName("user_id")
        val user_id: String
)
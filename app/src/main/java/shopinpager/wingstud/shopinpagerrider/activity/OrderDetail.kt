package shopinpager.wingstud.shopinpagerrider.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_order_detail.*
import kotlinx.android.synthetic.main.activity_payment_report_view_detail.*
import kotlinx.android.synthetic.main.activity_payment_report_view_detail.back_iv
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import shopinpager.wingstud.shopinpagerrider.R
import shopinpager.wingstud.shopinpagerrider.account.AccountManager
import shopinpager.wingstud.shopinpagerrider.rest.network.RestClient

class OrderDetail : AppCompatActivity() {

    private val session by lazy {
        AccountManager.getUserAccount()!!
    }
    var order_id : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)

        back_iv.setOnClickListener { onBackPressed() }
        if (intent.getStringExtra("order_id")!=null) {
            order_id = intent.getStringExtra("order_id")
        }
//        orderDetail()
    }

//    private fun orderDetail(){
//        val hashMap : HashMap<String,String> = HashMap()
//        hashMap["order_id"] = order_id
//        hashMap["auth_key"] = session.authKey
//        hashMap["delivery_boy_id"] = session.deliveryBoyId
//        RestClient.getInstance().apiInterface.orderDetails(hashMap).enqueue(object :
//            Callback<JsonObject> {
//            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
//                Snackbar.make(root, "Network Error", Snackbar.LENGTH_LONG).show()
//            }
//
//            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
//                if (response.isSuccessful){
//                    val body : String = response.body().toString();
//                    val jsonObj = JSONObject(body)
//                    Log.i("fadfasfdsaf",jsonObj.toString())
//
//                    if (jsonObj.optString("status")=="success"){
//                        val dataObj = jsonObj.optJSONArray("data")?.optJSONObject(0)
//                        Glide.with(this@OrderDetail).load(dataObj?.optString("image")).into(orderImagev)
//                        retroNameTv.text = dataObj?.optString("restaurant_name")
//                        deliveryCodeTv.text = dataObj?.optString("pickup_code")
//                        no_of_itemTv.text = dataObj?.optString("items")
//                        username_mobile.text = "${dataObj?.optString("user_name")} / ${dataObj?.optString("user_mobile")}"
//                        addressTv.text = dataObj?.optString("user_address")
//                        grandTotalTv.text = dataObj?.optString("grand_total")
//                    }
//                }
//            }
//        })
//    }
}

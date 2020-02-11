package shopinpager.wingstud.shopinpagerrider.activity

import android.app.ProgressDialog
import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import shopinpager.wingstud.shopinpagerrider.R
import shopinpager.wingstud.shopinpagerrider.databinding.ActivityForgotPasswordBinding
import shopinpager.wingstud.shopinpagerrider.response.CommanResponse
import shopinpager.wingstud.shopinpagerrider.rest.network.RestClient
import shopinpager.wingstud.shopinpagerrider.util.toast

class ForgotPassword : AppCompatActivity() {


    lateinit var binding : ActivityForgotPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_password)

        binding.btnSubmit.setOnClickListener { forgotPassword() }
    }

    fun forgotPassword(){
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("loading")
        progressDialog.setCancelable(false)
        progressDialog.show()
        val hashMap: HashMap<String, String> = HashMap()
        hashMap["mobile"] = binding.etMobileNo.text.toString();

        RestClient.getInstance().apiInterface.forgotPassword(hashMap).enqueue(object : Callback<CommanResponse> {
            override fun onFailure(call: Call<CommanResponse>, t: Throwable) {
//                dialog.dismiss()
                toast("Network connection error.")
                Log.e(ContentValues.TAG, "onFailure", t);
            }

            override fun onResponse(call: Call<CommanResponse>, response: Response<CommanResponse>) {
                progressDialog.dismiss()
                if (response.isSuccessful ) {
                    val body = response.body()!!
                    if(response.body()!!.status_code == 1){
                        toast( response.body()!!.message!!)
                        finish()
                    }

                } else {
//                    if (response.body() != null && response.body()!!.message != null)
                    toast( response.body()!!.message!!)
//                    else context?.toast( response.body()!!.message!!)
                }
            }
        })
    }
}

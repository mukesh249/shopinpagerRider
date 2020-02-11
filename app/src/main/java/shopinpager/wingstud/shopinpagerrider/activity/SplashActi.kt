package shopinpager.wingstud.shopinpagerrider.activity;

import android.content.Intent
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import shopinpager.wingstud.shopinpagerrider.App

import shopinpager.wingstud.shopinpagerrider.R;
import shopinpager.wingstud.shopinpagerrider.account.AccountManager
import shopinpager.wingstud.shopinpagerrider.response.SessionResponse
import shopinpager.wingstud.shopinpagerrider.rest.network.RestClient
import shopinpager.wingstud.shopinpagerrider.ui.login.LoginKt
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

public class SplashActi : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        thread()
    }

    private fun thread() {
        Thread(Runnable() {
            try {
                Thread.sleep(2000);
                val account = AccountManager.getUserAccount()

                if (account == null) {
                    startActivity(Intent(this, LoginKt::class.java))
                    finish()
                } else {
                    startActivity(Intent(this@SplashActi, MainActivity::class.java))
                    finish()
//                    checkSession()
                }

            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

        }).start();
    }

    fun checkSession() {
        val account = AccountManager.getUserAccount()
        val hashMap = HashMap<String, String>()
        hashMap["delivery_boy_id"] = account!!.deliverBoyId
        hashMap["auth_key"] = account.device_token
        hashMap["device_id"] = account.deviceId


        RestClient.getInstance().apiInterface.session(hashMap).enqueue(object : Callback<SessionResponse> {
            override fun onFailure(call: Call<SessionResponse>, t: Throwable) {
                AlertDialog.Builder(this@SplashActi)
                        .setMessage("It seems Internet connection is not available. Please check your internet connection and try again")
                        .setPositiveButton("Retry") { dialogInterface, i ->
                            dialogInterface.dismiss()
                            checkSession()
                        }
                        .setNegativeButton("Canecl") { dialogInterface, i ->
                            dialogInterface.dismiss()
                            finish()
                        }.show()
            }

            override fun onResponse(call: Call<SessionResponse>, response: Response<SessionResponse>) {

                if (response.isSuccessful && response.body()!!.appStatus=="inactive"){
                    startActivity(Intent(this@SplashActi, UnavailableActivity::class.java))
                    finish()
                    return
                }
                if (response.isSuccessful &&   response.body()!!.status == "success"){
                    startActivity(Intent(this@SplashActi, LoginKt::class.java))
                    finish()
                    return
                } else {
                    AlertDialog.Builder(this@SplashActi)
                            .setMessage("You have loged in from different Device. Please login again.")
                            .setPositiveButton("OK") { dialog, _ ->
                                dialog.dismiss()
                                App.get().logout()
                                finish()
                            }.show()
                }
            }
        })
    }

}

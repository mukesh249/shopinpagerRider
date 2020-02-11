package shopinpager.wingstud.shopinpagerrider.firebase

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.Intent.*
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import shopinpager.wingstud.shopinpagerrider.App
import shopinpager.wingstud.shopinpagerrider.account.AccountManager
import shopinpager.wingstud.shopinpagerrider.extensions.defaultSharedPreferences
import shopinpager.wingstud.shopinpagerrider.extensions.putString
import shopinpager.wingstud.shopinpagerrider.response.NewOrder
import shopinpager.wingstud.shopinpagerrider.ui.home.HomeFragment
import shopinpager.wingstud.shopinpagerrider.ui.newrequest.NewRequest

class MyFirebaseMessagingService : FirebaseMessagingService() {

    companion object {
        private val TAG = "MessagingService"
    }


    private val session by lazy { AccountManager.getUserAccount()!! }

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        App.get().defaultSharedPreferences.putString("Token", p0)
        Log.d(HomeFragment.TAG, "Firebase_Token " + p0);
    }

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)

//           toast("New pickup request")

        Log.d(TAG, "NEW MESSAGE  " + p0);

        if (p0 != null) {
            val order = p0.data
            Log.d(TAG, "DATA " + order)
            if (order["type"] == "rider_order_request") {

//                EventBus.getDefault().post(CounterStatusEvent())

                val amount = order["amount"]!!
                val user_address = order["address"]!!
                val seller_id = order["seller_id"]!!
                val user_mobile = order["mobile"]!!
                val payment_mode = order["payment_mode"]!!
                val delivery_date = order["delivery_date"]!!
                val user_lat = order["user_lat"]!!
                val user_lng = order["user_long"]!!
                val username = order["username"]!!

//                val user_id = order["id"]!!

                val seller_lat = order["seller_lat"]!!
                val seller_long = order["seller_long"]!!
                val seller_name = order["seller_name"]!!

                val seller_address = order["seller_address"]!!
                val order_id = order["id"]!!
                val order_no = order["order_id"]!!
                val count = order["count"]!!


                val newOrder = NewOrder(
                        order_id,
                        seller_id,
                        order_no,
                        amount,
                        payment_mode,
                        user_mobile,
                        user_address,
                        username,
                        seller_name,
                        seller_address,
                        delivery_date,
                        user_lat,
                        user_lng,
                        seller_lat,
                        seller_long,
                        count
                )

                val intent = Intent(applicationContext, NewRequest::class.java)
                intent.putExtra("newOrder", newOrder)
                intent.flags = FLAG_ACTIVITY_CLEAR_TOP or FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)

//                startActivity(Intent(applicationContext, NewRequest::class.java)
//                        .apply {
//                            flags = FLAG_ACTIVITY_NEW_TASK
//                            putExtra("newOrder", newOrder)
//
//                        })

//            } else if (order["type"] == "2") {
//                val channelId =
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
//                            createNotificationChannel("my_service", "My Background Service") else ""
//
//                val message = order["message"]
//                val builder = NotificationCompat.Builder(this, channelId)
//                        .setContentTitle("Grocito")
//                        .setContentText(message)
//                        .setStyle(
//                                NotificationCompat.BigTextStyle()
//                                        .bigText(message)
//                        )
//                        .setSmallIcon(R.drawable.logo)
//                val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//                service.notify(1, builder.build())
//            } else if (order["type"] == "3") {
//                val intent = Intent(this, UnavailableActivity::class.java)
//                intent.flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK
//
//                startActivity(intent)
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId: String, channelName: String): String {
        val chan = NotificationChannel(
                channelId,
                channelName, NotificationManager.IMPORTANCE_HIGH
        )
        chan.lightColor = Color.BLUE
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(chan)
        return channelId
    }
}
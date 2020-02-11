package shopinpager.wingstud.shopinpagerrider.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.google.android.gms.common.util.CollectionUtils.setOf
import shopinpager.wingstud.shopinpagerrider.App
import shopinpager.wingstud.shopinpagerrider.R
import shopinpager.wingstud.shopinpagerrider.account.AccountManager
import shopinpager.wingstud.shopinpagerrider.ui.cod.CodLisfFragment
import shopinpager.wingstud.shopinpagerrider.ui.earning.EarningFragment
import shopinpager.wingstud.shopinpagerrider.ui.home.HomeFragment
import shopinpager.wingstud.shopinpagerrider.ui.orderhistory.OrderHistory
import shopinpager.wingstud.shopinpagerrider.ui.paymentreport.PaymentReport
import shopinpager.wingstud.shopinpagerrider.ui.profile.ProfileFragment
import shopinpager.wingstud.shopinpagerrider.ui.todayorders.TodayOrderFragment
import shopinpager.wingstud.shopinpagerrider.ui.todaypayment.TodayPayment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*
import org.jetbrains.anko.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import shopinpager.wingstud.shopinpagerrider.response.SessionResponse
import shopinpager.wingstud.shopinpagerrider.rest.network.RestClient

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    private val session by lazy { AccountManager.getUserAccount()!! }

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val view : View = nav_view.getHeaderView(0)
        view.txtUsername.text = session.username
        view.emailTV.text = session.employeeNo
        Glide.with(this)
                .load( App.get().defaultSharedPreferences.getString("ImagePath","")+session.profile_image)
                .centerCrop()
                .into(view.imvUserImage)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_home,
                R.id.nav_profile,
                R.id.nav_earning,
                R.id.nav_cod,
                R.id.nav_today_pay,
                R.id.nav_today_order,
                R.id.nav_history,
                R.id.nav_logout), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        nav_view.setNavigationItemSelectedListener { menuItem ->
            val currentFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
            when (menuItem.itemId) {
                R.id.nav_profile -> {
                    if (currentFragment !is ProfileFragment) {
//                        toolbar.title = "Profile"
                        supportFragmentManager
                                .beginTransaction()
                                .add(R.id.nav_host_fragment, ProfileFragment()).addToBackStack(null)
                                .commit()
                    }
                }
                R.id.nav_home ->{
                    if (currentFragment !is HomeFragment) {
//                        toolbar.title = "Home"
                        supportFragmentManager
                                .beginTransaction()
                                .add(R.id.nav_host_fragment, HomeFragment()).addToBackStack(null)
                                .commit()
                    }
                }
                R.id.nav_earning ->{
                    if (currentFragment !is EarningFragment) {
//                        toolbar.title = "Your Earning"
                        supportFragmentManager
                                .beginTransaction()
                                .replace(R.id.nav_host_fragment, EarningFragment()).addToBackStack(null)
                                .commit()
                    }
                }
                R.id.nav_cod ->{
                    if (currentFragment !is CodLisfFragment) {
//                        toolbar.title = "Cod List"
                        supportFragmentManager
                                .beginTransaction()
                                .replace(R.id.nav_host_fragment, CodLisfFragment()).addToBackStack(null)
                                .commit()
                    }
                }
                R.id.nav_today_order ->{
                    if (currentFragment !is TodayOrderFragment) {
//                        toolbar.title = "Today Order's"
                        supportFragmentManager
                                .beginTransaction()
                                .replace(R.id.nav_host_fragment, TodayOrderFragment()).addToBackStack(null)
                                .commit()
                    }
                }
                R.id.nav_today_pay ->{
                    if (currentFragment !is TodayPayment) {
//                        toolbar.title = "Today payment's"
                        supportFragmentManager
                                .beginTransaction()
                                .replace(R.id.nav_host_fragment, TodayPayment()).addToBackStack(null)
                                .commit()
                    }
                }
                R.id.nav_history ->{
                    startActivity(Intent(this,OrderHistory::class.java))
                }
                R.id.nav_payment_rep ->{
                    startActivity(Intent(this,PaymentReport::class.java))
                }
                R.id.nav_logout -> {
//                    menuItem.isChecked = true
//                    val ft = getSupportFragmentManager().beginTransaction()
//                    val newFragment = LogoutDialogFragment.newInstance("pass content here")
//                    newFragment.show(ft, "dialog")
                    alert("Are you sure you want to logout?", null) {
                        yesButton {
                            it.dismiss()
                            logoutMethod()
//                            App.get().logout()
//                            finish()
                        }
                        noButton { it.dismiss() }
                    }.show()
                }
            }
            drawer_layout.closeDrawer(Gravity.START)
            true
        }
    }
    private fun logoutMethod() {
        val hashMap: HashMap<String, String> = HashMap()
        hashMap["user_id"] = session.deliverBoyId

        RestClient.getInstance().apiInterface.logout(hashMap).enqueue(object : Callback<SessionResponse> {
            override fun onFailure(call: Call<SessionResponse>, t: Throwable) {

            }

            override fun onResponse(call: Call<SessionResponse>, response: Response<SessionResponse>) {
                if (response.isSuccessful) {

                    val body = response.body()!!
                    Log.i("logout_res",body.toString());
                    if (body.status.equals("1")) {
                        App.get().logout()
                        finish()
                    }
                    Toast.makeText(this@MainActivity, response.body()!!.message, Toast.LENGTH_LONG).show()

                }

            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
//        val currentFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
//        if (currentFragment is HomeFragment) {
//                        toolbar.title = "Home"
//        }
    }
    //    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.main, menu)
//        return true
//    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}

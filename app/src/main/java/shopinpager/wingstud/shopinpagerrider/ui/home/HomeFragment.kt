package shopinpager.wingstud.shopinpagerrider.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.geofire.GeoFire
import com.firebase.geofire.GeoLocation
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.FirebaseDatabase
import com.tbruyelle.rxpermissions2.RxPermissions
import shopinpager.wingstud.shopinpagerrider.App
import shopinpager.wingstud.shopinpagerrider.account.AccountManager
import shopinpager.wingstud.shopinpagerrider.activity.DeliveryProductDetail
import shopinpager.wingstud.shopinpagerrider.activity.LiveTrackActivity
import shopinpager.wingstud.shopinpagerrider.databinding.FragmentHomeBinding
import shopinpager.wingstud.shopinpagerrider.extensions.defaultSharedPreferences
import shopinpager.wingstud.shopinpagerrider.extensions.putInt
import shopinpager.wingstud.shopinpagerrider.rest.network.RestClient
import shopinpager.wingstud.shopinpagerrider.rest.requests.OrderListResponse
import shopinpager.wingstud.shopinpagerrider.ui.cod.CodLisfFragment
import shopinpager.wingstud.shopinpagerrider.ui.todayorders.TodayOrderFragment
import shopinpager.wingstud.shopinpagerrider.ui.todaypayment.TodayPayment
import shopinpager.wingstud.shopinpagerrider.util.toast
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import shopinpager.wingstud.shopinpagerrider.R
import shopinpager.wingstud.shopinpagerrider.response.*
import shopinpager.wingstud.shopinpagerrider.Services.LocationService
import shopinpager.wingstud.shopinpagerrider.databinding.OrderAlertBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class HomeFragment : Fragment(), AssignedOrderAdapter.OrderTrackListener {


    override fun onSellerTrack(assignedOrder: AssignedOrder<ItemDetails>) {
        startActivity(Intent(activity, LiveTrackActivity::class.java)
                .apply {
                    putExtra("assignedOrder", assignedOrder)
                    putExtra("userTrack", false)
                })
    }

    override fun onUserTrack(assignedOrder: AssignedOrder<ItemDetails>) {
        startActivity(Intent(activity, LiveTrackActivity::class.java)
                .apply {
                    putExtra("assignedOrder", assignedOrder)
                    putExtra("userTrack", true)
                })
    }

    override fun onOrderDetail(assignedOrder: AssignedOrder<ItemDetails>) {
        startActivity(Intent(activity, DeliveryProductDetail::class.java).apply {
            putExtra("orderDetail", assignedOrder)
        })
    }

    val ref = FirebaseDatabase.getInstance().getReference("shopinpagerrider")
    private val geoFire: GeoFire by lazy { GeoFire(ref) }

    //private fun
    companion object {
        val TAG = "HomeFragment"
        lateinit var mInstance: HomeFragment
    }

    //    private lateinit var homeViewModel: HomeViewModel
    private var forceRefresh = false
    private var isRefreshing = false
    private val session by lazy { AccountManager.getUserAccount()!! }
    lateinit var binding: FragmentHomeBinding

    private val assignedOrderAdapter: AssignedOrderAdapter by lazy {
        AssignedOrderAdapter().apply { this.trackListener = this@HomeFragment }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Home"
        checkPermissions()
//        getToken()

        mInstance = this
        binding.statusSw.setOnClickListener {
            //            getDeviceLocation()
            changeOnlineStatus()
        }
        getDeviceLocation()
        binding.relEmptyWL.visibility = View.VISIBLE
        binding.assignedOrderRv.layoutManager = LinearLayoutManager(activity)
        binding.assignedOrderRv.adapter = assignedOrderAdapter
        loadAssignedOrder()

        binding.swipeView.setOnRefreshListener {
            if (!isRefreshing) {
                forceRefresh = true
                binding.swipeView.isRefreshing = true
                dashBoardData()
                loadAssignedOrder()
            }
        }
        binding.todayPayLL.setOnClickListener {
            swapFragment(TodayPayment())
//            childFragmentManager
//                    .beginTransaction()
//                    .replace(R.id.nav_host_fragment, TodayPayment()).addToBackStack(null)
//                    .commit()
        }
        binding.todayorLL.setOnClickListener {
            swapFragment(TodayOrderFragment())
//            childFragmentManager
//                    .beginTransaction()
//                    .replace(R.id.nav_host_fragment, TodayOrderFragment()).addToBackStack(null)
//                    .commit()
        }
        binding.codLL.setOnClickListener {
            swapFragment(CodLisfFragment())
//            childFragmentManager
//                    .beginTransaction()
//                    .replace(R.id.nav_host_fragment, CodLisfFragment()).addToBackStack(null)
//                    .commit()
        }
        return binding.getRoot()
    }

    private fun swapFragment(fragment: Fragment) {
        val fragmentTransaction = fragmentManager!!.beginTransaction();
        fragmentTransaction.add(R.id.nav_host_fragment, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    override fun onStart() {
        super.onStart()
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Home"
        loadAssignedOrder()
    }

    private var locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            Log.d(TAG, "onLocationChanged: $location")
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
            Log.d(TAG, "onStatusChanged: $provider $status $extras")
        }

        override fun onProviderEnabled(provider: String) {
            Log.d(TAG, "onProviderEnabled: $provider")
        }

        override fun onProviderDisabled(provider: String) {
            Log.d(TAG, "onProviderDisabled: $provider")
        }
    }

    private val dateFormatter by lazy { SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()) }

    private fun getDeviceLocation() {
        try {
            val locationResult = LocationServices.getFusedLocationProviderClient(requireActivity()).lastLocation
            locationResult.addOnSuccessListener { location ->

                if (locationResult != null) {
                    geoFire.setLocation(
                            session.deliverBoyId, GeoLocation(location.latitude, location.longitude)
                    ) { key, error -> Log.d("driver_location_update", "Location update on Firebase") }

                    val timestamp = dateFormatter.format(System.currentTimeMillis())
                    ref.child(session.deliverBoyId).child("timestamp").setValue(timestamp);
                }
                if (location != null) {

                    val currentLatLng = LatLng(location.latitude, location.longitude)
//                    val status = if (status_sw.isChecked)
//                    geoFire.setLocation(session.deliverBoyId, GeoLocation(location.latitude, location.longitude))
                    val timestamp = dateFormatter.format(System.currentTimeMillis())
                    ref.child(session.deliverBoyId).child("timestamp").setValue(timestamp)

                } else {

                    val locationManager =
                            context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            0,
                            0f,
                            locationListener
                    )
                    getDeviceLocation()
                }

            }

        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message)
        }
    }

    public fun getInstance(): HomeFragment {
        return mInstance
    }

    private fun changeOnlineStatus() {
        Log.d("HomeFragment", "changeOnlineStatus")
        val hashMap = HashMap<String, String>()
        hashMap["user_id"] = session.deliverBoyId

        RestClient.getInstance().apiInterface.changeOnlineStatus(hashMap)
                .enqueue(object : Callback<ChangeStatusResponse> {
                    override fun onFailure(call: Call<ChangeStatusResponse>, t: Throwable) {
                        context?.toast("Opps! Network unavailable")
                        binding.statusSw.isEnabled = !status_sw.isEnabled
                    }

                    override fun onResponse(
                            call: Call<ChangeStatusResponse>,
                            response: Response<ChangeStatusResponse>
                    ) {
                        if (response.isSuccessful && response.body()!!.status_code == 1) {
                            App.get().defaultSharedPreferences.putInt("status", response.body()!!.status)
                            if (response.body()!!.status == 1) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    context?.startForegroundService(Intent(
                                            context,
                                            LocationService::class.java
                                    ))
                                    context?.startForegroundService(
                                            Intent(
                                                    context,
                                                    LocationService::class.java
                                            )
                                    )
                                } else {
                                    context?.startService(
                                            Intent(
                                                    context,
                                                    LocationService::class.java
                                            )
                                    )
                                }
                                Snackbar.make(root, "You are online", Snackbar.LENGTH_LONG)
                                        .show()
                                status_sw.isChecked = true
                            } else {
                                context?.stopService(Intent(context, LocationService::class.java))
                                Snackbar.make(root, "You are offline", Snackbar.LENGTH_LONG).show()
                                status_sw.isChecked = false
                            }
                        } else {
                            if (response.body()!!.message != null) {
                                context?.toast(response.body()!!.message)
                            } else {
                                context?.toast("Opps! Something went wrong")
                            }
                            binding.statusSw.isChecked = !status_sw.isEnabled
                        }
                    }
                })
    }

    @SuppressLint("CheckResult")
    fun checkPermissions() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            RxPermissions(this).request(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            ).subscribe { isGranted ->
                if (isGranted) {
                    checkLocationSetting()
                } else {
                    context?.toast("You can't go further without permission.")
                    checkPermissions()
                }
            }
        } else {
            checkLocationSetting()
        }
    }

    private var mLocationPermissionGranted: Boolean = false
    private val requestCheckLocationSetting: Int = 199
    private fun checkLocationSetting() {
        val locationRequest = LocationRequest.create().apply {
            this.interval = 30000;
            this.fastestInterval = 5000
            this.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        val locationSettingRequest = LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
                .build()
        val task = LocationServices.getSettingsClient(requireContext()).checkLocationSettings(locationSettingRequest)
        task.addOnCompleteListener {
            try {
                it.getResult(ApiException::class.java)
                dashBoardData()
                mLocationPermissionGranted = true
            } catch (exception: ApiException) {
                //Location setting is not enabled show Location Dialog
                when (exception.statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                        // Location settings are not satisfied. But could be fixed by showing the LOCATION  dialog.
                        try {
                            // Cast to a resolvable exception.
                            val resolvable = exception as ResolvableApiException
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            resolvable.startResolutionForResult(requireActivity(), requestCheckLocationSetting)

                        } catch (e: IntentSender.SendIntentException) {
                            // Ignore the error.
                        } catch (e: ClassCastException) {
                            // Ignore, should be an impossible error.
                        }
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == requestCheckLocationSetting && resultCode == Activity.RESULT_OK) {
            mLocationPermissionGranted = true
            //startService()
            dashBoardData()
        } else {/// else show the message and show dialog again
            checkLocationSetting()
        }
    }

    private fun dashBoardData() {
//        progress_bar.visibility = View.VISIBLE

        val hashMap: HashMap<String, String> = HashMap()
//        hashMap["auth_key"] = session.authKey
        hashMap["user_id"] = session.deliverBoyId

        RestClient.getInstance().apiInterface.dashBoadData(hashMap).enqueue(object :
                Callback<HomeBoradResponse> {
            override fun onFailure(call: Call<HomeBoradResponse>, t: Throwable) {
                forceRefresh = false
                isRefreshing = false
                binding.swipeView.isRefreshing = false
                //                progress_bar.visibility = View.GONE
                Snackbar.make(root, "Network Error", Snackbar.LENGTH_LONG).show()
            }

            override fun onResponse(
                    call: Call<HomeBoradResponse>,
                    response: Response<HomeBoradResponse>
            ) {
                forceRefresh = false
                isRefreshing = false
                binding.swipeView.isRefreshing = false
//                progress_bar.visibility = View.GONE
                if (response.isSuccessful) {
                    val body = response.body()
//                    if (body!!.status == "success"){
//                        salaryAmt.text = body.totay_payment
                    binding.txtTodayOrder.text = if (body?.today_order_count.isNullOrEmpty()) {
                        "Rs. 0"
                    } else {
                        "${body?.today_order_count}"
                    }
                    binding.logintimeTv.text = "Login Time : ${body?.login_time}"

                    binding.txtTodayPayment.text = if (body?.today_payment.isNullOrEmpty()) {
                        "Rs. 0"
                    } else {
                        "Rs. ${body?.today_payment}"
                    }
                    if (body?.cod_amount.isNullOrEmpty()) {
                        binding.codAmt.text = "Rs. 0"
                    } else {
                        binding.codAmt.text = "Rs. ${body?.cod_amount}"

                        if (body?.cod_limit != null)
                            binding.codAmtMsgTv.text =
                                    String.format("COD Limit ${body.cod_limit}/- Don’t Cross Limit Deposit Excess Amount Else ID BLOCK in 24 Hours Automatically.")
                        if (body?.cod_limit != null && (body.cod_limit.toInt() <= body.cod_amount.toDouble())) {
                            codAmtMsgTv.setTextColor(Color.RED)
                            viewDetailsDialog(body.cod_limit, body.cod_amount)
                        }
                    }

                    binding.distanceTv.text = if (body?.current_completed_order?.distance.isNullOrEmpty()) {
                        "0km"
                    } else {
                        "${body?.current_completed_order?.distance}km"
                    }
                    binding.salaryAmt.text = if (body?.current_completed_amount.isNullOrEmpty()) {
                        "Rs. 0"
                    } else {
                        String.format(" Rs. %.2f", body?.current_completed_amount?.toBigDecimal())
                    }

                    val serviceStarted = LocationService.isServiceStarted
                    if (!serviceStarted) {
                        val onlineStatus = response.body()!!.status
                        if (onlineStatus == 1) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                activity?.startForegroundService(
                                        Intent(
                                                activity,
                                                LocationService::class.java
                                        )
                                )
                            } else {
                                activity?.startService(
                                        Intent(
                                                activity,
                                                LocationService::class.java
                                        )
                                )
                            }
                            binding.statusSw.isChecked = true

                        }else{
                            changeOnlineStatus()
                        }
                    } else {
                        binding.statusSw.isChecked = true
                    }

                }
            }
        })
    }

    fun loadAssignedOrder() {

        Log.i(
                "dfasfdasfd", session.deliverBoyId + "" +
                session.device_token + ""
        )
        binding.progressBar.visibility = View.VISIBLE

        val hashMap: HashMap<String, String> = HashMap()
        hashMap["user_id"] = session.deliverBoyId

        RestClient.getInstance().apiInterface.assignedorderList(hashMap)
                .enqueue(object : Callback<OrderListResponse<AssignedOrder<ItemDetails>>> {
                    override fun onFailure(call: Call<OrderListResponse<AssignedOrder<ItemDetails>>>, t: Throwable) {
                        forceRefresh = false
                        isRefreshing = false
                        swipe_view.isRefreshing = false

                        binding.progressBar.visibility = View.GONE
                        context?.toast("Network error")
                        Log.d(TAG, "loadAssignedOrder onFailure ", t)
                    }

                    override fun onResponse(
                            call: Call<OrderListResponse<AssignedOrder<ItemDetails>>>,
                            response: Response<OrderListResponse<AssignedOrder<ItemDetails>>>
                    ) {
                        if (swipe_view != null)
                            swipe_view.isRefreshing = false
                        forceRefresh = false
                        isRefreshing = false

                        binding.progressBar.visibility = View.GONE
                        if (response.isSuccessful && response.body()!!.status_code == 1) {
                            relEmptyWL.visibility = View.GONE
                            assignedOrderAdapter.assignedOrderList = response.body()!!.data!!
                            if (response.body()!!.data!!.isEmpty()) {
                                relEmptyWL.visibility = View.VISIBLE
                            }

                        } else {
                            relEmptyWL.visibility = View.VISIBLE
                            context?.toast(response.body()?.message ?: "Opps something went wrong")
                        }
                    }
                })
    }

    fun viewDetailsDialog(limit: String, amount: String) {
        val dialogBuilder =
                AlertDialog.Builder(requireContext())
        dialogBuilder.setCancelable(false)
        val inflater = layoutInflater
        val orderAlertBinding: OrderAlertBinding = DataBindingUtil.inflate(
                LayoutInflater.from(requireContext()), R.layout.order_alert, null, false
        )
        dialogBuilder.setView(orderAlertBinding.getRoot())
        orderAlertBinding.codAmt.text = "Rs. ${amount}"
        orderAlertBinding.codAmtMsgTv.setTextColor(Color.RED)
        orderAlertBinding.codAmtMsgTv.text =
                String.format("COD Limit ${limit}/- Don’t Cross Limit Deposit Excess Amount Else ID BLOCK in 24 Hours Automatically.")

        Handler().postDelayed({
            changeOnlineStatus()
        }, 2000)

        val retryCustomAlert: Dialog = dialogBuilder.create()
        retryCustomAlert.getWindow()!!.setBackgroundDrawable(
                ColorDrawable(Color.TRANSPARENT)
        )
        retryCustomAlert.show()
    }
}
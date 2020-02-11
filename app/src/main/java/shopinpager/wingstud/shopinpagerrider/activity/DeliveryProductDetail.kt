package shopinpager.wingstud.shopinpagerrider.activity

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import shopinpager.wingstud.shopinpagerrider.R
import shopinpager.wingstud.shopinpagerrider.account.AccountManager
import shopinpager.wingstud.shopinpagerrider.adapter.OrderDetailsAdapter
import shopinpager.wingstud.shopinpagerrider.databinding.ActivityDeliveryProductDetailBinding
import shopinpager.wingstud.shopinpagerrider.rest.network.RestClient
import kotlinx.android.synthetic.main.activity_delivery_product_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import shopinpager.wingstud.shopinpagerrider.response.*


class DeliveryProductDetail : AppCompatActivity() {

    val session by lazy { AccountManager.getUserAccount()!! }
    private var orderDetails: AssignedOrder<ItemDetails>? = null

    lateinit var binding: ActivityDeliveryProductDetailBinding;
    lateinit var adapter: OrderDetailsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_delivery_product_detail)

        if (intent.getSerializableExtra("orderDetail") != null) {
            orderDetails = intent.getSerializableExtra("orderDetail")!! as AssignedOrder<ItemDetails>
            map.setOnClickListener {
                startActivity(Intent(this@DeliveryProductDetail, LiveTrackActivity::class.java)
                        .apply {
                            putExtra("assignedOrder", orderDetails)
                            putExtra("userTrack", true)
                        })
            }
            orderDetail(orderDetails!!.id, orderDetails!!.seller_id,orderDetails!!.type)
            map.visibility = View.VISIBLE
            callCard.visibility = View.VISIBLE
            otp_enter.visibility = View.VISIBLE
            enterOtpEt.visibility = View.VISIBLE
            otp_enter.setOnClickListener {
                if (binding.enterOtpEt.text.toString().isNotEmpty()) {
                    CompleteDelivery(orderDetails!!.id, orderDetails!!.seller_id, binding.enterOtpEt.text.toString())
                } else {
                    binding.enterOtpEt.setError("Please enter Otp")
                    binding.enterOtpEt.requestFocus()
                }
            }
        } else {
            map.visibility = View.GONE
            callCard.visibility = View.GONE
            otp_enter.visibility = View.GONE
            enterOtpEt.visibility = View.GONE
            val id = intent.extras!!.getString("order_id", "")
            val seller_id = intent.extras!!.getString("seller_id", "")
            orderDetail(id, seller_id,"")
        }
        back_iv.setOnClickListener { onBackPressed() }
    }

    private fun orderDetail(orderId: String, sellerId: String,type : String) {
        val hashMap: HashMap<String, String> = HashMap()
        hashMap["order_id"] = orderId
        hashMap["seller_id"] = sellerId
        hashMap["type"] = type
        RestClient.getInstance().apiInterface.orderDetails(hashMap).enqueue(object : Callback<OrderDetailsResponse> {
            override fun onFailure(call: Call<OrderDetailsResponse>, t: Throwable) {

            }

            override fun onResponse(call: Call<OrderDetailsResponse>, response: Response<OrderDetailsResponse>) {
                if (response.isSuccessful && response.body()!!.status_code == 1) {
                    val body = response.body()!!
                    order_no.text = "${body.order_info.order_id}"
                    address.text = body.address
                    mob.text = body.mobile
                    payment_type.text = body.order_info.payment_mode
                    username.text = body.name

                    if (body.return_count == body.item_count) {
                        binding.deliveryChargeTv.text = "₹${0}"
                        binding.totalAmtTv.text = "₹0"
                    } else {
                        binding.deliveryChargeTv.text = "₹${body.shipping_charge}"
                        binding.totalAmtTv.text = "₹${body.total_amount}"
                        binding.wtAmtTv.text = "-₹${body.wallet_amount}"
                        binding.paytotalTv.text = "₹${body.total_amount.toDouble()-body.wallet_amount.toDouble()}"
                    }

                    adapter = OrderDetailsAdapter(this@DeliveryProductDetail, body.data)
                    binding.orderdetailsRv.adapter = adapter

                    //adapter.orderDetailList = body.data

                    callCard.setOnClickListener {
                        val intent = Intent(Intent.ACTION_DIAL)
                        intent.data = Uri.parse("tel:${orderDetails?.user_mobile}")
                        startActivity(intent)
                    }

                } else {

                }

            }
        })
    }

    private fun CompleteDelivery(orderId: String, sellerId: String, otpCode: String) {

        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("loading")
        progressDialog.setCancelable(false)
        progressDialog.show()
        val hashMap: HashMap<String, String> = HashMap()
        hashMap["user_id"] = session.deliverBoyId
        hashMap["order_id"] = orderId
        hashMap["seller_id"] = sellerId
        hashMap["code"] = otpCode
        Log.i("CompleteDelivery",hashMap.toString())
        RestClient.getInstance().apiInterface.completeDelivery(hashMap).enqueue(object : Callback<CommanResponse> {
            override fun onFailure(call: Call<CommanResponse>, t: Throwable) {
                progressDialog.dismiss()
            }

            override fun onResponse(call: Call<CommanResponse>, response: Response<CommanResponse>) {
                if (response.isSuccessful) {
                    val body = response.body()!!

                    if (body.status_code==1) {
                        binding.enterOtpEt.visibility = View.GONE
                        binding.otpEnter.visibility = View.GONE
                        finish()
                    }
                    Toast.makeText(this@DeliveryProductDetail, response.body()!!.message, Toast.LENGTH_LONG).show()
                }
                progressDialog.dismiss()
            }
        })
    }
}

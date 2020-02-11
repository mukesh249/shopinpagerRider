package shopinpager.wingstud.shopinpagerrider.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import shopinpager.wingstud.shopinpagerrider.R
import shopinpager.wingstud.shopinpagerrider.account.AccountManager
import shopinpager.wingstud.shopinpagerrider.adapter.PaidViewDetailAdapter
import shopinpager.wingstud.shopinpagerrider.rest.network.RestClient
import shopinpager.wingstud.shopinpagerrider.ui.paymentreport.PaymentReport
import shopinpager.wingstud.shopinpagerrider.util.FixedGridLayoutManager

import kotlinx.android.synthetic.main.activity_payment_report_view_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import shopinpager.wingstud.shopinpagerrider.response.PaymentViewResponse

class PaymentReportViewDetail : AppCompatActivity() {


    internal var scrollX = 0
    lateinit var paidAdapter : PaidViewDetailAdapter;
    private val session by lazy {
        AccountManager.getUserAccount()!!
    }

    var from_date : String = ""
    var to_date : String = ""
    var payment_id : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_report_view_detail)


        back_iv.setOnClickListener { onBackPressed() }
        if (intent.extras!=null) {
            from_date = intent.extras!!.getString("from_date","")
            to_date = intent.extras!!.getString("to_date","")
            payment_id = intent.extras!!.getString("payment_id","")
        }
        val manager = FixedGridLayoutManager()
        manager.setTotalColumnCount(1)
        rvClub.layoutManager = manager
        rvClub.addItemDecoration(
                DividerItemDecoration(
                        this,
                        DividerItemDecoration.VERTICAL
                )
        )

        rvClub.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                scrollX += dx
                headerScroll.scrollTo(scrollX, 0)
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
        if (PaymentReport.paid){
            paidReport()
        }
        if (PaymentReport.unpaid){
            unpaidReport()
        }

    }

    private fun paidReport(){
        val hashMap : HashMap<String,String> = HashMap()
        hashMap["user_id"] = session.deliverBoyId
        hashMap["payment_id"] = payment_id
        hashMap["from_date"] = from_date
        hashMap["to_date"] = to_date
        RestClient.getInstance().apiInterface.paidPaymentDetails(hashMap).enqueue(object :
                Callback<PaymentViewResponse> {
            override fun onFailure(call: Call<PaymentViewResponse>, t: Throwable) {
//                progress_bar.visibility = View.GONE
                Snackbar.make(root,  t.toString()+"Network Error", Snackbar.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<PaymentViewResponse>, response: Response<PaymentViewResponse>) {
//                progress_bar.visibility = View.GONE
                if (response.isSuccessful){
                    val body = response.body();
                    if (body!!.status == 1){
                        relEmptyWL.visibility = View.GONE
                        rvClub.visibility = View.VISIBLE
                        order_amount.text = "Rs.${body.order_amount.toDouble()}"
                        bonus_amount.text = "Rs.${body.bonus_amount.toDouble()}"
                        base_income.text = "Rs.${body.base_income.toDouble()}"
//                        insentive_amount.text = "Rs.${body.insentive_amount}"

                        val sal : Double = body.order_amount.toDouble()+body.bonus_amount.toDouble()+body.base_income.toDouble()
//                        val yoursal = sal - body.hudibaba_fee.toDouble()
                        yoursalary.text = sal.toString()
                        Log.i("sdfasdfsdafsafd",sal.toString()
//                                yoursal.toString()
                        )
                        paidAdapter = PaidViewDetailAdapter(this@PaymentReportViewDetail, body.data)
                        rvClub.adapter = paidAdapter

                    }else{
                        relEmptyWL.visibility = View.VISIBLE
                        rvClub.visibility = View.GONE
                    }
                }
            }
        })
    }
    private fun unpaidReport(){
        val hashMap : HashMap<String,String> = HashMap()
        hashMap["user_id"] = session.deliverBoyId
        hashMap["payment_id"] = payment_id
        hashMap["from_date"] = from_date
        hashMap["to_date"] = to_date
        RestClient.getInstance().apiInterface.orderlist(hashMap).enqueue(object :
                Callback<PaymentViewResponse> {
            override fun onFailure(call: Call<PaymentViewResponse>, t: Throwable) {
                progress_bar.visibility = View.GONE
                Snackbar.make(root, "Network Error", Snackbar.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<PaymentViewResponse>, response: Response<PaymentViewResponse>) {
                progress_bar.visibility = View.GONE
                if (response.isSuccessful){
                    val body = response.body();
                    if (body!!.status == 1){
                        relEmptyWL.visibility = View.GONE
                        rvClub.visibility = View.VISIBLE
                        order_amount.text = String.format("Rs.%.2f",body.order_amount.toDouble())
                        bonus_amount.text = String.format("Rs.%.2f",body.bonus_amount.toDouble())
                        base_income.text = String.format("Rs.%.2f",body.base_income.toDouble())
//                        insentive_amount.text = "Rs.${body.insentive_amount}"

                        val sal : Double = body.order_amount.toDouble()+body.bonus_amount+body.base_income
//                        val yoursal = sal - (sal/100)*body.hudibaba_fee.toInt()
                        yoursalary.text = sal.toString()
                        Log.i("sdfasdfsdafsafd",sal.toString()
//                                +yoursal.toString()
                        )
                        paidAdapter = PaidViewDetailAdapter(this@PaymentReportViewDetail, body.data)
                        rvClub.adapter = paidAdapter

                    }else{
                        relEmptyWL.visibility = View.VISIBLE
                        rvClub.visibility = View.GONE
                    }
                }
            }
        })
    }
}


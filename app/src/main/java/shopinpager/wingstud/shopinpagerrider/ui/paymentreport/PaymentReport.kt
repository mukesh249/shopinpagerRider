package shopinpager.wingstud.shopinpagerrider.ui.paymentreport

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import shopinpager.wingstud.shopinpagerrider.R
import shopinpager.wingstud.shopinpagerrider.account.AccountManager
import shopinpager.wingstud.shopinpagerrider.adapter.PaidKotAdapter
import shopinpager.wingstud.shopinpagerrider.response.PaidResponse
import shopinpager.wingstud.shopinpagerrider.rest.network.RestClient
import shopinpager.wingstud.shopinpagerrider.util.FixedGridLayoutManager
import kotlinx.android.synthetic.main.activity_payment_report.*
import kotlinx.android.synthetic.main.activity_payment_report.back_iv
import kotlinx.android.synthetic.main.activity_payment_report.progress_bar
import kotlinx.android.synthetic.main.activity_payment_report.root
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaymentReport : AppCompatActivity() {

    internal var scrollX = 0
    lateinit var paidAdapter : PaidKotAdapter;
    private val session by lazy {
        AccountManager.getUserAccount()!!
    }

    companion object {
        var paid: Boolean = false
        var unpaid: Boolean = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_report)

        paid = false
        unpaid = true
        back_iv.setOnClickListener{ onBackPressed()}
        paidTv.setOnClickListener {
            scrollX = 0
            paidTv.background = resources.getDrawable(R.drawable.rounded_conner_white)
            paidTv.setTextColor(resources.getColor(R.color.colorPrimary))
            unPaidTv.setTextColor(resources.getColor(R.color.white))
            unPaidTv.background = resources.getDrawable(R.drawable.rounded_conner_border)
            paid = true
            unpaid = false
            paidReport()
        }
        unPaidTv.setOnClickListener {
            scrollX = 0
            unPaidTv.background = resources.getDrawable(R.drawable.rounded_conner_white)
            unPaidTv.setTextColor(resources.getColor(R.color.colorPrimary))
            paidTv.setTextColor(resources.getColor(R.color.white))
            paidTv.background = resources.getDrawable(R.drawable.rounded_conner_border)
            unpaidReport()
            paid = false
            unpaid = true
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

        unpaidReport()
    }

    private fun paidReport(){
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("loading")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val hashMap: HashMap<String, String> = HashMap()
        hashMap["user_id"] = session.deliverBoyId

        RestClient.getInstance().apiInterface.paidPayment(hashMap).enqueue(object :
                Callback<PaidResponse> {
            override fun onFailure(call: Call<PaidResponse>, t: Throwable) {
//                progress_bar.visibility = GONE
                progressDialog.dismiss()
                Snackbar.make(root, "Network Error", Snackbar.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<PaidResponse>, response: Response<PaidResponse>) {
                progress_bar.visibility = GONE
                if (response.isSuccessful){
                    val body = response.body();
                    if (body!!.status_code == 1 && body.data.isNotEmpty()){
                        relEmptyWL.visibility = GONE
                        rvClub.visibility = VISIBLE
                        paidAdapter = PaidKotAdapter(this@PaymentReport, body.data)
                        rvClub.adapter = paidAdapter

                    }else{
                        relEmptyWL.visibility = VISIBLE
                        rvClub.visibility = GONE
                    }
                }
                progressDialog.dismiss()
            }
        })
    }
    private fun unpaidReport(){

        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("loading")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val hashMap: HashMap<String, String> = HashMap()
        hashMap["user_id"] = session.deliverBoyId

        RestClient.getInstance().apiInterface.unpaidPayment(hashMap).enqueue(object :
                Callback<PaidResponse> {
            override fun onFailure(call: Call<PaidResponse>, t: Throwable) {
//                progress_bar.visibility = GONE
                progressDialog.dismiss()
                Snackbar.make(root, "Network Error", Snackbar.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<PaidResponse>, response: Response<PaidResponse>) {
//                progress_bar.visibility = GONE
                if (response.isSuccessful){
                    val body = response.body();
                    if (body!!.status_code == 1 && body.data.isNotEmpty()){
                        relEmptyWL.visibility = GONE
                        rvClub.visibility = VISIBLE
                        paidAdapter = PaidKotAdapter(this@PaymentReport, body.data)
                        rvClub.adapter = paidAdapter
                    }else{
                        relEmptyWL.visibility = VISIBLE
                        rvClub.visibility = GONE
                    }
                }
                progressDialog.dismiss()
            }
        })
    }
}

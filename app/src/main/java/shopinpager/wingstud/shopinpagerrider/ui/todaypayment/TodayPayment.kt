package shopinpager.wingstud.shopinpagerrider.ui.todaypayment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar

import shopinpager.wingstud.shopinpagerrider.R
import shopinpager.wingstud.shopinpagerrider.account.AccountManager
import shopinpager.wingstud.shopinpagerrider.databinding.FragmentTodayPaymentBinding
import shopinpager.wingstud.shopinpagerrider.response.TodayPaymentResponse
import shopinpager.wingstud.shopinpagerrider.rest.network.RestClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class TodayPayment : Fragment() {

    val adapter by lazy { TodayPaymentAdapter() }
    private val session by lazy { AccountManager.getUserAccount()!! }
    lateinit var binding: FragmentTodayPaymentBinding;
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_today_payment, container, false)

        (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Today Payment's"
        binding.todayPayRv.adapter = adapter;
        todayOrder()
        return binding.root
    }

    private fun todayOrder() {
        binding.progressBar.visibility = View.VISIBLE

        val hashMap: HashMap<String, String> = HashMap()
        hashMap["user_id"] = session.deliverBoyId


        RestClient.getInstance().apiInterface.todayPayment(hashMap).enqueue(object :
                Callback<TodayPaymentResponse> {
            override fun onFailure(call: Call<TodayPaymentResponse>, t: Throwable) {
                binding.progressBar.visibility = View.GONE;
                Snackbar.make(binding.rootF,"Network Error", Snackbar.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<TodayPaymentResponse>, response: Response<TodayPaymentResponse>) {

                binding.progressBar.visibility = View.GONE
                if (response.isSuccessful){
                    val body = response.body()!!
                    if (body.status_code==1 && body.data.isNotEmpty()){
                        binding.todayPayRv.visibility = View.VISIBLE
                        binding.relEmptyWL.visibility = View.GONE
                        adapter.todayOrderList = body.data
                    } else {
                        binding.todayPayRv.visibility = View.GONE
                        binding.relEmptyWL.visibility = View.VISIBLE
//                        Snackbar.make(root, body.message ?: "Opps! Something went wrong.", Snackbar.LENGTH_LONG).show()
                    }
                }

            }

        })
    }


}

package shopinpager.wingstud.shopinpagerrider.ui.orderhistory.framgments


import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import shopinpager.wingstud.shopinpagerrider.R
import shopinpager.wingstud.shopinpagerrider.account.AccountManager
import shopinpager.wingstud.shopinpagerrider.response.OrderHistoryResponse
import shopinpager.wingstud.shopinpagerrider.rest.network.RestClient
import shopinpager.wingstud.shopinpagerrider.ui.orderhistory.OrderHistoryAdapter
import kotlinx.android.synthetic.main.fragment_rejected.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import shopinpager.wingstud.shopinpagerrider.databinding.FragmentRejectedBinding


/**
 * A simple [Fragment] subclass.
 */
class RejectedFragment : Fragment() {

    private val session by lazy { AccountManager.getUserAccount()!! }

    private val orderCompletedAdapter by lazy { OrderHistoryAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rejected, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        order_history_rv.layoutManager = LinearLayoutManager(requireContext())
        order_history_rv.adapter = orderCompletedAdapter
        loadOrderHistory()
    }
    private fun loadOrderHistory() {
        val hashMap = HashMap<String ,String>()
        hashMap["user_id"] = session.deliverBoyId
        hashMap["status"] = "rejected"
        RestClient.getInstance().apiInterface.completed(hashMap).enqueue(object : Callback<OrderHistoryResponse> {
            override fun onFailure(call: Call<OrderHistoryResponse>, t: Throwable) {

            }

            override fun onResponse(call: Call<OrderHistoryResponse>, response: Response<OrderHistoryResponse>) {
                if (response.isSuccessful) {
                    val body = response.body()!!
                    if (body.status_code == 1) {
                        Log.d(TAG, "order list fetched")
                        orderCompletedAdapter.ordersList = body.data!!
                    } else {
                    }
                }
            }
        })
    }

    companion object {
        private const val TAG = "OrderDeliveredFragment"
    }

}
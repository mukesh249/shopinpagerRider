package shopinpager.wingstud.shopinpagerrider.ui.home

import android.app.ProgressDialog
import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import shopinpager.wingstud.shopinpagerrider.R
import shopinpager.wingstud.shopinpagerrider.extensions.inflate
import shopinpager.wingstud.shopinpagerrider.response.AssignedOrder
import shopinpager.wingstud.shopinpagerrider.response.ItemDetails
import kotlinx.android.synthetic.main.assigned_order_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import shopinpager.wingstud.shopinpagerrider.account.AccountManager
import shopinpager.wingstud.shopinpagerrider.response.AcceptResponse
import shopinpager.wingstud.shopinpagerrider.rest.network.RestClient
import shopinpager.wingstud.shopinpagerrider.util.toast
import java.util.HashMap


class AssignedOrderAdapter : RecyclerView.Adapter<AssignedOrderAdapter.ViewHolder>() {

    var trackListener: OrderTrackListener? = null
    private var context: Context? = null
    val session by lazy { AccountManager.getUserAccount()!! }
    var assignedOrderList : List<AssignedOrder<ItemDetails>> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(parent.inflate(R.layout.assigned_order_item))
    }

    override fun getItemCount(): Int {
        return assignedOrderList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order = assignedOrderList[position]
        holder.itemView.let {
            Log.d("Adapter", "Assigned Order $order")
//            val restaurantName = order.restaurantAddress
//            Log.d("Adapter", "name  " + restaurantName)
            it.restaurant_tv.text = order.pickup_name
            it.restaurant_address.text = order.pickup_address
            it.timestamp.text = order.delivery_date+" "+order.delivery_time
            it.order_id.text = "${order.order_id}"
            it.amount_tv.text = "₹${order.amount}"
            it.payment_type.text = order.payment_mode
            it.username_tv.text = order.seller_name
            it.user_address.text = order.seller_address

            if (order.status.equals("requested")){
                it.btnLL.visibility = View.VISIBLE
                it.view_details_tv.visibility = View.GONE
            }else{
                it.btnLL.visibility = View.GONE
                it.view_details_tv.visibility = View.VISIBLE
            }
        }
        holder.itemView.accept.setOnClickListener {
            accept(order.id,"accepted",order.seller_id,"1")
        }
        holder.itemView.reject.setOnClickListener {
            accept(order.id,"rejected",order.seller_id,"1")
        }
        holder.itemView.view_details_tv.setOnClickListener {
            trackListener?.onOrderDetail(order)
        }
        holder.itemView.restaurant_track.setOnClickListener {
            trackListener?.onSellerTrack(order)
        }
        holder.itemView.user_address_track.setOnClickListener {
            trackListener?.onUserTrack(order)
        }

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    interface OrderTrackListener {
        fun onSellerTrack(assignedOrder: AssignedOrder<ItemDetails>)
        fun onUserTrack(assignedOrder: AssignedOrder<ItemDetails>)
        fun onOrderDetail(assignedOrder: AssignedOrder<ItemDetails>)
    }
    fun accept(orderId : String,status : String,sellerId :String,count :String){

        val progressDialog = ProgressDialog(context)
        progressDialog.setMessage("loading")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val hashMap: HashMap<String, String> = HashMap()
        hashMap["user_id"] = session.deliverBoyId
        hashMap["status"] = status
        hashMap["order_id"] = orderId
        hashMap["seller_id"] = sellerId
        hashMap["count"] = count
        RestClient.getInstance().apiInterface.acceptNewOrder(hashMap).enqueue(object : Callback<AcceptResponse> {
            override fun onFailure(call: Call<AcceptResponse>, t: Throwable) {
                progressDialog.dismiss()
                Log.d("NewRequest", "onFailure", t)
                context?.toast("Opps! something went wrong")
            }

            override fun onResponse(call: Call<AcceptResponse>, response: Response<AcceptResponse>) {
                progressDialog.dismiss()
                if (response.isSuccessful && response.body()!!.status_code == 1) {
                    context?.toast(response.body()!!.message.toString())
//                    notifyDataSetChanged()
                    HomeFragment.mInstance.getInstance().loadAssignedOrder()
                } else {
                    if (response.body()!!.error_message != null) {
                        context?.toast(response.body()!!.error_message!!)
                    } else {
                        context?.toast("Opps! something went wrong")
                    }
                }
            }
        })
    }
}
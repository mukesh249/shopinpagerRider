package shopinpager.wingstud.shopinpagerrider.ui.todayorders

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import shopinpager.wingstud.shopinpagerrider.R
import shopinpager.wingstud.shopinpagerrider.extensions.inflate
import shopinpager.wingstud.shopinpagerrider.response.TodayPaymentResponse
import kotlinx.android.synthetic.main.todayorder_item.view.*

class TodayOrderAdapter : RecyclerView.Adapter<TodayOrderAdapter.ViewHolder>() {

//    var todayOrderInterface: ViewHolder.TodayOrderInterface? = null

    var todayOrderList: List<TodayPaymentResponse.Data> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.todayorder_item, false))
    }

    override fun getItemCount(): Int = todayOrderList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val todayOrder = todayOrderList[position]
        holder.bind(todayOrder)

        holder.itemView.let {
            it.txtOrderId.text = "${todayOrder.order_id}"
            it.txtDeliveryTime.text = "Delivery Date :${todayOrder.delivery_date}"

//            val total = todayOrder.payment_amount.toInt()+todayOrder.payment_amount

            it.txtPrice.text = String.format("Rs. %.2f ",todayOrder.amount)
            it.restaurant_name.text = todayOrder.seller_name
            it.restaurant_address.text = todayOrder.seller_address
            it.PickAddress.text = todayOrder.pickup_address
            it.Pickname.text = todayOrder.pickup_name
        }

//        holder.itemView.txtView.setOnClickListener {
//            trackListener?.viewDetail(todayOrder)!!;
//        }

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
    {
        private lateinit var context: Context
        fun bind(order: TodayPaymentResponse.Data) {
            context = super.itemView.context
            itemView.txtView.setOnClickListener {
                //                todayOrderInterface.viewDetail(order)
                // Move to AnotherActivity
//                    startActivity(this.context,Intent(this.context, DeliveryDetailActivity::class.java)
//                        .apply {
//                            putExtra("orderDetailView", order)
//                        }
//                        ,null)
            }
        }
    }

    interface TodayOrderInterface {
        fun viewDetail(order : TodayPaymentResponse.Data);
    }
}
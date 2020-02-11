package shopinpager.wingstud.shopinpagerrider.ui.todaypayment

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import shopinpager.wingstud.shopinpagerrider.R
import shopinpager.wingstud.shopinpagerrider.extensions.inflate
import shopinpager.wingstud.shopinpagerrider.response.TodayPaymentResponse
import kotlinx.android.synthetic.main.today_payment_item.view.*



class TodayPaymentAdapter : RecyclerView.Adapter<TodayPaymentAdapter.ViewHolder>() {

    var todayOrderList: List<TodayPaymentResponse.Data> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.today_payment_item, false))
    }

    override fun getItemCount(): Int = todayOrderList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val todayOrder = todayOrderList[position]
        holder.itemView.let {
            it.txtOrderId.text = "${todayOrder.order_id}"
            it.txtMobile.text = "Mobile : ${todayOrder.user_mobile}"
            it.txtPrice.text = String.format("Rs. %.2f", todayOrder.commission)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)



}
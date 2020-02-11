package shopinpager.wingstud.shopinpagerrider.ui.orderhistory

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import shopinpager.wingstud.shopinpagerrider.R
import shopinpager.wingstud.shopinpagerrider.extensions.inflate
import shopinpager.wingstud.shopinpagerrider.response.OrderHistoryResponse
import kotlinx.android.synthetic.main.item_completed_order.view.*

class OrderHistoryAdapter : RecyclerView.Adapter<OrderHistoryAdapter.HistoryOrderItem>() {


    var ordersList: List<OrderHistoryResponse.Data> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryOrderItem {
        return HistoryOrderItem(parent.inflate(R.layout.item_completed_order))
    }

    override fun getItemCount() = ordersList.size

    override fun onBindViewHolder(holder: HistoryOrderItem, position: Int) {
        val order = ordersList[position]
        val amount = order.amount
        val orderId = order.order_id
        val paymentType = order.payment_mode
        val pickAddress = order.pickup_address
        val pickName = order.pickup_name
        val sellerAddress = order.seller_address

        holder.itemView.let {
            it.order_id.text = orderId
            it.amount.text = "₹"+amount
            it.payment_type.text = paymentType
            it.pickAddress.text = pickAddress
            it.pickUpName.text = pickName
            it.sellerName.text = order.seller_name
            it.sellerAddress.text = sellerAddress
        }

    }

    class HistoryOrderItem(view: View) : RecyclerView.ViewHolder(view)
}
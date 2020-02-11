package shopinpager.wingstud.shopinpagerrider.ui.earning

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import shopinpager.wingstud.shopinpagerrider.R
import shopinpager.wingstud.shopinpagerrider.extensions.inflate
import shopinpager.wingstud.shopinpagerrider.response.DataEarning
import kotlinx.android.synthetic.main.item_earning.view.*

class EarningAdapter : RecyclerView.Adapter<EarningAdapter.ViewHolder>() {


    var earningList: List<DataEarning> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.item_earning))
    }

    override fun getItemCount(): Int {
        return earningList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataEarning = earningList[position]
        if (!dataEarning.user_name.isNullOrEmpty()){
            holder.itemView.username_tv.visibility = View.VISIBLE
            holder.itemView.username_tv.text = dataEarning.user_name
        }else{
            holder.itemView.username_tv.visibility = View.GONE
        }

        holder.itemView.comsAmtTv.text = String.format("  Rs. %.2f", dataEarning.earning.toBigDecimal())
        holder.itemView.timestamp.text = dataEarning.delivery_date
//        + "," + dataEarning.delivery_time
        holder.itemView.order_id.text = dataEarning.order_id

        holder.itemView.user_address.text = dataEarning.seller_address
        holder.itemView.user_Name.text = dataEarning.seller_name

        holder.itemView.restaurant_tv.text = dataEarning.pickup_name
        holder.itemView.restaurant_address.text = dataEarning.pickup_address
        holder.itemView.orderDistanceTv.text = dataEarning.distance

        holder.itemView.distanceTv.text = dataEarning.distance

        if (dataEarning.earning != null && dataEarning.bonus != null) {
            val totalEarn = dataEarning.earning.toBigDecimal() + dataEarning.bonus.toBigDecimal()
            holder.itemView.totalEarTv.text = "  Rs. " + totalEarn
        }


        if (dataEarning.bonus != null)
            holder.itemView.bounsAmt.text = "  Rs. " + dataEarning.bonus
//        holder.itemView.restaurant_address.text = dataEarning.seller_address
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
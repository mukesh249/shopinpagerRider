package shopinpager.wingstud.shopinpagerrider.ui.cod

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import shopinpager.wingstud.shopinpagerrider.R
import shopinpager.wingstud.shopinpagerrider.extensions.inflate
import shopinpager.wingstud.shopinpagerrider.response.Cod
import kotlinx.android.synthetic.main.cod_list_item.view.*

class CODAdapter(private val context: Context?, private val clubList: List<CodResponse.Cod>) :
        RecyclerView.Adapter<CODAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(viewGroup.inflate(R.layout.cod_list_item))
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val paidA = clubList[position]
        holder.bind(paidA)
        holder.itemView.let {
            if (position % 2 == 0) {
                it.backLL.setBackgroundColor(context?.resources!!.getColor(R.color.light_gray))
            } else {
                it.backLL.setBackgroundColor(context?.resources!!.getColor(R.color.white))
            }
            it.snoTv.text = (position + 1).toString()
            it.orderIdTv.text = paidA.order.order_id

            val total = paidA.order.net_amount.toInt()+paidA.order.shipping_charge

            it.AmtTv.text = String.format("Rs. %.2f",total.toBigDecimal())
            it.statusTv.text = paidA.order.status
//            it.riderNameTv.text = paidA.name
//            it.riderMobileTv.text = paidA.order.mobile
            it.paymentModeTv.text = paidA.payment_mode

            var date = paidA.order.delivery_date
//            var spf = SimpleDateFormat("yyyy-mm-dd")
//            val newDate = spf.parse(date)
//            spf = SimpleDateFormat("dd/MM/yyyy")
//            date = spf.format(newDate)
            println(date)
            it.dateTv.text = date
        }

    }

    override fun getItemCount(): Int {
        return clubList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private lateinit var context: Context
        fun bind(order: CodResponse.Cod) {
            context = super.itemView.context
            itemView.actionTv.setOnClickListener {
                //                todayOrderInterface.viewDetail(order)
                // Move to AnotherActivity
//                ContextCompat.startActivity(this.context,
//                    Intent(this.context, OrderDetail::class.java)
//                        .apply {
//                            putExtra("order_id", order.order_id)
//                        }
//                    ,
//                    null)
            }
        }
    }

}
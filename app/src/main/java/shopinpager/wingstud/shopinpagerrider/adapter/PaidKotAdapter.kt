package shopinpager.wingstud.shopinpagerrider.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import shopinpager.wingstud.shopinpagerrider.R
import shopinpager.wingstud.shopinpagerrider.activity.PaymentReportViewDetail
import shopinpager.wingstud.shopinpagerrider.databinding.PaidItemBinding
import shopinpager.wingstud.shopinpagerrider.response.PaidResponse
import shopinpager.wingstud.shopinpagerrider.ui.paymentreport.PaymentReport
import kotlinx.android.synthetic.main.paid_item.view.*
import java.text.SimpleDateFormat


class PaidKotAdapter(private val context: Context, private val clubList: List<PaidResponse.Data>) :
        RecyclerView.Adapter<PaidKotAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = DataBindingUtil.inflate<PaidItemBinding>(
                LayoutInflater.from(viewGroup.context),
                R.layout.paid_item,
                viewGroup,
                false
        )
        return ViewHolder(view.root)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val paidA = clubList[position]
        holder.bind(paidA)
        holder.itemView.let {
            if (position % 2 == 0) {
                it.backLL.setBackgroundColor(context.resources.getColor(R.color.light_gray))
            } else {
                it.backLL.setBackgroundColor(context.resources.getColor(R.color.white))
            }
            it.snoTv.text = "${(position + 1)}"


            if (PaymentReport.paid) {
//                it.deductionTv.text = "${paidA.deduction}"

                if (paidA.from_date != null) {
                    var date = paidA.from_date
                    var spf = SimpleDateFormat("yyyy-mm-dd")
                    val newDate = spf.parse(date)
                    spf = SimpleDateFormat("dd/mm/yyyy")
                    date = spf.format(newDate)
                    it.fromDateTv.text = date
                }

                if (paidA.to_date != null) {
                    var tdate = paidA.to_date
                    var tspf = SimpleDateFormat("yyyy-mm-dd")
                    val tnewDate = tspf.parse(tdate)
                    tspf = SimpleDateFormat("dd/mm/yyyy")
                    tdate = tspf.format(tnewDate)
                    it.toDateTv.text = tdate
                }

                it.commissionTv.text = "${paidA.amount_per_km}"
                it.totalDistanceTv.text = paidA.total_distance.toString()
                it.totalOrderTv.text = "${paidA.total_count}"
                it.orderAmtTv.text = "${paidA.cod_total}"
                it.payableAmtTv.text = "${paidA.payable}"
//                it.feeTv.text = "${paidA.grocito_fee}"

            } else if (PaymentReport.unpaid) {
                it.commissionTv.text = "${paidA.amount_per_km}"

                if (paidA.from_date!=null) {
                    var date = paidA.from_date
                    var spf = SimpleDateFormat("yyyy-mm-dd")
                    val newDate = spf.parse(date)
                    spf = SimpleDateFormat("dd/mm/yyyy")
                    date = spf.format(newDate)
                    it.fromDateTv.text = date
                }

                if (paidA.to_date!=null) {
                    var tdate = paidA.to_date
                    var tspf = SimpleDateFormat("yyyy-mm-dd")
                    val tnewDate = tspf.parse(tdate)
                    tspf = SimpleDateFormat("dd/mm/yyyy")
                    tdate = tspf.format(tnewDate)
                    it.toDateTv.text = tdate
                }
                if (paidA != null) {
//                    var dataObj: DataObj = paidA.data
                    Log.i("datasdf", paidA.toString())
                    it.totalDistanceTv.text = "${paidA.total_distance}"
                    it.totalOrderTv.text = "${paidA.total_count}"
                    it.orderAmtTv.text = "${paidA.cod_total}"

//                    val dis: Int = paidA.total_distance
//                    val com: Int = paidA.amount_per_km
//
//                    val bas_in: Int = paidA.base_income
//                    val n_day: Int = paidA.total_days
//
//
//                    val payble: Int = dis * com
//                    val amt = bas_in * n_day
//
//                    val fee = ((payble + amt) * 15) / 100
//                    it.feeTv.text = fee.toString()
//                    val totalPay = (payble + amt) - fee
//
//                    val abc = (paidA.total_distance.toDouble() * paidA.amount_per_km.toDouble())
//                    val totalAmt: Double = abc + paidA.base_income.toDouble() + paidA.bonus.toDouble()
//                    val totPer: Double = totalAmt - (totalAmt * 15) / 100

                    it.payableAmtTv.text = "${paidA.payable}"
                }
            }

        }
    }

    override fun getItemCount(): Int {
        return clubList.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private lateinit var context: Context
        fun bind(order: PaidResponse.Data) {
            context = super.itemView.context
            itemView.actionTv.setOnClickListener {
                ContextCompat.startActivity(this.context,
                        Intent(this.context, PaymentReportViewDetail::class.java)
                                .apply {
                                    if (PaymentReport.paid) {
                                        putExtra("from_date", order.from_date)
                                        putExtra("to_date", order.to_date)
                                        putExtra("payment_id", order.payment_id)
                                    } else {
                                        putExtra("from_date", order.from_date)
                                        putExtra("to_date", order.to_date)
                                        putExtra("payment_id", order.payment_id)
                                    }
                                }
                        ,
                        null)
            }
        }
    }

}
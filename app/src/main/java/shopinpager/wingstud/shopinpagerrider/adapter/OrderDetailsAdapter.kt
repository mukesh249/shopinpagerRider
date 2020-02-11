package shopinpager.wingstud.shopinpagerrider.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import shopinpager.wingstud.shopinpagerrider.R
import shopinpager.wingstud.shopinpagerrider.extensions.inflate
import shopinpager.wingstud.shopinpagerrider.response.Data
import kotlinx.android.synthetic.main.order_details_item.view.*

class OrderDetailsAdapter(private val context: Context, private val orderdetailList : List<Data>) : RecyclerView.Adapter<OrderDetailsAdapter.ViewHolder>() {
//    var orderdetailList : List<Data> = emptyList()
//        set(value) {
//            field = value
//            notifyDataSetChanged()
//        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       return ViewHolder(parent.inflate(R.layout.order_details_item))
    }

    override fun getItemCount(): Int {
        return orderdetailList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ordetailModel = orderdetailList[position]
//        holder.bind(ordetailModel)
        holder.itemView.nameTv.text = ordetailModel.name
        holder.itemView.price_tv.text = String.format("₹ %.2f",ordetailModel.price)
        holder.itemView.qty_tv.text = String.format("Qty : ${ordetailModel.qty}")
        holder.itemView.weight_tv.text = String.format("Weight : ${ordetailModel.weight}")

        Glide.with(context).load("https://www.shopinpager.com/public/admin/uploads/product/"+ordetailModel.image).into(holder.itemView.productImage)
    }

    class ViewHolder (view : View) :RecyclerView.ViewHolder(view)
}
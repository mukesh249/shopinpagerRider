package shopinpager.wingstud.shopinpagerrider.ui.orderhistory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import shopinpager.wingstud.shopinpagerrider.R
import shopinpager.wingstud.shopinpagerrider.ui.orderhistory.framgments.DeliveredFragment
import shopinpager.wingstud.shopinpagerrider.ui.orderhistory.framgments.RejectedFragment
import kotlinx.android.synthetic.main.activity_order_history.*
import shopinpager.wingstud.shopinpagerrider.adapter.ViewPagerAdpater
import shopinpager.wingstud.shopinpagerrider.databinding.ActivityOrderHistoryBinding

class OrderHistory : AppCompatActivity() {

    lateinit var binding: ActivityOrderHistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_order_history)

//        binding.viewPager.adapter = PagerViewAdapter(supportFragmentManager)

        binding.viewPager.adapter = ViewPagerAdpater(supportFragmentManager)

        binding.deliveredOrders.setOnClickListener { binding.viewPager.currentItem = 0 }
        binding.rejectedOrders.setOnClickListener { binding.viewPager.currentItem = 1 }

        binding.backIv.setOnClickListener { onBackPressed() }

        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                if (position == 0){
                    binding.deliveredOrders.setTextColor(resources.getColor(R.color.colorPrimaryDark))
                    binding.deliveredOrders.background = resources.getDrawable(R.drawable.rounded_conner_white)
                    binding.rejectedOrders.background = resources.getDrawable(R.drawable.rounded_conner_border)
                    binding.rejectedOrders.setTextColor(resources.getColor(R.color.white))
                }
                if (position == 1){
                    binding.deliveredOrders.setTextColor(resources.getColor(R.color.white))
                    binding.rejectedOrders.setTextColor(resources.getColor(R.color.colorPrimaryDark))
                    binding.deliveredOrders.background =resources.getDrawable(R.drawable.rounded_conner_border)
                    binding.rejectedOrders.background = resources.getDrawable(R.drawable.rounded_conner_white)
                }
            }
        })


    }
}

//class PagerViewAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
//    override fun getItem(i: Int): Fragment {
//        return when (i) {
//            0 -> DeliveredFragment()
//            1 -> RejectedFragment()
//            else -> null
//        }!!
//
//    }
//
//    override fun getCount(): Int {
//        return 2
//    }
//
//}

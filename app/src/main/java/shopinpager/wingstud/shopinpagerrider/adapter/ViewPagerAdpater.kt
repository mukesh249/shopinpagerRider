package shopinpager.wingstud.shopinpagerrider.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import shopinpager.wingstud.shopinpagerrider.ui.orderhistory.framgments.DeliveredFragment
import shopinpager.wingstud.shopinpagerrider.ui.orderhistory.framgments.RejectedFragment

class ViewPagerAdpater(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
                return when (position) {
            0 -> DeliveredFragment()
            1 -> RejectedFragment()
            else -> null
        }!!
    }

    override fun getCount(): Int {
       return 2
    }
}
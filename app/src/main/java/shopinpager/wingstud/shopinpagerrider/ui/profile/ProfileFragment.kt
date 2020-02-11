package shopinpager.wingstud.shopinpagerrider.ui.profile


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import shopinpager.wingstud.shopinpagerrider.R
import shopinpager.wingstud.shopinpagerrider.account.AccountManager
import shopinpager.wingstud.shopinpagerrider.databinding.FragmentProfile2Binding
import shopinpager.wingstud.shopinpagerrider.util.setProfileImage


class ProfileFragment : Fragment() {

    private val session by lazy { AccountManager.getUserAccount()!! }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding : FragmentProfile2Binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile2, container, false)
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Profile"
        context?.setProfileImage(session.profile_image,binding.avatarIv)
        binding.nameTv.text = session.username
        binding.mobTv.text = session.mobile
        binding.emailTv.text = session.email
        binding.employeeId.text = session.employeeNo
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Profile"
    }
}

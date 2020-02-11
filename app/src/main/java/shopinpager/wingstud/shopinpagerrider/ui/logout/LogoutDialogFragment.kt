package shopinpager.wingstud.shopinpagerrider.ui.logout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import shopinpager.wingstud.shopinpagerrider.R
import shopinpager.wingstud.shopinpagerrider.databinding.LogoutfragmentBinding

class LogoutDialogFragment : DialogFragment() {
    private var content: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        content = arguments!!.getString("content")

        // Pick a style based on the num.
        val style = DialogFragment.STYLE_NO_FRAME
//        val theme = R.style.DialogTheme
        setStyle(style, theme)
    }
    lateinit var binding: LogoutfragmentBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.logoutfragment, container, false)

        binding.logoutLayout.txtRAMsg.text = "Are you sure? logout from Grocito"
        binding.logoutLayout.txtRAFirst.text = "Cancel"
        binding.logoutLayout.txtRASecond.text = "Yes"

        binding.logoutLayout.txtRAFirst.setOnClickListener {
            dismiss()
        }
        binding.logoutLayout.txtRASecond.setOnClickListener {

        }
        return binding.root
    }

    companion object {


        /**
         * Create a new instance of CustomDialogFragment, providing "num" as an
         * argument.
         */
        fun newInstance(content: String): LogoutDialogFragment {
            val f = LogoutDialogFragment()

            // Supply num input as an argument.
            val args = Bundle()
            args.putString("content", content)
            f.arguments = args

            return f
        }
    }
}
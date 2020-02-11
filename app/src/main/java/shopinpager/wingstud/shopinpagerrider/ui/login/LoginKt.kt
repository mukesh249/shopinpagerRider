package shopinpager.wingstud.shopinpagerrider.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_login_kt.*
import org.jetbrains.anko.toast
import shopinpager.wingstud.shopinpagerrider.App
import shopinpager.wingstud.shopinpagerrider.R
import shopinpager.wingstud.shopinpagerrider.activity.ForgotPassword
import shopinpager.wingstud.shopinpagerrider.activity.MainActivity
import shopinpager.wingstud.shopinpagerrider.databinding.ActivityLoginKtBinding
import shopinpager.wingstud.shopinpagerrider.extensions.defaultSharedPreferences
import shopinpager.wingstud.shopinpagerrider.extensions.putString
import shopinpager.wingstud.shopinpagerrider.extensions.setLinkSpan
import shopinpager.wingstud.shopinpagerrider.rest.responses.LoginResponse

class LoginKt : AppCompatActivity() , Loginlistener {


    var viewModel : LoginVIewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityLoginKtBinding = DataBindingUtil.setContentView(this,R.layout.activity_login_kt)
        viewModel = ViewModelProviders.of(this).get(LoginVIewModel::class.java)
        binding.loginViewModel = viewModel
        viewModel!!.loginlistener = this

        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener(this){ instanceIdResult ->
            val newToken = instanceIdResult.token
            Log.i("newToken", newToken)
            App.get().defaultSharedPreferences.putString("Token", newToken)
        }
        val textWatcher = TextListener()
        username_et.addTextChangedListener(textWatcher)
        password_et.addTextChangedListener(textWatcher)

        binding.forgotBt.setOnClickListener {  startActivity(Intent(this, ForgotPassword::class.java)) }

        toc.movementMethod = LinkMovementMethod.getInstance()
        toc.text = SpannableString("Accept the Terms and Condition").apply {
            setLinkSpan("Terms and Condition", {
                val intent = Intent(this@LoginKt,TermsAndCondition::class.java)
                startActivity(intent)
            }, ContextCompat.getColor(this@LoginKt, R.color.Grey_600)
            )
        }
        check_box.setOnClickListener { buttonEnable() }
    }


    override fun onStarted() {
        toast("Login Started")
    }

    override fun onSuccess(loginResponse: LoginResponse?) {
        if (loginResponse!!.status_code == 1) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        toast(loginResponse.message.toString())
    }

    override fun onFailure(message: String) {
        toast(message)
    }

    private fun buttonEnable() {
        sign_bt.isEnabled = !username_et.text.isNullOrEmpty() && !password_et.text.isNullOrEmpty() && check_box.isChecked
    }

    inner class TextListener : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
            buttonEnable()
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

    }
}

package shopinpager.wingstud.shopinpagerrider.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import shopinpager.wingstud.shopinpagerrider.R

class UnavailableActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_unavailable)
    }

    fun call(view : View){
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:9672345662")
        startActivity(intent)
    }
}

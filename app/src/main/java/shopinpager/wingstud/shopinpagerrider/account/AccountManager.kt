package shopinpager.wingstud.shopinpagerrider.account
import android.util.Log
import com.google.gson.Gson
import shopinpager.wingstud.shopinpagerrider.App
import shopinpager.wingstud.shopinpagerrider.extensions.defaultSharedPreferences
import shopinpager.wingstud.shopinpagerrider.extensions.putString

class AccountManager {

    companion object {
        private var self: UserAccount? = null

        fun clear(){
            self = null
        }

        fun setUserAccount(account: UserAccount) {
            val gson = Gson()
            val accountJson = gson.toJson(account)
            Log.d("SharedPref", "AccountJson ${accountJson}")

            App.get().defaultSharedPreferences.putString("account", accountJson)
            self = account
        }

        fun getUserAccount(): UserAccount? {
            if (self == null) {
                val accountString = App.get().defaultSharedPreferences.getString("account", null)
                if (accountString != null) {
                    val gson = Gson()
                    self = gson.fromJson<UserAccount>(accountString, UserAccount::class.java)
                }
            }
            return self
        }


    }
}
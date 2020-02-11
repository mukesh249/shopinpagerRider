package shopinpager.wingstud.shopinpagerrider.extensions

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.fragment.app.Fragment



inline val Fragment.defaultSharedPreferences: SharedPreferences
	get() = requireContext().defaultSharedPreferences


inline val Context.defaultSharedPreferences: SharedPreferences
	get() = PreferenceManager.getDefaultSharedPreferences(this)


fun Context.sharedPreferences(name: String): SharedPreferences =
	this.getSharedPreferences(name, MODE_PRIVATE)

 fun SharedPreferences.clear() {
	this.edit().clear().apply()
}

 fun SharedPreferences.putBoolean(key: String, value: Boolean) {
	this.edit().putBoolean(key, value).apply()
}

 fun SharedPreferences.putFloat(key: String, value: Float) {
	this.edit().putFloat(key, value).apply()
}

inline fun SharedPreferences.putInt(key: String, value: Int) {
	this.edit().putInt(key, value).apply()
}

 fun SharedPreferences.putLong(key: String, value: Long) {
	this.edit().putLong(key, value).apply()
}

 fun SharedPreferences.putString(key: String, value: String?) {
	this.edit().putString(key, value).apply()
}

 fun SharedPreferences.putStringSet(key: String, values: Set<String>?) {
	this.edit().putStringSet(key, values).apply()
}

 fun SharedPreferences.remove(key: String) {
	this.edit().remove(key).apply()
}


package shopinpager.wingstud.shopinpagerrider.ui.login

import shopinpager.wingstud.shopinpagerrider.rest.responses.LoginResponse

interface Loginlistener {
    fun onStarted()
    fun onSuccess(loginResponse: LoginResponse?)
    fun onFailure(message : String)
}
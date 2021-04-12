package authentication

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SVerificationViewModel(application: Application) : AndroidViewModel(application) {
    var otp1 = ""
    var otp2 = ""
    var otp3 = ""
    var otp4 = ""
    var otp5 = ""
    var otp6 = ""

    val isOTPNumberValid by lazy {
        MutableLiveData<Boolean>()
    }

    var countDownTimer = MutableLiveData<Int>()

    fun checkOTP() {
        isOTPNumberValid.value =
            (otp1.isNotEmpty() && otp2.isNotEmpty() && otp3.isNotEmpty() && otp4.isNotEmpty() && otp5.isNotEmpty() && otp6.isNotEmpty())
        println(isOTPNumberValid.value)
        println("$otp1$otp2$otp3$otp4$otp5$otp6")
    }
}

package authentication

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SLoginViewModel : ViewModel() {
    var mobileNumber = ""
    val isMobileNumberValid by lazy {
        MutableLiveData<Boolean>()
    }

    fun checkForValidMobileNumber() {
        isMobileNumberValid.value = mobileNumber.isNotEmpty() && mobileNumber.length == 10
    }
}
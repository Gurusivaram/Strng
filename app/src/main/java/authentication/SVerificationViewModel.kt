package authentication

import android.R
import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.TaskExecutors
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import java.util.concurrent.TimeUnit

class SVerificationViewModel : ViewModel() {
    var otp1 = ""
    var otp2 = ""
    var otp3 = ""
    var otp4 = ""
    var otp5 = ""
    var otp6 = ""

    val isOTPNumberValid by lazy {
        MutableLiveData<Boolean>()
    }

    fun checkOTP() {
        isOTPNumberValid.value =
            (otp1.isNotEmpty() && otp2.isNotEmpty() && otp3.isNotEmpty() && otp4.isNotEmpty() && otp5.isNotEmpty() && otp6.isNotEmpty())
                    && (otp1 < "9" && otp1 >= "0" && otp2 < "9" && otp2 >= "0" && otp3 < "9" && otp3 >= "0" && otp4 < "9" && otp4 >= "0" && otp5 < "9" && otp5 >= "0" && otp6 < "9" && otp6 >= "0")
    }
}

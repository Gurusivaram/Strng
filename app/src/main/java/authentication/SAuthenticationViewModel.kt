package authentication

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel

class SAuthenticationViewModel(application: Application) : AndroidViewModel(application){
    var mobileNumber = ""
}
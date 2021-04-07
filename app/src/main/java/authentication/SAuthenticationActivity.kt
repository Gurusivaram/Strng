package authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.strng.R
import com.example.strng.databinding.SActivityAuthenticationBinding

class SAuthenticationActivity : AppCompatActivity() {
    private lateinit var authenticationBinding: SActivityAuthenticationBinding
    companion object{
        const val LOGIN_TAG = "LOGIN"
        const val VERIFICATION_TAG = "OTP"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authenticationBinding = SActivityAuthenticationBinding.inflate(layoutInflater)
        setContentView(authenticationBinding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.apply {
                beginTransaction().add(
                    R.id.fl_authentication, SVerificationFragment(),
                    VERIFICATION_TAG
                ).commit()
            }
        }
    }

    fun navigateToFragment(fragmentTag: String) {
        when (fragmentTag) {
            LOGIN_TAG -> checkAndAttachFragment(fragmentTag, SLoginFragment())
            VERIFICATION_TAG -> checkAndAttachFragment(fragmentTag, SVerificationFragment())
        }
    }

    private fun checkAndAttachFragment(fragmentTag: String, fragment: Fragment) {
        supportFragmentManager.apply {
            if (findFragmentByTag(fragmentTag) == null) {
                beginTransaction()
                    .add(R.id.fl_authentication, fragment, fragmentTag)
                    .commit()
            } else {
                findFragmentByTag(fragmentTag)?.let {
                    beginTransaction()
                        .remove(it)
                        .add(R.id.fl_authentication, fragment, fragmentTag)
                        .commit()
                }
            }
        }
    }

//    fun navigateChatScreen() {
//        Intent(this, CLContactListActivity::class.java).apply {
//            startActivity(this)
//            finish()
//        }
//    }
}
package authentication


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.ViewModelProvider
import com.example.strng.R
import com.example.strng.databinding.SActivityAuthenticationBinding
import java.security.SecureRandom
import java.util.*

class SAuthenticationActivity : AppCompatActivity() {
    private lateinit var authenticationBinding: SActivityAuthenticationBinding
    private lateinit var authenticationViewModel: SAuthenticationViewModel

    //    var mResult = ""
//    private val mRandom: Random = SecureRandom()
    companion object {
        const val LOGIN_TAG = "LOGIN"
        const val VERIFICATION_TAG = "OTP"
        const val TAG = "SAuthenticationActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authenticationBinding = SActivityAuthenticationBinding.inflate(layoutInflater)
        authenticationViewModel = ViewModelProvider(this).get(SAuthenticationViewModel::class.java)
        setContentView(authenticationBinding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<SLoginFragment>(R.id.fc_authentication_login)
                addToBackStack(null)
            }
        }
//        sendSafetyNetRequest()
//        shareResult()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.findFragmentById(R.id.fc_authentication_login)?.isVisible == true) {
            super.onBackPressed()
            finish()
        } else {
            supportFragmentManager.apply {
                val previousFragment = findFragmentById(R.id.fc_authentication_verification)
                if (previousFragment != null) {
                    beginTransaction()
                        .remove(previousFragment)
                        .commit()
                    commit {
                        setReorderingAllowed(true)
                        replace<SLoginFragment>(R.id.fc_authentication_login)
                        addToBackStack(null)
                    }
                }
            }
        }
    }

    fun getViewModelAuthentication() = authenticationViewModel

//    fun navigateToFragment(fragmentTag: String) {
//        when (fragmentTag) {
//            LOGIN_TAG -> checkAndAttachFragment(fragmentTag, SLoginFragment())
//            VERIFICATION_TAG -> checkAndAttachFragment(fragmentTag, SVerificationFragment())
//        }
//    }
//
//    private fun checkAndAttachFragment(fragmentTag: String, fragment: Fragment) {
//        supportFragmentManager.apply {
//            if (findFragmentByTag(fragmentTag) == null) {
//                beginTransaction()
//                    .replace(R.id.fl_authentication, fragment, fragmentTag)
//                    .commit()
//            } else {
//                findFragmentByTag(fragmentTag)?.let {
//                    beginTransaction()
//                        .remove(it)
//                        .replace(R.id.fl_authentication, fragment, fragmentTag)
//                        .commit()
//                }
//            }
//        }
//    }


//    private fun sendSafetyNetRequest() {
//        Log.i(TAG, "Sending SafetyNet API request.")
//
//        /*
//        Create a nonce for this request.
//        The nonce is returned as part of the response from the
//        SafetyNet API. Here we append the string to a number of random bytes to ensure it larger
//        than the minimum 16 bytes required.
//        Read out this value and verify it against the original request to ensure the
//        response is correct and genuine.
//        NOTE: A nonce must only be used once and a different nonce should be used for each request.
//        As a more secure option, you can obtain a nonce from your own server using a secure
//        connection. Here in this sample, we generate a String and append random bytes, which is not
//        very secure. Follow the tips on the Security Tips page for more information:
//        https://developer.android.com/training/articles/security-tips.html#Crypto
//         */
//        // TODO(developer): Change the nonce generation to include your own, used once value,
//        // ideally from your remote server.
//        val nonceData = "Safety Net Sample: " + System.currentTimeMillis()
//        val nonce = getRequestNonce(nonceData)
//
//        /*
//         Call the SafetyNet API asynchronously.
//         The result is returned through the success or failure listeners.
//         First, get a SafetyNetClient for the foreground Activity.
//         Next, make the call to the attestation API. The API key is specified in the gradle build
//         configuration and read from the gradle.properties file.
//         */
//        val client = SafetyNet.getClient(this)
//        task.addOnSuccessListener(this, mSuccessListener)
//            .addOnFailureListener(this, mFailureListener)
//    }
//
//    /**
//     * Generates a 16-byte nonce with additional data.
//     * The nonce should also include additional information, such as a user id or any other details
//     * you wish to bind to this attestation. Here you can provide a String that is included in the
//     * nonce after 24 random bytes. During verification, extract this data again and check it
//     * against the request that was made with this nonce.
//     */
//    private fun getRequestNonce(data: String): ByteArray? {
//        val byteStream = ByteArrayOutputStream()
//        val bytes = ByteArray(24)
//        mRandom.nextBytes(bytes)
//        try {
//            byteStream.write(bytes)
//            byteStream.write(data.toByteArray())
//        } catch (e: IOException) {
//            return null
//        }
//        return byteStream.toByteArray()
//    }
//
//    /**
//     * Called after successfully communicating with the SafetyNet API.
//     * The #onSuccess callback receives an
//     * [com.google.android.gms.safetynet.SafetyNetApi.AttestationResponse] that contains a
//     * JwsResult with the attestation result.
//     */
//    private val mSuccessListener =
//        OnSuccessListener<AttestationResponse> { attestationResponse -> /*
//                          Successfully communicated with SafetyNet API.
//                          Use result.getJwsResult() to get the signed result data. See the server
//                          component of this sample for details on how to verify and parse this result.
//                          */
//            mResult = attestationResponse.jwsResult
//            Log.d(
//                TAG, """
//     Success! SafetyNet result:
//     ${mResult.toString()}
//
//     """.trimIndent()
//            )
//
//            /*
//                              TODO(developer): Forward this result to your server together with
//                              the nonce for verification.
//                              You can also parse the JwsResult locally to confirm that the API
//                              returned a response by checking for an 'error' field first and before
//                              retrying the request with an exponential backoff.
//                              NOTE: Do NOT rely on a local, client-side only check for security, you
//                              must verify the response on a remote server!
//                             */
//        }
//
//    /**
//     * Called when an error occurred when communicating with the SafetyNet API.
//     */
//    private val mFailureListener = OnFailureListener { e ->
//        // An error occurred while communicating with the service.
//        mResult = null.toString()
//        if (e is ApiException) {
//            // An error with the Google Play Services API contains some additional details.
//            Log.d(
//                TAG, "Error: " +
//                        CommonStatusCodes.getStatusCodeString(e.statusCode) + ": " +
//                        e.statusMessage
//            )
//        } else {
//            // A different, unknown type of error occurred.
//            Log.d(TAG, "ERROR! " + e.message)
//        }
//    }
//
//    /**
//     * Shares the result of the SafetyNet API call via an [Intent.ACTION_SEND] intent.
//     * You can use this call to extract the result from the device for testing purposes.
//     */
//    private fun shareResult() {
//        if (mResult == null) {
//            Log.d(TAG, "No result received yet. Run the verification first.")
//            return
//        }
//        val sendIntent = Intent()
//        sendIntent.action = Intent.ACTION_SEND
//        sendIntent.putExtra(Intent.EXTRA_TEXT, mResult)
//        sendIntent.type = "text/plain"
//        startActivity(sendIntent)
//    }
}

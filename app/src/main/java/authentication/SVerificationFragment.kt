package authentication

import android.R.attr.delay
import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import authentication.SAuthenticationActivity.Companion.LOGIN_TAG
import com.example.strng.R
import com.example.strng.databinding.SFragmentVerificationBinding
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import utils.SUtils
import java.util.concurrent.TimeUnit


class SVerificationFragment : Fragment() {
    private lateinit var verificationBinding: SFragmentVerificationBinding
    private lateinit var hostContext: SAuthenticationActivity
    private lateinit var authenticationViewModel: SAuthenticationViewModel
    private lateinit var verificationViewModel: SVerificationViewModel
    private lateinit var alerts: SUtils
    private var mVerificationId = ""
    lateinit var countdown_timer: CountDownTimer
    private var counter = 60
    private lateinit var mAuth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        verificationBinding = SFragmentVerificationBinding.inflate(layoutInflater)
        return verificationBinding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        hostContext = context as SAuthenticationActivity
        authenticationViewModel = hostContext.getViewModelAuthentication()
        verificationViewModel = ViewModelProvider(this).get(SVerificationViewModel::class.java)
        alerts = SUtils(hostContext)
        mAuth = FirebaseAuth.getInstance()
//        sendVerificationCode(authenticationViewModel.mobileNumber)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        intiTextWatchers()
        initData()
        initClickListeners()
        initObservers()
    }

    private fun initToolbar() {
        val radius = (resources.getDimension(R.dimen.spacing_70) / resources.displayMetrics.density)
        val materialShapeDrawable = verificationBinding.toolbarVerification.background as MaterialShapeDrawable
        materialShapeDrawable.shapeAppearanceModel = materialShapeDrawable.shapeAppearanceModel
            .toBuilder()
            .setBottomLeftCorner(CornerFamily.ROUNDED, radius)
            .setBottomRightCorner(CornerFamily.ROUNDED, radius)
            .build()
    }

    private fun intiTextWatchers() {
        with(verificationBinding){
            with(verificationViewModel){
                tieOtp1.doAfterTextChanged {
                    if (it != null) {
                        otp1 = it.last().toString()
                    }
                    tieOtp2.requestFocus()
                }
                tieOtp2.doAfterTextChanged {
                    if (it != null) {
                        otp2 = it.last().toString()
                    }
                    tieOtp3.requestFocus()
                }
                tieOtp3.doAfterTextChanged {
                    if (it != null) {
                        otp3 = it.last().toString()
                    }
                    tieOtp4.requestFocus()
                }
                tieOtp4.doAfterTextChanged {
                    if (it != null) {
                        otp4 = it.last().toString()
                    }
                    tieOtp5.requestFocus()
                }
                tieOtp5.doAfterTextChanged {
                    if (it != null) {
                        otp5 = it.last().toString()
                    }
                    tieOtp6.requestFocus()
                }
                tieOtp6.doAfterTextChanged {
                    if (it != null) {
                        otp6 = it.last().toString()
                    }
                }
            }
        }
    }

    private fun initData() {
        verificationBinding.apply {
            val text =
                "${tvVerificationHeading.text} <font color='#14354A'><b>${authenticationViewModel.mobileNumber}?</b></font>"
            tvVerificationHeading.text = Html.fromHtml(text)
            initTimerForOTP()
        }
    }

    private fun initTimerForOTP(){
//        with(verificationBinding){
//            handler.postDelayed({
//                --counter
//                tvResendOtpTimer.text = counter.toString()
//                if (counter == 0) {
//                    handler.removeCallbacksAndMessages(null)
//                }
//            }, 1000)
//        }

        countdown_timer = object : CountDownTimer(60, 1000) {
            override fun onFinish() {
                println("countdown finished")
            }

            override fun onTick(p0: Long) {
//                time_in_milli_seconds = p0
//                updateTextUI()
                println("tick tick $p0")
            }
        }
        countdown_timer.start()

    }

    private fun initClickListeners() {
        with(verificationBinding){
            btnVerification.setOnClickListener {
                verificationViewModel.checkOTP()
            }
            tvVerificationHeading.setOnClickListener {
                hostContext.navigateToFragment(LOGIN_TAG)
            }
            tvResendOtp.setOnClickListener {
                initTimerForOTP()
            }
        }
    }

    private fun initObservers(){
        verificationViewModel.isOTPNumberValid.observe(viewLifecycleOwner, {
            if (it) {
                with(verificationViewModel) {
                    verifyVerificationCode("$otp1$otp2$otp3$otp4$otp5$otp6")
                }
            } else {
                //error --> enter valid otp
            }
        })
    }

    private fun sendVerificationCode(number: String) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            "+91$number",
            60,
            TimeUnit.SECONDS,
            hostContext,
            mCallbacks
        )
    }

    private val mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks =
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                val code = phoneAuthCredential.smsCode
                if (code != null) {
//                    verificationBinding.tieOtp4.setText(code)
                    verifyVerificationCode(code)
                }
            }

            override fun onVerificationFailed(e: FirebaseException) {
                verificationBinding.tvVerificationResult.text = e.message
                Toast.makeText(hostContext, e.message, Toast.LENGTH_LONG).show()
            }

            override fun onCodeSent(
                s: String,
                forceResendingToken: PhoneAuthProvider.ForceResendingToken
            ) {
                super.onCodeSent(s, forceResendingToken)
                mVerificationId = s
            }
        }


    private fun verifyVerificationCode(code: String) {
        val credential = PhoneAuthProvider.getCredential(mVerificationId, code)
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(
                hostContext
            ) { task ->
                if (task.isSuccessful) {
                    verificationBinding.tvVerificationResult.text = "Authentication Successful"
                    println("Authentication Successful")
                } else {
                    verificationBinding.tvVerificationResult.text = "Authentication Failed"
                    println("Authentication Failed")
                }
            }
    }
}
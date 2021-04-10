package authentication

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.ViewModelProvider
import authentication.SAuthenticationActivity.Companion.LOGIN_TAG
import chats.SChatsActivity
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


class SVerificationFragment : Fragment(R.layout.s_fragment_verification) {
    private lateinit var verificationBinding: SFragmentVerificationBinding
    private lateinit var hostContext: SAuthenticationActivity
    private lateinit var authenticationViewModel: SAuthenticationViewModel
    private lateinit var verificationViewModel: SVerificationViewModel
    private lateinit var alerts: SUtils
    private var mVerificationId = ""
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
        val materialShapeDrawable =
                verificationBinding.toolbarVerification.background as MaterialShapeDrawable
        materialShapeDrawable.shapeAppearanceModel = materialShapeDrawable.shapeAppearanceModel
                .toBuilder()
                .setBottomLeftCorner(CornerFamily.ROUNDED, radius)
                .setBottomRightCorner(CornerFamily.ROUNDED, radius)
                .build()
    }

    private fun intiTextWatchers() {
        with(verificationBinding) {
            with(verificationViewModel) {
                tieOtp1.doAfterTextChanged {
                    if (it != null && tieOtp1.text.toString().length > 1) {
                        otp1 = it.last().toString()
                        tieOtp1.setText(it.last().toString())
                    } else if (tieOtp1.text.toString().length == 1) {
                        tieOtp2.requestFocus()
                    }
                }
                tieOtp2.doAfterTextChanged {
                    if (it != null && tieOtp2.text.toString().length > 1) {
                        otp2 = it.last().toString()
                        tieOtp2.setText(it.last().toString())
                    } else if (it.toString().isNullOrEmpty()) {
                        tieOtp1.requestFocus()
                    } else if (tieOtp2.text.toString().length == 1) {
                        tieOtp3.requestFocus()
                    }
                }
                tieOtp3.doAfterTextChanged {
                    if (it != null && tieOtp3.text.toString().length > 1) {
                        otp3 = it.last().toString()
                        tieOtp3.setText(it.last().toString())
                    } else if (it.toString().isNullOrEmpty()) {
                        tieOtp2.requestFocus()
                    } else if (tieOtp3.text.toString().length == 1) {
                        tieOtp4.requestFocus()
                    }
                }
                tieOtp4.doAfterTextChanged {
                    if (it != null && tieOtp4.text.toString().length > 1) {
                        otp4 = it.last().toString()
                        tieOtp4.setText(it.last().toString())
                    } else if (it.toString().isNullOrEmpty()) {
                        tieOtp3.requestFocus()
                    } else if (tieOtp4.text.toString().length == 1) {
                        tieOtp5.requestFocus()
                    }
                }
                tieOtp5.doAfterTextChanged {
                    if (it != null && tieOtp5.text.toString().length > 1) {
                        otp5 = it.last().toString()
                        tieOtp5.setText(it.last().toString())
                    } else if (it.toString().isNullOrEmpty()) {
                        tieOtp4.requestFocus()
                    } else if (tieOtp5.text.toString().length == 1) {
                        tieOtp6.requestFocus()
                    }
                }
                tieOtp6.doAfterTextChanged {
                    if (it != null && tieOtp6.text.toString().length > 1) {
                        otp6 = it.last().toString()
                        tieOtp6.setText(it.last().toString())
                    } else if (it.toString().isNullOrEmpty()) {
                        tieOtp5.requestFocus()
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

    private fun initTimerForOTP() {
        var counter = 60
        verificationViewModel.countDownTimer.value = counter
        object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                verificationBinding.tvResendOtpTimer.text =
                        "${verificationViewModel.countDownTimer.value.toString()}s"
                verificationViewModel.countDownTimer.value = --counter
            }

            override fun onFinish() {
            }
        }.start()

    }

    private fun initClickListeners() {
        with(verificationBinding) {
            btnVerification.setOnClickListener {
//                verificationViewModel.checkOTP()
                Intent(hostContext, SChatsActivity::class.java).apply {
                    startActivity(this)
                    hostContext.finish()
                }
            }
            tvVerificationHeading.setOnClickListener {
                hostContext.supportFragmentManager.apply {
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
    }

    private fun initObservers() {
        with(verificationViewModel) {
            isOTPNumberValid.observe(viewLifecycleOwner, {
                if (it) {
                    with(verificationViewModel) {
                        verifyVerificationCode("$otp1$otp2$otp3$otp4$otp5$otp6")
                    }
                } else {
                    verificationBinding.tvVerificationResult.text = "Enter valid OTP"
                }
            })
            countDownTimer.observe(viewLifecycleOwner, {
                if (it != null && it == 0) {
                    with(verificationBinding) {
                        tvResendOtp.typeface = Typeface.DEFAULT_BOLD
                        tvResendOtp.setTextColor(resources.getColor(R.color.appColor))
                    }
                    verificationBinding.tvResendOtp.setOnClickListener {
                        if (countDownTimer.value == 0)
                            initTimerForOTP()
//                        sendVerificationCode(authenticationViewModel.mobileNumber)
                    }
                } else {
                    with(verificationBinding) {
                        tvResendOtp.typeface = Typeface.DEFAULT
                        tvResendOtp.setTextColor(resources.getColor(R.color.appText))
                    }
                }
            })
        }
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
                    println("phone auth output   $phoneAuthCredential")
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
                    verificationBinding.tvVerificationResult.text = "Verification Code Sent"
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
package authentication

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import authentication.SAuthenticationActivity.Companion.VERIFICATION_TAG
import com.example.strng.R
import com.example.strng.databinding.SFragmentLoginBinding
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import utils.SUtils

class SLoginFragment : Fragment() {
    private lateinit var loginBinding: SFragmentLoginBinding
    private lateinit var hostContext: SAuthenticationActivity
    private lateinit var loginViewModel: SLoginViewModel
    private lateinit var authenticationViewModel: SAuthenticationViewModel
    private lateinit var alerts: SUtils

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        loginBinding = SFragmentLoginBinding.inflate(layoutInflater)
        loginViewModel = ViewModelProvider(this).get(SLoginViewModel::class.java)
        return loginBinding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        hostContext = context as SAuthenticationActivity
        authenticationViewModel = hostContext.getViewModelAuthentication()
        alerts = SUtils(hostContext)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        intiTextWatchers()
        initObservers()
        initClickListeners()

    }

    private fun initToolbar() {
        val radius = (resources.getDimension(R.dimen.spacing_70) / resources.displayMetrics.density)
        val materialShapeDrawable = loginBinding.toolbarLogin.background as MaterialShapeDrawable
        materialShapeDrawable.shapeAppearanceModel = materialShapeDrawable.shapeAppearanceModel
            .toBuilder()
            .setBottomLeftCorner(CornerFamily.ROUNDED, radius)
            .setBottomRightCorner(CornerFamily.ROUNDED, radius)
            .build()

    }

    private fun intiTextWatchers() {
        loginBinding.tieMobileNumber.doAfterTextChanged {
            loginViewModel.mobileNumber = it.toString()
            authenticationViewModel.mobileNumber = it.toString()
        }
    }

    private fun initObservers(){
        loginViewModel.isMobileNumberValid.observe(viewLifecycleOwner, {
            if(it){
                hostContext.navigateToFragment(VERIFICATION_TAG)
            }else{
                loginBinding.tieMobileNumber.error = getString(R.string.error_mobile_number_invalid)
            }
        })
    }

    private fun initClickListeners(){
        loginBinding.btnGetIn.setOnClickListener {
            loginViewModel.checkForValidMobileNumber()
        }
    }
}
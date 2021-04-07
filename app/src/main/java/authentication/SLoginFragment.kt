package authentication

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.strng.R
import com.example.strng.databinding.SFragmentLoginBinding
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import utils.SUtils

class SLoginFragment : Fragment() {
    private lateinit var loginBinding: SFragmentLoginBinding
    private lateinit var hostContext: SAuthenticationActivity
    private lateinit var alerts: SUtils
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        loginBinding = SFragmentLoginBinding.inflate(layoutInflater)
        return loginBinding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        hostContext = context as SAuthenticationActivity
        alerts = SUtils(hostContext)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val radius = (resources.getDimension(R.dimen.spacing_70) / resources.displayMetrics.density)
        val materialShapeDrawable = loginBinding.toolbarLogin.background as MaterialShapeDrawable
        materialShapeDrawable.shapeAppearanceModel = materialShapeDrawable.shapeAppearanceModel
            .toBuilder()
            .setBottomLeftCorner(CornerFamily.ROUNDED, radius)
            .setBottomRightCorner(CornerFamily.ROUNDED, radius)
            .build()
    }
}
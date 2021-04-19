package profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import authentication.SAuthenticationActivity
import com.example.strng.R
import com.example.strng.databinding.SFragmentProfileEditBinding
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import utils.SUtils

data class FakeImages(var data: String? = null)
class SProfileEditFragment: Fragment(R.layout.s_fragment_profile_edit)  {
    private val profileEditBinding by lazy{
        SFragmentProfileEditBinding.inflate(layoutInflater)
    }
    private lateinit var profileActivityContext: SProfileActivity
    private var fakeList = mutableListOf(FakeImages(), FakeImages(), FakeImages())
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        profileEditBinding = SFragmentProfileEditBinding.inflate(layoutInflater)
        return profileEditBinding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        profileActivityContext = context as SProfileActivity
        profileEditBinding.rvProfileImages.layoutManager  = SProfileImageLayoutManager(profileActivityContext, LinearLayoutManager.HORIZONTAL, false)
        profileEditBinding.rvProfileImages.adapter = SProfileImageAdapter(fakeList)
        profileEditBinding.rvProfileImages.smoothScrollToPosition(1)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
    }
    private fun initToolbar() {
        val radius = (resources.getDimension(R.dimen.spacing_70) / resources.displayMetrics.density)
        val materialShapeDrawable = profileEditBinding.toolbarProfileEdit.background as MaterialShapeDrawable
        materialShapeDrawable.shapeAppearanceModel = materialShapeDrawable.shapeAppearanceModel
            .toBuilder()
            .setBottomLeftCorner(CornerFamily.ROUNDED, radius)
            .setBottomRightCorner(CornerFamily.ROUNDED, radius)
            .build()

    }
}
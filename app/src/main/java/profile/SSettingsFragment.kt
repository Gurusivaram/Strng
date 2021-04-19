package profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.strng.R
import com.example.strng.databinding.SFragmentSettingsBinding

class SSettingsFragment: Fragment(R.layout.s_fragment_settings)  {
    private val settingsBinding by lazy{
        SFragmentSettingsBinding.inflate(layoutInflater)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return settingsBinding.root
    }
}
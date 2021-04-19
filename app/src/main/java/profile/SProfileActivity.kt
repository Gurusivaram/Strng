package profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.strng.R
import com.example.strng.databinding.SActivityProfileBinding

class SProfileActivity : AppCompatActivity() {
    private lateinit var profileBinding: SActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        profileBinding = SActivityProfileBinding.inflate(layoutInflater)
        setContentView(profileBinding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<SProfileEditFragment>(R.id.fc_profile_edit)
                addToBackStack(null)
            }
        }
    }
}
package splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import authentication.SAuthenticationActivity
import com.example.strng.databinding.SActivitySplashBinding
import constants.SConstants.SPLASH_SHOW_TIME

class SSplashActivity : AppCompatActivity() {
    private lateinit var splashBinding: SActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashBinding = SActivitySplashBinding.inflate(layoutInflater)
        setContentView(splashBinding.root)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        Handler().postDelayed({
            val intent = Intent(this, SAuthenticationActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_SHOW_TIME.toLong())
    }
}
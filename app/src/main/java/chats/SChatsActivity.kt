package chats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.strng.R
import com.example.strng.databinding.SActivityChatsBinding
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable

class SChatsActivity : AppCompatActivity() {
    private lateinit var chatsBinding: SActivityChatsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        chatsBinding = SActivityChatsBinding.inflate(layoutInflater)
        setContentView(chatsBinding.root)
    }

    override fun onStart() {
        super.onStart()
        initToolbar()
    }

    private fun initToolbar() {
        val radius = (resources.getDimension(R.dimen.spacing_70) / resources.displayMetrics.density)
        val materialShapeDrawable = chatsBinding.toolbarChats.background as MaterialShapeDrawable
        materialShapeDrawable.shapeAppearanceModel = materialShapeDrawable.shapeAppearanceModel
                .toBuilder()
                .setBottomLeftCorner(CornerFamily.ROUNDED, radius)
                .setBottomRightCorner(CornerFamily.ROUNDED, radius)
                .build()

    }

}
package chats

import data.FakeUserData
import android.content.Intent
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.strng.R
import com.example.strng.databinding.SActivityChatsBinding
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable


class SChatsActivity : AppCompatActivity() {
    private lateinit var chatsBinding: SActivityChatsBinding
    private var chats = mutableListOf(
            FakeUserData("Mark Zukerberg"),
            FakeUserData("Bill Gates"),
            FakeUserData("Steve Jobs"),
            FakeUserData("Bill Gates"),
            FakeUserData("Steve Jobs"),
            FakeUserData("Bill Gates"),
            FakeUserData("Steve Jobs"),
            FakeUserData("Bill Gates"),
            FakeUserData("Steve Jobs"),
            FakeUserData("Bill Gates"),
            FakeUserData("Steve Jobs"),
            FakeUserData("Bill Gates"),
            FakeUserData("Steve Jobs"),
            FakeUserData("Bill Gates"),
            FakeUserData("Steve Jobs"),
            FakeUserData("Bill Gates"),
            FakeUserData("Steve Jobs"),
            FakeUserData("Bill Gates"),
            FakeUserData("Steve Jobs"),
            FakeUserData("Bill Gates"),
            FakeUserData("Steve Jobs"),
            FakeUserData("Bill Gates"),
            FakeUserData("Steve Jobs"),
            FakeUserData("Bill Gates"),
            FakeUserData("Steve Jobs"),
            FakeUserData("Bill Gates"),
            FakeUserData("Steve Jobs"),
            FakeUserData("Bill Gates"),
            FakeUserData("Steve Jobs"),
            FakeUserData("Bill Gates"),
            FakeUserData("Steve Jobs"),
    )
    private var chatsAdapter = SChatsAdapter(chats)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        chatsBinding = SActivityChatsBinding.inflate(layoutInflater)
        setContentView(chatsBinding.root)
        initToolbar()
        chatsBinding.rvChats.layoutManager = LinearLayoutManager(this)
        chatsBinding.rvChats.adapter = chatsAdapter
        chatsBinding.rvChats.addItemDecoration(DividerItemDecorator(resources.getDrawable(R.drawable.divider_style, null)))

        chatsBinding.ibMenu.setOnClickListener {
            PopupMenu(this, it).apply {
                menuInflater.inflate(R.menu.menu_chats_screen, menu)
                setOnMenuItemClickListener {  it1->
                    println("Menu clicked   ${it1.title}")
                    true
                }
            }.show()
        }

        chatsBinding.btnFab.setOnClickListener {
            Intent(this, SContactsActivity::class.java).apply {
                startActivity(this)
            }
        }
    }

    private fun initToolbar() {
        val radius = (resources.getDimension(R.dimen.spacing_70) / resources.displayMetrics.density)
        val materialShapeDrawable =
                chatsBinding.toolbarChats.background as MaterialShapeDrawable
        materialShapeDrawable.shapeAppearanceModel = materialShapeDrawable.shapeAppearanceModel
                .toBuilder()
                .setBottomLeftCorner(CornerFamily.ROUNDED, radius)
                .setBottomRightCorner(CornerFamily.ROUNDED, radius)
                .build()
    }

}

class DividerItemDecorator(private val divider: Drawable?) : RecyclerView.ItemDecoration() {

    override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val dividerLeft = parent.paddingLeft
        val dividerRight = parent.width - parent.paddingRight
        val childCount = parent.childCount
        for (i in 0..childCount - 2) {
            val child: View = parent.getChildAt(i)
            val params =
                    child.layoutParams as RecyclerView.LayoutParams
            val dividerTop: Int = child.bottom + params.bottomMargin
            val dividerBottom = dividerTop + (divider?.intrinsicHeight?:0)
            divider?.setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom)
            divider?.draw(canvas)
        }
    }
}
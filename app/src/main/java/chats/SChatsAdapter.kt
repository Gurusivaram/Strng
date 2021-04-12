package chats

import data.FakeUserData
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.strng.databinding.SChatsItemBinding

class SChatsAdapter(private var chats: MutableList<FakeUserData>) : RecyclerView.Adapter<SChatsAdapter.ViewHolder>() {
    inner class ViewHolder(private val chatItemBinding: SChatsItemBinding): RecyclerView.ViewHolder(chatItemBinding.root){
        fun bind(item: FakeUserData){
            with(chatItemBinding){
                tvChatItemProfileName.text = item.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val chatItemBinding = SChatsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(chatItemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(chats[holder.adapterPosition])
    }

    override fun getItemCount(): Int = chats.size
}
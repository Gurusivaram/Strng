package chats

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.strng.databinding.SChatsItemBinding
import data.SContactModel

class SContactsAdapter(private var contacts: MutableList<SContactModel>) : RecyclerView.Adapter<SContactsAdapter.ViewHolder>() {
    inner class ViewHolder(private val chatItemBinding: SChatsItemBinding): RecyclerView.ViewHolder(chatItemBinding.root){
        fun bind(item: SContactModel){
            with(chatItemBinding){
                tvChatItemProfileName.text = item.name
                tvChatItemLastChat.text = item.number
                tvChatItemTimeStamp.visibility = View.INVISIBLE
                tvChatItemUnreadCount.visibility = View.INVISIBLE
                ivChatItemSeen.visibility = View.INVISIBLE
                tvChatItemActiveStatus.visibility = View.INVISIBLE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val chatItemBinding = SChatsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(chatItemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(contacts[holder.adapterPosition])
    }

    override fun getItemCount(): Int = contacts.size
}
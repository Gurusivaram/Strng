package profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginStart
import androidx.recyclerview.widget.RecyclerView
import com.example.strng.databinding.SChatsItemBinding
import com.example.strng.databinding.SProfileImageItemBinding
import data.SContactModel

class SProfileImageAdapter(private var contacts: MutableList<FakeImages>) : RecyclerView.Adapter<SProfileImageAdapter.ViewHolder>() {
    inner class ViewHolder(private val profileImageBinding: SProfileImageItemBinding): RecyclerView.ViewHolder(profileImageBinding.root){
        fun bind(item: FakeImages, position: Int){
            with(profileImageBinding){
                if(position == 0){
                    profileImageBinding.ivProfileImage.marginStart
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val profileImageBinding = SProfileImageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(profileImageBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(contacts[holder.adapterPosition], holder.adapterPosition)
    }

    override fun getItemCount(): Int = contacts.size
}
package id.tix.gitusers.features.presentation.users.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.tix.gitusers.R
import id.tix.gitusers.databinding.ItemUserBinding
import id.tix.gitusers.features.domain.entity.model.User

class UserAdapter(val onClick : (User?)->Unit) : PagingDataAdapter<User, UserAdapter.UserViewHolder>(
    object : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }
) {

    var header = LoadingStateHeaderAdapter(this)
    var footer = LoadingStateFooterAdapter(this)

    fun withLoadStates() = ConcatAdapter(header,this,footer)

    class UserViewHolder(private val binding : ItemUserBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(user: User?, onClick: (User?) -> Unit){
            binding.user = user
            Glide.with(binding.imgProfile).load(user?.avatar_url).placeholder(R.drawable.ic_baseline_account_circle_24).circleCrop().into(binding.imgProfile)
            binding.root.setOnClickListener {
                onClick(user)
            }
        }
    }



    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position), onClick)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
}
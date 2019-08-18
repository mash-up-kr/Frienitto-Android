package com.mashup.frienitto.room.close

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mashup.frienitto.data.UserPreview
import com.mashup.frienitto.databinding.ListItemMatchFriendBinding
import com.mashup.frienitto.room.home.RoomUserDiffUtilCallBack

class RoomCloseListAdapter(private val listener: (item: UserPreview) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val userItemList = ArrayList<UserPreview>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MovieItemViewHolder(
            ListItemMatchFriendBinding.inflate(LayoutInflater.from(parent.context), parent, false), listener
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MovieItemViewHolder).binding.item = userItemList[position]
        holder.binding.executePendingBindings()
        holder.binding.root.setOnClickListener { listener.invoke(userItemList[position]) }
    }

    override fun getItemCount(): Int {
        return userItemList.size
    }

    //기록된 활동들에 대한 뷰홀더
    class MovieItemViewHolder(
        val binding: ListItemMatchFriendBinding,
        val listener: (item: UserPreview) -> Unit
    ) : RecyclerView.ViewHolder(binding.root)

    fun updateListItems(movieItem: ArrayList<UserPreview>) {
        userItemList.clear()
        userItemList.addAll(movieItem)
        notifyDataSetChanged()
    }
}
package com.mashup.frienitto.room.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mashup.frienitto.data.UserPreview
import com.mashup.frienitto.databinding.ListItemSmallUserBinding

class RoomUserListAdapter(private val listener: (item: UserPreview) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val userItemList = ArrayList<UserPreview>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MovieItemViewHolder(
                ListItemSmallUserBinding.inflate(LayoutInflater.from(parent.context), parent, false), listener)
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
    class MovieItemViewHolder(var binding: ListItemSmallUserBinding,
                              val listener: (item: UserPreview) -> Unit) : RecyclerView.ViewHolder(binding.root)

    fun updateListItems(movieItem: ArrayList<UserPreview>) {
        val diffCallback = RoomUserDiffUtilCallBack(movieItem, userItemList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        userItemList.clear()
        userItemList.addAll(movieItem)
        diffResult.dispatchUpdatesTo(this)
    }
}
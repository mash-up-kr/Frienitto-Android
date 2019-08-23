package com.mashup.frienitto.matching.finish

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mashup.frienitto.data.Mission
import com.mashup.frienitto.data.UserPreview
import com.mashup.frienitto.databinding.ListItemMatchFriendBinding

class MatchingFinishListAdapter(private val listener: (item: Mission) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val userItemList = ArrayList<Mission>()

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
        val listener: (item: Mission) -> Unit
    ) : RecyclerView.ViewHolder(binding.root)

    fun updateListItems(movieItem: ArrayList<Mission>) {
        userItemList.clear()
        userItemList.addAll(movieItem)
        notifyDataSetChanged()
    }
}
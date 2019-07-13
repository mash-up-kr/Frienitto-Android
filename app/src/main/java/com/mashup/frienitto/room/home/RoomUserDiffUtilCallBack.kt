package com.mashup.frienitto.room.home
import androidx.recyclerview.widget.DiffUtil
import com.mashup.frienitto.data.UserPreview

class RoomUserDiffUtilCallBack(private var newList: ArrayList<UserPreview>?,
                               private var oldList: ArrayList<UserPreview>?) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList?.size ?: 0
    }

    override fun getNewListSize(): Int {
        return newList?.size ?: 0
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList?.get(oldItemPosition)?.id == newList?.get(newItemPosition)?.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return newList?.get(newItemPosition)?.userName == oldList?.get(oldItemPosition)?.userName
    }
}
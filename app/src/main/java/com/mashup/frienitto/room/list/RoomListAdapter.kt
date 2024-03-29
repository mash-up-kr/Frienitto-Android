package com.mashup.frienitto.room.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mashup.frienitto.data.RoomInfo
import com.mashup.frienitto.databinding.ItemRoomCardBinding
import com.mashup.frienitto.databinding.ItemRoomCreationCardBinding


const val ROOM_CARD = 1
const val ROOM_CREATION = 2

class RoomListAdapter(private val roomListViewModel: RoomListViewModel) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val roomList = ArrayList<RoomInfo>()
    override fun getItemCount(): Int {
        return roomList.size + 1
    }

    fun updateListItems(list: ArrayList<RoomInfo>) {
        roomList.clear()
        roomList.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (position >= roomList.size) {
            ROOM_CREATION
        } else {
            ROOM_CARD
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ROOM_CARD -> {
                RoomCardViewHolder(
                    ItemRoomCardBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }
            else -> {
                RoomCreationViewHolder(
                    ItemRoomCreationCardBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ROOM_CARD -> {
                with((holder as RoomCardViewHolder).binding) {
                    this.item = roomList[position]
                    this.viewModel = roomListViewModel
                    this.executePendingBindings()
                }

            }
            ROOM_CREATION -> {
                with((holder as RoomCreationViewHolder).binding) {
                    this.viewModel = roomListViewModel
                    this.executePendingBindings()
                }
            }
        }

    }


    class RoomCardViewHolder(val binding: ItemRoomCardBinding) :
        RecyclerView.ViewHolder(binding.root)

    class RoomCreationViewHolder(val binding: ItemRoomCreationCardBinding) :
        RecyclerView.ViewHolder(binding.root)


}

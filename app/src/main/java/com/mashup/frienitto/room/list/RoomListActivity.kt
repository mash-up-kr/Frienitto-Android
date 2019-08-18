package com.mashup.frienitto.room.list

import android.os.Bundle
import androidx.lifecycle.Observer
import com.mashup.frienitto.R
import com.mashup.frienitto.base.BaseActivity
import com.mashup.frienitto.data.RoomInfo
import com.mashup.frienitto.databinding.ActivityRoomListBinding
import org.koin.android.viewmodel.ext.android.viewModel

class RoomListActivity(override val layoutResourceId: Int = R.layout.activity_room_list) :
    BaseActivity<ActivityRoomListBinding>() {

    private val viewModel: RoomListViewModel by viewModel()
    private val adapter: RoomListAdapter by lazy {
        RoomListAdapter(viewModel)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewDataBinding.root)
        viewDataBinding.viewModel = viewModel
        viewDataBinding.lifecycleOwner = this

        viewModel.roomList.observe(this, Observer {
            adapter.updateListItems(it as ArrayList<RoomInfo>)
        })

    }
}

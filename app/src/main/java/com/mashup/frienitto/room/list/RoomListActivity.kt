package com.mashup.frienitto.room.list

import android.os.Bundle
import com.mashup.frienitto.R
import com.mashup.frienitto.base.BaseActivity
import com.mashup.frienitto.databinding.ActivityRoomListBinding
import org.koin.android.viewmodel.ext.android.viewModel

class RoomListActivity(override val layoutResourceId: Int = R.layout.activity_room_list) :
    BaseActivity<ActivityRoomListBinding>() {

    private val viewModel: RoomListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewDataBinding.root)
        viewDataBinding.viewModel = viewModel
        viewDataBinding.lifecycleOwner = this


    }
}

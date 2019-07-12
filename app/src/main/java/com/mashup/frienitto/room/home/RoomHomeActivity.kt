package com.mashup.frienitto.room.home

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.mashup.frienitto.R
import com.mashup.frienitto.base.BaseActivity
import com.mashup.frienitto.data.ResponseRoom
import com.mashup.frienitto.data.UserPreview
import com.mashup.frienitto.databinding.ActivityRoomHomeBinding
import org.koin.android.viewmodel.ext.android.viewModel

class RoomHomeActivity : BaseActivity<ActivityRoomHomeBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_room_home

    private val viewModel:RoomHomeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewDataBinding.root)
        viewDataBinding.viewModel = viewModel
        viewDataBinding.roomModel = ResponseRoom(11, "마니또 방이름", "2020-01-02", "", viewModel.data.value!!)

        initView()
        ovbserveItem()
    }

    private fun initView() {
        viewDataBinding.rvRoomHome.layoutManager = GridLayoutManager(this, 2)
        viewDataBinding.rvRoomHome.adapter = RoomUserListAdapter { item ->
            //TODO onclick
        }
    }

    private fun ovbserveItem() {
        viewModel.data.observe(this, Observer {
            (viewDataBinding.rvRoomHome.adapter as RoomUserListAdapter).updateListItems(it as ArrayList<UserPreview>)
        })
    }
}

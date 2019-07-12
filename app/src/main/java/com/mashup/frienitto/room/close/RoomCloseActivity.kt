package com.mashup.frienitto.room.close

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.mashup.frienitto.R
import com.mashup.frienitto.base.BaseActivity
import com.mashup.frienitto.data.UserPreview
import com.mashup.frienitto.databinding.ActivityRoomCloseBinding
import com.mashup.frienitto.room.home.RoomUserListAdapter
import org.koin.android.viewmodel.ext.android.viewModel

class RoomCloseActivity : BaseActivity<ActivityRoomCloseBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_room_close

    private val viewModel: RoomCloseViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewDataBinding.root)
        viewDataBinding.viewModel = viewModel

        initView()
        observeItem()
    }

    private fun initView() {
        viewDataBinding.rvRoomClose.layoutManager = LinearLayoutManager(this)
        viewDataBinding.rvRoomClose.adapter = RoomCloseListAdapter { item ->
            //TODO onclick
        }
    }

    private fun observeItem() {
        viewModel.data.observe(this, Observer {
            (viewDataBinding.rvRoomClose.adapter as RoomCloseListAdapter).updateListItems(it as ArrayList<UserPreview>)
        })
    }
}

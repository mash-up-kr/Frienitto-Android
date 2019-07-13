package com.mashup.frienitto.room.mypage

import android.os.Bundle
import com.mashup.frienitto.R
import com.mashup.frienitto.base.BaseActivity
import com.mashup.frienitto.databinding.ActivityRoomMyPageBinding
import org.koin.android.viewmodel.ext.android.viewModel

class RoomMyPageActivity : BaseActivity<ActivityRoomMyPageBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_room_my_page
    private val viewModel: RoomMyPageViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewDataBinding.root)
        viewDataBinding.viewModel = viewModel
        //viewDataBinding.roomModel = ResponseRoom(11, "마니또 방이름", "2020-01-02", "", viewModel.data.value!!)
    }
}
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
import android.R.layout
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.mashup.frienitto.data.ResponseRoomDetailData
import com.mashup.frienitto.repository.room.RoomRepository
import com.mashup.frienitto.utils.setUserImage
import kotlinx.android.synthetic.main.dialog_user_datail_layout.view.*
import org.koin.android.ext.android.inject


class RoomHomeActivity : BaseActivity<ActivityRoomHomeBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_room_home

    private val viewModel: RoomHomeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewDataBinding.root)
        viewDataBinding.viewModel = viewModel

        initView()
        ovbserveItem()
    }

    private fun initView() {
        viewDataBinding.rvRoomHome.layoutManager = GridLayoutManager(this, 2)
        viewDataBinding.rvRoomHome.adapter = RoomUserListAdapter { item ->
            //TODO onclick
            createDialog(item.imageCode, item.userName)
        }
    }

    fun createDialog(imageCode: Int, name: String) {
        val dialogBuilder = AlertDialog.Builder(this@RoomHomeActivity)
        val layoutView = layoutInflater.inflate(R.layout.dialog_user_datail_layout, null)
        layoutView.iv_user_image.setUserImage(imageCode)
        layoutView.tv_user_name.text = name
        dialogBuilder.setView(layoutView)
        val alertDialog = dialogBuilder.create()
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()
    }

    private fun ovbserveItem() {
        viewModel.data.observe(this, Observer {
            (viewDataBinding.rvRoomHome.adapter as RoomUserListAdapter).updateListItems(it as ArrayList<UserPreview>)
        })

        viewModel.roomData.observe(this, Observer {
            viewDataBinding.roomModel = it
        })
    }
}

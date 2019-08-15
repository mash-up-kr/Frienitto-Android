package com.mashup.frienitto.room.home

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.mashup.frienitto.R
import com.mashup.frienitto.base.BaseActivity
import com.mashup.frienitto.data.UserPreview
import com.mashup.frienitto.databinding.ActivityRoomHomeBinding
import org.koin.android.viewmodel.ext.android.viewModel
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.mashup.frienitto.matching.MatchingAnimationActivity
import com.mashup.frienitto.utils.setUserImage
import kotlinx.android.synthetic.main.dialog_user_datail_layout.view.*


class RoomHomeActivity : BaseActivity<ActivityRoomHomeBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_room_home

    private val viewModel: RoomHomeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewDataBinding.root)
        viewDataBinding.viewModel = viewModel

        initView()
        observeItem()
    }

    private fun initView() {
        viewDataBinding.rvRoomHome.layoutManager = GridLayoutManager(this, 2)
        viewDataBinding.rvRoomHome.adapter = RoomUserListAdapter { item ->
            //TODO onclick
            createDialog(item.imageCode, item.userName)
        }
    }

    private fun createDialog(imageCode: Int, name: String) {
        val dialogBuilder = AlertDialog.Builder(this@RoomHomeActivity)
        val layoutView = layoutInflater.inflate(R.layout.dialog_user_datail_layout, null)
        layoutView.iv_user_image.setUserImage(imageCode)
        layoutView.tv_user_name.text = name
        dialogBuilder.setView(layoutView)
        val alertDialog = dialogBuilder.create()
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()
        alertDialog.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    private fun observeItem() {
        showProgress()
        viewModel.data.observe(this, Observer {
            (viewDataBinding.rvRoomHome.adapter as RoomUserListAdapter).updateListItems(it as ArrayList<UserPreview>)
            dismissProgress()
        })

        viewModel.roomData.observe(this, Observer {
            viewDataBinding.roomModel = it
        })

        addDisposable(
            viewModel.startMatching.subscribe {
                //Todo 매칭 유무따라 보여지는 페이지 달라져야함
                if (it) {
                    startActivity(Intent(this, MatchingAnimationActivity::class.java).apply{addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)})
                    finish()
                }
            }
        )
    }
}

package com.mashup.frienitto.room.home

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.mashup.frienitto.R
import com.mashup.frienitto.base.BaseActivity
import com.mashup.frienitto.data.UserPreview
import com.mashup.frienitto.databinding.ActivityRoomHomeBinding
import org.koin.android.viewmodel.ext.android.viewModel
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.mashup.frienitto.Constants
import com.mashup.frienitto.RxBus.RxBus
import com.mashup.frienitto.RxBus.RxEvent
import com.mashup.frienitto.matching.MatchingAnimationActivity
import com.mashup.frienitto.adapter.setUserImage
import com.mashup.frienitto.matching.home.MatchingHomeActivity
import kotlinx.android.synthetic.main.dialog_user_datail_layout.view.*
import org.jetbrains.anko.toast
import org.koin.android.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf


class RoomHomeActivity : BaseActivity<ActivityRoomHomeBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_room_home
    
    private lateinit var viewModel: RoomHomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewDataBinding.root)
        initIntentData()
        viewDataBinding.viewModel = viewModel
        initView()
        observeItem()
    }

    private fun initIntentData() {
        val roomId = intent.getIntExtra("roomId", -1)
        viewModel = getViewModel { parametersOf(roomId) }
    }

    private fun initView() {
        viewDataBinding.rvRoomHome.layoutManager = GridLayoutManager(this, 2)
        viewDataBinding.rvRoomHome.adapter = RoomUserListAdapter { item ->
            createDialog(item)
        }
    }

    private fun createDialog(item: UserPreview) {
        val dialogBuilder = AlertDialog.Builder(this@RoomHomeActivity)
        val layoutView = layoutInflater.inflate(R.layout.dialog_user_datail_layout, null)
        layoutView.iv_user_image.setUserImage(item.imageCode)
        layoutView.tv_user_name.text = item.userName
        layoutView.tv_user_des.text = item.description
        dialogBuilder.setView(layoutView)
        val alertDialog = dialogBuilder.create()
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()
        alertDialog.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    private fun observeItem() {
        viewModel.data.observe(this, Observer {
            (viewDataBinding.rvRoomHome.adapter as RoomUserListAdapter).updateListItems(it as ArrayList<UserPreview>)
        })

        viewModel.roomData.observe(this, Observer {
            viewDataBinding.roomModel = it
        })

        addDisposable(
            viewModel.startMatching.subscribe {
                if (it) {
                    RxBus.publish(RxEvent.EventRefreshEvent())
                    finish()
                    val prefs = this.getSharedPreferences(Constants.FRENTTO_PREF, Context.MODE_PRIVATE)
                    prefs.edit().putBoolean(viewModel.roomData.value?.id.toString(), true).apply()
                    startActivity(Intent(this, MatchingAnimationActivity::class.java).apply { addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY) })
                }
            }
        )

        addDisposable(
                viewModel.deleteRoom.subscribe {
                    if (it) {
                        RxBus.publish(RxEvent.EventRefreshEvent())
                        finish()
                    }
                }
        )

        addDisposable(
            viewModel.commonError.subscribe {
                if (it) {
                    toast(R.string.error_unkown)
                } else {
                    toast(R.string.error_unkown)
                    finish()
                }
            }
        )

        viewModel.showLoadingDialog.observe(this, Observer {
            if (it) showProgress()
            else dismissProgress()
        })

        viewModel.requestToast.observe(this, Observer {
            toast(it)
        })
    }
}

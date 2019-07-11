package com.mashup.frienitto.room.entry

import android.os.Bundle
import com.mashup.frienitto.R
import com.mashup.frienitto.base.BaseActivity
import org.jetbrains.anko.toast
import org.koin.android.viewmodel.ext.android.viewModel

class RoomEntryActivity(override val layoutResourceId: Int = R.layout.activity_room_entry) :
    BaseActivity<com.mashup.frienitto.databinding.ActivityRoomEntryBinding>() {

    val viewModel: RoomEntryViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewDataBinding.root)
        viewDataBinding.viewModel = viewModel

        addDisposable(
            viewModel.moveActivity.subscribe {
                if (it) {
                    toast("성공쓰")
                    //페이지가 넘어가야쥬
                    //startActivity(Intent(this))
                }
            }
        )

        addDisposable(
            viewModel.commonError.subscribe {stringRes->
                toast(resources.getString(stringRes))
            }
        )
    }


}

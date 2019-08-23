package com.mashup.frienitto.room.join

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.lifecycle.Observer
import com.mashup.frienitto.R
import com.mashup.frienitto.RxBus.RxBus
import com.mashup.frienitto.RxBus.RxEvent
import com.mashup.frienitto.base.BaseActivity
import com.mashup.frienitto.databinding.ActivityRoomJoinBinding
import com.mashup.frienitto.matching.MatchingAnimationActivity
import com.mashup.frienitto.room.home.RoomHomeActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_creation_room.*
import org.jetbrains.anko.toast
import org.koin.android.viewmodel.ext.android.viewModel

class RoomJoinActivity(override val layoutResourceId: Int = R.layout.activity_room_join) :
    BaseActivity<ActivityRoomJoinBinding>() {

    val viewModel: RoomJoinViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewDataBinding.root)
        viewDataBinding.viewModel = viewModel

        addDisposable(
            viewModel.moveActivity.subscribe {
                //Todo 매칭 유무따라 보여지는 페이지 달라져야함
                if (it) {
                    RxBus.publish(RxEvent.EventRefreshEvent())
                    startActivity(Intent(this,RoomHomeActivity::class.java))
                    //startActivity(Intent(this, MatchingAnimationActivity::class.java).apply{addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)})
                    finish()
                }
            }
        )

        addDisposable(
            viewModel.commonError.subscribe {stringRes->
                toast(resources.getString(stringRes))
            }
        )

        addDisposable(
            viewModel.roomNameSubject
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if(it.isBlank()){
                        clearEditText(et_room_name as EditText)
                    }
                },{})
        )

        addDisposable(
            viewModel.roomCodeSubject
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if(it.isBlank()){
                        clearEditText(et_room_pw as EditText)
                    }
                },{})
        )

        viewModel.showLoadingDialog.observe(this, Observer {
            if(it) showProgress()
            else dismissProgress()
        })
    }

    private fun clearEditText(edit: EditText){
        edit.text.clear()
    }


}

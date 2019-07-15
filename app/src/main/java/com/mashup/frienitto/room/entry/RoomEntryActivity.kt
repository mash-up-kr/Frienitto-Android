package com.mashup.frienitto.room.entry

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import com.mashup.frienitto.R
import com.mashup.frienitto.base.BaseActivity
import com.mashup.frienitto.matching.MatchingAnimationActivity
import com.mashup.frienitto.room.home.RoomHomeActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_creation_room.*
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
                //방장이 매칭 버튼 안 눌렀을 경우
                if (it) {
                    startActivity(Intent(this,RoomHomeActivity::class.java))
                    finish()
                }
                //방장이 매칭 버튼 눌렀을 경우
                // startActivity(Intent(this,MatchingAnimationActivity::class.java).apply{addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)})
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
    }

    private fun clearEditText(edit: EditText){
        edit.text.clear()
    }


}

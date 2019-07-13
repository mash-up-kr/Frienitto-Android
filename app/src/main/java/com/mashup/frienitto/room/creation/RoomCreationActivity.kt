package com.mashup.frienitto.room.creation

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import com.mashup.frienitto.R
import com.mashup.frienitto.base.BaseActivity
import com.mashup.frienitto.databinding.ActivityCreationRoomBinding
import com.mashup.frienitto.matching.MatchingAnimationActivity
import com.mashup.frienitto.room.home.RoomHomeActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_creation_room.*
import org.jetbrains.anko.AnkoLogger
import org.koin.android.viewmodel.ext.android.viewModel

class RoomCreationActivity(override val layoutResourceId: Int = R.layout.activity_creation_room) :
    BaseActivity<ActivityCreationRoomBinding>() ,AnkoLogger{

    val viewModel: RoomCreationViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewDataBinding.root)
        viewDataBinding.viewModel = viewModel

        addDisposable(
            viewModel.endDateSubject
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    when (it) {
                        1 -> {
                            ViewCompat.setBackgroundTintList(btn_end_date_1, ContextCompat.getColorStateList(this, R.color.navy))
                            ViewCompat.setBackgroundTintList(btn_end_date_2, ContextCompat.getColorStateList(this, R.color.lightGray))
                            btn_end_date_1.setTextColor(resources.getColor(R.color.white))
                            btn_end_date_2.setTextColor(resources.getColor(R.color.lightGray2))

                        }
                        2 -> {
                            ViewCompat.setBackgroundTintList(btn_end_date_1, ContextCompat.getColorStateList(this, R.color.lightGray))
                            ViewCompat.setBackgroundTintList(btn_end_date_2, ContextCompat.getColorStateList(this, R.color.navy))
                            btn_end_date_1.setTextColor(resources.getColor(R.color.lightGray2))
                            btn_end_date_2.setTextColor(resources.getColor(R.color.white))
                        }
                    }
                }, {})
        )
        addDisposable(
            viewModel.submitBtnSubject
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({isActive->
                    if(isActive){
                        ViewCompat.setBackgroundTintList(btn_room_creation, ContextCompat.getColorStateList(this, R.color.orange))
                        btn_room_creation.isClickable = true
                    }else{
                        ViewCompat.setBackgroundTintList(btn_room_creation, ContextCompat.getColorStateList(this, R.color.gray))
                        btn_room_creation.isClickable = false
                    }

                }, {})
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

        viewModel.isFinish.observe(this, Observer {
            startActivity(Intent(this, RoomHomeActivity::class.java))
            finishAffinity()
        })
    }


    private fun clearEditText(edit:EditText){
        edit.text.clear()
    }
}

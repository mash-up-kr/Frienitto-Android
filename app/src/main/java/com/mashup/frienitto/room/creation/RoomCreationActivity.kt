package com.mashup.frienitto.room.creation

import android.os.Bundle
import androidx.lifecycle.Observer
import com.mashup.frienitto.R
import com.mashup.frienitto.base.BaseActivity
import com.mashup.frienitto.databinding.ActivityCreationRoomBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_creation_room.*
import org.koin.android.viewmodel.ext.android.viewModel

class RoomCreationActivity(override val layoutResourceId: Int = R.layout.activity_creation_room) :
    BaseActivity<ActivityCreationRoomBinding>() {

    val viewModel: RoomCreationViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewDataBinding.root)
        viewDataBinding.viewModel = viewModel
        
        compositeDisposable.add(
            viewModel.endDateSubject
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    when (it) {
                        1 -> {
                            btn_end_date_2.setBackgroundResource(R.color.gray)
                            btn_end_date_1.setBackgroundResource(R.color.orange)
                        }
                        2 -> {
                            btn_end_date_1.setBackgroundResource(R.color.gray)
                            btn_end_date_2.setBackgroundResource(R.color.orange)
                        }
                    }
                }, {})
        )
        compositeDisposable.add(
            viewModel.submitBtnSubject
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({isActive->
                    if(isActive){
                        btn_room_creation.setBackgroundResource(R.color.orange)
                        btn_room_creation.isClickable = true
                    }else{
                        btn_room_creation.setBackgroundResource(R.color.gray)
                        btn_room_creation.isClickable = false
                    }

                }, {})
        )
    }


}

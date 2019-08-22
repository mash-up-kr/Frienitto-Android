package com.mashup.frienitto.matching.home

import android.os.Bundle
import androidx.lifecycle.Observer
import com.mashup.frienitto.R
import com.mashup.frienitto.base.BaseActivity
import com.mashup.frienitto.databinding.ActivityMatchingHomeBinding
import org.jetbrains.anko.toast
import org.koin.android.viewmodel.ext.android.getViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MatchingHomeActivity : BaseActivity<ActivityMatchingHomeBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_matching_home
    private lateinit var viewModel: MatchingHomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewDataBinding.root)
        initIntentData()
        viewDataBinding.viewModel = viewModel

        addDisposable(
            viewModel.commonError.subscribe {
                if (it) {
                    toast(R.string.error_unkown)
                }
            }
        )

        viewModel.missionData.observe(this, Observer {
            viewDataBinding.missionModel = it
        })

        viewModel.showLoadingDialog.observe(this, Observer {
            if(it) showProgress()
            else dismissProgress()
        })
        //viewDataBinding.roomModel = RoomInfo(11, "마니또 방이름", "2020-01-02", "", viewModel.data.value!!)
    }

    private fun initIntentData() {
        val roomId = intent.getIntExtra("roomId", -1)
        viewModel = getViewModel { parametersOf(roomId) }
    }
}
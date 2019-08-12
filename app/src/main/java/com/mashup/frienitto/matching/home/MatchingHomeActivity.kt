package com.mashup.frienitto.matching.home

import android.os.Bundle
import com.mashup.frienitto.R
import com.mashup.frienitto.base.BaseActivity
import com.mashup.frienitto.databinding.ActivityMatchingHomeBinding
import org.koin.android.viewmodel.ext.android.viewModel

class MatchingHomeActivity : BaseActivity<ActivityMatchingHomeBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_matching_home
    private val viewModel: MatchingHomeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewDataBinding.root)
        viewDataBinding.viewModel = viewModel
        //viewDataBinding.roomModel = ResponseRoom(11, "마니또 방이름", "2020-01-02", "", viewModel.data.value!!)
    }
}
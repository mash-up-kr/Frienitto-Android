package com.mashup.frienitto.matching.finish

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.mashup.frienitto.R
import com.mashup.frienitto.base.BaseActivity
import com.mashup.frienitto.data.Mission
import com.mashup.frienitto.databinding.ActivityMatchingFinishBinding
import org.jetbrains.anko.toast
import org.koin.android.viewmodel.ext.android.viewModel

class MatchingFinishActivity : BaseActivity<ActivityMatchingFinishBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_matching_finish

    private val viewModel: MatchingFinishViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewDataBinding.root)
        viewDataBinding.viewModel = viewModel

        initView()
        observeItem()
    }

    private fun initView() {
        viewDataBinding.rvRoomClose.layoutManager = LinearLayoutManager(this)
        viewDataBinding.rvRoomClose.adapter = MatchingFinishListAdapter { item ->
            //TODO onclick
        }

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
    }

    private fun observeItem() {
        viewModel.data.observe(this, Observer {
            (viewDataBinding.rvRoomClose.adapter as MatchingFinishListAdapter).updateListItems(it as ArrayList<Mission>)
        })
    }
}

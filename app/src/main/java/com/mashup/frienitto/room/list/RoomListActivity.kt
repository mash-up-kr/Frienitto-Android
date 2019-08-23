package com.mashup.frienitto.room.list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.mashup.frienitto.Constants
import com.mashup.frienitto.R
import com.mashup.frienitto.RxBus.RxBus
import com.mashup.frienitto.RxBus.RxEvent
import com.mashup.frienitto.base.BaseActivity
import com.mashup.frienitto.data.RoomInfo
import com.mashup.frienitto.databinding.ActivityRoomListBinding
import com.mashup.frienitto.matching.MatchingAnimationActivity
import com.mashup.frienitto.matching.home.MatchingHomeActivity
import com.mashup.frienitto.matching.finish.MatchingFinishActivity
import com.mashup.frienitto.room.creation.RoomCreationActivity
import com.mashup.frienitto.room.home.RoomHomeActivity
import com.mashup.frienitto.room.join.RoomJoinActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_room_list.*
import org.koin.android.viewmodel.ext.android.viewModel
import com.mashup.frienitto.home.HomeActivity

class RoomListActivity(override val layoutResourceId: Int = R.layout.activity_room_list) :
    BaseActivity<ActivityRoomListBinding>() {

    private val viewModel: RoomListViewModel by viewModel()

    private val adapter: RoomListAdapter by lazy {
        RoomListAdapter(viewModel)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewDataBinding.root)
        viewDataBinding.viewModel = viewModel
        viewDataBinding.lifecycleOwner = this

        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(rv_room_list)

        rv_room_list.layoutManager = LinearLayoutManager(this).apply { orientation = LinearLayoutManager.HORIZONTAL }
        rv_room_list.adapter = adapter

        viewModel.roomList.observe(this, Observer {
            adapter.updateListItems(it as ArrayList<RoomInfo>)
        })

        addDisposable(
            viewModel.moveToRoomCard
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ roomInfo ->
                    identifyActivityToMove(roomInfo)

                }, {})
        )

        addDisposable(
            viewModel.moveToRoom
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    when (it) {
                        RoomType.CREATION -> startActivity(Intent(this, RoomCreationActivity::class.java))
                        RoomType.ENTRY -> startActivity(Intent(this, RoomJoinActivity::class.java))
                    }
                }, {})
        )

        addDisposable(
            viewModel.logout
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it) {
                        finishAffinity()
                        val intent = Intent(this, HomeActivity::class.java)
                        startActivity(intent)
                    }
                }, {})
        )
        addDisposable(
                RxBus.listen(RxEvent.EventRefreshEvent::class.java).subscribe {
                    viewModel.getRoomList()
                }
        )

        viewModel.showLoadingDialog.observe(this, Observer {
            if(it) showProgress()
            else dismissProgress()
        })

    }

    private fun identifyActivityToMove(roomInfo: RoomInfo) {
        when (roomInfo.status) {
            "CREATED" -> startActivity(Intent(this, RoomHomeActivity::class.java))
            "MATCHED" -> checkFirstEntry(roomInfo.id)
            "EXPIRED" -> startActivity(Intent(this, MatchingFinishActivity::class.java))
        }
    }

    private fun checkFirstEntry(roomId: Int) {
        val prefs = this.getSharedPreferences(Constants.FRENTTO_PREF, Context.MODE_PRIVATE)
        if (prefs.contains(roomId.toString())) {
            startActivity(Intent(this, MatchingHomeActivity::class.java))
        } else {
            prefs.edit().putBoolean(roomId.toString(), true).apply()
            startActivity(Intent(this, MatchingAnimationActivity::class.java))
        }
    }
}

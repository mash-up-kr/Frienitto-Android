package com.mashup.frienitto.room.list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.mashup.frienitto.R
import com.mashup.frienitto.base.BaseActivity
import com.mashup.frienitto.data.RoomInfo
import com.mashup.frienitto.databinding.ActivityRoomListBinding
import com.mashup.frienitto.matching.MatchingAnimationActivity
import com.mashup.frienitto.matching.home.MatchingHomeActivity
import com.mashup.frienitto.room.close.RoomCloseActivity
import com.mashup.frienitto.room.creation.RoomCreationActivity
import com.mashup.frienitto.room.home.RoomHomeActivity
import com.mashup.frienitto.room.join.RoomJoinActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_room_list.*
import org.jetbrains.anko.startActivity
import org.koin.android.viewmodel.ext.android.viewModel

const val MATCHING_ANIMATION_TABLE = "MATCHING_ANIMATION_TABLE"

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

    }

    private fun identifyActivityToMove(roomInfo: RoomInfo) {
        when (roomInfo.status) {
            "CREATED" -> startActivity(Intent(this, RoomHomeActivity::class.java).apply { putExtra("roomId",roomInfo.id.toString()) })
            "MATCHED" -> checkFirstEntry(roomInfo.id.toString())
            "EXPIRED" -> startActivity(Intent(this, RoomCloseActivity::class.java))
        }
    }

    private fun checkFirstEntry(roomId: String) {
        val prefs = this.getSharedPreferences(MATCHING_ANIMATION_TABLE, Context.MODE_PRIVATE)
        if (prefs.contains(roomId)) {
            startActivity(Intent(this, MatchingAnimationActivity::class.java))
        } else {
            prefs.getBoolean(roomId, true)
            startActivity(Intent(this, MatchingHomeActivity::class.java))
        }
    }
}

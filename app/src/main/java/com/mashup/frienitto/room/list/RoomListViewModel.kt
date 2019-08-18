package com.mashup.frienitto.room.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mashup.frienitto.base.BaseViewModel
import com.mashup.frienitto.data.RoomInfo
import com.mashup.frienitto.repository.room.RoomRepository
import com.mashup.frienitto.repository.user.UserRepository
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import org.jetbrains.anko.info

class RoomListViewModel(
    private val userRepository: UserRepository,
    private val roomRepository: RoomRepository
) : BaseViewModel(), AnkoLogger {
    private val _username = MutableLiveData<String>()
    val username: LiveData<String>
        get() = _username

    private val _email = MutableLiveData<String>()
    val email: LiveData<String>
        get() = _email

    private val _roomList = MutableLiveData<List<RoomInfo>>()
    val roomList: LiveData<List<RoomInfo>>
        get() = _roomList

    init {

        userRepository.getUserInfo()?.let {
            info { "tag1 $it" }
            _username.postValue(it.user.username)
            _email.postValue(it.user.email)
        }

        info{"tag1 tokein ${userRepository.getUserInfo()?.token}"}
        addDisposable(
            roomRepository.getRoomList(userRepository.getUserInfo()?.token!!)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    info { "tag1 what??? $it" }
                    it.data.let {
                        info { "tag1 $it" }
                        _roomList.postValue(it)
                    }
                }, {
                    info { "tag1 error ${it.printStackTrace()}" }
                    info { "tag1 error ${it.message}" }
                })
        )
    }

    fun onCardClick(roomId: Int) {

    }

    fun onRoomCreationClick() {

    }

    fun onRoomEnterClick() {

    }


}


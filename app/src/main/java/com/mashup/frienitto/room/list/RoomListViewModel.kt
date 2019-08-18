package com.mashup.frienitto.room.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mashup.frienitto.base.BaseViewModel
import com.mashup.frienitto.data.RoomInfo
import com.mashup.frienitto.repository.room.RoomRepository
import com.mashup.frienitto.repository.user.UserRepository

class RoomListViewModel(
    private val userRepository: UserRepository,
    private val roomRepository: RoomRepository
) : BaseViewModel() {
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
            _username.postValue(it.user.username)
            _email.postValue(it.user.email)
        }

        addDisposable(
            roomRepository.getRoomList(userRepository.getTokenizer())
                .subscribe({
                    it.data?.let {
                        _roomList.postValue(it)
                    }
                }, {
                    it.message
                })
        )
    }

    fun onCardClick(roomId:Int){

    }

    fun onRoomCreationClick() {

    }

    fun onRoomEnterClick() {

    }


}


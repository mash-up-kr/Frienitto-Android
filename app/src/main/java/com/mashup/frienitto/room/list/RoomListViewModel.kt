package com.mashup.frienitto.room.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mashup.frienitto.base.BaseViewModel
import com.mashup.frienitto.data.RoomInfo
import com.mashup.frienitto.repository.room.RoomRepository
import com.mashup.frienitto.repository.user.UserRepository
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import org.jetbrains.anko.info
import java.security.PublicKey

enum class RoomType {
    CREATION,
    ENTRY
}

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

    val moveToRoomCard = PublishSubject.create<RoomInfo>()

    val moveToRoom = PublishSubject.create<RoomType>()

    init {

        userRepository.getUserInfo()?.let {
            _username.postValue(it.user.username)
            _email.postValue(it.user.email)
        }

        addDisposable(
            roomRepository.getRoomList(userRepository.getUserInfo()?.token!!)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    it.data.let {
                        _roomList.postValue(it)
                    }
                }, {
                    debug { it.message }
                })
        )
    }

    fun onCardClick(item: RoomInfo) {
        moveToRoomCard.onNext(item)
    }

    fun onRoomButtonClick(roomType: RoomType) {
        moveToRoom.onNext(roomType)
    }


}


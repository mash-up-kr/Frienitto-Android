package com.mashup.frienitto.room.list

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mashup.frienitto.base.BaseViewModel
import com.mashup.frienitto.data.RoomInfo
import com.mashup.frienitto.repository.room.RoomRepository
import com.mashup.frienitto.repository.user.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
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
    private val application: Application,
    private val userRepository: UserRepository,
    private val roomRepository: RoomRepository
) : BaseViewModel(), AnkoLogger {
    private val _username = MutableLiveData<String>()
    val username: LiveData<String>
        get() = _username

    private val _userImage = MutableLiveData<Int>()
    val userImage: LiveData<Int>
        get() = _userImage

    private val _email = MutableLiveData<String>()
    val email: LiveData<String>
        get() = _email

    private val _roomList = MutableLiveData<List<RoomInfo>>()
    val roomList: LiveData<List<RoomInfo>>
        get() = _roomList

    val moveToRoomCard = PublishSubject.create<RoomInfo>()

    val moveToRoom = PublishSubject.create<RoomType>()

    val logout = PublishSubject.create<Boolean>()

    init {

        showLoadingDialog()

        userRepository.getUserInfo()?.let {
            _username.postValue(it.user.username)
            _email.postValue(it.user.email)
            _userImage.postValue(it.user.image_code)
        }

        addDisposable(
            roomRepository.getRoomList(userRepository.getUserInfo()?.token!!)
                .subscribeOn(Schedulers.io())
                .doOnEvent { t1, t2 -> dissmissLoadingDialog() }
                .subscribe({
                    it.data.let {
                        _roomList.postValue(it)
                    }
                }, {
                    debug { it.message }
                })
        )
    }

    fun onLogoutClick() {
        userRepository.logout(application)
        logout.onNext(true)
    }

    fun onCardClick(item: RoomInfo) {
        roomRepository.roomId = item.id
        roomRepository.expiredDate = item.expiresDate
        moveToRoomCard.onNext(item)
    }

    fun onRoomButtonClick(roomType: RoomType) {
        moveToRoom.onNext(roomType)
    }


}


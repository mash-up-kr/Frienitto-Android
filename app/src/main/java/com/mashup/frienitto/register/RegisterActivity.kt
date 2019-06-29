package com.mashup.frienitto.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mashup.frienitto.R
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val adapter = RegisterPagerAdapter(supportFragmentManager)
        viewPager.adapter = adapter
    }

    fun setCurrentPage(position: Int) {
        when (position) {
            0 -> tv_signin_info.text = getString(R.string.sign_in_1)
            1 -> tv_signin_info.text = getString(R.string.sign_in_2)
            2 -> tv_signin_info.text = getString(R.string.sign_in_3)
            3 -> tv_signin_info.text = getString(R.string.sign_in_4)
        }

        tv_pager_position.text = (position + 1).toString()
        viewPager.currentItem = position
    }
}

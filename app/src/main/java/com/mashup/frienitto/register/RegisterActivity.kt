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
        tv_pager_position.text = (position + 1).toString()
        viewPager.currentItem = position
    }
}

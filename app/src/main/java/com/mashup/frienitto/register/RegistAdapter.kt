package com.mashup.frienitto.register

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class RegisterPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    private val list: ArrayList<Fragment> = ArrayList()

    init {
        list.add(EmailAuthFragment())
        list.add(EmailConfirmFragment())
        list.add(RegisterPasswordFragment())
        list.add(RegisterInfoFragment())
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return ""//list[position].title()
    }

    override fun getItem(position: Int): Fragment {
        return list[position]
    }

    override fun getCount(): Int {
        return list.size
    }
}
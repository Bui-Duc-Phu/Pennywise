package com.example.pennywise.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.viewpager2.widget.ViewPager2
import com.example.pennywise.Adapter.AdapterViewPager
import com.example.pennywise.R
import com.example.pennywise.databinding.ActivityMainBinding
import com.example.pennywise.ui.fragment.Chat
import com.example.pennywise.ui.fragment.Home
import com.example.pennywise.ui.fragment.Setting
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class Main : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    private val fragmentArrayList = ArrayList<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewPagerAndBottomNav()
    }

    private fun setupViewPagerAndBottomNav() {
        val fragments = listOf(
            Home(),
            Chat(),
            Setting(),

        )

        // Gán Adapter cho ViewPager2
        binding.viewPager2.adapter = AdapterViewPager(this, fragments)

        // Đặt mục đầu tiên trong BottomNavigationView được chọn
        binding.bottomNavigation.selectedItemId = R.id.nav_home

        // Đồng bộ BottomNavigationView với ViewPager2
        binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> binding.viewPager2.currentItem = 0
                R.id.nav_chat -> binding.viewPager2.currentItem = 1
                R.id.nav_settings -> binding.viewPager2.currentItem = 2
            }
            true
        }

        // Đồng bộ ViewPager2 với BottomNavigationView
        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> binding.bottomNavigation.menu.findItem(R.id.nav_home).isChecked = true
                    1 -> binding.bottomNavigation.menu.findItem(R.id.nav_chat).isChecked = true
                    2 -> binding.bottomNavigation.menu.findItem(R.id.nav_settings).isChecked = true
                }
            }
        })
    }




}


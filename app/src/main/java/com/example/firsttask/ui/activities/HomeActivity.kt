package com.example.firsttask.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.firsttask.R
import com.example.firsttask.ui.fragments.add_member.AddMemberFragment
import com.example.firsttask.ui.fragments.all_members.AllMembersFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setFragment(AllMembersFragment())
        bottom_nav.selectedItemId = R.id.menu_all_members
    }

    override fun onResume() {
        super.onResume()
        val badge = bottom_nav.getOrCreateBadge(R.id.menu_all_members)
        badge.isVisible = true
        badge.number = 10

        bottom_nav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_all_members -> {
                    setFragment(AllMembersFragment())
                    true
                }
                R.id.menu_add_member -> {
                    setFragment(AddMemberFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
            .commit()
    }
}
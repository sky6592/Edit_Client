package com.doublejj.edit.ui.modules.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProviders
import com.doublejj.edit.R
import com.doublejj.edit.databinding.ActivityMainBinding
import com.doublejj.edit.ui.modules.main.home.HomeFragment
import com.doublejj.edit.ui.modules.main.myedit.MyeditFragment
import com.doublejj.edit.ui.modules.main.ranking.RankingFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private val TAG: String = javaClass.simpleName.toString()
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    private lateinit var transaction: FragmentTransaction
    private val tagList = mutableListOf("home", "ranking", "myedit")
    private val resList = mutableListOf(R.id.menu_home, R.id.menu_ranking, R.id.menu_myedit)
    private lateinit var homeFragment: HomeFragment
    private lateinit var rankingFragment: RankingFragment

    private lateinit var myeditFragment: MyeditFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        binding.viewModel = viewModel

        // prepare instances
        homeFragment = HomeFragment()
        rankingFragment = RankingFragment()
        myeditFragment = MyeditFragment()

        // set transaction of sfm
        transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fl_main, homeFragment, tagList[0])
        transaction.add(R.id.fl_main, rankingFragment, tagList[1])
        transaction.add(R.id.fl_main, myeditFragment, tagList[2])
        transaction.hide(rankingFragment)
        transaction.hide(myeditFragment)
        transaction.addToBackStack(null)
        transaction.commit()

        binding.bnvMain.menu.findItem(resList[0]).isChecked = true

        binding.bnvMain.setOnNavigationItemSelectedListener {
            val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()

            when(it.itemId) {
                resList[0] -> {
                    transaction.show(homeFragment)
                    transaction.hide(rankingFragment)
                    transaction.hide(myeditFragment)
                }
                resList[1] -> {
                    transaction.show(rankingFragment)
                    transaction.hide(homeFragment)
                    transaction.hide(myeditFragment)
                }
                resList[2] -> {
                    transaction.show(myeditFragment)
                    transaction.hide(homeFragment)
                    transaction.hide(rankingFragment)
                }
            }
            transaction.commit()

            return@setOnNavigationItemSelectedListener true
        }
    }

}
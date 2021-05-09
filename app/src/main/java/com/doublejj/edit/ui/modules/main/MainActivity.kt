package com.doublejj.edit.ui.modules.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.doublejj.edit.R
import com.doublejj.edit.databinding.ActivityMainBinding
import com.doublejj.edit.ui.modules.main.home.HomeFragment
import com.doublejj.edit.ui.modules.main.myedit.MyeditFragment
import com.doublejj.edit.ui.modules.main.ranking.RankingFragment

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

    private var backButtonTime = 0L
    private var fragmentCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.mainViewModel = viewModel

        // prepare instances
        homeFragment = HomeFragment()
        rankingFragment = RankingFragment()
        myeditFragment = MyeditFragment()

        // set transaction of sfm
        transaction = supportFragmentManager.beginTransaction()

        transaction.add(R.id.fl_main, homeFragment, tagList[0])
        // TODO : 이전 활동 내역 남아있도록 fragment show, hide 처리
//        transaction.add(R.id.fl_main, rankingFragment, tagList[1])
//        transaction.add(R.id.fl_main, myeditFragment, tagList[2])
//        transaction.hide(rankingFragment)
//        transaction.hide(myeditFragment)
        transaction.addToBackStack(null)
        transaction.commit()

        binding.bnvMain.menu.findItem(resList[0]).isChecked = true

        binding.bnvMain.setOnNavigationItemSelectedListener {
            val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()

            when(it.itemId) {
                resList[0] -> {
                    viewModel.lastActivityIndex = 0
                    transaction.replace(R.id.fl_main, homeFragment)
//                    transaction.show(homeFragment)
//                    transaction.hide(rankingFragment)
//                    transaction.hide(myeditFragment)
                }
                resList[1] -> {
                    viewModel.lastActivityIndex = 1
                    transaction.replace(R.id.fl_main, rankingFragment)
//                    transaction.show(rankingFragment)
//                    transaction.hide(homeFragment)
//                    transaction.hide(myeditFragment)
                }
                resList[2] -> {
                    viewModel.lastActivityIndex = 2
                    transaction.replace(R.id.fl_main, myeditFragment)
//                    transaction.show(myeditFragment)
//                    transaction.hide(homeFragment)
//                    transaction.hide(rankingFragment)
                }
            }

            // 프래그먼트 즉시 빠져나오기
            while (supportFragmentManager.backStackEntryCount > 1) {
                supportFragmentManager.popBackStackImmediate()
            }

            transaction.commit()

            return@setOnNavigationItemSelectedListener true
        }
    }

    override fun onResume() {
        super.onResume()

        // TODO : onResume일 때 fragment 재포커싱
        val fragIndex = viewModel.lastActivityIndex
        binding.bnvMain.menu.findItem(resList[fragIndex]).isChecked = true
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()

        when(fragIndex) {
            0 -> {
                viewModel.lastActivityIndex = 0
                transaction.replace(R.id.fl_main, homeFragment)
//                transaction.show(homeFragment)
//                transaction.hide(rankingFragment)
//                transaction.hide(myeditFragment)
            }
            1 -> {
                viewModel.lastActivityIndex = 1
                transaction.replace(R.id.fl_main, rankingFragment)
//                transaction.show(rankingFragment)
//                transaction.hide(homeFragment)
//                transaction.hide(myeditFragment)
            }
            2 -> {
                viewModel.lastActivityIndex = 2
                transaction.replace(R.id.fl_main, myeditFragment)
//                transaction.show(myeditFragment)
//                transaction.hide(homeFragment)
//                transaction.hide(rankingFragment)
            }
        }
        transaction.commit()

        // TODO : onResume일 때 데이터 처리
    }

    override fun onBackPressed() {
        val currentTime = System.currentTimeMillis()
        val gapTime = currentTime - backButtonTime

        if (gapTime in 0..2000) {
            // 2초 안에 두번 뒤로가기 누를 시 앱 종료
//            if (this.fragmentCount <= 0) {
                finish()
                finish()
//                homeFragment.onDetach()
//                rankingFragment.onDetach()
//                myeditFragment.onDetach()
//            }
        }
        else {
            backButtonTime = currentTime
            if (this.fragmentCount <= 1) Toast.makeText(this, "뒤로가기 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show()
            else super.onBackPressed()
        }
    }

    fun increaseFragmentCount() {
        this.fragmentCount += 1
    }
    fun decreaseFragmentCount() {
        this.fragmentCount -= 1
    }
    fun getFragmentCount() : Int {
        return this.fragmentCount
    }
}
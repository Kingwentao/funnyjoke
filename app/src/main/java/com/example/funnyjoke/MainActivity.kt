package com.example.funnyjoke

import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavActionBuilder
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.funnyjoke.utils.NavGraphBuilder
import com.example.libnetwork.ApiResponse
import com.example.libnetwork.GetRequest
import com.example.libnetwork.JsonCallBack
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )

        val fragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)!!

        navController = NavHostFragment.findNavController(fragment)
        NavGraphBuilder.build(navController, this, fragment.id)

        setupActionBarWithNavController(navController, appBarConfiguration)
        nav_view.setupWithNavController(navController)
        //设置导航栏的选择监听
        nav_view.setOnNavigationItemSelectedListener(this)


        //测试
        val request = GetRequest<JSONObject>("www.mooc.com")
        request.execute()

        request.execute(object : JsonCallBack<JSONObject>() {

            override fun onSuccess(respone: ApiResponse<JSONObject>) {
                super.onSuccess(respone)
            }
        })

    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        navController.navigate(menuItem.itemId)
        //根据菜单的是否有title来设定是否选中
        return !TextUtils.isEmpty(menuItem.title)
    }

}

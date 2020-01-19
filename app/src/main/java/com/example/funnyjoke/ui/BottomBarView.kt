package com.example.funnyjoke.ui

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.text.TextUtils
import android.util.AttributeSet
import com.example.funnyjoke.R
import com.example.funnyjoke.utils.AppConfig
import com.google.android.material.bottomnavigation.*

/**
 * author: created by wentaoKing
 * date: created in 2020-01-18
 * description:
 */
class BottomBarView : BottomNavigationView {

    private val sIcons = arrayOf(R.drawable.icon_tab_home,R.drawable.icon_tab_sofa,
        R.drawable.icon_tab_publish,R.drawable.icon_tab_find,R.drawable.icon_tab_mine)


    constructor(context: Context): super(context){
        init()
    }

    constructor(context: Context, attributeSet: AttributeSet): super(context, attributeSet){
        init()
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int): super(context, attributeSet, defStyleAttr){
        init()
    }

    fun init() {
        val bottomBar = AppConfig.getBottomBar()
        val tabs = bottomBar.tabs
        val state = Array(2){IntArray(10)}
        state[0] = intArrayOf(R.attr.state_above_anchor)
        state[1] = intArrayOf()

        val color = intArrayOf(
            Color.parseColor(bottomBar.activeColor),Color.parseColor(bottomBar.inActiveColor))
        val colorStateList = ColorStateList(state,color)
        itemIconTintList = colorStateList
        itemTextColor = colorStateList
        labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED
        selectedItemId = bottomBar.selectTab

        for (tab in tabs){
            if (tab.enable){
                val id = getTabId(tab.pageUrl)
                if (id > 0){
                    //获取菜单item
                    val menuItem = menu.add(0,id,tab.index,tab.title)
                    //设置菜单按钮的图标
                    menuItem.setIcon(sIcons[tab.index])
                }
            }
        }

        //给菜单栏的按钮设置大小
        for(tab in tabs){
            val menuView = getChildAt(0) as BottomNavigationMenuView
            val itemView = menuView.getChildAt(tab.index) as BottomNavigationItemView
            val iconSize = dp2px(tab.size)
            itemView.setIconSize(iconSize)

            if(TextUtils.isEmpty(tab.title)){
                itemView.setIconTintList(ColorStateList.valueOf(Color.parseColor(tab.tintColor)))
                itemView.setShifting(false)
            }
        }
    }

    private fun dp2px(size: Int): Int {
        return (context.resources.displayMetrics.density * size + 0.5f).toInt()
    }

    private fun getTabId(pageUrl: String): Int{
        val destination = AppConfig.getDestConfig()[pageUrl]
        if (destination == null) {
            return  -1
        } else {
            return destination.id
        }

    }


}
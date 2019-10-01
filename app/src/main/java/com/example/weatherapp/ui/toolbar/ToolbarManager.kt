package com.example.weatherapp.ui.toolbar

import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.App
import com.example.weatherapp.R
import com.example.weatherapp.ui.activity.SettingsActivity
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

interface ToolbarManager {
    val toolbar: Toolbar
    var toolbarTitle: String
        get() = toolbar.title.toString()
        set(value) {
            toolbar.title = value
        }

    fun initToolbar() {
        toolbar.inflateMenu(R.menu.menu_main)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_setting -> toolbar.context.startActivity<SettingsActivity>()
                else -> App.instance.toast("Unknown option")
            }
            true
        }
    }

    fun enableHomeAsUp(up: () -> Unit) {
        toolbar.navigationIcon = createUpDrawable()
        toolbar.setNavigationOnClickListener { up() }
    }

    private fun createUpDrawable() = with(DrawerArrowDrawable(toolbar.context)) {
        progress = 1f
        this
    }

    fun attachToScroll(recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0)
                    toolbar.slideExit()
                else
                    toolbar.slideEnter()
            }
        })
    }

    private fun View.slideExit() {
        if (translationY == 0f)
            animate().translationY(-height.toFloat())
    }

    private fun View.slideEnter() {
        if (translationY < 0f)
            animate().translationY(0f)
    }
}
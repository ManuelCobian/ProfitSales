package com.example.core.ui.activities

import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.luvsoft.base.R

const val REQUEST_CODE = 1001

open class BaseActivity : AppCompatActivity() {

    fun setupActionBar(toolbar: Toolbar) {
        setupActionBar(toolbar, true)
    }

    protected fun setupActionBar(
        toolbar: Toolbar,
        showButtonBack: Boolean,
        title: String = "",
        icon: Int = R.drawable.ic_arrow_back_24
    ) {
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(icon)
            actionBar.setDisplayHomeAsUpEnabled(showButtonBack)
            actionBar.setDisplayShowHomeEnabled(showButtonBack)
            if (title.isNotEmpty()) actionBar.title = title
        }
    }

    protected fun setbackPressed(function: () -> Unit) {
        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    function.invoke()
                }
            }
        )
    }
}
package com.example.myapplication

import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toDrawable
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.myapplication.databinding.ActivityMyNavigationBinding
import com.google.android.material.navigation.NavigationView

class MyNavigationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyNavigationBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        binding = ActivityMyNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val drawerLayout: DrawerLayout = binding.main
        val navView: NavigationView = binding.nv1 //объект шторки(Выплывающая менюшка по бокам экрана)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView2) as NavHostFragment //Берём фрагмент, что является хостом навигации
        val navController = navHostFragment.navController //берём из него контроллер
        binding.bnvNavView.setupWithNavController(navController) //Собственно связываем нижнюю менюшку с графом навигации

        setSupportActionBar(binding.tb1) //Говорим о существовании ToolBar'а

        appBarConfiguration = AppBarConfiguration(setOf(R.id.navFragment, R.id.fragment_home, R.id.fragment_gallery, R.id.fragment_slideshow), drawerLayout) //Я не знаю, что делают эти строки
        setupActionBarWithNavController(navController, appBarConfiguration)


        navView.setupWithNavController(navController) //Тепер кнопки в шторке будут вести на фрагменты в соотвествии с присаным в них кодом(id кнопки должен совпадать с id желаемого фрагмента в навигационном графе

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean { //В этом методе задаём toolBar'у кнопки из папки menu.

        menuInflater.inflate(R.menu.tool_bar_menu, menu)

        val item1: MenuItem = menu.findItem(R.id.tbItem1)
        val item2: MenuItem = menu.findItem(R.id.tbItem2)

        item1.icon?.setTint(getColor(R.color.testColor))

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean { //В этом методе мы задаём реакцию на нажатия
        when (item.itemId) {
            R.id.tbItem1 -> Toast.makeText(this, "First item", Toast.LENGTH_SHORT).show()
            R.id.tbItem2 -> Toast.makeText(this, "Second item", Toast.LENGTH_SHORT).show()

        }
        return super.onOptionsItemSelected(item)
    }
}
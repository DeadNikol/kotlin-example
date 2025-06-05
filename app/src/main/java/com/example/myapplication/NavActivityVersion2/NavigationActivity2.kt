package com.example.myapplication.NavActivityVersion2

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityNavigation2Binding

class NavigationActivity2 : AppCompatActivity() {
    lateinit var binding: ActivityNavigation2Binding
    private lateinit var host: NavHostFragment
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityNavigation2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        //Нахождение навигационного контроллера в активности представленно двумя строками
        host =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView3) as NavHostFragment
        navController = host.navController
        //Включаем боковое меню
        val sideBar = binding.navViewVersion2 //Объект боковой менюшки
        sideBar.setupWithNavController(navController)

        //Добавляем на ToolBar кнопку вызова боковой менюшки и название текущего фрагмента
        val appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout = binding.main) //Второй параметр нужен для кнопки-гамбургера
        val toolBar = binding.toolbarVersion2
        setSupportActionBar(toolBar) // Эта строка появилась только при налаживании троеточия на верхнем баре
        toolBar.setupWithNavController(navController, appBarConfiguration)

        //Добавляем нижний бар, но он почему то не работает одновременно с боковым баром
//        val bottomBar = binding.bottomnavigationViewVersion2
//        bottomBar.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_right_menu_version_2, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.testID -> Toast.makeText(this, "Проверка успешно прошла", Toast.LENGTH_SHORT).show()
        }
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }
}
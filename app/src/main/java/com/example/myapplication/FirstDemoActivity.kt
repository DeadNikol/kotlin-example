package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.MotionEvent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.NavActivityVersion2.NavigationActivity2
import com.example.myapplication.databinding.ActivityFirstDemoBinding
import kotlinx.parcelize.Parcelize

class FirstDemoActivity: AppCompatActivity() {
    private lateinit var binding: ActivityFirstDemoBinding
    private val TAG = "condition"
    private lateinit var mainActivityViewModel: MainActivityViewModel //Этот класс нужен для сохранения данных по время работы приложения, а так же он нужен для наблюдателя

//    fun showMessage(s: String){
//        Toast.makeText(this,s,Toast.LENGTH_SHORT).show()
//        Log.d(TAG, s)
//    }
//
//    override fun onPause() {
//        super.onPause()
//        showMessage("onPause")
//    }
//    override fun onRestart() {
//        super.onRestart()
//        showMessage("onRestart")
//    }
//    override fun onStart() {
//        super.onStart()
//        showMessage("onStart")
//    }
//    override fun onDestroy() {
//        super.onDestroy()
//        showMessage("onDestroy")
//    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        binding = ActivityFirstDemoBinding.inflate(layoutInflater)

        val viewModelProvider = ViewModelProvider(this) //Проводник между классом и этой активностью
        mainActivityViewModel = viewModelProvider[MainActivityViewModel::class.java] //Явно указываем класс

        val sharedPreferences = getSharedPreferences("user_preferences", Context.MODE_PRIVATE) //Переменная, отвечающая за локальное хранилище



        binding.etOne?.setText(sharedPreferences.getString("notify", "Либо ты всё знаешь, либо не записал, либо всё сломалось")) //Подсасываем строку из хранилища
        setContentView(binding.root)



//        val t = Toast.makeText(this,"I am toast", Toast.LENGTH_SHORT)
//        t.show()

        mainActivityViewModel.getEditTextFromFirstDemoActivity().observe(this@FirstDemoActivity){ //Вешаем наблюдателя, что бы следил за изменениями строки
            val editor = sharedPreferences.edit() //Создаём объект редактора
            editor.putString("notify", binding.etOne?.text.toString()) //Вставляем новую строку
            editor.apply() //Применяем изменнения
        }
        binding.etOne?.addTextChangedListener{ //Если текст изменился...

            mainActivityViewModel.getEditTextFromFirstDemoActivity().value = it.toString() //...меняем объект, на котором висит наблюдатель
        }

        val word = extraWord("Galaxy", "Галактика")


        with(binding){
            btnOpenSecond?.setOnClickListener {
                val intent = Intent(this@FirstDemoActivity, SecondDemoActivity::class.java)
                intent.putExtra("EXTRA_KEY_TEXT", "don't panic")
                intent.putExtra("EXTRA_KEY_NUMBER", 42)
                intent.putExtra("EXTRA_KEY_WORD", word)

//                val bundle = Bundle()
//                bundle.putString("EXTRA_KEY_TEXT", "don't panic")
//                bundle.putInt("EXTRA_KEY_NUMBER", 42)
//                bundle.putSerializable("EXTRA_KEY_WORD", word)
//                intent.putExtras(
//                    bundleOf(
//                        "EXTRA_KEY_TEXT" to "don't panic",
//                        "EXTRA_KEY_NUMBER" to 42,
//                        "EXTRA_KEY_WORD" to word,
//                    )
//                )

                startActivity(intent)
            }
            btnOpenMain?.setOnClickListener {
                val intent = Intent(this@FirstDemoActivity, MainActivity::class.java)
                startActivity(intent)
            }
            textView3?.setOnTouchListener { _, motionEvent ->
                if(motionEvent.action == MotionEvent.ACTION_DOWN){startActivity(Intent(this@FirstDemoActivity, PhotoActivity::class.java))}
                true
            }
            btnOpenThird?.setOnClickListener {
                val intent = Intent(this@FirstDemoActivity, ThirdDemoActivity::class.java)
                startActivity(intent)
            }
            btnOpenStorage?.setOnClickListener {
                val intent = Intent(this@FirstDemoActivity, StorageActivity1::class.java)
                startActivity(intent)
            }
            btnToFragments?.setOnClickListener {
                val intent = Intent(this@FirstDemoActivity, TestFragmentActivity::class.java)
                startActivity(intent)
            }
            btnToRecyclerView?.setOnClickListener {
                val intent = Intent(this@FirstDemoActivity, RecyclerViewActivity::class.java)
                startActivity(intent)
            }
            btnToCreateUIInCode?.setOnClickListener {
                startActivity(Intent(this@FirstDemoActivity, CreateUIInCodeActivity::class.java))
            }
            btnToThousand?.setOnClickListener {
                startActivity(Intent(this@FirstDemoActivity, ThousandActivity::class.java))
            }
            btnToNotification?.setOnClickListener {
                startActivity(Intent(this@FirstDemoActivity, NotificationActivity::class.java))
            }
            btnToService?.setOnClickListener {
                startActivity(Intent(this@FirstDemoActivity, ServiceActivity::class.java))
            }
            btnToGetLocation?.setOnClickListener{
                startActivity(Intent(this@FirstDemoActivity, GeoLocationActivity::class.java))
            }
            btnToBroadcastReceiver?.setOnClickListener {
                startActivity(Intent(this@FirstDemoActivity, BroadCastReceiverActivity::class.java))
            }
            btnToInnerService?.setOnClickListener {
                startActivity(Intent(this@FirstDemoActivity, InnerSensorsActivity::class.java))
            }
            btnToEnvironmentalMonitoring?.setOnClickListener{
                startActivity(Intent(this@FirstDemoActivity, EnvironmentalMonitoringActivity::class.java))
            }
            btnToNavGraph?.setOnClickListener {
                startActivity(Intent(this@FirstDemoActivity, MyNavigationActivity::class.java))
            }
            btnToNavDrawer?.setOnClickListener {
                startActivity(Intent(this@FirstDemoActivity, NavDrawerActivity::class.java))
            }
            btnToNavigationActivityVarsion2?.setOnClickListener {
                startActivity(Intent(this@FirstDemoActivity, NavigationActivity2::class.java))
            }
            btnToVectorImage?.setOnClickListener {
                startActivity(Intent(this@FirstDemoActivity, VectorImageActivity::class.java))
            }
            btnToSaveDataInDownloads?.setOnClickListener {
                startActivity(Intent(this@FirstDemoActivity, SaveDataOnAndroidActivity::class.java))
            }

        }

    }

//    data class extraWord(
//        val original: String,
//        val translate: String,
//        var learned: Boolean = false,
//    ): Serializable

//    data class extraWord(
//        val original: String,
//        val translate: String,
//        var learned: Boolean = false,
//    ): Parcelable {
//        override fun describeContents(): Int {
//            return 0
//        }
//
//        override fun writeToParcel(dest: Parcel, flags: Int) {
//            dest.writeString(original)
//            dest.writeString(translate)
//            dest.writeByte(if (learned) 1 else 0)
//        }
//
//        constructor(parcel: Parcel): this(
//            original = parcel.readString().toString(),
//            translate = parcel.readString().toString(),
//            learned = parcel.readByte() != 0.toByte()
//        )
//
//        companion object CREATOR : Parcelable.Creator<extraWord> {
//            override fun createFromParcel(source: Parcel): extraWord {
//                return extraWord(source)
//            }
//
//            override fun newArray(size: Int): Array<extraWord?> {
//                return arrayOfNulls(size)
//            }
//
//        }
//    }

    @Parcelize //Нужно в билде градла написать id("kotlin-parcelize") в плагины, что бы заработало
    data class extraWord(
        val original: String,
        val translate: String,
        var learned: Boolean = false,
    ): Parcelable
}
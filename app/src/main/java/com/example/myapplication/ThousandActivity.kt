package com.example.myapplication

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnCreateContextMenuListener
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.DialogFragment
import com.example.myapplication.databinding.ActivityThousandBinding

class ThousandActivity : AppCompatActivity(), DialogInterface.OnClickListener {

    private lateinit var binding: ActivityThousandBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThousandBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding){
            btnToStandartDialog.setOnClickListener {
                MyAlertDialog().show(supportFragmentManager, "EmptyDialog")
            }
            btnToDialogWithRadioButtons.setOnClickListener{
                AlterDialogWithRadioButtons().show(supportFragmentManager, "RadioButtons")
            }
            btnToDialogWithFLags.setOnClickListener{
                AlertDialogWithFlags().show(supportFragmentManager, "Flags")
            }
            btnToTimePicker.setOnClickListener {
                MyTimePickerDialog().show(supportFragmentManager, "TimePicker")
            }
            btnToChoosePlayers.setOnClickListener {
                PlayersPickerDialog().show(supportFragmentManager, "Players")
            }
        }
    }

    override fun onClick(dialog: DialogInterface?, which: Int) {
        when(which){
            DialogInterface.BUTTON_POSITIVE -> binding.tvAnswerFromDialog.text = "Вы согласились"
            DialogInterface.BUTTON_NEUTRAL -> binding.tvAnswerFromDialog.text = "Вы не определилсь"
            DialogInterface.BUTTON_NEGATIVE -> binding.tvAnswerFromDialog.text = "Вы отказались"

        }
    }

    class MyAlertDialog: DialogFragment(){
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            return activity?.let{
                AlertDialog.Builder(it)
                    .setMessage("Вы примите приглишение?")
                    .setPositiveButton("Да", activity as DialogInterface.OnClickListener)
                    .setNeutralButton("Не знаю", activity as DialogInterface.OnClickListener)
                    .setNegativeButton("Нет", activity as DialogInterface.OnClickListener)

                    .create()
            } ?: throw IllegalStateException("Activity cannot be null")
        }
    }

    class AlterDialogWithRadioButtons: DialogFragment(){
        val langs = arrayOf("Java","Kotlin","C++","Python")
        var choice = 0
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            return activity?.let{
                AlertDialog.Builder(it)
                    .setSingleChoiceItems(langs, -1, {dialog, which -> choice = which})
                    .setPositiveButton("ok") //После этого в фигурный скобках сразу пишем действие-ответ, так как мы не добавляли слушатель
                    {dialog, which -> Toast.makeText(activity, langs[choice] + " отличный выбор", Toast.LENGTH_SHORT).show() } // После этого можно написать dismiss(), что бы закрыть окошко сразу после выбора
                    .create()

            }?: throw IllegalStateException("Activity cannot be null")
        }
    }
    class AlertDialogWithFlags: DialogFragment(){
        val langs = arrayOf("Java","Kotlin","C++","Python")
        val checked = booleanArrayOf(false, true, true, false)
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            return activity?.let{
                AlertDialog.Builder(it)
                    .setMultiChoiceItems(langs, checked){dialog, which, isChecked ->
                        checked[which] = isChecked
                    }
                    .setPositiveButton("ok", {dialog, which -> Toast.makeText(activity, "Выбрано языков: " + checked.count({it}), Toast.LENGTH_SHORT).show()})
                    .create()
            }?: throw IllegalStateException("Activity cannot be null")
        }
    }
    class MyTimePickerDialog: DialogFragment(){
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val c = Calendar.getInstance()
            val curHour = c.get(Calendar.HOUR_OF_DAY)
            val curMinute = c.get(Calendar.MINUTE)
            return TimePickerDialog(activity, {_, hour, minute -> Toast.makeText(activity, "Установлено время "+ hour+":"+minute, Toast.LENGTH_SHORT).show()}, curHour, curMinute, false)
        }
    }
    class PlayersPickerDialog: DialogFragment(){
        val playersNumber = arrayOf("2","3","4")
        var choice = 0
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            return activity?.let{
                AlertDialog.Builder(it)
//                    .setView(layoutInflater.inflate(R.layout.alert_dialog_view_for_choose_players, null)) //Тут ещё подумаем, как сделать кастомную всплывашку так, что бы та работала. Только разметка удалена
                    .setSingleChoiceItems(playersNumber, 0, {dialog, which -> choice = which})
                    .setPositiveButton("ok")
                    {dialog, which -> Toast.makeText(activity, playersNumber[choice] +" игрока играет!", Toast.LENGTH_SHORT).show()
                        when(which){
                            0 -> playersNumber.toString()
                            1 -> playersNumber.toString()
                            2 -> playersNumber.toString()
                        }
                    }
                    .create()

            }?:throw IllegalStateException("Activity cannot be null")
        }
    }
}
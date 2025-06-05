package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Этот класс наследуется от адаптера для RecycleView, что бы можно было самому настраивать логику
class RecyclerViewItem(private val name:List<String>): RecyclerView.Adapter<RecyclerViewItem.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        //Этот класс содержит ссылки на элементы UI каждого элемента
        val firstText: TextView = itemView.findViewById(R.id.tvFirstText)
        val secondText: TextView = itemView.findViewById(R.id.tvSecondText)
        val element: LinearLayout = itemView.findViewById(R.id.Layout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        //Этот метод содержит представление одного элемента списка RecyclerView
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item, parent, false) // Созание xlm разметки списка
        return MyViewHolder(itemView) //мы кидаем разметку элемента в наш класс. Этот класс на её основе создаёт элементы разметки в виде перменных, позволяя с ними работать
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) { //Тут чисто логика прописывается
        //Связываем данные с элементами
        holder.firstText.text = name[position]
        holder.secondText.text = "Some text"
        holder.element.setBackgroundResource(R.drawable.shape_rounded_container)
        // Сверху прописан вид по умлочанию. Когда эти элементы будут уходить за экран, они будут меняться на вид по умолчанию
        holder.secondText.setOnClickListener { holder.secondText.text = "Хватит тыкать!" }
        holder.element.setOnClickListener { holder.element.setBackgroundResource(R.drawable.shape_rounded_container_correct)}
    }

    override fun getItemCount(): Int {
        return name.size
    }

}
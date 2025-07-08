package com.example.recylerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExamAdapter(var list :ArrayList<ExamEntity>,var clickInterface: ClickOn):RecyclerView.Adapter<ExamAdapter.Viewholder>() {
    class Viewholder (var view: View):RecyclerView.ViewHolder(view){
        var textName=view.findViewById<TextView>(R.id.edt1)
        var textDate=view.findViewById<TextView>(R.id.edt2)
        var updateText=view.findViewById<Button>(R.id.btnUpdate)
        var deleteText=view.findViewById<Button>(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExamAdapter.Viewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.exam_item, parent, false)
        return Viewholder(view)
    }
    override fun onBindViewHolder(holder: ExamAdapter.Viewholder, position: Int) {
        holder.apply {
            textName.setText(list[position].examName)
            textDate.setText(list[position].examDate)
            updateText.setOnClickListener {
                clickInterface.update(position)
            }
            deleteText.setOnClickListener {
                clickInterface.delete(position)
            }
        }
    }
    override fun getItemCount(): Int {
        return list.size
    }
    interface ClickOn{
        fun update(position: Int)
        fun delete(position: Int)
    }

}
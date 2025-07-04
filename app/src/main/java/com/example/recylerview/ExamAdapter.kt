package com.example.recylerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExamAdapter(var list :ArrayList<ExamEntity>):RecyclerView.Adapter<ExamAdapter.Viewholder>() {
    class Viewholder (var view: View):RecyclerView.ViewHolder(view){
        var visay=view.findViewById<TextView>(R.id.edt1)
        var date=view.findViewById<TextView>(R.id.edt2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExamAdapter.Viewholder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.exam_item, parent, false)
        return Viewholder(view)
    }
    override fun onBindViewHolder(holder: ExamAdapter.Viewholder, position: Int) {
        holder.apply {
            visay.setText(list[position].examName)
            date.setText(list[position].examDate)
        }
    }
    override fun getItemCount(): Int {
        return list.size
    }

}
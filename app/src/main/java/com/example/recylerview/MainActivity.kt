package com.example.recylerview

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recylerview.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var ExamAdapter: ExamAdapter
    var ExamItem= arrayListOf<ExamEntity>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        ExamItem.add(ExamEntity(examName = "Maths", examDate = "20/08/2023"))
        ExamItem.add(ExamEntity(examName = "Maths", examDate = "20/08/2023"))
        ExamItem.add(ExamEntity(examName = "Maths", examDate = "20/08/2023"))
        ExamItem.add(ExamEntity(examName = "Maths", examDate = "20/08/2023"))
        ExamAdapter=ExamAdapter(ExamItem)
        binding.rv1.layoutManager= LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        binding.rv1.adapter=ExamAdapter
        ExamAdapter.notifyDataSetChanged()
    }
}
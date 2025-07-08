package com.example.recylerview

import android.app.Dialog
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText

import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recylerview.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore


class MainActivity : AppCompatActivity(), ExamAdapter.ClickOn {
    private lateinit var binding: ActivityMainBinding


    lateinit var ExamAdapter: ExamAdapter
    var Item = arrayListOf<ExamEntity>()
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
        Item.add(ExamEntity(examName = "Maths", examDate = "20/08/2023"))
        Item.add(ExamEntity(examName = "Maths", examDate = "20/08/2023"))

        ExamAdapter = ExamAdapter(Item, this)
        binding.rv1.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rv1.adapter = ExamAdapter
        ExamAdapter.notifyDataSetChanged()
        binding.fab1.setOnClickListener {
            var dialog = Dialog(this)
            dialog.setContentView(R.layout.dialogue_box)
            var textName = dialog.findViewById<EditText>(R.id.et1)
            var textDate = dialog.findViewById<EditText>(R.id.et2)

            dialog.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            dialog.show()

            var submitText = dialog.findViewById<Button>(R.id.btnSubmit)
            var cancelText = dialog.findViewById<Button>(R.id.btnCancel)

            submitText.setOnClickListener {
                var sub = textName.text.toString()
                var date = textDate.text.toString()
                Item.add(ExamEntity(examName = sub, examDate = date))
                ExamAdapter.notifyDataSetChanged()
                Toast.makeText(
                    this@MainActivity,
                    "${textName.text}${textDate.text}",
                    Toast.LENGTH_SHORT
                ).show()
                dialog.dismiss()
            }
            cancelText.setOnClickListener {

                dialog.dismiss()
            }
        }
    }



    override fun update(position: Int) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialogue_box)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        val textName = dialog.findViewById<EditText>(R.id.et1)
        val textDate = dialog.findViewById<EditText>(R.id.et2)
        val submitText = dialog.findViewById<Button>(R.id.btnSubmit)
        val cancelText = dialog.findViewById<Button>(R.id.btnCancel)


        textName.setText(Item[position].examName)
        textDate.setText(Item[position].examDate)

        dialog.show()
        submitText.setOnClickListener {
            val newName = textName.text.toString()
            val newDate = textDate.text.toString()

            Item[position] = ExamEntity(newName, newDate)
            ExamAdapter.notifyItemChanged(position)
            dialog.dismiss()
        }
        cancelText.setOnClickListener {
            dialog.dismiss()
        }
    }


    override fun delete(position: Int) {
            Item.removeAt(position)
            ExamAdapter.notifyItemRemoved(position)
            Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show()
        }


    }

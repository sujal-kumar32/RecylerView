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
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore


class MainActivity : AppCompatActivity(), ExamAdapter.ClickOn {
    private lateinit var binding: ActivityMainBinding

    lateinit var ExamAdapter: ExamAdapter
    var Item = arrayListOf<ExamEntity>()
    val db = Firebase.firestore
    var collectionName = db.collection("exams")
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

        collectionName.addSnapshotListener { snapshots, error ->
            if (error != null) {
                Toast.makeText(this, "Firestore Error: ${error.message}", Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }

            for (dc in snapshots!!.documentChanges) {
                val exam = dc.document.toObject(ExamEntity::class.java)
                exam.id = dc.document.id

                when (dc.type) {
                    DocumentChange.Type.ADDED -> {
                        Item.add(exam)
                    }
                    DocumentChange.Type.MODIFIED -> {
                        val index = getIndex(exam)
                        if (index > -1) Item[index] = exam
                    }
                    DocumentChange.Type.REMOVED -> {
                        val index = getIndex(exam)
                        if (index > -1) Item.removeAt(index)
                    }
                }
            }
            ExamAdapter.notifyDataSetChanged()
        }


        ExamAdapter = ExamAdapter(Item, this)
        binding.rv1.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rv1.adapter = ExamAdapter
        ExamAdapter.notifyDataSetChanged()
        binding.fab1.setOnClickListener {
           try {


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
                   val name = textName.text.toString()
                   val date = textDate.text.toString()
                   val newExam = ExamEntity(examName = name, examDate = date)

                   db.collection("exams").add(newExam)
                       .addOnSuccessListener {
                           Toast.makeText(this, "Exam Added", Toast.LENGTH_SHORT).show()
                       }
                       .addOnFailureListener {
                           Toast.makeText(this, "Add Failed", Toast.LENGTH_SHORT).show()
                       }
                   dialog.dismiss()
               }

               cancelText.setOnClickListener {

                   dialog.dismiss()
               }
           }catch (e:Exception){
               Toast.makeText(this, e.localizedMessage, Toast.LENGTH_SHORT).show()
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

        val exam = Item[position]
        textName.setText(exam.examName)
        textDate.setText(exam.examDate)

        dialog.show()

        submitText.setOnClickListener {
            val newName = textName.text.toString()
            val newDate = textDate.text.toString()

            val updatedExam = ExamEntity(newName, newDate)

            exam.id?.let {
                db.collection("exams").document(it)
                    .set(updatedExam)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show()
                    }
            }
            dialog.dismiss()
        }

        cancelText.setOnClickListener { dialog.dismiss() }
    }



    override fun delete(position: Int) {
        Item[position].id?.let {
            db.collection("exams").document(it)
                .delete()
                .addOnSuccessListener {
                    Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Delete failed", Toast.LENGTH_SHORT).show()
                }
        }
    }


    fun getIndex(exam: ExamEntity): Int {
        return Item.indexOfFirst { it.id == exam.id }
    }



}

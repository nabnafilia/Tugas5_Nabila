package com.example.tugas5kelasa

import DatabaseHelper
import RecyclerViewAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    private lateinit var itemList: MutableList<String>
    private lateinit var databaseHelper: DatabaseHelper // Inisialisasi DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Inisialisasi DatabaseHelper
        databaseHelper = DatabaseHelper(this)

        // Ambil seluruh data dari database
        itemList = databaseHelper.getAllItems()

        // Mengatur padding berdasarkan WindowInsets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inisialisasi RecyclerView
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Inisialisasi RecyclerViewAdapter dengan itemList
        recyclerViewAdapter = RecyclerViewAdapter(itemList)
        recyclerView.adapter = recyclerViewAdapter

        val buttonAdd = findViewById<Button>(R.id.buttonAdd)
        buttonAdd.setOnClickListener {
            showAddItemDialog() // Tampilkan dialog saat tombol ditekan
        }
    }

    // Method untuk menampilkan dialog tambah item
    private fun showAddItemDialog() {
        val builder = AlertDialog.Builder(this)
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_item, null)

        builder.setView(dialogView)

        // Mengambil referensi dari layout dialog
        val editTextItemName = dialogView.findViewById<EditText>(R.id.editTextItemName)
        val buttonSave = dialogView.findViewById<Button>(R.id.buttonSave)

        val alertDialog = builder.create()
        alertDialog.show()

        // Event listener untuk tombol save
        buttonSave.setOnClickListener {
            val newItem = editTextItemName.text.toString()

            if (newItem.isNotEmpty()) {
                val databaseHelper = DatabaseHelper(this)

                // Menyimpan item baru ke dalam database
                databaseHelper.insertItem(newItem)

                // Kosongkan itemList dan ambil semua item terbaru dari database
                itemList.clear()
                itemList.addAll(databaseHelper.getAllItems()) // Ambil semua item terbaru
                recyclerViewAdapter.notifyDataSetChanged() // Perbarui tampilan

                alertDialog.dismiss() // Menutup dialog
        } else {
                editTextItemName.error = "Item name cannot be empty"
            }
        }
    }

    private fun refreshItemList() {
        itemList.clear() // Kosongkan itemList yang lama
        itemList.addAll(databaseHelper.getAllItems()) // Ambil semua data dari database
        recyclerViewAdapter.notifyDataSetChanged()
    }
    // Method untuk menambahkan item baru ke RecyclerView
    private fun addItemToRecyclerView(newItem: String) {
        // Tambah data baru ke dalam itemList
        itemList.add(newItem)

        // Perbarui data dari database untuk memastikan semua data ditampilkan
        itemList.clear()
        itemList.addAll(DatabaseHelper(this).getAllItems())

        // Notifikasi adapter untuk memperbarui tampilan
        recyclerViewAdapter.notifyDataSetChanged()
}
}

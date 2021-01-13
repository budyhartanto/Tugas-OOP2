package com.example.crudkoi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crudkoi.Database.AppRoomDB
import com.example.crudkoi.Database.Constant
import com.example.crudkoi.Database.Koi
import kotlinx.android.synthetic.main.activity_koi.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class KoiActivity : AppCompatActivity() {

    val db by lazy { AppRoomDB(this) }
    lateinit var koiAdapter: KoiAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_koi)
        setupListener()
        setupRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        loadKoi()
    }

    fun loadKoi(){
        CoroutineScope(Dispatchers.IO).launch {
            val allKoi = db.koiDao().getAllKoi()
            Log.d("HelmActivity", "dbResponse: $allKoi")
            withContext(Dispatchers.Main) {
                koiAdapter.setData(allKoi)
            }
        }
    }

    fun setupListener() {
        btn_createKoi.setOnClickListener {
           intentEdit(0, Constant.TYPE_CREATE)
        }
    }

    fun setupRecyclerView() {
        koiAdapter = KoiAdapter(arrayListOf(), object: KoiAdapter.OnAdapterListener {
            override fun onClick(koi: Koi) {
                intentEdit(koi.id, Constant.TYPE_READ)
            }

            override fun onDelete(koi: Koi) {
                deleteDialog(koi)
            }

            override fun onUpdate(helm: Koi) {
                // edit data
                intentEdit(helm.id, Constant.TYPE_UPDATE)
            }

        })
        list_koi.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = koiAdapter
        }
    }

    fun intentEdit(koiId: Int, intentType: Int ) {
        startActivity(
            Intent(applicationContext, EditKoiActivity::class.java)
                .putExtra("intent_id", koiId)
                .putExtra("intent_type", intentType)
        )
    }

    private fun deleteDialog(koi: Koi) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setTitle("Konfirmasi")
            setMessage("Yakin ingin menghapus data ini?")
            setNegativeButton("Batal") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            setPositiveButton("Hapus") { dialogInterface, i ->
                dialogInterface.dismiss()
                CoroutineScope(Dispatchers.IO).launch {
                    db.koiDao().deleteKoi(koi)
                    loadKoi()
                }
            }
        }
        alertDialog.show()
    }
}
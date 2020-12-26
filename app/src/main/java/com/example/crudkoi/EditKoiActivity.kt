package com.example.crudkoi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.crudkoi.Database.AppRoomDB
import com.example.crudkoi.Database.Constant
import com.example.crudkoi.Database.Koi
import kotlinx.android.synthetic.main.activity_edit_koi.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditKoiActivity : AppCompatActivity() {

    val db by lazy { AppRoomDB(this) }
    private var koiId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_koi)
        setupListener()
        setupView()
    }

    fun setupListener(){
        btn_saveKoi.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                db.koiDao().addKoi(
                    Koi(0, txt_jenis.text.toString(), Integer.parseInt(txt_stok.text.toString()), Integer.parseInt(txt_harga.text.toString()) )
                )
                finish()
            }
        }
    }

    fun setupView() {
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val intentType = intent.getIntExtra("intent_type", 0)
        when (intentType) {
            Constant.TYPE_CREATE -> {

            }
            Constant.TYPE_READ -> {
                btn_saveKoi.visibility = View.GONE
                getKoi()
            }
        }
    }

    fun getKoi() {
        koiId = intent.getIntExtra("intent_id", 0)
        CoroutineScope(Dispatchers.IO).launch {
           val kois =  db.koiDao().getKoi( koiId )[0]
            txt_jenis.setText( kois.jenis )
            txt_stok.setText( kois.stok.toString() )
            txt_harga.setText( kois.harga.toString() )
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
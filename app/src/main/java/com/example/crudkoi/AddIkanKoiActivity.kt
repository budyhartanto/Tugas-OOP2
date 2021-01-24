package com.example.crudkoi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.database.*

class AddIkanKoiActivity : AppCompatActivity() {

    private lateinit var tvNama : TextView
    private lateinit var etKoi : EditText
    private lateinit var etJumlah : EditText
    private lateinit var btnKoi : Button
    private lateinit var lvKoi : ListView
    private lateinit var koiList : MutableList<Ikankoi>
    private lateinit var ref : DatabaseReference


    companion object{
        const val EXTRA_NAMA = "extra_nama"
        const val EXTRA_ID = "extra_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_ikan_koi)

        val id =intent.getStringExtra(EXTRA_ID)
        val nama = intent.getStringExtra(EXTRA_NAMA)

        koiList = mutableListOf()

        ref = FirebaseDatabase.getInstance().getReference("ikan_koi").child(id.toString())

        tvNama = findViewById(R.id.tv_nama)
        etKoi = findViewById(R.id.et_koi)
        etJumlah = findViewById(R.id.et_jumlah)
        btnKoi = findViewById(R.id.btn_koi)
        lvKoi = findViewById(R.id.lv_koi)

        btnKoi.setOnClickListener{
            saveKoi()
        }
    }

    fun saveKoi(){
        val namaKoi = etKoi.text.toString().trim()
        val jumlahText = etJumlah.text.toString().trim()
        val jumlah = jumlahText.toInt()

        if(namaKoi.isEmpty()){
            etKoi.error = "Koi harus diisi"
            return
        }
        if(jumlahText.isEmpty()){
            etJumlah.error = "Jumlah harus diisi"
            return
        }

        val koiId = ref.push().key

        val koi = Ikankoi(koiId!!,namaKoi,jumlah)

        if (koiId != null) {
            ref.child(koiId).setValue(koi).addOnCompleteListener{
                Toast.makeText(applicationContext, "IkanKoi berhasil ditambahkan", Toast.LENGTH_SHORT).show()
            }
        }

        ref.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    koiList.clear()
                    for(h in p0.children){
                        val ikankoi = h.getValue(Ikankoi::class.java)
                        if (ikankoi != null) {
                            koiList.add(ikankoi)
                        }
                    }

                    val adapter = IkanKoiAdapter(this@AddIkanKoiActivity, R.layout.item_koi, koiList)
                    lvKoi.adapter = adapter
                }
            }

        })
    }
}

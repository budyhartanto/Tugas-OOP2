package com.example.crudkoi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import com.google.firebase.database.*


class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var etNama : EditText
    private lateinit var etAlamat : EditText
    private lateinit var btnSave : Button
    private lateinit var listUsr : ListView
    private lateinit var ref : DatabaseReference
    private lateinit var usrList: MutableList<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ref = FirebaseDatabase.getInstance().getReference("user")

        etNama = findViewById(R.id.et_nama)
        etAlamat = findViewById(R.id.et_alamat)
        btnSave = findViewById(R.id.btn_save)
        listUsr = findViewById(R.id.lv_usr)
        btnSave.setOnClickListener(this)

        usrList = mutableListOf()

        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    usrList.clear()
                    for (h in p0.children) {
                        val user = h.getValue(User::class.java)
                        if (user != null) {
                            usrList.add(user)
                        }
                    }

                    val adapter = UserAdapter(this@MainActivity, R.layout.item_usr, usrList)
                    listUsr.adapter = adapter
                }
            }
        })

        listUsr.setOnItemClickListener{ parent, view, position, id ->
            val user = usrList.get(position)

            val intent = Intent(this@MainActivity, AddIkanKoiActivity::class.java)
            intent.putExtra(AddIkanKoiActivity.EXTRA_ID, user.id)
            intent.putExtra(AddIkanKoiActivity.EXTRA_NAMA, user.nama)
            startActivity(intent)
        }
    }

    override fun onClick(v: View?) {
        saveData()
    }

    private fun saveData(){
        val nama = etNama.text.toString().trim()
        val alamat = etAlamat.text.toString().trim()

        if(nama.isEmpty()){
            etNama.error = "Isi Nama!"
            return
        }

        if(alamat.isEmpty()){
            etAlamat.error = "Isi alamat!"
            return
        }



        val usrId = ref.push().key

        val usr = User(usrId!!,nama,alamat)

        if (usrId != null) {
            ref.child(usrId).setValue(usr).addOnCompleteListener {
                Toast.makeText(applicationContext, "Data berhasil ditambahkan", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}
package com.example.crudkoi

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class UserAdapter(val mCtx : Context, val layoutResId : Int, val usrList :List<User> ) :
    ArrayAdapter<User>(mCtx, layoutResId, usrList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater : LayoutInflater = LayoutInflater.from(mCtx)

        val view : View = layoutInflater.inflate(layoutResId, null)

        val tvNama : TextView = view.findViewById(R.id.tv_nama)
        val tvAlamat : TextView = view.findViewById(R.id.tv_alamat)
        val tvEdit : TextView = view.findViewById(R.id.tv_edit)


        val user = usrList[position]

        tvEdit.setOnClickListener{
            showUpdateDialog(user)
        }


        tvNama.text = user.nama
        tvAlamat.text = user.alamat

        return view
    }

    fun showUpdateDialog(user: User) {
        val builder = AlertDialog.Builder(mCtx)
        builder.setTitle("Edit Data")

        val inflater = LayoutInflater.from(mCtx)
        val view = inflater.inflate(R.layout.update_dialog, null)

        val etNama = view.findViewById<EditText>(R.id.et_nama)
        val etAlamat = view.findViewById<EditText>(R.id.et_alamat)

        etNama.setText(user.nama)
        etAlamat.setText(user.alamat)

        builder.setView(view)

        builder.setPositiveButton("Update"){p0,p1 ->
            val dbUsr = FirebaseDatabase.getInstance().getReference("user")

            val nama = etNama.text.toString().trim()
            val alamat = etAlamat.text.toString().trim()
            if(nama.isEmpty()){
                etNama.error = "Mohon nama di iisi"
                etNama.requestFocus()
                return@setPositiveButton
            }
            if(alamat.isEmpty()){
                etAlamat.error = "Mohon alamat diisi"
                etAlamat.requestFocus()
                return@setPositiveButton
            }


            val user1 = User(user.id, nama, alamat)


            dbUsr.child(user1.id).setValue(user1)

            Toast.makeText(mCtx, "Data berhasil di update", Toast.LENGTH_SHORT).show()

        }

        builder.setNeutralButton("no"){p0,p1 ->

        }


        builder.setNegativeButton("delete"){p0,p1 ->

            val dbUsr =  FirebaseDatabase.getInstance().getReference("user").child(user.id)


            dbUsr.removeValue()


            Toast.makeText(mCtx, "Data berhasil dihapus", Toast.LENGTH_SHORT).show()

        }

        val alert = builder.create()
        alert.show()
    }
}
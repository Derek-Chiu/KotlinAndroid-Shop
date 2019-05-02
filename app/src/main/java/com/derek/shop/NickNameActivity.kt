package com.derek.shop

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_nick_name.*

class NickNameActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_nick_name)
    done.setOnClickListener {
      setNickname(edit_nickname.text.toString())
      val uid = FirebaseAuth.getInstance().currentUser?.uid
      FirebaseDatabase.getInstance()
        .getReference("users")
        .child(uid!!)
        .child("nickname")
        .setValue(edit_nickname.text.toString())
      setResult(Activity.RESULT_OK)
      finish()
    }
  }
}

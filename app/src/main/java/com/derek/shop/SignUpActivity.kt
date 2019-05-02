package com.derek.shop

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_sign_up)
    signup.setOnClickListener {
      val sEmail = edit_email.text.toString()
      val sPassword = edit_password.text.toString()
      FirebaseAuth.getInstance()
          .createUserWithEmailAndPassword(sEmail, sPassword)
          .addOnCompleteListener { task: Task<AuthResult> ->
            if (task.isSuccessful) {
              AlertDialog.Builder(this)
                  .setTitle("Sign Up")
                  .setMessage("Account created")
                  .setPositiveButton("OK") { dialog, which ->
                    dialog.dismiss()
                    setResult(Activity.RESULT_OK)
                    finish()
                  }.show()
            } else {
              AlertDialog.Builder(this)
                  .setTitle("Sign Up")
                  .setMessage(task.exception?.message)
                  .setPositiveButton("OK", null)
                  .show()

            }
          }
    }
  }
}

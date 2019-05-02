package com.derek.shop

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlin.math.log

class ContactActivity : AppCompatActivity() {

  private val RC_CONTACTS: Int = 110
  private val TAG = ContactActivity::class.java.simpleName

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_contact)

    val permission =
      ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
    if (permission != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(this,
        arrayOf(Manifest.permission.READ_CONTACTS),
        RC_CONTACTS)
    } else {
      readContacts()
    }
  }

  override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    if (requestCode == RC_CONTACTS) {
      if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        readContacts()
      }
    }
  }

  private fun readContacts() {
    val cursor = contentResolver.query(
      ContactsContract.Contacts.CONTENT_URI,
      null, null, null, null
    )
    while (cursor.moveToNext()) {
      val name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
      Log.d(TAG, "onCreate: $name")
    }
  }


}

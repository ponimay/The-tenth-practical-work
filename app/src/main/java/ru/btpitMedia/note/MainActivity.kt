package ru.btpitMedia.note

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout

class MainActivity : AppCompatActivity() {
    var colors = arrayOf(0xFF19473E, 0xFF0A41ED, 0xFF570928, 0xFFFF0000, 0xFF00FF00, 0xFF0000FF, 0xFFFFFF00, 0xFFFF00FF, 0xFF00FFFF)
    var sheetNumber = 0
    private lateinit var text: EditText
    private var size = 16
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sheetNumber = getIntent().getIntExtra("sheetNumber", 0)
        text = findViewById(R.id.text)
        val next: Button = findViewById(R.id.next)
        val previousBtn: Button = findViewById(R.id.previous)

        next.setOnClickListener {
            if (sheetNumber < colors.size - 1) {
                val intent = Intent(this, this::class.java)
                intent.putExtra("sheetNumber", sheetNumber + 1)
                startActivity(intent)
            } else {
                Toast.makeText(applicationContext, "Последняя страница", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        val plus = findViewById<Button>(R.id.plus)
        val minus = findViewById<Button>(R.id.minus)

        plus.setOnClickListener {
            plusFont()
        }

        minus.setOnClickListener {
            minusFont()
        }
        previousBtn.setOnClickListener {
            pPage(it)
        }
    }

    override fun onPause() {
        super.onPause()
        val prefs = getPreferences(Context.MODE_PRIVATE).edit()
        prefs.putString("note" + sheetNumber, text.editableText.toString())
        prefs.apply()
    }

    override fun onResume() {
        super.onResume()
        val sheet: ConstraintLayout = findViewById(R.id.sheet)
        sheet.setBackgroundColor(colors[sheetNumber].toInt())
        val saveState =
            getPreferences(Context.MODE_PRIVATE).getString("note" + sheetNumber.toString(), null)
        if (saveState != null) {
            text.setText(saveState)
        }
    }
    fun pPage(view: View) {
        if (sheetNumber > 0) {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("sheetNumber", sheetNumber - 1)
            startActivity(intent)
        } else {
            Toast.makeText(applicationContext, "Первая страница", Toast.LENGTH_SHORT).show()
        }
    }
    private fun plusFont() {
        size += 2
        text.textSize = size.toFloat()
    }

    private fun minusFont() {
        size -= 2
        text.textSize = size.toFloat()
    }
}
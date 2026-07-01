package com.example.myandroidapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val constraintLayout = androidx.constraintlayout.widget.ConstraintLayout(this)
        
        val textView = TextView(this).apply {
            id = TextView.generateViewId()
            text = "Welcome to My Mobile-Built App!"
            textSize = 24f
        }
        
        val button = Button(this).apply {
            id = Button.generateViewId()
            text = "Click Me"
            setOnClickListener {
                Toast.makeText(this@MainActivity, "Hello World! Built completely from my phone!", Toast.LENGTH_LONG).show()
            }
        }
        
        constraintLayout.addView(textView)
        constraintLayout.addView(button)
        
        val set = androidx.constraintlayout.widget.ConstraintSet()
        set.clone(constraintLayout)
        
        set.connect(textView.id, androidx.constraintlayout.widget.ConstraintSet.TOP, androidx.constraintlayout.widget.ConstraintSet.PARENT_ID, androidx.constraintlayout.widget.ConstraintSet.TOP, 100)
        set.connect(textView.id, androidx.constraintlayout.widget.ConstraintSet.START, androidx.constraintlayout.widget.ConstraintSet.PARENT_ID, androidx.constraintlayout.widget.ConstraintSet.START, 0)
        set.connect(textView.id, androidx.constraintlayout.widget.ConstraintSet.END, androidx.constraintlayout.widget.ConstraintSet.PARENT_ID, androidx.constraintlayout.widget.ConstraintSet.END, 0)
        
        set.connect(button.id, androidx.constraintlayout.widget.ConstraintSet.TOP, textView.id, androidx.constraintlayout.widget.ConstraintSet.BOTTOM, 50)
        set.connect(button.id, androidx.constraintlayout.widget.ConstraintSet.START, androidx.constraintlayout.widget.ConstraintSet.PARENT_ID, androidx.constraintlayout.widget.ConstraintSet.START, 0)
        set.connect(button.id, androidx.constraintlayout.widget.ConstraintSet.END, androidx.constraintlayout.widget.ConstraintSet.PARENT_ID, androidx.constraintlayout.widget.ConstraintSet.END, 0)
        
        set.applyTo(constraintLayout)
        setContentView(constraintLayout)
    }
}


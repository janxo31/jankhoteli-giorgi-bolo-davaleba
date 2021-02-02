package com.example.bolodavaleba

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var SignOutButon: Button
    private lateinit var DisplayName: TextView

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        auth = FirebaseAuth.getInstance()
        SignOutButon = findViewById(R.id.SignOutButon)
        DisplayName = findViewById(R.id.DisplayName)
        val TextTranslationY = Numbers_prog.translationY


        SignOutButon.setOnClickListener{
            signOut()

        }

        DisplayName.text = auth.currentUser?.displayName


        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Numbers_prog.text = progress.toString()

                val translationDistance = (TextTranslationY + progress * resources.getDimension(R.dimen.text_anim_step) * -1)

                Numbers_prog.animate().translationY(translationDistance)
                if (!fromUser)
                        Numbers_prog.animate().setDuration(500).rotation(360f)
                            .translationY(TextTranslationY)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })

        ResetButon.setOnClickListener { view ->
            seekBar.progress = 0


        }

    }

    private fun signOut(){
        auth.signOut()
        val intent = Intent(this, Register::class.java)
        startActivity(intent)
        finish()
    }
}
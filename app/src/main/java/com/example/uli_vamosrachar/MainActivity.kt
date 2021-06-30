package com.example.uli_vamosrachar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    // TTS
    lateinit var mTTS : TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rachada = ContaRachada()
        val cifrao: String = getString(R.string.cifrao) + " "
        val valorInicial: String = getString(R.string.valorInicial)

        val contaInput: EditText = findViewById(R.id.contaInput)
        val contaBTN: ImageButton = findViewById(R.id.contaBTN)
        val grupoInput: EditText = findViewById(R.id.grupoInput)
        val grupoBTN: ImageButton = findViewById(R.id.grupoBTN)
        val resultado: TextView = findViewById(R.id.resultado)
        val shareBTN: FloatingActionButton = findViewById(R.id.shareBTN)
        val speakBTN: FloatingActionButton = findViewById(R.id.speakBTN)

        (cifrao + valorInicial).also { resultado.text = it }

        contaBTN.setOnClickListener {
            contaInput.requestFocus()
        }

        grupoBTN.setOnClickListener {
            grupoInput.requestFocus()
        }

        shareBTN.setOnClickListener {
            val msg = "O valor da conta dividida por pessoa deu " + cifrao + rachada.rachaConta()
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
            val intent = Intent(Intent.ACTION_SEND)
            "text/plain".also { intent.type = it }
            intent.putExtra(Intent.EXTRA_TEXT, msg)
            startActivity(intent)
        }

        with(contaInput) {

            addTextChangedListener(object: TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    try {
                        rachada.contaTotal = text.toString().toDouble()
                    } catch (e: Exception) {
                        rachada.contaTotal = 0.0
                    }
                    (cifrao + rachada.rachaConta()).also { resultado.text = it }
                }
            })
        }

        with(grupoInput) {

            addTextChangedListener(object: TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    try {
                        rachada.grupoNumero = text.toString().toInt()
                    } catch (e: Exception) {
                        rachada.grupoNumero = 1
                    }
                    (cifrao + rachada.rachaConta()).also { resultado.text = it }
                }
            })
        }

        //TTS
        mTTS = TextToSpeech(applicationContext, TextToSpeech.OnInitListener{})

        speakBTN.setOnClickListener {
            val fala = "O valor da conta dividida por pessoa deu " + rachada.rachaConta() + " reais"
            Toast.makeText(this, fala, Toast.LENGTH_SHORT).show()
            mTTS.speak(fala, TextToSpeech.QUEUE_FLUSH, null)
        }
    }
}
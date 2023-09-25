package com.example.imcapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ResultIMCActivity : AppCompatActivity() {

    private lateinit var mostrarResult:TextView
    private lateinit var resultadoIMC:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_imcactivity)
        initComponents()
        showResultOnScreen()
    }

    private fun initComponents()
    {
        mostrarResult = findViewById<TextView>(R.id.mostrarResultado)
        resultadoIMC = intent.extras?.getString("RESULTADO_CALCULO").orEmpty()
    }

    private fun showResultOnScreen()
    {
        //establecer el texto que se mostrará por pantalla
        mostrarResult.text = resultadoIMC
    }
}
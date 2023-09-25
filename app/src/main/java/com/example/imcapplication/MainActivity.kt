package com.example.imcapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.google.android.material.slider.Slider
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    //botones

    // lateinit significa que se inicializa cuando yo quiera, pero no se puede hacer la referencaia aqui, solo en onCreate
    private lateinit var boton_hombre:CardView
    private lateinit var boton_mujer:CardView
    private lateinit var slider_altura:Slider
    private lateinit var texto_altura:TextView
    private lateinit var input_peso:EditText
    private lateinit var input_edad:EditText
    private lateinit var button_calcular:Button

    //valores para el calculo
    var isMan:Boolean=true
    var isWoman:Boolean=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initComponents()
        initListeners()
        selectGender()

    }

    private fun initComponents()
    {
        boton_hombre = findViewById<androidx.cardview.widget.CardView>(R.id.masculino)
        boton_mujer = findViewById<androidx.cardview.widget.CardView>(R.id.femenino)
        slider_altura = findViewById<com.google.android.material.slider.Slider>(R.id.slider_altura)
        texto_altura = findViewById<TextView>(R.id.texto_altura)
        input_edad = findViewById<EditText>(R.id.input_edad)
        input_peso = findViewById<EditText>(R.id.input_peso)
        button_calcular = findViewById<Button>(R.id.calcular_resultado)
    }

    private  fun initListeners()
    {
        //botones de genero
        boton_hombre.setOnClickListener{
            isMan=true
            isWoman=false
            selectGender()
        }
        boton_mujer.setOnClickListener {
            isMan=false
            isWoman=true
            selectGender()
        }
        //listener slider
        slider_altura.addOnChangeListener { _, value, _ ->
            //definir el formato que seguiran los numeros
            val df = DecimalFormat("#.##")
            //el resultado se almacena en resultado siguiendo el formato df
            val resultado = df.format(value)
            texto_altura.text = "$resultado cm"
        }
        //boton de resultado
        button_calcular.setOnClickListener(){

            //comprobar que no esten vacias las casillas de peso y edad
            if((input_peso.text.toString().isNotEmpty()) && (input_edad.text.toString().isNotEmpty()))
            {
                //preparar el Intent, donde le especificas donde estas y a donde vas
                val intent = Intent(this, ResultIMCActivity::class.java)

                intent.putExtra("RESULTADO_CALCULO",calcularIMC())

                startActivity(intent)
            }
        }
    }

    //lo que harán los botones de genero cuando se pulsen
    private fun selectGender()
    {
        //Hay que usar el setCardBackgroundColor, si usar el que no tiene card
        //llena de color todo el fondo y no solo lo el cardView
        boton_hombre.setCardBackgroundColor(getBackgroundColor(isMan))
        boton_mujer.setCardBackgroundColor(getBackgroundColor(isWoman))
    }

    //funcion que va a determinar el color que va a tener el genero dependiendo si esta seleccionado
    private fun getBackgroundColor(isSelected:Boolean): Int {
        //en esta variable voy a almacenar el color que interesa
        val colorRef = if (isSelected)
            R.color.boton_on_click
        else
            R.color.boton

        return ContextCompat.getColor(this, colorRef)
    }

    private fun calcularIMC(): String
    {
        //cuando el resultado del imc esté entre estos valores...

        Log.d("aqui",(((slider_altura.value)/100)*2).toString())
                                                        //el problema esta en la altura, ya que convierte 165 en 165.00 (teoría)
        when((input_peso.text.toString().toDouble())/(((slider_altura.value)/100)*2))
        {
            in -1.0..18.9->{return "Bajo peso"}
            in 19.0..24.9->{return "Normal"}
            in 25.0..29.9->{return "Sobrepeso"}
            in 30.0..34.9->{return  "Obesidad 1"}
            in 35.0..40.0->{return  "Obesidad 2"}
            in 40.0..9999.0->{return  "Obesidad 3"}
        }

        return "Error 404"
    }
}

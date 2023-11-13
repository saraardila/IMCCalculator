package com.nawin.imccalculator.imccalculator

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.slider.RangeSlider
import com.nawin.imccalculator.R
import java.text.DecimalFormat

class ImcCalculatorActivity : AppCompatActivity(){

    private lateinit var viewMale: CardView  //--> lateinit es para que se ejecute cuando yo diga.
    private lateinit var viewFemale: CardView //--> inicializar
    private lateinit var tvHeight: TextView
    private lateinit var rsHeight: RangeSlider
    private lateinit var btnSubtractWeight: FloatingActionButton
    private lateinit var btnPlusWeight: FloatingActionButton
    private lateinit var tvWeight: TextView
    private lateinit var btnSubtractAge: FloatingActionButton
    private lateinit var btnPlusAge: FloatingActionButton
    private lateinit var tvAge: TextView
    private lateinit var btnCalculate: Button

    companion object { //--> objeto que se puede acceder desde cualquier pantalla.

        const val IMC_KEY = "IMC_RESULT" //--para que sea igual en todas las llamadas y no la caguemos.
    }

    private var isManSelected: Boolean = true
    private var isFemaleSelected: Boolean = false
    private var currentWeight: Int = 70 //--> inicializar desde donde empieza el peso
    private var currentAge: Int = 25
    private var currentHeight: Int = 120

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imc_calculator)

        initComponent() //--> Las buscamos
        initListener() //--> Le decimos lo que tiene que hacer
        initUI() //--> inicializamos los metodos.
    }
/////////////////////LAS LLAMADAS/////////////////////

    private fun initComponent() {

        viewMale = findViewById(R.id.viewMale)
        viewFemale = findViewById(R.id.viewFemale)
        tvHeight = findViewById(R.id.tvHeight)
        rsHeight = findViewById(R.id.rsHeight)
        btnPlusWeight = findViewById(R.id.btnPlusWeight)
        btnSubtractWeight = findViewById(R.id.btnSubtractWeight)
        tvWeight = findViewById(R.id.tvWeigth)
        btnPlusAge = findViewById(R.id.btnPlusAge)
        btnSubtractAge = findViewById(R.id.btnSubtractAge)
        tvAge = findViewById(R.id.tvAge)
        btnCalculate = findViewById(R.id.btnCalculate)
    }

    private fun initListener() {

        viewMale.setOnClickListener() {
            setGenderColor()
            changeGender()
        }
        viewFemale.setOnClickListener() {
            setGenderColor()
            changeGender()
        }
        //-->Para que el valor del range slider y del textview sean igual
        rsHeight.addOnChangeListener { _, value, _ -> //--> si no se usa, se pone _

            val df = DecimalFormat("#.##")
            currentHeight = df.format(value).toInt() //--> para quitar los decimales del valor

            tvHeight.text = "$currentHeight cm" //--> el textview es igual al valor del rs.
        }

        btnPlusWeight.setOnClickListener {
            //--> asi se simplifica sumar/restar
            currentWeight += 1

            setWeight()
        }
        btnSubtractWeight.setOnClickListener {

            currentWeight -= 1

            setWeight()

        }

        btnPlusAge.setOnClickListener {

            currentAge += 1

            setAge()
        }
        btnSubtractAge.setOnClickListener {

            currentAge -= 1

            setAge()

        }

        btnCalculate.setOnClickListener() {

            val result = calculateIMC()
            navigateToResult(result)
        }

    }




////////////////////LA LOGICA//////////////

    private fun changeGender() { //--> para que cambie de true a false, sino no hace nada.
        isManSelected = !isManSelected
        isFemaleSelected = !isFemaleSelected
    }

    //--> Buscamos si es chico o chica y en funcion de eso devuelve el color.
    private fun getBackgroundColor(isSelectedComponent: Boolean): Int { //--> los dos puntitos es lo que devuelve del return
        //-->Le damos la condicion de si es chico o chica, que color pone.
        val colorReference = if (isSelectedComponent) {
            R.color.background_component_selected
        } else {
            R.color.background_component
        }

        return ContextCompat.getColor(
            this,
            colorReference
        ) //--> esto es un int y es donde realmente recuperamos el color.
        // esto para recuperar el color seria lo mismo que esto:
        // ContextCompat.getColor(this, R.color.background_component_selected) , pero habria que hacerlo dos veces, por eso lo guardamos en un var y optimizamos
    }

    private fun setGenderColor() {//--> le pasamos el color del background al cardview.

        viewMale.setCardBackgroundColor(getBackgroundColor(isManSelected)) //--> le pasamos si es true o false
        viewFemale.setCardBackgroundColor(getBackgroundColor(isFemaleSelected))
    }

    private fun setWeight() {

        tvWeight.text =
            currentWeight.toString() //--> se lo asignamos al text para que lo vaya cambiando.
    }

    private fun setAge() {

        tvAge.text = currentAge.toString() //--> se lo asignamos al text para que lo vaya cambiando.

    }

    private fun calculateIMC() :Double{
        val df = DecimalFormat("#.#")
        val imc = currentWeight / (currentHeight.toDouble() / 100 * currentHeight.toDouble() / 100)
        return  df.format(imc).toDouble()
        //Log.i("Nawin", "El imc es: $result")
    }

    private fun navigateToResult(result: Double) {

        val intent = Intent(this,ResultIMCActivity::class.java)
        intent.putExtra(IMC_KEY, result)
        startActivity(intent)
    }

    private fun initUI() { //--> para que aparezca inicializado con algo

        setGenderColor()
        setWeight()
        setAge()
    }
}
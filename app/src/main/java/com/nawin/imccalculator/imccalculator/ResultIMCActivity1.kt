package com.nawin.imccalculator.imccalculator
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.nawin.imccalculator.R

class ResultIMCActivity : AppCompatActivity() {

    private lateinit var tvResult: TextView
    private lateinit var tvIMC: TextView
    private lateinit var tvDescription: TextView
    private lateinit var btnRecalculate: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_imcactivity)

        val result = intent.extras?.getDouble(ImcCalculatorActivity.IMC_KEY)
            ?: -1.0 //-- que busque si hay algun intent que devuelve y sino, que devuelva un double

        initComponents()
        initUI(result)
        initListeners()

    }

    private fun initListeners() {

        btnRecalculate.setOnClickListener { onBackPressed() }//--> volver atrás
    }

    private fun initUI(result: Double) {

        tvIMC.text = result.toString()

        when (result) {

            in 0.00..18.50 -> { //--> Bajo peso
                tvResult.text = getString(R.string.title_bajopeso)
                tvDescription.text = getString(R.string.description_bajopeso)
                tvResult.setTextColor(ContextCompat.getColor(this, R.color.pesobajo))
            }

            in 18.51..24.99 -> { //--> Peso normal
                tvResult.text = getString(R.string.title_peso_normal)
                tvDescription.text = getString(R.string.description_peso_normal)
                tvResult.setTextColor(ContextCompat.getColor(this, R.color.pesonormal))
            }

            in 25.00..29.99 -> { //-->Sobrepeso
                tvResult.text = getString(R.string.title_sobrepeso)
                tvDescription.text = getString(R.string.description_sobrepeso)
                tvResult.setTextColor(ContextCompat.getColor(this, R.color.sobrepeso))
            }

            in 30.00..99.00 -> { //-->Obesidad
                tvResult.text = getString(R.string.title_obesidad)
                tvDescription.text = getString(R.string.description_obesidad)
                tvResult.setTextColor(ContextCompat.getColor(this, R.color.obesidad))
            }

            else -> { //error
                tvResult.text = getString(R.string.error)
                tvIMC.text = getString(R.string.error)
                tvDescription.text = getString(R.string.error)
                tvResult.setTextColor(ContextCompat.getColor(this, R.color.obesidad))
            }
        }
    }

    private fun initComponents() {

        tvIMC = findViewById(R.id.tvIMC)
        tvResult = findViewById(R.id.tvResult)
        tvDescription = findViewById(R.id.tvDescription)
        btnRecalculate = findViewById(R.id.btnRecalculate)
    }
}

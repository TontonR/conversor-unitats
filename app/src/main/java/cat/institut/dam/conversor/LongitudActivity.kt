package cat.institut.dam.conversor

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class LongitudActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_longitud);

        // Al polsar convertir
        findViewById<Button>(R.id.botoConvertir).setOnClickListener {
            val valorString : String = findViewById<EditText>(R.id.editValor).text.toString().trim();

            if (valorString.isEmpty()){
                Toast.makeText(this, R.string.error_buid , Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val valorDouble : Double? = valorString.toDoubleOrNull();
            if (valorDouble == null){
                Toast.makeText(this, R.string.error_numeric , Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val desdeId : Int = findViewById<RadioGroup>(R.id.radioGroupDe).checkedRadioButtonId;
            val desdeButton = findViewById<RadioButton>(desdeId);
            val finsId : Int = findViewById<RadioGroup>(R.id.radioGroupA).checkedRadioButtonId;
            val finsButton = findViewById<RadioButton>(finsId);
            if ( desdeButton.text == finsButton.text) {
                Toast.makeText(this, R.string.error_unitats , Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val result = calcularConversion(valorDouble, desdeButton.text.toString(), finsButton.text.toString())
            val symbol = if (finsButton.text.toString() == getString(R.string.quilometre)){
                " km"
            } else if (finsButton.text.toString() == getString(R.string.milla)){
                " mi"
            } else {
                " yd"
            }
            val resultString = String.format(Locale.US, "%.4f", result) + symbol

            findViewById<TextView>(R.id.textResultat).text = resultString;
        }
    }

    /**
     * Metode encarregat de retornar el valor convertit
     */

    private fun  calcularConversion(value: Double, de: String, a: String): Double {
        var result = value;

        // es passa tot primer a km
        if (de == getString(R.string.milla)) {
            result *= 1.60934;
        } else if (de == getString(R.string.yarda)) {
            result *= 0.0009144;
        }

        // retorna en la magnitud dessitgada
        if (a == getString(R.string.quilometre)) {
            return result;
        } else if (a == getString(R.string.milla)) {
            return  result * 0.621371;
        } else if (a == getString(R.string.yarda)) {
            return result * 1093.6133;
        } else {
            Toast.makeText(this, R.string.error_generic , Toast.LENGTH_SHORT).show();
        }
        return result;
    }
}
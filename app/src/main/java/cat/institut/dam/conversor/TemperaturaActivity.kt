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

class TemperaturaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperatura);

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
            val symbol = if (finsButton.text.toString() == getString(R.string.celsius)){
                " ºC"
            } else if (finsButton.text.toString() == getString(R.string.fahrenheit)){
                " ºF"
            } else {
                " K"
            }
            val resultString = String.format(Locale.US, "%.2f", result) + symbol

            findViewById<TextView>(R.id.textResultat).text = resultString;
        }
    }

    /**
     * Metode encarregat de retornar el valor convertit
     */

    private fun  calcularConversion(value: Double, de: String, a: String): Double {
        var result = value;

        // es passa tot primer a celsius
        if (de == getString(R.string.fahrenheit)) {
            result = (result - 32) * 5 / 9;
        } else if (de == getString(R.string.kelvin)) {
            result -= 273.15;
        }

        // retorna en la magnitud dessitgada
        if (a == getString(R.string.celsius)) {
            return result;
        } else if (a == getString(R.string.fahrenheit)) {
            return  result * 9 / 5 + 32;
        } else if (a == getString(R.string.kelvin)) {
            return result + 273.15;
        } else {
            Toast.makeText(this, R.string.error_generic , Toast.LENGTH_SHORT).show();
        }
        return result;
    }
}
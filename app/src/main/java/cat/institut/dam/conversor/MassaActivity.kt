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

class MassaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_massa);

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
            val symbol = if (finsButton.text.toString() == getString(R.string.quilogram)){
                " kg"
            } else if (finsButton.text.toString() == getString(R.string.lliura)){
                " lb"
            } else {
                " oz"
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

        // es passa tot primer a kg
        if (de == getString(R.string.lliura)) {
            result *=  0.453592
        } else if (de == getString(R.string.unca)) {
            result *= 0.0283495;
        }

        // retorna en la magnitud dessitgada
        if (a == getString(R.string.quilogram)) {
            return result;
        } else if (a == getString(R.string.lliura)) {
            return  result * 2.20462;
        } else if (a == getString(R.string.unca)) {
            return result * 35.27396;
        } else {
            Toast.makeText(this, R.string.error_generic , Toast.LENGTH_SHORT).show();
        }
        return result;
    }
}
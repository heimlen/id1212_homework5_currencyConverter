package se.heimlen.id1212.homework5.currencyconverter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Formatter;

/**
 * Activity to showcase the result of the conversion to the user.
 */

public class ResultActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        showResult();
    }

    private void showResult() {
        Intent intent = getIntent();
        String currencyFrom = intent.getStringExtra("currencyFrom");
        String currencyTo = intent.getStringExtra("currencyTo");
        double convertAmount = intent.getDoubleExtra("convertAmount", 0.0);
        double resultAmount = intent.getDoubleExtra("resultAmount", 0.0);

        final TextView textCurrencyFrom = findViewById(R.id.textCurrencyFrom);
        final TextView textCurrencyTo = findViewById(R.id.textCurrencyTo);
        final TextView textConvertAmount = findViewById(R.id.textConvertAmount);
        final TextView textResultAmount = findViewById(R.id.textResultAmount);

        textCurrencyFrom.setText(currencyFrom);
        textCurrencyTo.setText(currencyTo);
        DecimalFormat formatter = new DecimalFormat("#0.00");
        textConvertAmount.setText(formatter.format(convertAmount));
        textResultAmount.setText(formatter.format(resultAmount));
    }
}

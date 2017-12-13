package se.heimlen.id1212.homework5.currencyconverter;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * The main activity of the currency converter, this activity contains the loading of currencies,
 * the picking of currencies and the computation to find the converted currency.
 */

public class MainActivity extends AppCompatActivity {
    private JSONObject jsonObject;
    private JSONObject exchangeRates;
    private JSONArray currencyNames;
    private ArrayList<String> exchangeRatesName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new DownloadJSON().execute();
    }
    //TODO Create new methods setupUI(), convert(), etc. and remove code that has not to do with downloading
    //json from DownloadJSON class,
    /**
     * inner class that extends AsyncTask to ensure multithreaded UI
     */
    private class DownloadJSON extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            exchangeRatesName = new ArrayList<>();
//            jsonObject = JSONFunctions.getJSONFromURL("https://api.fixer.io/latest");
            try {
                jsonObject = new JSONObject(IOUtils.toString(new URL("https://api.fixer.io/latest"), Charset.forName("UTF-8")));
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
            try {
                exchangeRatesName.add(jsonObject.getString("base"));
                exchangeRates = jsonObject.getJSONObject("rates");
                currencyNames = exchangeRates.names();
                exchangeRates.put("EUR", 1.0);
                for(int i = 0; i < currencyNames.length(); i++) {
                    exchangeRatesName.add(currencyNames.getString(i));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            // Locate the spinner in activity_main.xml
            final Spinner convertFrom = findViewById(R.id.convertFromSpinner);
            final Spinner convertTo = findViewById(R.id.convertToSpinner);
            final EditText amount = findViewById(R.id.amount);
            final Button convert = findViewById(R.id.convert);

            // Fill spinners with fetched JSONData
            convertFrom.setAdapter(new ArrayAdapter<>(MainActivity.this,
                            android.R.layout.simple_spinner_dropdown_item,
                            exchangeRatesName));

            convertTo.setAdapter(new ArrayAdapter<>(MainActivity.this,
                            android.R.layout.simple_spinner_dropdown_item,
                            exchangeRatesName));

            convert.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    String currencyFrom = convertFrom.getSelectedItem().toString();
                    String currencyTo = convertTo.getSelectedItem().toString();
                    double convertAmount = Double.valueOf(amount.getText().toString());
                    double cFrom = exchangeRates.optDouble(currencyFrom);
                    double cTo = exchangeRates.optDouble(currencyTo);

                    double endResult = convertAmount * (cTo / cFrom);

                    String endResults = "Endresult is " + String.valueOf(endResult);

                    Toast.makeText(MainActivity.this,endResults,Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}

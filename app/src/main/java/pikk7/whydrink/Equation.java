package pikk7.whydrink;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Equation extends AppCompatActivity {
    ArrayList<Drink> drinks;
    public static String EXTRA_MESSAGE = "";
    public Spinner spinnerGlass;
    public Spinner spinner;
    public Spinner spinnerDrink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equation);
        //preferencies beállítása
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        //toolbar és actionbar
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        drinks = new ArrayList<>();
        spinner = findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.sexes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //ital választó
        spinnerDrink = findViewById(R.id.spinnerDrink);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.DrinkTypes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDrink.setAdapter(adapter2);


        //pohár választó
        spinnerGlass = findViewById(R.id.spinnerGlasses);

// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, R.array.glasses, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinnerGlass.setAdapter(adapter3);


        Button calcBTN = findViewById(R.id.calculate);


        calcBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double Wt = 0;
                EditText weight = findViewById(R.id.weight);
                double DP = 0;
                EditText elapsedTime = findViewById(R.id.elapsedTime);
                try {
                    Wt = Double.parseDouble(weight.getText().toString());
                } catch (NumberFormatException ex) {
                    Log.e("Wt", "Calculator button onClick", ex);
                    // Intent intent=new Intent(getBaseContext(),ErrorActivity.class);
                    // intent.putExtra(EXTRA_MESSAGE,getString(R.string.error_weight));
                    //startActivity(intent);
                    weight.setError(getString(R.string.error_weight));
               Wt=-1;

                }catch (Exception ext) {
                    Log.e("EveryThing", ext.getMessage(), ext);
                    Intent intent = new Intent(getApplicationContext(), ErrorActivity.class);
                    intent.putExtra(EXTRA_MESSAGE, "LOL");
                    startActivity(intent);
                }


                try {

                    DP = Double.parseDouble(elapsedTime.getText().toString());

                } catch (NumberFormatException ex) {
                    Log.e("DP", "Calculator button onClick", ex);

                    elapsedTime.setError(getString(R.string.error_Count));
                    return;

                } catch (Exception ext) {
                    Log.e("EveryThing", ext.getMessage(), ext);
                    Intent intent = new Intent(getApplicationContext(), ErrorActivity.class);
                    intent.putExtra(EXTRA_MESSAGE, "LOL");
                    startActivity(intent);
                }
                if(Wt<0){
                    return;
                }
                boolean male = false;

                Spinner spinner = findViewById(R.id.spinner);

                if (spinner.getSelectedItem().equals("Male")) {
                    male = true;
                }

                Spanned message = WidmarkFormula(toSD(drinks), male, Wt, DP);

                Intent intent = new Intent(getApplicationContext(), Result.class);
                intent.putExtra(EXTRA_MESSAGE, message.toString());
                startActivity(intent);
            }
        });
    }

    double toSD(ArrayList<Drink> drinks) {
        double SD = 0;

        for (int i = 0; i < drinks.size(); ++i) {
            SD = SD + drinks.get(i).getAlcoholDose();
        }

        return SD;
    }

    Spanned WidmarkFormula(double SD, boolean male, double Wt, double DP) {
        double BW = 0.49;
        double MR = 0.017;
        if (male) {
            BW = 0.58;
            MR = 0.015;
        }
        double equ = (((0.806 * SD * 1.2) / (BW * Wt)) - MR * DP);

        return  new SpannableString(String.valueOf(equ));
    }


    public void AddDrink(View view) {

        TextView count = findViewById(R.id.count);
        Drink current;
        int db;
        try {
            db = Integer.parseInt(count.getText().toString());

        } catch (NumberFormatException ex) {
            Log.e("Add", "AddDrink onClick", ex);

            count.setError(getString(R.string.error_Count));
            return;

        }
        if (db < 1) {
            count.setError(getString(R.string.not_null));
            return;
        }
        if (MainActivity.local.equals("hu")) {
            current = new Drink(DrinkTypeHu.valueOf(spinnerDrink.getSelectedItem().toString()), db, GlassesHu.valueOf((spinnerGlass.getSelectedItem().toString())));

        } else {
            current = new Drink(DrinkTypeEn.valueOf(spinnerDrink.getSelectedItem().toString()), db, GlassesEn.valueOf((spinnerGlass.getSelectedItem().toString())));

        }
        drinks.add(current);

        count.setText("");

        Toast.makeText(getApplicationContext(), current.drinkType.toString() + " " + current.db + " " + current.glass.toString(), Toast.LENGTH_SHORT).show();

        TextView drinkList = findViewById(R.id.DrinkList);
        String text = drinkList.getText().toString();
        drinkList.setText(text + System.lineSeparator() + current.drinkType.toString() + " " + current.db + " " + current.glass.toString());
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    //menü lekezelése
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(getApplicationContext(),LangugesSettingsActivity.class));
                return true;


            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
    public void Reset(View view) {
        drinks = new ArrayList<>();
        EditText weight = findViewById(R.id.weight);
        weight.setText("");
        TextView drinkList = findViewById(R.id.DrinkList);
        drinkList.setText("");
        EditText elapsedTime = findViewById(R.id.elapsedTime);
        elapsedTime.setText("");
    }
}

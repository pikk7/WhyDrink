package pikk7.whydrink;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class Equation extends AppCompatActivity {
    ArrayList<Drink> drinks;
    public static final String EXTRA_MESSAGE = "pikk7.whydrink.MESSAGE";
    public  Spinner spinnerGlass;
    public Spinner spinner;
    public Spinner spinnerDrink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equation);
        //nem választó spinner
        drinks=new ArrayList<>();
        spinner =  findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.sexes,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //ital választó
        spinnerDrink =  findViewById(R.id.spinnerDrink);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,R.array.DrinkTypes,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDrink.setAdapter(adapter2);


        //pohár választó
        spinnerGlass =  findViewById(R.id.spinnerGlasses);

// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,R.array.glasses,android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinnerGlass.setAdapter(adapter3);




        Button calcBTN= findViewById(R.id.calculate);


        calcBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double Wt=0;
                EditText weight=findViewById(R.id.weight);
              if(!weight.getText().toString().equals("")) {
                     Wt = Double.parseDouble(weight.getText().toString());
              }else{
                  Intent intent=new Intent(getBaseContext(),ErrorActivity.class);
                  intent.putExtra(EXTRA_MESSAGE,"You have to give your weight");
                  startActivity(intent);
              }
                boolean male=false;

                Spinner spinner =  findViewById(R.id.spinner);

                if(spinner.getSelectedItem().equals("Male")){
                   male=true;
               }
               double DP=0;
                EditText elapsedTime=findViewById(R.id.elapsedTime);
                if(elapsedTime.getText().toString().equals("")) {
                    Intent intent=new Intent(getApplicationContext(),ErrorActivity.class);
                    intent.putExtra(EXTRA_MESSAGE, "You have to give the elapsed time from the start!");
                    startActivity(intent);
                }
             //még nincs meg minden adat
                DP = Double.parseDouble(elapsedTime.getText().toString());

                Spanned message = WidmarkFormula(toSD(drinks),male,Wt,DP);

                 Intent intent=new Intent(getApplicationContext(),Result.class);
                intent.putExtra(EXTRA_MESSAGE, message.toString());
                startActivity(intent);

            }
        });
    }
    double toSD(ArrayList<Drink> drinks){
        double SD=0;

    for(int i=0;i<drinks.size();++i){
        SD=SD+drinks.get(i).getAlcoholDose();
    }

        return SD;
    }
    Spanned WidmarkFormula(double SD, boolean male, double Wt, double DP){
        double BW=0.49;
        double MR=0.017;
        if(male){
           BW=0.58;
           MR=0.015;
       }
       double equ=(((0.806*SD*1.2)/(BW*Wt))-MR*DP);

        Spannable spannableString = new SpannableString(String.valueOf(equ));

        //spannableString.setSpan(new ForegroundColorSpan(Color.RED),5,6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }



    public void AddDrink(View view) {

        TextView count = findViewById(R.id.count);

        if (count.getText().toString().equals("")) {
            Intent intent=new Intent(getBaseContext(),ErrorActivity.class);
            intent.putExtra(EXTRA_MESSAGE,"You have to give the count of drinks");
            startActivity(intent);
        }else {
            int db = Integer.parseInt(count.getText().toString());
            Drink current = new Drink(DrinkType.valueOf(spinnerDrink.getSelectedItem().toString()), db, Glasses.valueOf((spinnerGlass.getSelectedItem().toString())));
            drinks.add(current);

            count.setText("");

          Toast.makeText(getApplicationContext(),current.drinkType.toString()+" "+current.db +" "+ current.glass.toString(), Toast.LENGTH_SHORT).show();

        }

    }
}

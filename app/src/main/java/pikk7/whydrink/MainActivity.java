package pikk7.whydrink;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    //globalok amiket használok össze vissza
    public static LinkedList<String> reasons=new LinkedList<>();
    public static int place=0;
  //  public static int reminderHour=0;
   // public static int reminderMinutes=25;
   // String EXTRA_MESSAGE="";
    static MainActivity instance;
    public static String local="hu";


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
       // local=Locale.getDefault().getLanguage();

        reasonsInput();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //preferencies beállítása

        instance=this;
        //toolbar és actionbar
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
       setSupportActionBar(myToolbar);

        final TextView tv1 =findViewById(R.id.textView);
        tv1.setText(getReason(place));


        Button b = findViewById(R.id.nextRSN);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                place++;
                Spanned data=getReason(place);

                tv1.setText(data);

                if(place==reasons.size()){
                    place=0;
                }
            }
        });



        Button x=findViewById(R.id.prevRSN);
        x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    place--;
                    Spanned data=getReason(place);

                    tv1.setText(data);
                }catch(IndexOutOfBoundsException ex){
                    place=reasons.size()-1;
                    Spanned data=getReason(place);

                    tv1.setText(data);
                }

            }
        });


/*
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent notificationIntent = new Intent("android.media.action.DISPLAY_NOTIFICATION");
        notificationIntent.addCategory("android.intent.category.DEFAULT");
        PendingIntent broadcast = PendingIntent.getBroadcast(this, 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, reminderHour);
        calendar.set(Calendar.MINUTE,reminderMinutes);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,broadcast );
       */

//stackoverflow cucc
        /*
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, reminderHour);
        calendar.set(Calendar.MINUTE,reminderMinutes);

        Intent notifyIntent = new Intent(this,AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 100, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),1000 , pendingIntent);
*/


        Button c = findViewById(R.id.toEQ);

        final Intent relativeCall=new Intent(getApplicationContext(),Equation.class);

        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(relativeCall);
            }
        });

    }
    //menü lekezelése
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(getApplicationContext(),LangugesSettingsActivity.class));
                return true;

            case R.id.action_credits:
                startActivity(new Intent(getApplicationContext(),CreditsActivity.class));
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public void reasonsInput() {
        if (findViewById(R.id.nextRSN) != null && place == reasons.size() - 1) {
            Toast.makeText(getApplicationContext(), R.string.array_end, Toast.LENGTH_LONG).show();
        }
        if (reasons.size() != 0 && local.equals(Locale.getDefault().getLanguage())) {
            local=Locale.getDefault().getLanguage();
            return;
        }


        Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("M d");

        String data[] = ft.format(date).split(" ");

        String month = data[0];
        String day = data[1];

        reasons.clear();
        String tmp = "a"+month + "_" + day;
        Resources res = getResources();
        int resID =getResources().getIdentifier(tmp,"array", this.getPackageName());


        reasons = new LinkedList<>(Arrays.asList(res.getStringArray(resID)));

        Collections.shuffle(reasons);
    }
        public Spanned getReason ( int place){
            reasonsInput();

            String[] data;
            if (place >= reasons.size() - 1) {
                place = 0;
            }
            data = reasons.get(place).split(" – ", 2);
         try{
             return Html.fromHtml("<b><p>" + data[0] + "</p></b>" + data[1]);
         }catch (ArrayIndexOutOfBoundsException e){

             return Html.fromHtml(reasons.get(place));
         }
        }

        public static MainActivity getInstance () {
            return instance;
        }

        public boolean onCreateOptionsMenu (Menu menu){
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_main, menu);
            return true;
        }

    @Override
    protected void onRestart() {
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);

        String tmp = sharedPref.getString(getString(R.string.languages_settings), "");

       try{
           String[] wasd=tmp.split(" ");
           local=wasd[2];
           Locale myLocale = Locale.forLanguageTag(local);
           Resources res = this.getResources();
           DisplayMetrics display = res.getDisplayMetrics();
           Configuration configuration = res.getConfiguration();
           configuration.locale = myLocale;
           res.updateConfiguration(configuration, display);
           recreate();
       }catch(Exception e){
           Log.e("SUPER AIDS", String.valueOf(e));
       }


        super.onRestart();
    }
}

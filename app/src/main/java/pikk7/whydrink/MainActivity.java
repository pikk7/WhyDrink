package pikk7.whydrink;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    //globalok amiket használok össze vissza
    static final LinkedList<String> reasons=new LinkedList<>();
    static int count=0;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        reasonsInput();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      final TextView tv1 =findViewById(R.id.textView);

        tv1.setText(getReason());

        Button b = findViewById(R.id.nextRSN);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spanned data=getReason();

                tv1.setText(data);
            }
        });

      /*  Intent notifyIntent = new Intent(this, AlarmReceiver.class);

        final PendingIntent notifyPendingIntent = PendingIntent.getBroadcast
                (this, NOTIFICATION_ID, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
*/
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE, 0);
/*
        alarmer.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, notifyPendingIntent);

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
public void reasonsInput(){
        if(reasons.size()>0){
            return;
        }
        if(findViewById(R.id.nextRSN)!=null){
            Toast.makeText(getApplicationContext(),"End of the list. Shuffle and restart.",Toast.LENGTH_SHORT).show();

        }

    Date date = new Date();
    SimpleDateFormat ft =new SimpleDateFormat ("M d");

    String data[] = ft.format(date).split(" ");

    String month = data[0];
    String day = data[1];


    BufferedReader reader;
    try {
        reader = new BufferedReader(
                new InputStreamReader(getAssets().open(month+"_"+day+".txt"), "UTF-8"));

        // do reading, usually loop until end of file reading
        String mLine;
        while ((mLine = reader.readLine()) != null && !mLine.equals("==Deaths==")&&!mLine.equals( "==Births==")) {
            if(!mLine.equals("==Births==")&&!mLine.equals("==Events==")&& !mLine.equals("==Deaths==")){
                reasons.add(mLine);
                count++;
            }
        }
    } catch (IOException e) {
        Log.e("reasons adding",e.toString());
    }
    Collections.shuffle(reasons);
}
    public  Spanned getReason(){
        reasonsInput();
        String[] data=reasons.poll().split("\\s+",2);
        if(data[0].equals(" ")){
            String [] retval=data[1].split("\\s+",2);
            return Html.fromHtml("<b><p>"+retval[0]+"</p></b>"+retval[1]);
        }
        return Html.fromHtml("<b><p>"+data[0]+"</p></b>"+data[1]);
    }


}

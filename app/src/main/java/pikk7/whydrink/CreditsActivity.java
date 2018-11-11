package pikk7.whydrink;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CreditsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        //toolbar és actionbar
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        Button insta=findViewById(R.id.button2);

        insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // mediaLink is something like "https://instagram.com/p/6GgFE9JKzm/" or
                    String mediaLink= "https://instagram.com/_u/emesepoti";
                    Uri uri = Uri.parse(mediaLink);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);

                    intent.setPackage("com.instagram.android");
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Log.e("Emese instaja", e.getMessage());
                }
            }
        });
        TextView me=findViewById(R.id.button4);
        me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // mediaLink is something like "https://instagram.com/p/6GgFE9JKzm/" or
                    String mediaLink= "https://instagram.com/_u/pikkhet";
                    Uri uri = Uri.parse(mediaLink);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);

                    intent.setPackage("com.instagram.android");
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Log.e("Csabi instaja", e.getMessage());
                }
            }
        });

    }

}

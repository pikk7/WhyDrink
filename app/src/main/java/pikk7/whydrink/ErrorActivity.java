package pikk7.whydrink;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class ErrorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);
        Intent intent = getIntent();
        String message = intent.getStringExtra(Equation.EXTRA_MESSAGE);

        // Capture the layout's TextView and set the string as its text
        TextView resultText = findViewById(R.id.error);
        resultText.setText(message);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
    }
}

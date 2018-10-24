package pikk7.whydrink;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        String text=resultText.getText()+"\n"+message;
        resultText.setText(text);

    }
}

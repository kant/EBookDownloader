package android.nachiketa.ebookdownloader;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }

        TextView textView = findViewById(R.id.tvDisplay);
        try {
            textView.setText(new Global().getRandomQuote());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void execute(View view) {
        EditText etQuery = findViewById(R.id.etQuery);
        RadioButton radBook = findViewById(R.id.radBookName);

        if (!etQuery.getText().toString().equals("")) {
            String choice;
            if (radBook.isChecked())
                choice = "book";
            else
                choice = "author";
            Intent intent = new Intent(this, DownloadActivity.class);
            intent.putExtra("searchQuery", etQuery.getText().toString());
            intent.putExtra("searchBy", choice);
            startActivity(intent);
        } else {
            FancyToast.makeText(this, "Don't shoot blanks!", Toast.LENGTH_LONG, FancyToast.ERROR, false).show();
            etQuery.setFocusable(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("History");
        menu.add("Recommendations");
        menu.add("Exit");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getTitle().toString()) {
            case "History":
                startActivity(new Intent(getApplicationContext(), History.class));
                break;
            case "Recommendations":
                startActivity(new Intent(this, Recommendations.class));
                break;
            case "Exit":
                System.exit(0);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
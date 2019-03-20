package ru.virarnd.coderslist;

import androidx.appcompat.app.AppCompatActivity;
import ru.virarnd.coderslist.views.UsersFragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    public static final String GITHUB = "GitHub";
    public static final String STACKOVERFLOW = "StackOverflow";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, UsersFragment.newInstance(GITHUB))
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, UsersFragment.newInstance(STACKOVERFLOW))
                    .addToBackStack("BackStack")
                    .commit();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}

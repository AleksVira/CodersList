package ru.virarnd.coderslist;

import androidx.appcompat.app.AppCompatActivity;
import ru.virarnd.coderslist.views.CalculatorFragment;
import ru.virarnd.coderslist.views.UsersFragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    public static final String GITHUB = "GitHub";
    public static final String STACKOVERFLOW = "StackOverflow";
    private static final String TAG = MainActivity.class.getSimpleName();


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
        if (item.getItemId() == R.id.action_show_overflow) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, UsersFragment.newInstance(STACKOVERFLOW))
                    .addToBackStack("BackStack")
                    .commit();
            return true;
        } else if (item.getItemId() == R.id.action_show_calculator) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, CalculatorFragment.newInstance())
                    .addToBackStack("BackStack")
                    .commit();

            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}

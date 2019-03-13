package ru.virarnd.coderslist;

import androidx.appcompat.app.AppCompatActivity;
import ru.virarnd.coderslist.views.GitUsersFragment;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, new GitUsersFragment(), GitUsersFragment.class.getSimpleName())
                    .commit();
        }
    }
}

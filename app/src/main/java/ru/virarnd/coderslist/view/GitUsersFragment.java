package ru.virarnd.coderslist.view;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.readystatesoftware.chuck.ChuckInterceptor;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.virarnd.coderslist.R;
import ru.virarnd.coderslist.model.GithubUsersService;
import ru.virarnd.coderslist.model.User;
import ru.virarnd.coderslist.model.UserRecyclerAdapter;

public class GitUsersFragment extends Fragment {

    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    private Unbinder unbinder;
    private UserRecyclerAdapter adapter;
    private GithubUsersService githubUsersService;
    private Call<List<User>> call;

    public GitUsersFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_git_users, container, false);
        unbinder = ButterKnife.bind(this, view);
        adapter = new UserRecyclerAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        createRetrofitInstance();
//        call = githubUsersService.getUser(0);
        call = githubUsersService.getLimitedPerPageUser(0, 50);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                adapter.addUsers(response.body());
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(getContext(), "Error on network request", Toast.LENGTH_LONG).show();
            }
        });

/*
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("Petro", "https://avatars0.githubusercontent.com/u/2?v=4"));
        users.add(new User("Kolyan", null));
        adapter.addUsers(users);
*/

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (call != null) {
            call.cancel();
        }
        unbinder.unbind();
    }


    private void createRetrofitInstance() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        ChuckInterceptor chuckInterceptor = new ChuckInterceptor(getContext());
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor()
//                .addInterceptor()
                .addNetworkInterceptor(httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY))
                .addNetworkInterceptor(chuckInterceptor)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        githubUsersService = retrofit.create(GithubUsersService.class);
    }

}

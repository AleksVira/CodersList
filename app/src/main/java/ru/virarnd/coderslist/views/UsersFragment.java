package ru.virarnd.coderslist.views;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.virarnd.coderslist.App;
import ru.virarnd.coderslist.R;
import ru.virarnd.coderslist.models.UserModel;
import ru.virarnd.coderslist.models.github.GitUsersUserModel;
import ru.virarnd.coderslist.models.overflow.OverflowUsersUserModel;
import ru.virarnd.coderslist.models.users.User;
import ru.virarnd.coderslist.models.users.UserRecyclerAdapter;
import ru.virarnd.coderslist.presenters.UserPresenter;

public class UsersFragment extends Fragment implements UserPresenter.View {

    public static final String TAG = UsersFragment.class.getSimpleName();

    public static final String GITHUB = "GITHUB";

    @BindView(R.id.recycler_git)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private Unbinder unbinder;
    private UserRecyclerAdapter adapter;
    private static UserPresenter presenter;

    public static UsersFragment newInstance(boolean isGitHub) {
        Bundle args = new Bundle();
        args.putBoolean(GITHUB, isGitHub);
        UsersFragment fragment = new UsersFragment();
        fragment.setArguments(args);
        Log.d(TAG, "Instance created, isGitHub = " + isGitHub);
        return fragment;
    }

    public UsersFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users, container, false);
        unbinder = ButterKnife.bind(this, view);

        adapter = new UserRecyclerAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        UserModel userModel;
        if (getArguments().getBoolean(GITHUB)) {
            userModel = new GitUsersUserModel(((App) getActivity().getApplication()).getGithubUsersService());
        } else {
            userModel = new OverflowUsersUserModel(((App) getActivity().getApplication()).getOverflowUsersService());
        }
        if (presenter == null) {
            presenter = new UserPresenter(userModel);
        }
        presenter.attachView(this);

/*
        disposable = userModel.getUsers()
                .retry(3)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(users -> adapter.addUsers(users),
                        error -> Toast.makeText(getContext(), "Error on network request", Toast.LENGTH_LONG).show());
*/

/*
        userModel.getUsers(new UserModel.ModelResponse<List<User>>() {
            @Override
            public void onSuccess(List<User> response) {
                adapter.addUsers(response);
            }

            @Override
            public void onError(Throwable error) {
                Toast.makeText(getContext(), "Error on network request", Toast.LENGTH_LONG).show();
            }
        });
*/


/*
        githubUsersService = ((App) getActivity().getApplication()).getGithubUsersService();
        call = githubUsersService.getLimitedPerPageUser(0, USERS_PER_PAGE);
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
*/

 /*       adapter.setLoadMoreListener(userId -> {
            if (callMoreUsers != null && !callMoreUsers.isExecuted()) {
                return;
            }
            callMoreUsers = githubUsersService.getLimitedPerPageUser(userId, USERS_PER_PAGE);
            callMoreUsers.enqueue(new Callback<List<User>>() {
                @Override
                public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                    adapter.addUsers(response.body());
                }

                @Override
                public void onFailure(Call<List<User>> call, Throwable t) {
                    Toast.makeText(getContext(), "Error on network request", Toast.LENGTH_LONG).show();
                }
            });
        });
*/
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
        if (getActivity().isFinishing()) {
            presenter.stopLoading();
            presenter = null;
        }
        unbinder.unbind();
    }


    @Override
    public void onUserListLoaded(List<User> userList) {
        adapter.addUsers(userList);
        progressBar.setVisibility(View.GONE);

    }

    @Override
    public void onError(String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
        progressBar.setVisibility(View.GONE);
    }
}

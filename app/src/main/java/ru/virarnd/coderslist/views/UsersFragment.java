package ru.virarnd.coderslist.views;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.jakewharton.rxbinding3.widget.RxTextView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.virarnd.coderslist.App;
import ru.virarnd.coderslist.R;
import ru.virarnd.coderslist.models.UserModel;
import ru.virarnd.coderslist.models.github.GitUsersUserModel;
import ru.virarnd.coderslist.models.overflow.OverflowUsersUserModel;
import ru.virarnd.coderslist.models.users.User;
import ru.virarnd.coderslist.models.users.UserRecyclerAdapter;
import ru.virarnd.coderslist.presenters.UserPresenter;

import static ru.virarnd.coderslist.MainActivity.GITHUB;

public class UsersFragment extends Fragment implements UserPresenter.View {

    public static final String TAG = UsersFragment.class.getSimpleName();

    public static final String KEY = "USER_MODEL";

    @BindView(R.id.recycler_git)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.te_input)
    TextInputEditText editText;

    private Unbinder unbinder;
    private UserRecyclerAdapter adapter;
    private UserPresenter presenter;
    private String key;

    public static UsersFragment newInstance(String userModelName) {
        Bundle args = new Bundle();
        args.putString(KEY, userModelName);
        UsersFragment fragment = new UsersFragment();
        fragment.setArguments(args);
//        Log.d(TAG, "Instance created, isGitHub = " + userModelName);
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
        key = getArguments().getString(KEY);
        if (key.equals(GITHUB)) {
            userModel = new GitUsersUserModel(((App) getActivity().getApplication()).getGithubUsersService());
        } else {
            userModel = new OverflowUsersUserModel(((App) getActivity().getApplication()).getOverflowUsersService());
        }

        presenter = ((App) getActivity().getApplication()).getUserPresenter(key);
        if (presenter == null) {
            presenter = new UserPresenter(userModel, ((App) getActivity().getApplication()).getRoomDatabase());
            ((App) getActivity().getApplication()).setUserPresenter(key, presenter);
        }
        presenter.attachView(this);


        RxTextView.textChanges(editText)
                .debounce(700, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(charSequence -> {
                    if (charSequence.length() > 0) {
                        presenter.selectFiltered(charSequence);
                    } else {
                        presenter.noFilter();
                    }
                })
                .subscribe();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
        if (isRemoving()) {
            presenter.stopLoading();
            ((App) getActivity().getApplication()).setUserPresenter(key, null);
        }
        unbinder.unbind();
    }


    @Override
    public void onUserListLoaded(List<User> userList) {
        adapter.addUsers(userList);
        progressBar.setVisibility(View.GONE);

    }

    @Override
    public void onFilteredUserListLoaded(List<User> filteredList) {
        adapter.setFilteredUsers(filteredList);
    }

    @Override
    public void onRemoveFilter(List<User> oldUserList) {
        adapter.setNoFilter(oldUserList);
    }

    @Override
    public void onError(String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
        progressBar.setVisibility(View.GONE);
    }

}

package ru.virarnd.coderslist.views;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ru.virarnd.coderslist.R;
import ru.virarnd.coderslist.presenters.CalculatorPresenter;

public class CalculatorFragment extends Fragment implements CalculatorPresenter.View {

    private static final String TAG = CalculatorFragment.class.getSimpleName();

    @BindView(R.id.tv_result)
    TextView textViewResult;

    @BindView(R.id.arg1)
    EditText editTextArgument1;

    @BindView(R.id.arg2)
    EditText editTextArgument2;

    @OnClick({R.id.plus_button, R.id.minus_button, R.id.multiplication_button, R.id.division_button})
    void onButtonClick(View view) {
        String strArg1 = editTextArgument1.getText().toString();
        String strArg2 = editTextArgument2.getText().toString();
        presenter.calculateMe(strArg1, strArg2, view.getId());
    }


    private CalculatorPresenter presenter;
    private Unbinder unbinder;


    public CalculatorFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        return new CalculatorFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculator, container, false);
        unbinder = ButterKnife.bind(this, view);
        if (presenter == null) {
            presenter = new CalculatorPresenter();
        }
        presenter.attachView(this);
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
        unbinder.unbind();
    }

    @Override
    public void onUserInputError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCalculationReady(String solution) {
        textViewResult.setText(solution);
    }

    @Override
    public void clearArg2() {
        editTextArgument2.setText("");
    }
}

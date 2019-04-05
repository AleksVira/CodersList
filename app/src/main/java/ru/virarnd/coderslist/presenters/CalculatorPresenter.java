package ru.virarnd.coderslist.presenters;

import android.text.TextUtils;
import android.util.Log;

import ru.virarnd.coderslist.R;
import ru.virarnd.coderslist.models.calculator.CalculateHelper;

public class CalculatorPresenter {

    private static final String TAG = CalculatorPresenter.class.getSimpleName();

    private View view;
    private final CalculateHelper calculateHelper = new CalculateHelper();

    public void calculateMe(String strArg1, String strArg2, int viewId) {
        if (TextUtils.isEmpty(strArg1) || TextUtils.isEmpty(strArg2)) {
            view.onUserInputError("Есть пустое поле!");
        } else {
            double arg1 = Double.parseDouble(strArg1);
            double arg2 = Double.parseDouble(strArg2);
            double solution = 0;
            switch (viewId) {
                case R.id.plus_button:
                    solution = calculateHelper.addition(arg1, arg2);
                    break;
                case R.id.minus_button:
                    solution = calculateHelper.subtraction(arg1, arg2);
                    break;
                case R.id.multiplication_button:
                    solution = calculateHelper.multiplication(arg1, arg2);
                    break;
                case R.id.division_button:
                    if (arg2 == 0) {
                        view.onUserInputError("Делить на ноль нельзя!");
                        view.clearArg2();
                        solution = 0;
                    } else {
                        solution = calculateHelper.division(arg1, arg2);
                    }
                    break;
                default:
                    Log.d(TAG, "Непонятная кнопка");
            }
            view.onCalculationReady(String.valueOf(solution));
        }

    }

    public void detachView() {
        this.view = null;
    }

    public interface View {
        void onUserInputError(String s);

        void onCalculationReady(String answer);

        void clearArg2();
    }


    public void attachView(View view) {
        this.view = view;
    }




}

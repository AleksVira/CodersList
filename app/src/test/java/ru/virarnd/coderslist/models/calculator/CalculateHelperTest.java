package ru.virarnd.coderslist.models.calculator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CalculateHelperTest {

    private CalculateHelper calculateHelper;
    @Before
    public void setUp(){
        calculateHelper = new CalculateHelper();
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Тест закончен");
    }

    @Test
    public void addition_isCorrect() {
        assertEquals(34.0, calculateHelper.addition(27, 7), 0.0001);
        assertEquals(15.0, calculateHelper.addition(15, 0), 0.0001);
    }

    @Test
    public void subtraction_isCorrect() {
        assertEquals(20.0, calculateHelper.subtraction(27, 7), 0.0001);
        assertEquals(19.0, calculateHelper.subtraction(19, 0), 0.0001);
    }

    @Test
    public void multiplication_isCorrect() {
        assertEquals(189.0, calculateHelper.multiplication(27, 7), 0.0001);
        assertEquals(0.0, calculateHelper.multiplication(27, 0), 0.0001);
    }

    @Test
    public void division_isCorrect() {
        assertEquals(3.857142, calculateHelper.division(27, 7), 0.0001);
    }

    @Test
    public void division_zero() {
        assertEquals(Double.POSITIVE_INFINITY, calculateHelper.division(27, 0), 0.0);
    }
}
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

import flo.calculator.BaseCalculator;
import flo.calculator.BigDecimalCalculator;

class BigDecimalCalculatorTest {

    BigDecimalCalculator calculator;

    private boolean testFunctionCall(String functionName, String[] values, double[] expectedResults) {
        assert (values.length == expectedResults.length);
        BigDecimal result;
        for (int i = 0; i < values.length; i++) {
            String expression = String.format("%s(%s)", functionName, values[i]);
            result = eval(expression);
            if (result.doubleValue() != expectedResults[i]) throw new AssertionError(String.format("expected: %s=%f but got: %f", expression, expectedResults[i], result.doubleValue()));
        }
        return true;
    }

    private BigDecimal eval(String expression) {
        return calculator.evaluate(expression);
    }

    @BeforeEach
    void setUp() {
        calculator = new BigDecimalCalculator();
    }

    // calculator instantiation
    @Test
    public void testCreateNewCalculator() {
        calculator = new BigDecimalCalculator(100, RoundingMode.DOWN, false, 100);
        assertEquals(100, calculator.getDecimalPlaces());
        assertEquals(calculator.getRoundingMode(), RoundingMode.DOWN);
        assertFalse(calculator.getUseRadians());
        assertEquals(100, calculator.getPrecision());
    }

    @Test
    public void testDefaultCalculator() {
        calculator = new BigDecimalCalculator();
        assertEquals(2, calculator.getDecimalPlaces());
        assertEquals(RoundingMode.DOWN, calculator.getRoundingMode());
        assertTrue(calculator.getUseRadians());
        assertEquals(MathContext.DECIMAL128.getPrecision(), calculator.getPrecision());
    }

    @Test
    public void testCreateNewCalculatorWithInvalidParams() {
        assertThrowsExactly(BigDecimalCalculator.InvalidStartUpConfigurationException.class, () -> calculator = new BigDecimalCalculator(-10, null, true, -25));
    }

    // number recognition tests
    @Test
    public void testRationalNumber() {
        BigDecimal result = eval("1.54");
        assertEquals(1.54, result.doubleValue());
    }

    @Test
    public void testNegativeRationalNumber() {
        calculator.setDecimalPlaces(4);
        BigDecimal result = eval("-88.9234");
        assertEquals(-88.9234, result.doubleValue());
    }

    @Test
    public void testNaturalNumberBelowIntLimit() {
        BigDecimal result = eval("91827364");
        assertEquals(91827364, result.intValue());
    }

    @Test
    public void testNegativeNumberBelowIntLimit() {
        BigDecimal result = eval("-198590");
        assertEquals(-198590, result.intValue());
    }

    // addition tests
    @Test
    public void testBasicAddition() {
        assertEquals(2, eval("1+1").intValue());
        assertEquals(333, eval("234+99").intValue());
    }

    @Test
    public void testBasicAdditionWithRationalNumbers() {
        calculator.setDecimalPlaces(5);
        BigDecimal result = eval("1.12345+5.43210");
        assertEquals(6.55555, result.doubleValue());
    }

    @Test
    public void testAdditionWithMixedNumbers() {
        BigDecimal result = eval("42+0.42");
        assertEquals(42.42, result.doubleValue());
    }

    @Test
    public void testMultipleAdditions() {
        BigDecimal result = eval("10 + 40 + 50");
        assertEquals(100, result.intValue());

        result = eval("10 + 40 + 50 + 60 + 70");
        assertEquals(230, result.intValue());
    }

    // test subtraction
    @Test
    public void testBasicSubtraction() {
        BigDecimal result = eval("10-7");
        assertEquals(3, result.intValue());
    }

    @Test
    public void testBasicSubtractionWithRationalNumbers() {
        calculator.setDecimalPlaces(6);
        BigDecimal result = eval("1.725444-0.025442");
        assertEquals(1.700002, result.doubleValue());
    }

    @Test
    public void testMultipleSubtractions() {
        BigDecimal result = eval("10-7-3");
        assertEquals(0, result.intValue());
    }

    @Test
    public void testSubtractionWithMixedNumbers() {
        BigDecimal result = eval("100-24.5-0.5-12.125-12.875");
        assertEquals(50, result.doubleValue());
    }

    @Test
    public void testSubtractionPositiveToNegative() {
        BigDecimal result = eval("10-15");
        assertEquals(-5, result.intValue());
    }

    // test multiplication
    @Test
    public void testBasicMultiplication() {
        BigDecimal result = eval("8*9");
        assertEquals(72, result.intValue());
    }

    @Test
    public void testBasicMultiplicationWithRationalNumbers() {
        BigDecimal result = eval("1.5*1.5");
        assertEquals(2.25, result.doubleValue());
    }

    @Test
    public void testMultipleMultiplications() {
        BigDecimal result = eval("1*2*3*4*5*6*7*8*9");
        assertEquals(362880, result.intValue());
    }

    @Test
    public void testPositiveNegativeMultiplication() {
        BigDecimal result = eval("-8*19");
        assertEquals(-152, result.intValue());
    }

    @Test
    public void testNegativeNegativeMultiplication() {
        BigDecimal result = eval("-12*-15");
        assertEquals(180, result.intValue());
    }

    // test division
    @Test
    public void testBasicDivision() {
        BigDecimal result = eval("225/15");
        assertEquals(15, result.intValue());
    }

    @Test
    public void testMultipleDivisions() {
        BigDecimal result = eval("2048/2/512");
        assertEquals(2, result.intValue());
    }

    @Test
    public void testPositiveNegativeDivision() {
        BigDecimal result = eval("1024/-4");
        assertEquals(-256, result.intValue());
    }

    @Test
    public void testNegativeNegativeDivision() {
        BigDecimal result = eval("-81/-9");
        assertEquals(9, result.intValue());
    }

    @Test
    public void testDivisionByZero() {
        BigDecimalCalculator.CalculationException e = assertThrowsExactly(BigDecimalCalculator.CalculationException.class, () -> eval("4/0"));
        assertEquals(BigDecimalCalculator.ERROR_MESSAGE.DIVISION_BY_ZERO, e.errorMessage);
    }

    // brackets
    @Test
    public void testSingleBrackets() {
        BigDecimal result = eval("(5)");
        assertEquals(5, result.intValue());
    }

    @Test
    public void testMultipleBrackets() {
        BigDecimal result = eval("((((((42))))))");
        assertEquals(42, result.intValue());
    }

    @Test
    void testBracketsMultiplication() {
        BigDecimal result = eval("(15)(20)");
        assertEquals(300, result.intValue());
    }

    @Test
    void testMultipleBracketsMultiplication() {
        BigDecimal result = eval("(2+3)(7-2)(5*2)(15/3)");
        assertEquals(1250, result.intValue());
    }

    // unary minus
    @Test
    public void testBasicUnaryMinus() {
        BigDecimal result = eval("-7");
        assertEquals(-7, result.intValue());
    }

    @Test
    public void testMultipleUnaryMinuses() {
        BigDecimal result = eval("--7");
        assertEquals(7, result.intValue());
        result = eval("-----------42");
        assertEquals(-42, result.intValue());
    }

    // power
    @Test
    public void testBasicPower() {
        BigDecimal result = eval("5^3");
        assertEquals(125, result.intValue());
    }

    @Test
    void testMultiplePowers() {
        BigDecimal result = eval("2^3^2");
        assertEquals(512, result.intValue());
    }

    @Test
    void testPowerAsSquareRoot() {
        BigDecimal result = eval("81^0.5");
        assertEquals(9, result.doubleValue());
    }

    // test precedence
    @Test
    public void testAddSubMulDivPrecedence() {
        BigDecimal result = eval("2+3*2");
        assertEquals(8, result.intValue());
        result = eval("10+20/2-2");
        assertEquals(18, result.intValue());
    }

    @Test
    public void testAllPrecedenceRules() {
        BigDecimal result = eval("2+(3-2)^3-2/(1+1)");
        assertEquals(2, result.intValue());
    }

    @Test
    void testPrecedenceWithUnaryMinusAndPower() {
        BigDecimal result = eval("-1^2");
        assertEquals(-1, result.intValue());

        result = eval("(-1)^2");
        assertEquals(1, result.intValue());
    }

    @Test
    public void testPrecedenceWithImplicitMultiplication() {
        BigDecimal result = calculator.evaluate("(2)(2)+2");
        assertEquals(6, result.intValue());
    }

    // white space tests
    @Test
    public void testWithWhiteSpaceInBetween() {
        BigDecimal result = eval("1       +            4");
        assertEquals(5, result.intValue());
    }

    @Test
    public void testWithLeadingWhitespace() {
        BigDecimal result = eval("           198+123");
        assertEquals(321, result.intValue());
    }

    @Test
    public void testWithTrailingWhitespace() {
        BigDecimal result = eval("700000+12323                ");
        assertEquals(712323, result.intValue());
    }

    @Test
    public void testWithWhitespaceAsNumberSeparator() {
        BigDecimal result = eval("4 000 190 + 10 000 310");
        assertEquals(14000500, result.intValue());
    }

    // percentage
    @Test
    public void testPercentageSuffix() {
        BigDecimal result = eval("10%");
        assertEquals(0.1, result.doubleValue());
    }

    @Test
    public void testPercentageAdd() {
        BigDecimal result = eval("100+15%");
        assertEquals(115, result.intValue());
    }

    @Test
    public void testPercentageSub() {
        BigDecimal result = eval("200-50%");
        assertEquals(100, result.intValue());
    }

    @Test
    public void testPercentageOver100Percent() {
        BigDecimal result = eval("20-150%");
        assertEquals(-10, result.intValue());
    }

    @Test
    public void testPercentageMultiplication() {
        BigDecimal result = eval("40*75%");
        assertEquals(30, result.intValue());
    }

    // factorial
    @Test
    public void testFactorial() {
        BigDecimal result = eval("5!");
        assertEquals(120, result.intValue());
    }

    @Test
    public void testFactorialBrackets() {
        BigDecimal result = eval("(8-2)!");
        assertEquals(720, result.intValue());
    }

    @Test
    public void testFactorialWithNegativeValue() {
        BigDecimalCalculator.CalculationException e = assertThrowsExactly(BigDecimalCalculator.CalculationException.class, () -> eval("-5!"));

        assertEquals(BigDecimalCalculator.ERROR_MESSAGE.FACTORIAL_WITH_NEGATIVE_VALUE, e.errorMessage);
    }

    @Test
    public void testFactorialWithNonInteger() {
        BigDecimalCalculator.CalculationException e = assertThrowsExactly(BigDecimalCalculator.CalculationException.class, () -> eval("5.6!"));
        assertEquals(BigDecimalCalculator.ERROR_MESSAGE.FACTORIAL_WITH_NON_INTEGER, e.errorMessage);
    }

    // functions
    @Test
    public void testSquareRoot() {
        BigDecimal result = eval("√(9)");
        assertEquals(3, result.intValue());
    }

    @Test
    public void testSquareRoot2() {
        calculator.setDecimalPlaces(20);
        calculator.setRoundingMode(RoundingMode.DOWN);
        BigDecimal result = eval("√(8-6)");
        assertEquals(0, new BigDecimal("1.41421356237309504880").compareTo(result));
    }

    @Test
    public void testSinRad() {
        calculator.setDecimalPlaces(2);
        assertTrue(testFunctionCall(
                "sin",
                new String[]{"0", "π/6", "π/4", "π/3", "π/2", "2π/3", "3π/4", "5π/6", "π", "3π/2", "2π", "2π+π0.5", "-π/6"},
                new double[]{0, 0.5, 0.7, 0.86, 1, 0.86, 0.7, 0.5, 0, -1, 0, 1, -0.5}
        ));
    }

    @Test
    public void testSinDeg() {
        calculator.setDecimalPlaces(2);
        calculator.setUseRadians(false);
        assertTrue(testFunctionCall(
                "sin",
                new String[]{"0", "30", "45", "60", "90", "120", "135", "150", "180", "270", "360", "450", "-30"},
                new double[]{0, 0.5, 0.7, 0.86, 1, 0.86, 0.7, 0.5, 0, -1, 0, 1, -0.5}
        ));
    }

    @Test
    public void testCosRad() {
        calculator.setDecimalPlaces(4);
        assertTrue(testFunctionCall(
                "cos",
                new String[]{"0", "π/6", "π/4", "π/3", "π/2", "2π/3", "3π/4", "5π/6", "π", "3π/2", "2π", "2π+π0.5", "-π/6"},
                new double[]{1, 0.8660, 0.7071, 0.5, 0, -0.5, -0.7071, -0.8660, -1, 0, 1, 0, 0.8660}
        ));
    }

    @Test
    public void testCosDeg() {
        calculator.setDecimalPlaces(4);
        calculator.setUseRadians(false);
        assertTrue(testFunctionCall(
                "cos",
                new String[]{"0", "30", "45", "60", "90", "120", "135", "150", "180", "270", "360", "450", "-30"},
                new double[]{1, 0.8660, 0.7071, 0.5, 0, -0.5, -0.7071, -0.8660, -1, 0, 1, 0, 0.8660}
        ));
    }

    @Test
    public void testTanRad() {
        calculator.setDecimalPlaces(4);
        assertTrue(testFunctionCall(
                "tan",
                new String[]{"0", "π/6", "π/4", "π/3", "2π/3", "3π/4", "5π/6", "π", "2π", "-π/6"},
                new double[]{0,  0.5773,  1,  1.7320, -1.7320,  -1,   -0.5773,     0,     0,   -0.5773}
        ));
    }

    @Test
    public void testTanDeg() {
        calculator.setDecimalPlaces(4);
        calculator.setUseRadians(false);
        assertTrue(testFunctionCall(
                "tan",
                new String[]{"0", "30", "45", "60" , "120",    "135", "150",    "180", "360", "-30"},
                new double[]{0,  0.5773,  1,  1.7320, -1.7320,  -1,   -0.5773,     0,     0,   -0.5773}
        ));
    }

    @Test
    public void testCotRad() {
        calculator.setDecimalPlaces(4);
        assertTrue(testFunctionCall(
                "cot",
                new String[]{"π/4", "π/2", "3π/4", "π/4+π", "π/2+π", "3π/4+π"},
                new double[]{  1,     0,     -1,      1,       0,       -1}
        ));
    }

    @Test
    public void testCotDeg() {
        calculator.setDecimalPlaces(4);
        calculator.setUseRadians(false);
        assertTrue(testFunctionCall(
                "cot",
                new String[]{"45", "90", "135", "225", "270", "315"},
                new double[]{1, 0, -1, 1, 0, -1}
        ));
    }

    @Test
    public void testAsinRad() {
        calculator.setDecimalPlaces(4);
        assertTrue(testFunctionCall(
                "asin",
                new String[]{"-1", "-0.5", "0", "0.5", "1"},
                new double[]{-1.5707, -0.5235 ,0, 0.5235, 1.5707}
        ));
    }

    @Test
    public void testAsinDeg() {
        calculator.setDecimalPlaces(4);
        calculator.setUseRadians(false);
        assertTrue(testFunctionCall(
                "asin",
                new String[]{"-1", "-0.5", "0", "0.5", "1"},
                new double[]{-90, -30, 0, 30, 90}
        ));
    }

    @Test
    public void testLog10RoundingDown() {
        calculator.setDecimalPlaces(4);
        assertTrue(testFunctionCall(
                "log",
                new String[]{"0.5",     "1",      "2",      "3",      "4",      "6",      "8",     "10",    "100"},
                new double[]{-0.3010, 0, 0.3010, 0.4771, 0.6020, 0.7781, 0.9030, 1, 2}
        ));
    }

    @Test
    public void testLnRoundingDown() {
        calculator.setDecimalPlaces(4);
        assertTrue(testFunctionCall(
                "ln",
                new String[]{"0.5",     "1",      "2",      "3",      "4",      "6",      "8",     "10",    "100"},
                new double[]{-0.6931, 0, 0.6931, 1.0986, 1.3862, 1.7917, 2.0794, 2.3025, 4.6051}
        ));
    }

    @Test
    public void testLog2RoundingDown() {
        calculator.setDecimalPlaces(4);
        assertTrue(testFunctionCall(
                "log2",
                new String[]{"0.5",     "1",      "2",      "3",      "4",      "6",      "8",     "10",    "100"},
                new double[]{-1, 0, 1, 1.5849, 2, 2.5849, 3, 3.3219, 6.6438}
        ));
    }

    @Test
    public void testUnknownFunction() {
        assertThrowsExactly(BaseCalculator.UnknownFunctionException.class, () -> eval("1+unknownFunction(10)"));
    }

    // constants
    @Test
    public void testConstantE() {
        calculator.setDecimalPlaces(2);
        BigDecimal result = eval("e");
        assertEquals(2.71, result.doubleValue());
    }

    @Test
    public void testConstantPi() {
        BigDecimal result = eval("π");
        assertEquals(3.14, result.doubleValue());
    }

    @Test
    public void testUnknownConstant() {
        assertThrowsExactly(BaseCalculator.UnknownConstantException.class, () -> eval("unknownConstant^2"));
    }

    // calculator configuration
    @Test
    public void testSetDecimalPlaces() {
        calculator.setDecimalPlaces(2);
    }

    @Test
    public void testGetDecimalPlaces() {
        int decimalPlaces = calculator.getDecimalPlaces();
    }

    @Test
    public void testDecimalPlacesPersistence() {
        calculator.setDecimalPlaces(5);
        assertEquals(5, calculator.getDecimalPlaces());
        calculator.setDecimalPlaces(100);
        assertEquals(100, calculator.getDecimalPlaces());
        calculator.setDecimalPlaces(2000);
        assertEquals(2000, calculator.getDecimalPlaces());
    }

    @Test
    public void testNegativeDecimalPlaces() {
        calculator.setDecimalPlaces(5);
        calculator.setDecimalPlaces(-10);
        assertEquals(5, calculator.getDecimalPlaces());
    }

    @Test
    public void testSetRoundingUp() {
        calculator.setRoundingMode(RoundingMode.UP);
    }

    @Test
    public void testGetRoundingUp() {
        RoundingMode roundUp = calculator.getRoundingMode();
    }

    @Test
    public void testRoundingUpPersistence() {
        calculator.setRoundingMode(RoundingMode.UP);
        assertEquals(RoundingMode.UP, calculator.getRoundingMode());
        calculator.setRoundingMode(RoundingMode.DOWN);
        assertEquals(RoundingMode.DOWN, calculator.getRoundingMode());
    }

    @Test
    public void testSetUseRadians() {
        calculator.setUseRadians(true);
    }

    @Test
    public void testGetUseRadians() {
        boolean useRadians = calculator.getUseRadians();
    }

    @Test
    public void testUseRadiansPersistence() {
        calculator.setUseRadians(true);
        assertTrue(calculator.getUseRadians());
        calculator.setUseRadians(false);
        assertFalse(calculator.getUseRadians());
    }

    @Test
    public void testSetPrecision() {
        calculator.setPrecision(100);
    }

    @Test
    public void testGetPrecision() {
        int precision = calculator.getPrecision();
    }

    @Test
    public void testPrecisionPersistence() {
        calculator.setPrecision(100);
        assertEquals(100, calculator.getPrecision());
        calculator.setPrecision(321);
        assertEquals(321, calculator.getPrecision());
    }

    @Test
    public void testSetNegativePrecision() {
        calculator.setPrecision(25);
        calculator.setPrecision(-1030);
        assertEquals(25, calculator.getPrecision());
    }

    // decimal places
    @Test
    public void testDecimalPlacesAfterCalculation() {
        calculator.setDecimalPlaces(1);
        BigDecimal result = eval("1.05+1.05");
        assertEquals(2.1, result.doubleValue());
    }

    @Test
    public void testDecimalPlacesWithNegativeNumbers() {
        calculator.setDecimalPlaces(6);
        BigDecimal result = eval("10-12.654321");
        assertEquals(-2.654321, result.doubleValue());
    }
}
package flo.calculator;

import ch.obermuhlner.math.big.BigDecimalMath;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public final class BigDecimalCalculator extends BaseCalculator<BigDecimal> {

    public enum ERROR_MESSAGE {
        FACTORIAL_WITH_NEGATIVE_VALUE,
        FACTORIAL_WITH_NON_INTEGER,
        DIVISION_BY_ZERO
    }

    private MathContext mathContext;
    private int decimalPlaces;
    private boolean useRadians;

    public BigDecimalCalculator(int decimalPlaces, RoundingMode roundingMode, boolean useRadians, int precision) throws InvalidStartUpConfigurationException {
        if (decimalPlaces < 0 || precision < 0) throw new InvalidStartUpConfigurationException();

        mathContext = new MathContext(precision, roundingMode);
        this.decimalPlaces = decimalPlaces;
        this.useRadians = useRadians;
    }

    public BigDecimalCalculator() {
        mathContext = new MathContext(MathContext.DECIMAL128.getPrecision(), RoundingMode.DOWN);
        decimalPlaces = 2;
        useRadians = true;
    }

    private BigDecimal degreesToRadians(BigDecimal degrees) {
        BigDecimal factor = BigDecimalMath.pi(mathContext).divide(new BigDecimal(180), mathContext);
        return degrees.multiply(factor);
    }

    private BigDecimal radiansToDegrees(BigDecimal radians) {
        BigDecimal factor = new BigDecimal(180).divide(BigDecimalMath.pi(mathContext), MathContext.DECIMAL32);
        return radians.multiply(factor);
    }

    @Override
    public BigDecimal evaluate(String expression) {
        return super.evaluate(expression).setScale(decimalPlaces, mathContext.getRoundingMode()).stripTrailingZeros();
    }

    @Override
    public BigDecimal toNumber(String number) {
        return BigDecimalMath.toBigDecimal(number);
    }

    @Override
    public BigDecimal add(BigDecimal left, BigDecimal right) {
        return left.add(right);
    }

    @Override
    public BigDecimal subtract(BigDecimal left, BigDecimal right) {
        return left.subtract(right);
    }

    @Override
    public BigDecimal multiply(BigDecimal left, BigDecimal right) {
        return left.multiply(right);
    }

    @Override
    public BigDecimal divide(BigDecimal left, BigDecimal right) {
        if (right.compareTo(BigDecimal.ZERO) == 0) throw new CalculationException(ERROR_MESSAGE.DIVISION_BY_ZERO);
        return left.divide(right, MathContext.DECIMAL128);
    }

    @Override
    public BigDecimal pow(BigDecimal left, BigDecimal right) {
        return BigDecimalMath.pow(left, right, MathContext.DECIMAL128);
    }

    @Override
    public BigDecimal negate(BigDecimal number) {
        return number.multiply(new BigDecimal(-1));
    }

    @Override
    public BigDecimal toPercentage(BigDecimal number) {
        return number.divide(new BigDecimal(100), MathContext.DECIMAL128);
    }

    @Override
    public BigDecimal toPercentage(BigDecimal number, BigDecimal fraction) {
        return number.multiply(fraction.divide(new BigDecimal(100), MathContext.DECIMAL128));
    }

    @Override
    public BigDecimal factorial(BigDecimal number) {
        if (number.compareTo(BigDecimal.ZERO) < 0) throw new CalculationException(ERROR_MESSAGE.FACTORIAL_WITH_NEGATIVE_VALUE);
        if (number.intValue() != number.doubleValue()) throw new CalculationException(ERROR_MESSAGE.FACTORIAL_WITH_NON_INTEGER);
        return BigDecimalMath.factorial(number, MathContext.DECIMAL32);
    }

    @Override
    public boolean isConstant(String constantName) {
        return constantName.equals("e") || constantName.equals("π");
    }

    @Override
    public BigDecimal callFunction(String functionName, BigDecimal x) throws UnknownFunctionException {
        switch (functionName) {
            case "sin":
                if (!useRadians) x = degreesToRadians(x);
                return BigDecimalMath.sin(x, MathContext.DECIMAL32);
            case "sin⁻¹":
                if (!useRadians) return radiansToDegrees(BigDecimalMath.asin(x, MathContext.DECIMAL32));
                return BigDecimalMath.asin(x, MathContext.DECIMAL32);
            case "sinh":
                if (!useRadians) x = degreesToRadians(x);
                return BigDecimalMath.sinh(x, MathContext.DECIMAL32);
            case "sinh⁻¹":
                if (!useRadians) return radiansToDegrees(BigDecimalMath.asinh(x, MathContext.DECIMAL32));
                return BigDecimalMath.asinh(x, MathContext.DECIMAL32);
            case "cos":
                if (!useRadians) x = degreesToRadians(x);
                return BigDecimalMath.cos(x, MathContext.DECIMAL32);
            case "cos⁻¹":
                if (!useRadians) return radiansToDegrees(BigDecimalMath.acos(x, MathContext.DECIMAL32));
                return BigDecimalMath.acos(x, MathContext.DECIMAL32);
            case "cosh":
                if (!useRadians) x = degreesToRadians(x);
                return BigDecimalMath.cosh(x, MathContext.DECIMAL32);
            case "cosh⁻¹":
                if (!useRadians) return radiansToDegrees(BigDecimalMath.acosh(x, MathContext.DECIMAL32));
                return BigDecimalMath.acosh(x, MathContext.DECIMAL32);
            case "tan":
                if (!useRadians) x = degreesToRadians(x);
                return BigDecimalMath.tan(x, MathContext.DECIMAL32);
            case "tan⁻¹":
                if (!useRadians) return radiansToDegrees(BigDecimalMath.atan(x, MathContext.DECIMAL32));
                return BigDecimalMath.atan(x, MathContext.DECIMAL32);
            case "tanh":
                if (!useRadians) x = degreesToRadians(x);
                return BigDecimalMath.tanh(x, MathContext.DECIMAL32);
            case "tanh⁻¹":
                if (!useRadians) return radiansToDegrees(BigDecimalMath.atanh(x, MathContext.DECIMAL32));
                return BigDecimalMath.atanh(x, MathContext.DECIMAL32);
            case "cot":
                if (!useRadians) x = degreesToRadians(x);
                return BigDecimalMath.cot(x, MathContext.DECIMAL32);
            case "cot⁻¹":
                if (!useRadians) return radiansToDegrees(BigDecimalMath.acot(x, MathContext.DECIMAL32));
                return BigDecimalMath.acot(x, MathContext.DECIMAL32);
            case "coth":
                if (!useRadians) x = degreesToRadians(x);
                return BigDecimalMath.coth(x, MathContext.DECIMAL32);
            case "coth⁻¹":
                if (!useRadians) return radiansToDegrees(BigDecimalMath.acoth(x, MathContext.DECIMAL32));
                return BigDecimalMath.acoth(x, MathContext.DECIMAL32);
            case "√":
                return BigDecimalMath.sqrt(x, mathContext);
            case "ln":
                return BigDecimalMath.log(x, MathContext.DECIMAL32);
            case "log2":
                return BigDecimalMath.log2(x, MathContext.DECIMAL32);
            case "log":
                return BigDecimalMath.log10(x, MathContext.DECIMAL32);
            case "abs":
                return x.abs(MathContext.DECIMAL32);
            default:
                throw new UnknownFunctionException(functionName);
        }
    }

    @Override
    public BigDecimal resolveConstant(String constantName) throws UnknownConstantException {
        switch (constantName) {
            case "e":
                return BigDecimalMath.e(mathContext);
            case "π":
                return BigDecimalMath.pi(mathContext);
            default:
                throw new UnknownConstantException(constantName);
        }
    }

    public void setDecimalPlaces(int decimalPlaces) {
        if (decimalPlaces < 0) return;
        this.decimalPlaces = decimalPlaces;
    }

    public int getDecimalPlaces() {
        return decimalPlaces;
    }

    public void setRoundingMode(RoundingMode mode) {
        mathContext = new MathContext(mathContext.getPrecision(), mode);
    }

    public RoundingMode getRoundingMode() {
        return mathContext.getRoundingMode();
    }

    public void setUseRadians(boolean useRadians) {
        this.useRadians = useRadians;
    }

    public boolean getUseRadians() {
        return useRadians;
    }

    public void setPrecision(int precision) {
        if (precision < 0) return;
        mathContext = new MathContext(precision, mathContext.getRoundingMode());
    }

    public int getPrecision() {
        return mathContext.getPrecision();
    }

    static public class CalculationException extends RuntimeException {
        public ERROR_MESSAGE errorMessage;

        CalculationException(ERROR_MESSAGE errorMessage) {
            this.errorMessage = errorMessage;
        }
    }

    static public class InvalidStartUpConfigurationException extends RuntimeException { }
}


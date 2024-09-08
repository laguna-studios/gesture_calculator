package flo.calculator;

import android.util.Log;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenSource;
import org.antlr.v4.runtime.tree.ParseTree;

import java.nio.charset.StandardCharsets;

import flo.calculator.antlr.ExpressionBaseVisitor;
import flo.calculator.antlr.ExpressionLexer;
import flo.calculator.antlr.ExpressionParser;


public abstract class BaseCalculator<T> extends ExpressionBaseVisitor<T> {

    private String removeSpaces(String number) {
        return number.replaceAll(" ", "");
    }

    public T evaluate(String expression) {
        CodePointCharStream charStream = CharStreams.fromString(expression);
        ExpressionLexer lexer = new ExpressionLexer(charStream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        ExpressionParser parser = new ExpressionParser(tokenStream);
        ParseTree tree = parser.parse();
        return visit(tree);
    }

    @Override
    public T visitNumber(ExpressionParser.NumberContext ctx) {
        return toNumber(removeSpaces(ctx.NUMBER().getText()));
    }

    @Override
    public T visitConstFunc(ExpressionParser.ConstFuncContext ctx) {

        String identifier = ctx.identifier.getText();

        if (ctx.expr() == null) return resolveConstant(identifier);

        T number = visit(ctx.expr());
        if (isConstant(identifier)) {
            T constant = resolveConstant(identifier);
            return multiply(constant, number);
        }
        return callFunction(identifier, number);

    }

    @Override
    public T visitBrackets(ExpressionParser.BracketsContext ctx) {
        return visit(ctx.expr());
    }

    @Override
    public T visitPower(ExpressionParser.PowerContext ctx) {
        T left = visit(ctx.left);
        T right = visit(ctx.right);

        return pow(left, right);
    }

    @Override
    public T visitImplicitMul(ExpressionParser.ImplicitMulContext ctx) {
        T left = visit(ctx.left);
        T right = visit(ctx.right);

        return multiply(left, right);
    }

    @Override
    public T visitUnaryMinus(ExpressionParser.UnaryMinusContext ctx) {
        T number = visit(ctx.expr2());
        return negate(number);
    }

    @Override
    public T visitSuffix(ExpressionParser.SuffixContext ctx) {
        T number = visit(ctx.expr2());
        if (ctx.op.getText().equals("!")) return factorial(number);
        return toPercentage(number);
    }

    @Override
    public T visitMulDiv(ExpressionParser.MulDivContext ctx) {
        T left = visit(ctx.left);
        T right = visit(ctx.right);

        if (ctx.DIVIDE() != null) {
            return divide(left, right);
        }
        return multiply(left, right);
    }

    @Override
    public T visitAddSubPerc(ExpressionParser.AddSubPercContext ctx) {
        T left = visit(ctx.left);
        T right = toPercentage(left, visit(ctx.right));

        if (ctx.op.getText().equals("+")) return add(left, right);
        return subtract(left, right);
    }

    @Override
    public T visitAddSub(ExpressionParser.AddSubContext ctx) {
        T left = visit(ctx.left);
        T right = visit(ctx.right);

        if (ctx.op.getText().equals("+")) return add(left, right);
        return subtract(left, right);
    }

    public abstract T toNumber(String number);

    public abstract T add(T left, T right);

    public abstract T subtract(T left, T right);

    public abstract T multiply(T left, T right);

    public abstract T divide(T left, T right);

    public abstract T pow(T left, T right);

    public abstract T negate(T number);

    public abstract T toPercentage(T number);

    public abstract T toPercentage(T number, T fraction);

    public abstract T factorial(T number);

    public abstract boolean isConstant(String constantName);

    public abstract T callFunction(String functionName, T x);

    public abstract T resolveConstant(String constantName);

    static public class UnknownFunctionException extends RuntimeException {

        String functionName;

        public UnknownFunctionException(String functionName) {
            this.functionName = functionName;
        }

        @Override
        public String getMessage() {
            return String.format("Unknown function: %s", functionName);
        }
    }

    static public class UnknownConstantException extends RuntimeException {

        String constantName;

        public UnknownConstantException(String constantName) {
            this.constantName = constantName;
        }

        @Override
        public String getMessage() {
            return String.format("Unknown constant: %s", constantName);
        }
    }
}

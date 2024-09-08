// Generated from Expression.g4 by ANTLR 4.9.3
package flo.calculator.antlr;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ExpressionParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ExpressionVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link ExpressionParser#parse}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParse(ExpressionParser.ParseContext ctx);
	/**
	 * Visit a parse tree produced by the {@code number}
	 * labeled alternative in {@link ExpressionParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumber(ExpressionParser.NumberContext ctx);
	/**
	 * Visit a parse tree produced by the {@code constFunc}
	 * labeled alternative in {@link ExpressionParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstFunc(ExpressionParser.ConstFuncContext ctx);
	/**
	 * Visit a parse tree produced by the {@code brackets}
	 * labeled alternative in {@link ExpressionParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBrackets(ExpressionParser.BracketsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code factorUp}
	 * labeled alternative in {@link ExpressionParser#factor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFactorUp(ExpressionParser.FactorUpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code power}
	 * labeled alternative in {@link ExpressionParser#factor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPower(ExpressionParser.PowerContext ctx);
	/**
	 * Visit a parse tree produced by the {@code implicitMul}
	 * labeled alternative in {@link ExpressionParser#factor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImplicitMul(ExpressionParser.ImplicitMulContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprUp2}
	 * labeled alternative in {@link ExpressionParser#expr2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprUp2(ExpressionParser.ExprUp2Context ctx);
	/**
	 * Visit a parse tree produced by the {@code unaryMinus}
	 * labeled alternative in {@link ExpressionParser#expr2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryMinus(ExpressionParser.UnaryMinusContext ctx);
	/**
	 * Visit a parse tree produced by the {@code suffix}
	 * labeled alternative in {@link ExpressionParser#expr2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSuffix(ExpressionParser.SuffixContext ctx);
	/**
	 * Visit a parse tree produced by the {@code mulDiv}
	 * labeled alternative in {@link ExpressionParser#expr2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMulDiv(ExpressionParser.MulDivContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprUp}
	 * labeled alternative in {@link ExpressionParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprUp(ExpressionParser.ExprUpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code addSub}
	 * labeled alternative in {@link ExpressionParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddSub(ExpressionParser.AddSubContext ctx);
	/**
	 * Visit a parse tree produced by the {@code addSubPerc}
	 * labeled alternative in {@link ExpressionParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddSubPerc(ExpressionParser.AddSubPercContext ctx);
}
// Generated from Expression.g4 by ANTLR 4.9.3
package flo.calculator.antlr;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ExpressionLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.9.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		IDENTIFIER=10, NUMBER=11, WHITESPACE=12, DIVIDE=13, MULTIPLY=14;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
			"IDENTIFIER", "NUMBER", "WHITESPACE", "DIVIDE", "MULTIPLY"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'\u03C0'", "'\u221A'", "'('", "')'", "'^'", "'-'", "'%'", "'!'", 
			"'+'", null, null, null, "'\u00F7'", "'\u00D7'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, "IDENTIFIER", 
			"NUMBER", "WHITESPACE", "DIVIDE", "MULTIPLY"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public ExpressionLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Expression.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\20P\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3"+
		"\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\7\13\64\n\13\f\13"+
		"\16\13\67\13\13\3\f\6\f:\n\f\r\f\16\f;\3\f\3\f\6\f@\n\f\r\f\16\fA\5\f"+
		"D\n\f\3\r\6\rG\n\r\r\r\16\rH\3\r\3\r\3\16\3\16\3\17\3\17\2\2\20\3\3\5"+
		"\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\3\2\7"+
		"\3\2c|\7\2\62;C\\c|\u00bb\u00bb\u207d\u207d\4\2\"\"\62;\3\2\62;\5\2\13"+
		"\f\17\17\"\"\2T\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3"+
		"\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2"+
		"\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\3\37\3\2\2\2\5!\3"+
		"\2\2\2\7#\3\2\2\2\t%\3\2\2\2\13\'\3\2\2\2\r)\3\2\2\2\17+\3\2\2\2\21-\3"+
		"\2\2\2\23/\3\2\2\2\25\61\3\2\2\2\279\3\2\2\2\31F\3\2\2\2\33L\3\2\2\2\35"+
		"N\3\2\2\2\37 \7\u03c2\2\2 \4\3\2\2\2!\"\7\u221c\2\2\"\6\3\2\2\2#$\7*\2"+
		"\2$\b\3\2\2\2%&\7+\2\2&\n\3\2\2\2\'(\7`\2\2(\f\3\2\2\2)*\7/\2\2*\16\3"+
		"\2\2\2+,\7\'\2\2,\20\3\2\2\2-.\7#\2\2.\22\3\2\2\2/\60\7-\2\2\60\24\3\2"+
		"\2\2\61\65\t\2\2\2\62\64\t\3\2\2\63\62\3\2\2\2\64\67\3\2\2\2\65\63\3\2"+
		"\2\2\65\66\3\2\2\2\66\26\3\2\2\2\67\65\3\2\2\28:\t\4\2\298\3\2\2\2:;\3"+
		"\2\2\2;9\3\2\2\2;<\3\2\2\2<C\3\2\2\2=?\7\60\2\2>@\t\5\2\2?>\3\2\2\2@A"+
		"\3\2\2\2A?\3\2\2\2AB\3\2\2\2BD\3\2\2\2C=\3\2\2\2CD\3\2\2\2D\30\3\2\2\2"+
		"EG\t\6\2\2FE\3\2\2\2GH\3\2\2\2HF\3\2\2\2HI\3\2\2\2IJ\3\2\2\2JK\b\r\2\2"+
		"K\32\3\2\2\2LM\7\u00f9\2\2M\34\3\2\2\2NO\7\u00d9\2\2O\36\3\2\2\2\b\2\65"+
		";ACH\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
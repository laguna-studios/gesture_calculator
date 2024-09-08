grammar Expression;

// Parser

parse: expr | EOF;

term
	:	NUMBER #number
	|   identifier=(IDENTIFIER|'π'|'√') ('(' expr ')')? #constFunc
	|	'(' expr ')' #brackets
	;

//Implicit multiplication
factor
	:	term #factorUp
	|	<assoc=right> left=term '^' right=factor #power
	|	left=term right=factor #implicitMul
	;


expr2
	:	factor #exprUp2
	|	'-' expr2 #unaryMinus
	|   <assoc=right> left=expr2 op=('%'|'!') #suffix
	|	left=expr2 op=(DIVIDE|MULTIPLY) right=expr2 #mulDiv
	;

expr
	:	expr2 #exprUp
	|   left=expr op=('+' | '-') right=expr2 suf='%' #addSubPerc
	|	left=expr op=('+' | '-') right=expr2 #addSub
	;


// Lexer
IDENTIFIER: [a-z][a-zA-Z0-9⁻¹]*;

NUMBER: [ 0-9]+ ('.' [0-9]+)?;

WHITESPACE: [ \r\n\t]+ -> skip;

DIVIDE: '÷';
MULTIPLY: '×';
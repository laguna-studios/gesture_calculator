import 'package:flutter/material.dart';

import '../data/model.dart';
import 'formatter.dart';

class CalculatorTextEditingController extends TextEditingController {

  final ExpressionFormatter expressionFormatter;

  CalculatorTextEditingController(this.expressionFormatter);

  int get cursorPosition =>
      selection.baseOffset == -1 ? text.length : selection.baseOffset;

  void setExpression(String expression) {
    value = TextEditingValue(text: expressionFormatter.format(expression));
  }

  void clearOne() {
    // nothing to delete
    if (cursorPosition == 0) return;

    // unformat expression and cursor
    int curPos = expressionFormatter.toUnformattedCursor(cursorPosition, value.text);
    String expression = expressionFormatter.unformat(value.text);

    // delete one token
    int charsToDelete = 1;

    // cursor is behind open bracket meaning a possible function
    String left = expression[curPos-1];
    if (left == CalculatorToken.bracketOpen.calculatorToken && curPos > 1) {
      int i = 2;
      left = expression[curPos-i];

      while ("abcdfghijklmnopqrstuvwxyz⁻¹√".contains(left)) {
        charsToDelete++;
        i++;

        if (curPos - i < 0) break;
        left = expression[curPos-i];
      }
    }
    

    expression = expression.substring(0, curPos - charsToDelete) +
        expression.substring(curPos, expression.length);
    
    curPos -= charsToDelete;

    // format expression and cursor
    expression = expressionFormatter.format(expression);
    curPos = expressionFormatter.toFormattedCursor(curPos, expression);

    // set expression and cursor position
    value = TextEditingValue(
        text: expression,
        selection: TextSelection.collapsed(offset: curPos));
  }

  void insert(CalculatorToken token) {
    int curPos = cursorPosition;
    String expression = expressionFormatter.unformat(value.text);
    curPos = expressionFormatter.toUnformattedCursor(curPos, value.text);
    
    // expression and cursor unformatted
    expression = expression.substring(0, curPos) +
        token.calculatorToken +
        expression.substring(curPos, expression.length);
    
    curPos += token.calculatorToken.length;
    
    // format expression and cursor
    expression = expressionFormatter.format(expression);
    curPos = expressionFormatter.toFormattedCursor(curPos, expression);
    
    // set expression and cursor position
    value = TextEditingValue(
        text: expression,
        selection: TextSelection.fromPosition(TextPosition(
            offset: curPos)));
  }

  void insertBracket() {
    bool isFirstToken = (cursorPosition == 0);
    String? previousToken = isFirstToken ? null : value.text[cursorPosition - 1];
    int openBracketCount = value.text.split("").where((element) => element.contains("(")).length;
    int closedBracketCount = value.text.split("").where((element) => element.contains(")")).length;

    if (isFirstToken) {
      insert(CalculatorToken.bracketOpen);
    } else if (previousToken == CalculatorToken.bracketOpen.calculatorToken) {
      insert(CalculatorToken.bracketOpen);
    } else if ("+-*/^".contains(previousToken!)) {
      insert(CalculatorToken.bracketOpen);
    } else if (openBracketCount <= closedBracketCount) {
      insert(CalculatorToken.bracketOpen);
    }
    else {
      insert(CalculatorToken.bracketClose);
    }
  }

  void adjustCursor() {
    int curPos = expressionFormatter.adjustCursor(cursorPosition, value.text);
    value = TextEditingValue(text: value.text, selection: TextSelection.collapsed(offset: curPos));
  }
}
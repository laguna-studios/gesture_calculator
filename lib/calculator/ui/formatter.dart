import "package:gesture_calculator/calculator/data/model.dart";

abstract class ExpressionFormatter {
  String format(String expression);

  String unformat(String expression);

  int toFormattedCursor(int unformattedCursorPosition, String formattedExpression);

  int toUnformattedCursor(int formattedCursorPosition, String formattedExpression);

  int adjustCursor(int formattedCursorPosition, String formattedExpression);
}

class SimpleFormatter extends ExpressionFormatter {
  final String decimalSeparator;
  final String groupSeparator;

  SimpleFormatter(this.decimalSeparator, this.groupSeparator);

  SimpleFormatter.english() : this(".", ",");
  SimpleFormatter.nonenglish() : this(",", ".");
  SimpleFormatter.spacing() : this(",", " ");

  String _formatNumber(String rawNumber) {
    if (rawNumber.length <= 3) return rawNumber;
    int numbersInRow = 0;
    String number = "";
    for (int i = rawNumber.length - 1; i >= 0; i--) {
      if (numbersInRow == 3) {
        number = groupSeparator + number;
        numbersInRow = 0;
      }

      number = rawNumber[i] + number;
      numbersInRow++;
    }

    return number;
  }

  @override
  String format(String expression) {
    // set decimal separator
    var e = expression.replaceAll(CalculatorToken.dot.calculatorToken, decimalSeparator);

    String newExpression = "";
    int startNumber = -1;
    bool behindDot = false;

    for (int i = 0; i < e.length; i++) {
      var c = e[i];

      if ("1234567890".contains(c)) {
        if (behindDot) {
          newExpression += c;
        } else if (startNumber == -1) {
          startNumber = i;
        }
      } else {
        if (c == decimalSeparator && startNumber != -1) {
          behindDot = true;
          newExpression += _formatNumber(e.substring(startNumber, i));
          startNumber = -1;
        } else if (startNumber != -1) {
          newExpression += _formatNumber(e.substring(startNumber, i));
          startNumber = -1;
          behindDot = false;
        } else {
          behindDot = false;
        }
        newExpression += c;
      }
    }

    if (startNumber != -1) {
      newExpression += _formatNumber(e.substring(startNumber));
    }

    return newExpression;
  }

  @override
  String unformat(String expression) {
    return expression.replaceAll(groupSeparator, "").replaceAll(decimalSeparator, ".");
  }

  @override
  int toFormattedCursor(int unformattedCursorPosition, String formattedExpression) {
    int cursorPos = 0;
    int tokensFound = 0;

    for (int i = 0; i < formattedExpression.length; i++) {
      if (tokensFound == unformattedCursorPosition) break;
      String c = formattedExpression[i];

      if (c != groupSeparator) tokensFound++;
      cursorPos++;
    }

    return cursorPos;
  }

  @override
  int toUnformattedCursor(int formattedCursorPosition, String formattedExpression) {
    // if cursor position is unknown put it at the end
    if (formattedCursorPosition == -1) {
      return unformat(formattedExpression).length;
    }

    int cursorPos = 0;

    for (int i = 0; i < formattedCursorPosition; i++) {
      String c = formattedExpression[i];
      if (c != groupSeparator) cursorPos++;
    }

    return cursorPos;
  }

  @override
  int adjustCursor(int formattedCursorPosition, String formattedExpression) {
    // if negative cursor position put it at the end
    if (formattedCursorPosition == -1) return formattedExpression.length;
    if (formattedCursorPosition == 0) return formattedCursorPosition;

    String left = formattedExpression[formattedCursorPosition - 1];
    // cursor is after group separator
    if (left == groupSeparator) return formattedCursorPosition - 1;

    int curPos = formattedCursorPosition;
    while ("abcdfghijklmnopqrstuvwxyz⁻¹√".contains(left)) {
      curPos++;
      left = formattedExpression[curPos - 1];
    }

    return curPos;
  }
}

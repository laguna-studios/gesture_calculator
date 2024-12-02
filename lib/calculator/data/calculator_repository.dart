import "package:gesture_calculator/calculator/data/provider/bigdecimal_android/calculator_platform_interface.dart";

class CalculatorRepository {
  static Future<String> evaluate(String expression) async {
    return CalculatorPlatform.instance.evaluate(expression);
  }

  static Future<void> setDecimalPlaces(int decimalPlaces) {
    return CalculatorPlatform.instance.setDecimalPlaces(decimalPlaces);
  }

  static Future<void> setUseRadians(bool useRadians) {
    return CalculatorPlatform.instance.setUseRadians(useRadians);
  }

  static Future<int> getDecimalPlaces() {
    return CalculatorPlatform.instance.getDecimalPlaces();
  }

  static Future<bool> getUseRadians() {
    return CalculatorPlatform.instance.getUseRadians();
  }
}

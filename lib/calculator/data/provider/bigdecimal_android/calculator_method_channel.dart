import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'calculator_platform_interface.dart';

/// An implementation of [CalculatorPlatform] that uses method channels.
class MethodChannelCalculator extends CalculatorPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('calculator');

  @override
  Future<String> evaluate(String expression) async {
    final result = await methodChannel.invokeMethod<String>('evaluate', {"expression": expression});
    return result!;
  }

  @override
  Future<int> getDecimalPlaces() async {
    final result = await methodChannel.invokeMethod<int>('getDecimalPlaces');
    return result!;
  }

  @override
  Future<bool> getUseRadians() async {
    final result = await methodChannel.invokeMethod<bool>('getUseRadians');
    return result!;
  }

  @override
  Future<void> setDecimalPlaces(int decimalPlaces) async {
    methodChannel.invokeMethod<void>('setDecimalPlaces', {"decimalPlaces": decimalPlaces});
  }

  @override
  Future<void> setUseRadians(bool useRadians) async {
    methodChannel.invokeMethod<void>('setUseRadians', {"useRadians": useRadians});
  }
}

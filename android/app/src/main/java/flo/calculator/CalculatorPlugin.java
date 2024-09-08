package flo.calculator;

import androidx.annotation.NonNull;

import java.math.BigDecimal;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

/** CalculatorPlugin */
public class CalculatorPlugin implements FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private MethodChannel channel;
  private final BigDecimalCalculator calculator = new BigDecimalCalculator();

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "calculator");
    channel.setMethodCallHandler(this);
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if (call.method.equals("evaluate")) {
      String expression = call.argument("expression");
      BigDecimal res = calculator.evaluate(expression);
      result.success(res.toPlainString());
    }
    else if (call.method.equals("setDecimalPlaces")) {
      int decimalPlaces = call.argument("decimalPlaces");
      calculator.setDecimalPlaces(decimalPlaces);
    }
    else if (call.method.equals("setUseRadians")) {
      boolean useRadians = call.argument("useRadians");
      calculator.setUseRadians(useRadians);
    }
    else if (call.method.equals("getDecimalPlaces")) {
      result.success(calculator.getDecimalPlaces());
    }
    else if (call.method.equals("getUseRadians")) {
      result.success(calculator.getUseRadians());
    }
    else {
      result.notImplemented();
    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }
}

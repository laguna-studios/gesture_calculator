package org.seniorlaguna.gcalculator.gesture_calculator

import androidx.annotation.NonNull
import flo.calculator.BigDecimalCalculator
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import java.math.BigDecimal


class MainActivity: FlutterActivity() {
    private val CHANNEL = "calculator"
    private val calculator = BigDecimalCalculator()
    override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler {
                call, result ->
            if (call.method.equals("evaluate")) {
                val expression: String = call.argument("expression")!!
                val res: BigDecimal = calculator.evaluate(expression)
                result.success(res.toPlainString())
            } else if (call.method.equals("setDecimalPlaces")) {
                val decimalPlaces: Int = call.argument("decimalPlaces")!!
                calculator.decimalPlaces = decimalPlaces
            } else if (call.method.equals("setUseRadians")) {
                val useRadians: Boolean = call.argument("useRadians")!!
                calculator.useRadians = useRadians
            } else if (call.method.equals("getDecimalPlaces")) {
                result.success(calculator.decimalPlaces)
            } else if (call.method.equals("getUseRadians")) {
                result.success(calculator.useRadians)
            } else {
                result.notImplemented()
            }
        }
    }
}

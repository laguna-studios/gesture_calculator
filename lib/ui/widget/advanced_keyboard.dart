import "package:flutter/material.dart";
import "package:gesture_calculator/bloc/settings_cubit.dart";
import "package:gesture_calculator/calculator/bloc/calculator_cubit.dart";
import "package:gesture_calculator/calculator/data/model.dart";
import "package:gesture_calculator/ui/index.dart";
import "package:gesture_calculator/ui/widget/keyboard_key.dart";

class AdvancedKeyboard extends StatelessWidget {
  final keyboard = [
    [CalculatorToken.ln, CalculatorToken.sin, CalculatorToken.cos, CalculatorToken.tan, CalculatorToken.cot],
    [CalculatorToken.log, CalculatorToken.sinh, CalculatorToken.cosh, CalculatorToken.tanh, CalculatorToken.coth],
    [CalculatorToken.squareRoot, null, null, null, null],
    [CalculatorToken.opPower, null, null, null, null],
    [CalculatorToken.factorial, null, null, null, null],
    [CalculatorToken.constPi, null, null, null, null],
    [CalculatorToken.radOrDeg, null, null, null, null],
  ];
  final keyboard2 = [
    [CalculatorToken.ePowX, CalculatorToken.asin, CalculatorToken.acos, CalculatorToken.atan, CalculatorToken.acot],
    [
      CalculatorToken.tenPowX,
      CalculatorToken.asinh,
      CalculatorToken.acosh,
      CalculatorToken.atanh,
      CalculatorToken.acoth,
    ],
    [CalculatorToken.squared, null, null, null, null],
    [CalculatorToken.opPower, null, null, null, null],
    [CalculatorToken.abs, null, null, null, null],
    [CalculatorToken.constE, null, null, null, null],
    [CalculatorToken.radOrDeg, null, null, null, null],
  ];

  final bool showFirstKeyboard;

  AdvancedKeyboard({super.key, required this.showFirstKeyboard});

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        for (List<CalculatorToken?> row in (showFirstKeyboard ? keyboard : keyboard2))
          Expanded(
            child: Row(
              children: [
                for (CalculatorToken? token in row)
                  token == null
                      ? const Spacer()
                      : Expanded(
                          child: KeyboardKey(
                            token: token,
                            callback: () {
                              if (token == CalculatorToken.radOrDeg) {
                                SettingsCubit.of(context).set(
                                  useRadians: !SettingsCubit.of(context).state.useRadians,
                                );
                              }
                              CalculatorCubit.of(context).onTokenPressed(token);
                            },
                            textSize: 30,
                            theme: Theme.of(context).extension<CalculatorTheme>()!,
                            extendedBackground: true,
                          ),
                        ),
              ],
            ),
          ),
      ],
    );
  }
}

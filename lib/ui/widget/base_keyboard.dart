import "package:flutter/material.dart";
import "package:gesture_calculator/calculator/bloc/calculator_cubit.dart";
import "package:gesture_calculator/calculator/data/model.dart";
import "package:gesture_calculator/ui/index.dart";
import "package:gesture_calculator/ui/widget/keyboard_key.dart";

class BaseKeyboard extends StatelessWidget {
  final keyboard = [
    [CalculatorToken.clear, CalculatorToken.clearOne, CalculatorToken.bracketAuto, CalculatorToken.opDivide],
    [CalculatorToken.num_7, CalculatorToken.num_8, CalculatorToken.num_9, CalculatorToken.opMultiply],
    [CalculatorToken.num_4, CalculatorToken.num_5, CalculatorToken.num_6, CalculatorToken.opSubtract],
    [CalculatorToken.num_1, CalculatorToken.num_2, CalculatorToken.num_3, CalculatorToken.opAdd],
    [
      CalculatorToken.opPercentage,
      CalculatorToken.num_0,
      CalculatorToken.dot,
      CalculatorToken.equals,
    ],
  ];

  BaseKeyboard({super.key});

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        for (List<CalculatorToken> row in keyboard)
          Expanded(
            child: Row(
              children: [
                for (CalculatorToken token in row)
                  Expanded(
                    child: KeyboardKey(
                      token: token,
                      theme: Theme.of(context).extension<CalculatorTheme>()!,
                      callback: () {
                        CalculatorCubit.of(context).onTokenPressed(token);
                      },
                      textSize: 42,
                    ),
                  ),
              ],
            ),
          ),
      ],
    );
  }
}

import "package:flutter/material.dart";
import "package:flutter_bloc/flutter_bloc.dart";
import "package:flutter_i18n/flutter_i18n.dart";
import "package:gesture_calculator/bloc/settings_cubit.dart";
import "package:gesture_calculator/calculator/bloc/calculator_cubit.dart";
import "package:gesture_calculator/calculator/data/model.dart";
import "package:gesture_calculator/ui/index.dart";

class KeyboardKey extends StatelessWidget {
  final List<CalculatorToken> highlightedOperators = [
    CalculatorToken.opAdd,
    CalculatorToken.opSubtract,
    CalculatorToken.opDivide,
    CalculatorToken.opMultiply,
    CalculatorToken.bracketAuto,
    CalculatorToken.clearOne,
    CalculatorToken.opPercentage,
  ];

  final CalculatorToken token;
  final Function() callback;
  final CalculatorTheme theme;
  final double textSize;
  final bool extendedBackground;

  KeyboardKey({
    super.key,
    required this.token,
    required this.callback,
    required this.theme,
    required this.textSize,
    this.extendedBackground = false,
  });

  Color get textColor {
    if (highlightedOperators.contains(token)) {
      return theme.operatorText;
    } else if (token == CalculatorToken.clear) {
      return theme.clearAllText;
    } else if (token == CalculatorToken.equals) {
      return theme.equalsColor;
    }
    return theme.defaultText;
  }

  Color get backgroundColor {
    if (token == CalculatorToken.equals) {
      return theme.equalsBackground;
    }
    return extendedBackground ? theme.buttonBackgroundExtended : theme.buttonBackgroundBase;
  }

  Color? get overlayColor {
    if (token == CalculatorToken.equals) {
      return theme.equalsOverlay;
    } else if (token == CalculatorToken.clear) {
      return theme.clearAllOverlay;
    }
    return null;
  }

  Widget getText(BuildContext context) {
    return FittedBox(
      child: BlocBuilder<SettingsCubit, SettingsState>(
        builder: (context, settingsState) {
          if (token == CalculatorToken.clearOne) {
            return Icon(
              Icons.backspace_outlined,
              color: textColor,
              size: 38 * settingsState.keyboardFontSizeFactor,
            );
          } else if (token == CalculatorToken.radOrDeg) {
            return BlocBuilder<CalculatorCubit, CalculatorState>(
              builder: (context, state) {
                return Text(
                  key: ValueKey(token),
                  state.useRadians ? "RAD" : "DEG",
                  style: TextStyle(fontSize: textSize * settingsState.keyboardFontSizeFactor, color: textColor),
                );
              },
            );
          }

          return AnimatedSwitcher(
            duration: const Duration(milliseconds: 200),
            child: Text(
              key: ValueKey(token),
              FlutterI18n.translate(context, token.name),
              style: TextStyle(fontSize: textSize * settingsState.keyboardFontSizeFactor, color: textColor),
            ),
            transitionBuilder: (child, animation) {
              return ScaleTransition(scale: animation, child: child);
            },
          );
        },
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return OutlinedButton(
      style: ButtonStyle(
        padding: const WidgetStatePropertyAll(EdgeInsets.zero),
        backgroundColor: WidgetStatePropertyAll(backgroundColor),
        overlayColor: WidgetStatePropertyAll(overlayColor),
        side: WidgetStatePropertyAll(
          BorderSide(color: Theme.of(context).extension<CalculatorTheme>()!.spacingColor, width: 0.5),
        ),
        shape: const WidgetStatePropertyAll(RoundedRectangleBorder()),
      ),
      onPressed: callback,
      child: Center(
        child: getText(context),
      ),
    );
  }
}

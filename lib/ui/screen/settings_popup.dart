import 'package:flutter/material.dart';
import 'package:gesture_calculator/ui/index.dart';

class SettingsPopup extends StatelessWidget {
  const SettingsPopup({
    super.key,
    required this.value,
    this.onValueCanged,
  });

  final ValueChanged<double>? onValueCanged;
  final double value;

  @override
  Widget build(BuildContext context) {
    final CalculatorTheme theme = Theme.of(context).extension<CalculatorTheme>()!;

    return Column(
      mainAxisAlignment: MainAxisAlignment.end,
      children: [
        Dialog(
          insetPadding: const EdgeInsets.symmetric(horizontal: 12, vertical: 12),
          alignment: Alignment.bottomCenter,
          child: Slider(
            min: 0.5,
            max: 2,
            value: value,
            activeColor: theme.equalsBackground,
            onChanged: onValueCanged,
          ),
        ),
      ],
    );
  }
}

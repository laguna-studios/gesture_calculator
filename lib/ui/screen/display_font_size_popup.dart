import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:gesture_calculator/bloc/settings_cubit.dart';
import 'package:gesture_calculator/ui/index.dart';

class DisplayFontSizePopup extends StatelessWidget {
  const DisplayFontSizePopup({
    super.key,
  });

  @override
  Widget build(BuildContext context) {
    return Dialog(
      insetPadding: const EdgeInsets.symmetric(horizontal: 12, vertical: 12),
      alignment: Alignment.bottomCenter,
      child: Column(
        mainAxisSize: MainAxisSize.min,
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          BlocBuilder<SettingsCubit, SettingsState>(builder: (context, state) {
            return Slider(
                min: 0.5,
                max: 2,
                value: state.displayFontSizeFactor,
                activeColor: Theme.of(context).extension<CalculatorTheme>()!.equalsBackground,
                onChanged: (v) {
                  SettingsCubit.of(context).set(displayFontSizeFactor: v);
                });
          }),
        ],
      ),
    );
  }
}

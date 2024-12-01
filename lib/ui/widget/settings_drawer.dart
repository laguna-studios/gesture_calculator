import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_i18n/flutter_i18n.dart';
import 'package:gesture_calculator/bloc/settings_cubit.dart';
import 'package:gesture_calculator/ui/index.dart';
import 'package:gesture_calculator/ui/screen/display_font_size_popup.dart';
import 'package:gesture_calculator/ui/screen/keyboard_font_size_popup.dart';

class SettingsDrawer extends StatelessWidget {
  const SettingsDrawer({
    super.key,
  });

  @override
  Widget build(BuildContext context) {
    CalculatorTheme theme = Theme.of(context).extension<CalculatorTheme>()!;

    return Drawer(
      child: BlocBuilder<SettingsCubit, SettingsState>(builder: (context, state) {
        return Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Padding(
              padding: const EdgeInsets.all(16),
              child: Column(
                mainAxisSize: MainAxisSize.min,
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text(
                    FlutterI18n.translate(context, "drawer.settings"),
                    style: const TextStyle(
                      fontSize: 24,
                    ),
                  ),
                  Text(
                    FlutterI18n.translate(context, "drawer.settings_subtitle"),
                    style: TextStyle(color: theme.resultText),
                  ),
                ],
              ),
            ),
            const Divider(
              height: 1,
              endIndent: 64,
              indent: 64,
            ),
            SwitchListTile(
                title: Text(
                  FlutterI18n.translate(context, "drawer.light_theme"),
                  style: TextStyle(color: theme.drawerText),
                ),
                value: state.lightTheme,
                onChanged: (v) {
                  SettingsCubit.of(context).set(lightTheme: v);
                }),
            ListTile(
              textColor: theme.drawerText,
              title: Text(FlutterI18n.translate(context, "drawer.display_font_size")),
              onTap: () {
                Navigator.pop(context);
                showDialog(context: context, builder: (context) => const DisplayFontSizePopup());
              },
            ),
            ListTile(
              textColor: theme.drawerText,
              title: Text(FlutterI18n.translate(context, "drawer.keyboard_font_size")),
              onTap: () {
                Navigator.pop(context);
                showDialog(context: context, builder: (context) => const KeyboardFontSizePopup());
              },
            ),
            SwitchListTile(
                title: Text(FlutterI18n.translate(context, "drawer.fullscreen"),
                    style: TextStyle(color: theme.drawerText)),
                value: state.fullscreen,
                onChanged: (_) => _onFullscreenSettingsChange(context)),
            const Divider(
              height: 1,
              endIndent: 64,
              indent: 64,
            ),
            SwitchListTile(
                title: Text(
                  FlutterI18n.translate(context, "drawer.scientific_mode"),
                  style: TextStyle(
                    color: theme.drawerText,
                  ),
                ),
                value: state.scientificModeEnabled,
                onChanged: (v) => SettingsCubit.of(context).set(scientificModeEnabled: v)),
            SwitchListTile(
                title:
                    Text(FlutterI18n.translate(context, "drawer.history"), style: TextStyle(color: theme.drawerText)),
                value: state.historyEnabled,
                onChanged: (v) => SettingsCubit.of(context).set(historyEnabled: v)),
          ],
        );
      }),
    );
  }

  void _onFullscreenSettingsChange(BuildContext context) {
    if (SettingsCubit.of(context).state.fullscreen) {
      SystemChrome.setEnabledSystemUIMode(SystemUiMode.edgeToEdge);
      SettingsCubit.of(context).set(fullscreen: false);
    } else {
      SystemChrome.setEnabledSystemUIMode(SystemUiMode.immersiveSticky);
      SettingsCubit.of(context).set(fullscreen: true);
    }
  }
}

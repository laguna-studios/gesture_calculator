import 'package:flutter/material.dart';
import 'package:gesture_calculator/bloc/ads_cubit.dart';
import 'package:gesture_calculator/bloc/review_cubit.dart';
import 'package:gesture_calculator/bloc/settings_cubit.dart';
import 'package:gesture_calculator/bloc/tutorial_cubit.dart';
import 'package:gesture_calculator/ui/index.dart';
import 'package:gesture_calculator/ui/screen/tutorial_message.dart';

class MainScreen extends StatefulWidget {
  const MainScreen({super.key});

  @override
  State<MainScreen> createState() => _MainScreenState();
}

class _MainScreenState extends State<MainScreen> with WidgetsBindingObserver {
  bool _showSecondKeyboard = false;
  final double _displayHeight = 0.375;

  @override
  void initState() {
    super.initState();
    WidgetsBinding.instance.addObserver(this);
  }

  @override
  void dispose() {
    WidgetsBinding.instance.removeObserver(this);
    super.dispose();
  }

  @override
  void didChangeAppLifecycleState(AppLifecycleState state) {
    if (state != AppLifecycleState.resumed) return;
    SettingsCubit.of(context).handleFullscreenSettings();

    ReviewCubit.of(context).requestReview().then((value) {
      if (!value) AdsCubit.of(context).showAppOpenAd();
    });
  }

  @override
  Widget build(BuildContext context) {
    final mq = MediaQuery.of(context);
    final double width = mq.size.width;
    final double screenHeight = mq.size.height - mq.viewPadding.top - mq.viewPadding.bottom;

    return SafeArea(
      child: Scaffold(
        drawer: const PromoDrawer(),
        endDrawer: const SettingsDrawer(),
        body: TutorialMessage(
          tutorialStep: TutotialCubit.switchScientificKeyboardStep,
          callback: (context) {
            setState(() => _showSecondKeyboard = true);
            Future.delayed(const Duration(milliseconds: 1500), () {
              setState(() {
                _showSecondKeyboard = false;
              });
              TutotialCubit.of(context).goToState(TutotialCubit.openHistoryStep);
            });
          },
          child: TutorialMessage(
            tutorialStep: TutotialCubit.openSettingsStep,
            skipToStep: TutotialCubit.done,
            callback: (context) async {
              Scaffold.of(context).openEndDrawer();
              Future.delayed(const Duration(milliseconds: 1500), () {
                Scaffold.of(context).closeEndDrawer();
                TutotialCubit.of(context).goToState(TutotialCubit.openScientificKeyboardStep);
              });
            },
            child: TutorialMessage(
              tutorialStep: TutotialCubit.openDrawerStep,
              callback: (context) {
                Scaffold.of(context).openDrawer();
                Future.delayed(const Duration(milliseconds: 1500), () {
                  Scaffold.of(context).closeDrawer();
                  TutotialCubit.of(context).goToState(TutotialCubit.done);
                });
              },
              child: Stack(
                fit: StackFit.expand,
                children: [
                  Positioned(
                      bottom: 0,
                      child: SizedBox(
                        height: screenHeight * (1 - _displayHeight),
                        width: width,
                        child: SlideKeyboard(
                          foregroundWidget: BaseKeyboard(),
                          backgroundWidget: AdvancedKeyboard(
                            showFirstKeyboard: !_showSecondKeyboard,
                          ),
                          onSwitchDetected: () {
                            setState(() {
                              _showSecondKeyboard = !_showSecondKeyboard;
                            });
                          },
                        ),
                      )),
                  Positioned(
                      top: 0,
                      child: Display(
                        height: screenHeight * _displayHeight,
                        expandedHeight: screenHeight,
                      ))
                ],
              ),
            ),
          ),
        ),
      ),
    );
  }
}

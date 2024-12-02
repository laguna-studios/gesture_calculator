import "package:flutter/material.dart";
import "package:flutter_bloc/flutter_bloc.dart";
import "package:flutter_i18n/flutter_i18n.dart";
import "package:gesture_calculator/bloc/tutorial_cubit.dart";
import "package:gesture_calculator/ui/index.dart";

class TutorialMessage extends StatelessWidget {
  final Widget child;
  final int tutorialStep;
  final Function(BuildContext) callback;
  final int? skipToStep;

  const TutorialMessage({
    super.key,
    required this.child,
    required this.tutorialStep,
    required this.callback,
    this.skipToStep,
  });

  @override
  Widget build(BuildContext context) {
    return BlocListener<TutotialCubit, int>(
      listenWhen: (_, current) => current == tutorialStep,
      listener: (context, state) => showDialog(
        context: context,
        builder: (dialogContext) => PopScope(
          canPop: false,
          child: AlertDialog(
            title: Text(
              FlutterI18n.translate(context, "tutorial.step$tutorialStep.title"),
              style: TextStyle(color: Theme.of(context).extension<CalculatorTheme>()!.drawerText),
            ),
            content: Text(
              FlutterI18n.translate(context, "tutorial.step$tutorialStep.message"),
              style: TextStyle(color: Theme.of(context).extension<CalculatorTheme>()!.drawerText),
            ),
            actionsAlignment: MainAxisAlignment.spaceBetween,
            actions: [
              skipToStep == null
                  ? const SizedBox.shrink()
                  : OutlinedButton(
                      onPressed: () {
                        TutotialCubit.of(context).skipTo(skipToStep!);
                        Navigator.pop(dialogContext);
                      },
                      style: ButtonStyle(
                        side: MaterialStateProperty.all(BorderSide.none),
                        foregroundColor: MaterialStateProperty.all(Colors.grey),
                      ),
                      child: Text(FlutterI18n.translate(context, "tutorial.skip")),
                    ),
              OutlinedButton(
                onPressed: () async {
                  Navigator.pop(dialogContext);
                  await Future.delayed(const Duration(milliseconds: 200));
                  if (!context.mounted) return;
                  callback(context);
                },
                style: ButtonStyle(side: MaterialStateProperty.all(BorderSide.none)),
                child: Text(FlutterI18n.translate(context, "tutorial.ok")),
              ),
            ],
          ),
        ),
        barrierDismissible: false,
      ),
      child: child,
    );
  }
}

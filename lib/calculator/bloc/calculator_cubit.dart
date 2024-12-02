import "package:flutter/material.dart";
import "package:flutter_bloc/flutter_bloc.dart";
import "package:gesture_calculator/calculator/data/calculator_repository.dart";
import "package:gesture_calculator/calculator/data/model.dart";
import "package:gesture_calculator/calculator/ui/calculator_text_editing_controller.dart";
import "package:freezed_annotation/freezed_annotation.dart";

part "calculator_cubit.freezed.dart";

@freezed
class CalculatorState with _$CalculatorState {
  const factory CalculatorState({
    required String result,
    required int decimalPlaces,
    required bool useRadians,
    required bool isCalculating,
  }) = _CalculatorState;
}

class CalculatorCubit extends Cubit<CalculatorState> {
  final CalculatorTextEditingController textController;
  final Function(String) onResult;

  static CalculatorCubit of(BuildContext context) => BlocProvider.of<CalculatorCubit>(context);

  CalculatorCubit(super.initialState, {required this.textController, required this.onResult});

  Future<void> init() async {
    CalculatorRepository.setDecimalPlaces(state.decimalPlaces);
    CalculatorRepository.setUseRadians(state.useRadians);
  }

  Future<void> onTokenPressed(CalculatorToken token) async {
    switch (token) {
      case CalculatorToken.equals:
        equals();
        break;
      case CalculatorToken.clear:
        clearAll();
        break;
      case CalculatorToken.clearOne:
        clearOne();
        break;
      case CalculatorToken.radOrDeg:
        setUseRadians(!state.useRadians);
        break;
      case CalculatorToken.bracketAuto:
        insertBrackets();
        break;
      default:
        insert(token);
    }
  }

  Future<void> insert(CalculatorToken token) async {
    textController.insert(token);
    _showResultPreview();
  }

  Future<void> insertBrackets() async {
    textController.insertBracket();
    _showResultPreview();
  }

  Future<void> clearOne() async {
    textController.clearOne();
    _showResultPreview();
  }

  Future<void> clearAll() async {
    textController.clear();
    emit(state.copyWith(result: ""));
  }

  Future<void> equals() async {
    if (!state.isCalculating) {
      if (state.result.isEmpty) return;

      onResult(textController.expressionFormatter.unformat(textController.text));
      textController.text = state.result;
    }
    emit(state.copyWith(result: ""));
  }

  Future<void> setDecimalPlaces(int decimalPlaces) async {
    CalculatorRepository.setDecimalPlaces(decimalPlaces);
    emit(state.copyWith(decimalPlaces: decimalPlaces));
    _showResultPreview();
  }

  Future<void> setUseRadians(bool useRadians) async {
    CalculatorRepository.setUseRadians(useRadians);
    emit(state.copyWith(useRadians: useRadians));
    _showResultPreview();
  }

  Future<void> setExpression(String expression) async {
    textController.setExpression(expression);
    _showResultPreview();
  }

  // EXPENSIVE TASK
  Future<String> _calculate(String expression) async {
    try {
      emit(state.copyWith(isCalculating: true));
      String result = await CalculatorRepository.evaluate(expression);
      emit(state.copyWith(isCalculating: false));
      return result;
    } catch (e) {
      emit(state.copyWith(isCalculating: false));
      return "";
    }
  }

  Future<void> _showResultPreview() async {
    try {
      String expression = textController.text;
      String result = await _calculate(textController.expressionFormatter.unformat(expression));

      // it took to long to calculate
      if (expression != textController.text) return;

      // format result
      result = textController.expressionFormatter.format(result);

      // result equals expression
      if (expression == result) {
        emit(state.copyWith(result: ""));
        return;
      }

      emit(state.copyWith(result: result));
    } catch (e) {
      emit(state.copyWith(result: ""));
    }
  }
}

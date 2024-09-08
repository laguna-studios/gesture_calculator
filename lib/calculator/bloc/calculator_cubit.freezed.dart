// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'calculator_cubit.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#custom-getters-and-methods');

/// @nodoc
mixin _$CalculatorState {
  String get result => throw _privateConstructorUsedError;
  int get decimalPlaces => throw _privateConstructorUsedError;
  bool get useRadians => throw _privateConstructorUsedError;
  bool get isCalculating => throw _privateConstructorUsedError;

  @JsonKey(ignore: true)
  $CalculatorStateCopyWith<CalculatorState> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $CalculatorStateCopyWith<$Res> {
  factory $CalculatorStateCopyWith(
          CalculatorState value, $Res Function(CalculatorState) then) =
      _$CalculatorStateCopyWithImpl<$Res, CalculatorState>;
  @useResult
  $Res call(
      {String result, int decimalPlaces, bool useRadians, bool isCalculating});
}

/// @nodoc
class _$CalculatorStateCopyWithImpl<$Res, $Val extends CalculatorState>
    implements $CalculatorStateCopyWith<$Res> {
  _$CalculatorStateCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? result = null,
    Object? decimalPlaces = null,
    Object? useRadians = null,
    Object? isCalculating = null,
  }) {
    return _then(_value.copyWith(
      result: null == result
          ? _value.result
          : result // ignore: cast_nullable_to_non_nullable
              as String,
      decimalPlaces: null == decimalPlaces
          ? _value.decimalPlaces
          : decimalPlaces // ignore: cast_nullable_to_non_nullable
              as int,
      useRadians: null == useRadians
          ? _value.useRadians
          : useRadians // ignore: cast_nullable_to_non_nullable
              as bool,
      isCalculating: null == isCalculating
          ? _value.isCalculating
          : isCalculating // ignore: cast_nullable_to_non_nullable
              as bool,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$_CalculatorStateCopyWith<$Res>
    implements $CalculatorStateCopyWith<$Res> {
  factory _$$_CalculatorStateCopyWith(
          _$_CalculatorState value, $Res Function(_$_CalculatorState) then) =
      __$$_CalculatorStateCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {String result, int decimalPlaces, bool useRadians, bool isCalculating});
}

/// @nodoc
class __$$_CalculatorStateCopyWithImpl<$Res>
    extends _$CalculatorStateCopyWithImpl<$Res, _$_CalculatorState>
    implements _$$_CalculatorStateCopyWith<$Res> {
  __$$_CalculatorStateCopyWithImpl(
      _$_CalculatorState _value, $Res Function(_$_CalculatorState) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? result = null,
    Object? decimalPlaces = null,
    Object? useRadians = null,
    Object? isCalculating = null,
  }) {
    return _then(_$_CalculatorState(
      result: null == result
          ? _value.result
          : result // ignore: cast_nullable_to_non_nullable
              as String,
      decimalPlaces: null == decimalPlaces
          ? _value.decimalPlaces
          : decimalPlaces // ignore: cast_nullable_to_non_nullable
              as int,
      useRadians: null == useRadians
          ? _value.useRadians
          : useRadians // ignore: cast_nullable_to_non_nullable
              as bool,
      isCalculating: null == isCalculating
          ? _value.isCalculating
          : isCalculating // ignore: cast_nullable_to_non_nullable
              as bool,
    ));
  }
}

/// @nodoc

class _$_CalculatorState implements _CalculatorState {
  const _$_CalculatorState(
      {required this.result,
      required this.decimalPlaces,
      required this.useRadians,
      required this.isCalculating});

  @override
  final String result;
  @override
  final int decimalPlaces;
  @override
  final bool useRadians;
  @override
  final bool isCalculating;

  @override
  String toString() {
    return 'CalculatorState(result: $result, decimalPlaces: $decimalPlaces, useRadians: $useRadians, isCalculating: $isCalculating)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_CalculatorState &&
            (identical(other.result, result) || other.result == result) &&
            (identical(other.decimalPlaces, decimalPlaces) ||
                other.decimalPlaces == decimalPlaces) &&
            (identical(other.useRadians, useRadians) ||
                other.useRadians == useRadians) &&
            (identical(other.isCalculating, isCalculating) ||
                other.isCalculating == isCalculating));
  }

  @override
  int get hashCode => Object.hash(
      runtimeType, result, decimalPlaces, useRadians, isCalculating);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_CalculatorStateCopyWith<_$_CalculatorState> get copyWith =>
      __$$_CalculatorStateCopyWithImpl<_$_CalculatorState>(this, _$identity);
}

abstract class _CalculatorState implements CalculatorState {
  const factory _CalculatorState(
      {required final String result,
      required final int decimalPlaces,
      required final bool useRadians,
      required final bool isCalculating}) = _$_CalculatorState;

  @override
  String get result;
  @override
  int get decimalPlaces;
  @override
  bool get useRadians;
  @override
  bool get isCalculating;
  @override
  @JsonKey(ignore: true)
  _$$_CalculatorStateCopyWith<_$_CalculatorState> get copyWith =>
      throw _privateConstructorUsedError;
}

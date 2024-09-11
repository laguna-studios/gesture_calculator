import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:google_mobile_ads/google_mobile_ads.dart';

class AdsCubit extends Cubit<bool> {
  static AdsCubit of(BuildContext context) =>
      BlocProvider.of<AdsCubit>(context);

  AdsCubit() : super(false);

  BannerAd? bannerAd;
  AppOpenAd? _appOpenAd;

  final Duration _appOpenAdMaxAge = Duration(hours: 4);
  final int _showAdLimit = 4;
  int _appOpenings = 0;
  DateTime? _appOpenAdAge;

  Future<void> init() async {
    _initBanner();
    _loadAppOpenAd();
  }

  Future<void> _initBanner() async {
    String adUnitId = kReleaseMode
        ? "ca-app-pub-4439621725258210/2637337518"
        : "ca-app-pub-3940256099942544/6300978111";
    bannerAd = BannerAd(
        size: AdSize.banner,
        adUnitId: adUnitId,
        listener: BannerAdListener(
          onAdLoaded: (_) => emit(true),
        ),
        request: const AdRequest());
    bannerAd?.load();
  }

  Future<void> _loadAppOpenAd() async {
    
    String adUnitId = kReleaseMode 
    ? "ca-app-pub-4439621725258210/2963000522"
    : "ca-app-pub-3940256099942544/5575463023";
    AppOpenAd.load(
        adUnitId: adUnitId,
        request: const AdRequest(),
        adLoadCallback: AppOpenAdLoadCallback(
            onAdLoaded: ((ad) {
              _appOpenAd = ad;
              _appOpenAdAge = DateTime.now();
            }),
            onAdFailedToLoad: (_) {}));
  }

  Future<void> showAppOpenAd() async {
    if (_appOpenAd == null || DateTime.now().difference(_appOpenAdAge!) >= _appOpenAdMaxAge) {
      _appOpenAd = null;
      _appOpenAdAge = null;
      _loadAppOpenAd();
      return;
    }

    _appOpenings++;

    if (_appOpenings >= _showAdLimit) {
      await _appOpenAd?.show();
      _appOpenAd = null;
      _appOpenings = 0;
    }    
  }
}

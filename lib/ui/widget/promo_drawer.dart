import 'package:flutter/material.dart';
import 'package:flutter_i18n/flutter_i18n.dart';
import 'package:gesture_calculator/ui/index.dart';
import 'package:in_app_review/in_app_review.dart';
import 'package:url_launcher/url_launcher.dart';

class PromoDrawer extends StatelessWidget {
  const PromoDrawer({
    super.key,
  });

  @override
  Widget build(BuildContext context) {
    CalculatorTheme theme = Theme.of(context).extension<CalculatorTheme>()!;

    return Drawer(
      child: Column(
        children: [
          Padding(
            padding: const EdgeInsets.only(top: 16.0, bottom: 0),
            child: ClipOval(
                child: Image.asset(
              "assets/lagunastudios.jpg",
              height: 124,
              width: 124,
            )),
          ),
          Text(
            "Laguna Studios",
            style: TextStyle(fontSize: 24, color: theme.drawerText),
          ),
          Text(
            "Apps made with 💜",
            style: TextStyle(color: theme.resultText),
          ),
          const Padding(
            padding: EdgeInsets.symmetric(vertical: 12.0),
            child: Divider(
              height: 1,
              endIndent: 64,
              indent: 64,
            ),
          ),
          ListTile(
            textColor: theme.drawerText,
            title: Text(
              FlutterI18n.translate(context, "drawer.like"),
            ),
            leading: const Icon(Icons.favorite),
            onTap: likeApp,
          ),
          const Padding(
            padding: EdgeInsets.symmetric(vertical: 4.0),
            child: Divider(
              height: 1,
              endIndent: 64,
              indent: 64,
            ),
          ),
          ListTile(
            textColor: theme.drawerText,
            title: Text(FlutterI18n.translate(context, "drawer.contact")),
            leading: const Icon(Icons.person),
            onTap: () => openUrl(FlutterI18n.translate(context, "drawer.url.contact")),
          ),
          ListTile(
            textColor: theme.drawerText,
            title: Text(FlutterI18n.translate(context, "drawer.terms")),
            leading: const Icon(Icons.casino),
            onTap: () => openUrl(FlutterI18n.translate(context, "drawer.url.terms")),
          ),
          ListTile(
            textColor: theme.drawerText,
            title: Text(FlutterI18n.translate(context, "drawer.privacy")),
            leading: const Icon(Icons.lock),
            onTap: () => openUrl(FlutterI18n.translate(context, "drawer.url.privacy")),
          ),
          ListTile(
            textColor: theme.drawerText,
            title: Text(FlutterI18n.translate(context, "drawer.about")),
            leading: const Icon(Icons.people),
            onTap: () => showAboutDialog(context: context),
          ),
        ],
      ),
    );
  }

  Future<void> likeApp() async {
    if (await InAppReview.instance.isAvailable()) {
      InAppReview.instance.requestReview();
    } else {
      openUrl("https://play.google.com/store/apps/details?id=org.seniorlaguna.gcalculator");
    }
  }

  Future<void> openUrl(String url) async {
    try {
      launchUrl(Uri.parse(url));
    } catch (e) {}
  }
}

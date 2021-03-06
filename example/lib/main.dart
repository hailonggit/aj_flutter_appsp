import 'package:flutter/material.dart';

import 'package:aj_flutter_appsp_example/notice/notice_page.dart';
import "styles.dart";
import 'package:aj_flutter_appsp_example/update/version_update_page.dart';

void main() => runApp(MaterialApp(
      home: MyApp(),
      theme: ThemeData(
        platform: TargetPlatform.iOS,
      ),
    ));

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  @override
  void initState() {
    super.initState();
  }

  //跳转到版本更新
  snaptoVersionUpdatePage() {
    Navigator.push(
        context, MaterialPageRoute(builder: (context) => VersionUpdatePage()));
  }

  //跳转到公告样式
  snaptoNoticePage() {
    Navigator.push(
        context, MaterialPageRoute(builder: (context) => NoticePage()));
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('移动服务平台SDK'),
      ),
      body: Column(
        children: <Widget>[
          SizedBox(
            height: 20,
          ),
          Center(
            child: Styles.getBtn(context, '版本更新', () {
              snaptoVersionUpdatePage();
            }),
          ),
          SizedBox(
            height: 10,
          ),
          Styles.getBtn(context, '公告样式', () {
            snaptoNoticePage();
          }),
        ],
      ),
    );
  }
}

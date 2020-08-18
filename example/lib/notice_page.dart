import 'dart:ui';
import 'styles.dart';
import 'package:flutter/material.dart';
import 'package:aj_flutter_appsp/sp_resp_update_model.dart';
import 'package:aj_flutter_appsp/appsp_status_code.dart';
import 'navigator_utils.dart';
import 'version_update_dialog.dart';
import 'package:aj_flutter_appsp/sp_resp_notice_model.dart';
import 'package:aj_flutter_appsp/sp_notice_model_item.dart';
import 'notice_type.dart';
import 'notice_dialog.dart';
import 'package:permission_handler/permission_handler.dart';
import 'package:aj_flutter_appsp/aj_flutter_appsp.dart' as appsp;
import 'package:flutter/services.dart';
import 'dart:io';
import 'dart:convert';
import 'dart:async';
import 'package:marquee/marquee.dart';

class NoticePage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return NoticeWidget();
  }
}

class NoticeWidget extends StatefulWidget {
  NoticeWidget({Key key}) : super(key: key);

  @override
  _NoticeState createState() => _NoticeState();
}

class _NoticeState extends State<NoticeWidget> {
  static const String appKey = "b9abfa24ee644e1d8baa39cef165261d";
  List<SpNoticeModelItem> marqueeeItems = [];
  var _scaffoldkey = new GlobalKey<ScaffoldState>();
  @override
  void initState() {
    super.initState();
  }

  _requestNoticeType(String noticeType) async {
    //无需改造数据，用服务器返回数据，下面的都是模拟的数据
    //ignore
    SpRespNoticeModel noticeModel =
        await appsp.AjFlutterAppSp.getNoticeModel(appKey: appKey);
    if (noticeModel == null) {
      Scaffold.of(context).showSnackBar(
        SnackBar(content: Text("没有公告信息")),
      );
      return;
    }
    print("noticeModel is $noticeModel ");
    String errorMsg = "";
    switch (noticeModel.statusCode) {
      case AppSpStatusCode.StatusCode_Success:
        break;
      case AppSpStatusCode.StatusCode_Cancel:
        errorMsg = "用户已取消json文件下载";
        break;
      case AppSpStatusCode.StatusCode_Timeout:
        errorMsg = "服务器json文件地址连接超时";
        break;
      case AppSpStatusCode.StatusCode_UrlFormatError:
        errorMsg = "请求地址格式错误";
        break;
    }

    if (errorMsg.isNotEmpty) {
      var snackBar = SnackBar(content: Text(errorMsg));
      _scaffoldkey.currentState.showSnackBar(snackBar);
      return;
    }
    _handleNotice(noticeModel.modelItemList, noticeType);
  }

  _handleNotice(List<SpNoticeModelItem> modelItems, String noticeType,
      {Color buttonColor, Color titleColor}) {
    if (noticeType == null) {
      //常规场景，不做模拟
      marqueeeItems.clear();
      modelItems.forEach((modelItem) {
        if (modelItem.templateType == NoticeType.Dialog) {
          _showNoticeDialog(modelItem,
              buttonColor: buttonColor, titleColor: titleColor);
        } else if (modelItem.templateType == NoticeType.Marquee) {
          marqueeeItems.add(modelItem);
        }
      });
      setState(() {});
      return;
    }
    //一下是模拟
    switch (noticeType) {
      case NoticeType.Dialog: //如果显示样式是dialog
        modelItems.forEach((modelItem) {
          modelItem.templateType = NoticeType.Dialog;
          _showNoticeDialog(modelItem,
              buttonColor: buttonColor, titleColor: titleColor);
        });
        break;
      case NoticeType.Marquee: //如果显示样式是跑马灯
        setState(() {
          modelItems.forEach((modelItem) {
            modelItem.templateType = NoticeType.Marquee;
          });
          marqueeeItems = modelItems;
        });
        break;
      default:
        break;
    }
  }

  _showNoticeDialog(SpNoticeModelItem noticeItem,
      {Color buttonColor, Color titleColor}) {
    showDialog(
        context: context,
        builder: (context) {
          NoticeDialog noticeDialog = new NoticeDialog(
            noticeItem: noticeItem,
            positiveText: "更新",
            buttonColor: buttonColor,
            titleColor: titleColor,
          );
          return noticeDialog;
        });
  }

  List<Widget> getMarqueenWidgets() {
    List<Widget> widgets = [];
    if (marqueeeItems.isEmpty) {
      return widgets;
    }
    marqueeeItems?.forEach((item) {
      Widget child = Container(
        child: Row(
          children: <Widget>[
            Image.asset(
              'images/alert.png',
              width: 20,
              height: 20,
            ),
            Padding(
              padding: EdgeInsets.all(16.0),
              child: Container(
                  height: 30.0,
                  width: MediaQuery.of(context).size.width - 100,
                  child: Marquee(
                    text: item.details,
                    blankSpace: 60.0,
                  )),
            )
          ],
          mainAxisAlignment: MainAxisAlignment.center,
        ),
        color: Colors.white,
      );
      widgets.add(child);
    });
    return widgets;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      key: _scaffoldkey,
      appBar: AppBar(
        title: const Text('公告样式'),
      ),
      body: Column(
        children: <Widget>[
          SizedBox(
            height: 20,
          ),
          Center(
              child: Styles.getBtn(context, '显示公告', () {
            _requestNoticeType(null);
          })),
          Center(
              child: Styles.getBtn(context, '模拟弹窗公告', () {
            _requestNoticeType(NoticeType.Dialog);
          })),
          SizedBox(
            height: 10,
          ),
          Styles.getBtn(context, '模拟跑马灯公告', () {
            _requestNoticeType(NoticeType.Marquee);
          }),
          SizedBox(
            height: 10,
          ),
          Column(children: getMarqueenWidgets())
        ],
      ),
    );
  }
}

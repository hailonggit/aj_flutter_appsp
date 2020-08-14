import 'sp_notice_model_item.dart';

class SpRespNoticeModel {
  List<SpNoticeModelItem> modelItemList;
   int statusCode;//状态码

  SpRespNoticeModel(
      {this.modelItemList,
        this.statusCode,
        });


  SpRespNoticeModel.fromJson(Map<String, dynamic> json) {
    if (json['modelItemList'] != null) {
      modelItemList = new List<SpNoticeModelItem>();
      json['modelItemList'].forEach((v) {
        modelItemList.add(new SpNoticeModelItem.fromJson(v));
      });
    }
    statusCode = json['statusCode'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    if (this.modelItemList != null) {
      data['modelItemList'] =
          this.modelItemList.map((v) => v.toJson()).toList();
    }
    data['statusCode'] = this.statusCode;
    return data;
  }

  @override
  String toString() {
    return 'SpRespNoticeModel{modelItemList: $modelItemList, statusCode: $statusCode}';
  }


}

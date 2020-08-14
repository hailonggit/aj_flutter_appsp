class SpRespUpdateModel {
  bool isExternalUrl;
  bool mustUpdate;
  bool showUpdate;
  int statusCode;
  String updateLog;
  String url;

  SpRespUpdateModel(
      {this.isExternalUrl,
      this.mustUpdate,
      this.showUpdate,
      this.statusCode,
      this.updateLog,
      this.url});


  SpRespUpdateModel.fromJson(Map<String, dynamic> json) {
    isExternalUrl = json['isExternalUrl'];
    mustUpdate = json['mustUpdate'];
    showUpdate = json['showUpdate'];
    statusCode = json['statusCode'];
    updateLog = json['updateLog'];
    url = json['url'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['isExternalUrl'] = this.isExternalUrl;
    data['mustUpdate'] = this.mustUpdate;
    data['showUpdate'] = this.showUpdate;
    data['statusCode'] = this.statusCode;
    data['updateLog'] = this.updateLog;
    data['url'] = this.url;
    return data;
  }

  @override
  String toString() {
    return 'SpRespUpdateModel{isExternalUrl: $isExternalUrl, mustUpdate: $mustUpdate, showUpdate: $showUpdate, statusCode: $statusCode, updateLog: $updateLog, url: $url}';
  }
}

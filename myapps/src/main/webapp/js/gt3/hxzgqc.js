var app = angular.module('app', []).controller('hxzgqcController',
		function($scope, $http, queue) {
			$scope.msg = "Gt3核心征管清册";
			$scope.isAdmin = true;
			$scope.impExl = false;
			$scope.types = [{"k":"业务表名称","v":"ywbmc"}, {"k":"业务表字段","v":"ywbzd"}, {"k":"代码表名称","v":"dmbmc"}, {"k":"代码表字段","v":"dmbzd"}];
			$scope.uploadType = "";
			$scope.dmbmcTable = {"pageNumber":1, "pageSize":20, "list":[]};
			$scope.ywbmcTable = {"pageNumber":1, "pageSize":20, "list":[]};
			$scope.showType = "";
			$scope.fields = [];
			$scope.ddls = {};
			$scope.advance_search = "";
			$scope.lastShowType = "";
			$scope.exact = {"type":"", "value":"", "bmcrec":[], "bzdrec":[]};
			
			$scope.exactSearch = function () {
				$http.get("./getTableExact?type=" + $scope.exact.type + "&value=" + $scope.exact.value).then(function (data) {
					$scope.lastShowType = $scope.showType;
					if ($scope.exact.type == "bmc") {
						$scope.exact.bmcrec = data.data;
						$scope.showType = "exactbmc";
					} else if ($scope.exact.type == "bzd") {
						$scope.exact.bzdrec = data.data;
						$scope.showType = "exactbzd";
					}
				}).catch(function(){
					alert("网络错误！");
				});
			};
			
			$scope.goAdSearch = function () {
				var url = "./adSearch";
				var para = "?content=" + $scope.advance_search;
				url = url + para;
				window.open(url);
			};

			$scope.importExcel = function() {
				if(!$scope.uploadType){
					return alert("请选择文件类型");
				}
				var uploadFile = $("#uploadFile").val();
				if (!uploadFile) {
					return alert("请选择需要上传的文件");
				}
				if (!/.[X|x][L|L][S|s][X|x]?$/i.test(uploadFile)) {
					alert("请选择Excel文件上传");
					return false;
				}
				var submitForm = document.getElementById("submitForm");
				submitForm.submit();
			};
			
			$scope.getTable = function (v) {
				var tb = $scope[v+"Table"];
				$http.get("./getTable?v=" + v + "&p=" + tb.pageNumber + "&l=" + tb.pageSize).then(function (data) {
					$scope[v+"Table"] = data.data;
					$scope.showType = v;
					$scope.lastShowType = $scope.showType;
				}).catch(function(){
					alert("网络错误！");
				});
			};
			
			$scope.pageChange = function(v, direction) {
				if (direction == "pre") {
					$scope[v+"Table"]["pageNumber"]-=1;
				} else {
					$scope[v+"Table"]["pageNumber"]+=1;
				}
				$scope.getTable(v);
			};
			
			$scope.getField = function(v) {
				var tb = v.tb.NAME_EN;
				$http.get("./getField?tb=" + tb).then(function (data) {
					$scope.fields = data.data;
					$scope.lastShowType = $scope.showType;
					$scope.showType = "fields";
				}).catch(function(){
					alert("网络错误！");
				});
			}; 
			
			$scope.getDDL = function(v) {
				var tb = v.tb.NAME_EN;
				$http.get("./getDDL?tb=" + tb).then(function (data) {
					$scope.ddls = data.data.ddl;
					$scope.showType = "ddls";
				}).catch(function(){
					alert("网络错误！");
				});
			}; 
			
			$scope.batchDDL = function() {
				$http.get("./getDDLs").then(function (data) {
					if(data.data == "ok"){
						alert("成功");
					}
				}).catch(function(){
					alert("网络错误！");
				});
			}; 
		});
var app = angular.module('app', []).controller('hxzgqcController',
		function($scope, $http, queue) {
			$scope.msg = "Gt3核心征管清册";
			$scope.isAdmin = true;
			$scope.impExl = false;
			$scope.types = [{"k":"业务表名称","v":"ywbmc"}, {"k":"业务表字段","v":"ywbzd"}, {"k":"代码表名称","v":"dmbmc"}, {"k":"代码表字段","v":"dmbzd"}];
			$scope.uploadType = "";
			$scope.dmbmcTable = {"pageNumber":1, "pageSize":50, "list":[]};
			$scope.ywbmcTable = {"pageNumber":1, "pageSize":50, "list":[]};
			$scope.showType = "";
			$scope.fields = [];

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
					$scope.showType = "fields";
				}).catch(function(){
					alert("网络错误！");
				});
			}; 
		});
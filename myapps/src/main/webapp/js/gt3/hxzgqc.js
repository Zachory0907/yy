var app = angular.module('app', []).controller('hxzgqcController',
		function($scope, $http, queue) {
			$scope.msg = "Gt3核心征管清册";
			$scope.isAdmin = true;
			$scope.impExl = false;
			$scope.types = [{"k":"业务表名称","v":"ywbmc"}, {"k":"业务表字段","v":"ywbzd"}, {"k":"代码表名称","v":"dmbmc"}, {"k":"代码表字段","v":"dmbzd"}];
			$scope.uploadType = "";
			$scope.tables = [];

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
				$http.get("./getTable?v=" + v).then(function (data) {
					$scope.table = data.data;
				}).catch(function(){
					alert("网络错误！");
				});
			};
		});
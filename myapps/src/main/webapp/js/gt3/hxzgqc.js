var app = angular.module('app', []).controller('hxzgqcController',
		function($scope, $http, queue) {
			$scope.msg = "Gt3核心征管清册";
			$scope.isAdmin = true;
			$scope.impExl = false;
			$scope.types = [{"k":"表名称清册","v":"bmc"}, {"k":"表字段清册","v":"bzd"}];
			$scope.uploadType = "";

			$scope.importExcel = function() {
				debugger;
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
		});
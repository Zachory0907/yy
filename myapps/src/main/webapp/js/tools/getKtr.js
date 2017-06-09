var app = angular.module('app', []).controller('ktrController',
		function($scope, $http, queue) {
			$scope.msg = "生成Ktr文件";
			$scope.table = {"tablename":"", "user": "", "fields":[], "ktr":""};
			$scope.impExl = false;
			
			$scope.exportKtr = function () {
				window.location.href = "./exportKtr?filePath=" + $scope.table.filePath;
			};
			
			$scope.importExcel = function() {
				var uploadFile = $("#uploadFile").val();
				if (!uploadFile) {
					return alert("请选择需要上传的文件");
				}
				if (!/.[X|x][L|L][S|s][X|x]?$/i.test(uploadFile)) {
					return alert("请选择Excel文件上传");
				}
				var submitForm = document.getElementById("submitForm");
				submitForm.submit();
			};
			
			$scope.copyDDL = function () {
				var content = document.getElementById("content");// 选择对象
				content.select();
				document.execCommand("Copy"); // 执行浏览器复制命令
				alert("已复制好，可贴粘。");
			};
			
			$scope.searchKtr = function(v) {
				if (!$scope.table.tablename) {
					return alert("请输入表名称");
				}
				$http.get("./maleKtrByTablename?tbname=" + $scope.table.tablename).then(function (data) {
					if (data.data.status == "ok") {
						$scope.table.fields = data.data.fields;
						$scope.table.ktr = data.data.ktrFile;
						$scope.table.tabMsg = data.data.tabMsg;
						$scope.table.filePath = data.data.filePath;
					} else {
						$scope.table.ktr = "未找到相关表！";
					}
				}).catch(function(){
					alert("网络错误！");
				});
			}; 
		});
var app = angular.module('app', []).controller('ddlController',
		function($scope, $http, queue) {
			$scope.msg = "扫描获取DDL";
			$scope.table = {"tablename":"", "user": "", "ddl":""};
			$scope.impExl = false;
			
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
			
			$scope.searchDDL = function(v) {
				if (!$scope.table.tablename) {
					return alert("请输入表名称");
				}
				if (!$scope.table.user) {
					return alert("请输入表所属用户");
				}
				$http.post("./searchDDL", $scope.table).then(function (data) {
					$scope.table.ddl = data.data.ddl;
				}).catch(function(){
					alert("网络错误！");
				});
			}; 
		});
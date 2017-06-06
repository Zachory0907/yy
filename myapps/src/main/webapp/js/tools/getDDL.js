var app = angular.module('app', []).controller('ddlController',
		function($scope, $http, queue) {
			$scope.msg = "扫描获取DDL";
			$scope.table = {"tablename":"", "user": "", "ddl":""};
			
			$scope.searchDDL = function(v) {
				if (!$scope.table.tablename) {
					return alert("请输入表名称");
				}
				if (!$scope.table.user) {
					return alert("请输入表所属用户");
				}
				$http.post("./searchDDL", $scope.table).then(function (data) {
					$scope.table.ddl = data.ddl;
				}).catch(function(){
					alert("网络错误！");
				});
			}; 
		});
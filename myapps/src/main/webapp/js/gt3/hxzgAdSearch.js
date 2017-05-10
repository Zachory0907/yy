var app = angular.module('app', []).controller('hxzgAdsearchController',
		function($scope, $http, queue, $compile) {
			$scope.msg = "高级搜索";
			$scope.searchMsg = "";
			$scope.content = "";
			$scope.results = {"pageNumber":1, "pageSize":20, "list":[], "totalPage":1, "firstPage":true, "lastPage":true};
			$scope.showMx = 0;
			$scope.tableMx = {};
			
			$scope.pageChange = function (direction) {
				if (direction == "pre") {
					$scope.results["pageNumber"]-=1;
				} else {
					$scope.results["pageNumber"]+=1;
				}
				executeSearch();
			};
			
			var getCont = function () {
				var url = window.location.href;
				var reg = /\?content=([^&]+)$/;
				if (reg.test(url)) {
					$scope.content = decodeURI(RegExp.$1);
				}
				executeSearch();
			};
			
			var executeSearch = function () {
				$("#tb").empty();;
				$http.get("./executeAdSearch?content=" + $scope.content + "&p=" + $scope.results.pageNumber + "&l=" + $scope.results.pageSize).then(function (data) {
					$scope.results = data.data;
					if ($scope.results.pageNumber>1) {
						$scope.results.firstPage = false;
					} else {
						$scope.results.firstPage = true;
					}
					if ($scope.results.totalPage > $scope.results.pageNumber) {
						$scope.results.lastPage = false;
					} else {
						$scope.results.lastPage = true;
					}
					var tb = "<table class=\"table table-hover\"><thead><tr>";
					tb = tb + "<th>序号</th><th>描述</th><th>操作</th></tr><thead><tbody>";
					for (var i=0, j=$scope.results.list.length; i<j; i++) {
						var ord = (i+1) + ($scope.results.pageNumber-1) * $scope.results.pageSize;
						var td = "<tr><td>" + ord + "</td><td>" + $scope.results.list[i].content + "</td><td><a href=\"javascript:;\" ng-click=\"showItem(" + ord + ")\">查看</a></td></tr>"; 
						tb += td;
					}
					tb = tb + "</tbody></table>";
					//var table = $(tb);
					var table = $compile(tb)($scope);
					table.appendTo("#tb");
				}).catch(function(){
					alert("网络错误！");
				});
			};
			
			var getField = function(v) {
				$http.get("./getField?tb=" + v).then(function (data) {
					$scope.fields = data.data;
				}).catch(function(){
					alert("网络错误！");
				});
			}; 
			
			$scope.showItem = function (v) {
				var ord = (v-1) - ($scope.results.pageNumber-1) * $scope.results.pageSize;
				var content = $scope.results.list[ord].content;
				var tb = content.split("<br />")[0].split("：")[1];
				tb = tb.replace(/<span.*?>/g ,"").replace(/<\/span>/g ,"").replace(/\s/g, "");;
				$http.get("./getTableMx?tb=" + encodeURI(tb)).then(function (data) {
					if (data.data.status == "error") {
						$scope.searchMsg = "未找到相关表";
					} else {
						$scope.tableMx = data.data;
						$scope.searchMsg = "搜索表：" + $scope.tableMx.NAME_ZH +"：";
						getField($scope.tableMx.NAME_EN);
					}
				}).catch(function(){
					alert("网络错误！");
				});
				$scope.showMx = 1;
			};
			getCont();
		});
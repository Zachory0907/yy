var app = angular.module('app', []).controller('hxzgAdsearchController',
		function($scope, $http, queue, $compile) {
			$scope.msg = "高级搜索";
			$scope.content = "";
			$scope.results = {"pageNumber":1, "pageSize":50, "list":[], "totalPage":1, "firstPage":true, "lastPage":true};
			
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
					var tb = "<table><thead><tr>";
					tb = tb + "<th>序号</th><th>描述</th><th>操作</th></tr><thead><tbody>";
					for (var i=0, j=$scope.results.list.length; i<j; i++) {
						var ord = (i+1) + ($scope.results.pageNumber-1) * $scope.results.pageSize;
						var td = "<tr><td>" + ord + "</td><td>" + $scope.results.list[i].content + "</td><td><a href=\"javascript:;\" ng-click=\"showItem()\">查看</a></td></tr>"; 
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
			
			var showItem = function (v) {
				debugger;
			};
			getCont();
		});
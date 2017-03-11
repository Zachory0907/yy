var app = angular.module('app', []).controller('indexController',
		function($scope, $http, queue) {
			$scope.msg = "主页";
			$scope.u ={"YY":false};

			$scope.show = function (con) {
				$scope.u[con] = !$scope.u[con];
			};
			
			$scope.go = function (con, fun) {
				var location = window.location.href;
				location = location.slice(0, (location.lastIndexOf("/") + 1));
				var toHref = con + "/" + fun;
				var url = location + toHref;
				window.open(url);
			};
		});
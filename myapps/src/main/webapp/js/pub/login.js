var app = angular.module('app', []).controller('loginController',
		function($scope, $http, queue) {
			$scope.msg = "欢迎";
			$scope.user = {};
			$scope.userMsg = {"uname":"a", "pwd":"b"};
			
			$scope.loginCheck = function() {
				var user = $scope.user;
				if (!user.uname)
					return $scope.userMsg.uname = "请输入用户名";
				if (!user.pwd)
					return $scope.userMsg.pwd = "请输入密码";
				$http.post("./loginCheck", user).then(function(data) {
					if (data.data.status == "ok")
						location.href = "./a";
				}).catch(function() {
					alert("网络错误");
				});
			};

		});
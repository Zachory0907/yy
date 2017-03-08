var app = angular.module('app', []).controller('loginController',
		function($scope, $http, queue) {
			$scope.msg = "登录";
			$scope.user = {};
			$scope.userMsg = {"uname":"", "pwd":""};
			
			$scope.loginCheck = function() {
				var user = $scope.user;
				if (!user.uname)
					return $scope.userMsg.uname = "请输入用户名";
				if (!user.pwd)
					return $scope.userMsg.pwd = "请输入密码";
				$http.post("./loginCheck", user).then(function(data) {
					if (data.data.status == "ok"){
						
					} else if (data.data.status == "error") {
						
					} else if (data.data.status == "nocheck") {
						
					} else if (data.data.status == "fatal"){
						
					}
				}).catch(function() {
					alert("网络错误");
				});
			};
			
			$scope.regist = function () {
				location.href = "./regist";
			}

		});
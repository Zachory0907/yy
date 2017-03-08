var app = angular.module('app', []).controller('registController',
		function($scope, $http, queue) {
			$scope.msg = "注册";
			$scope.user = {};
			$scope.userMsg = {"uname":"", "pwd":"", "mail":"", "pwdSure":""};
			
			$scope.registCheck = function() {
				$scope.userMsg = {};
				var user = $scope.user;
				var i = 0;
				if (!user.uname) {
					$scope.userMsg.uname = "请输入用户名";
					i++;
				}
				if (!user.mail) {
					$scope.userMsg.mail = "请输入邮箱";
					i++;
				}
				if (!user.pwd) {
					$scope.userMsg.pwd = "请输入密码";
					i++;
				}
				if (!user.pwdSure){
					$scope.userMsg.pwdSure = "请再次输入密码";
					i++;
				}
				if (i > 0) {
					if (!$scope.$$phase)
						$scope.$apply();
					return;
				}
				queue.add(isDuplicate).add(registUser)
				.finish(function() {
					location.href = "./login";
				}).start(function() {
				})
			};
			
			var isDuplicate = function (q) {
				$http.post("./isDuplicate", $scope.user).then(function(data) {
					if (data.data.status == "ok"){
						q.success();
					} else{
						q.error();
					}
				}).catch(function() {
					alert("网络错误");
				});
			}
			
			var registUser = function (q) {
				$http.post("./registUser", $scope.user).then(function(data) {
					if (data.data.status == "ok"){
						q.success();
					} else{
						q.error();
					}
				}).catch(function() {
					alert("网络错误");
				});
			}
			
			$scope.regist = function () {
				location.href = "./login";
			}

		});
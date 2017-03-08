var app = angular.module('app', []).controller('registController',
		function($scope, $http, queue) {
			$scope.msg = "注册";
			$scope.user = {};
			$scope.userMsg = {"uname":"", "pwd":"", "mail":"", "pwdSure":""};
			
			$scope.registCheck = function() {
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
				if (user.pwd != user.pwdSure) {
					return $scope.userMsg.pwdSure = "两次输入的密码不一致！";
				}
				if ($scope.userMsg.uname != "可以使用" || $scope.userMsg.mail != "可以使用")
					return;
				
				registUser();
			};
			
			$scope.blurName = function() {
				if (!$scope.user.uname) {
					return $scope.userMsg.uname = "请输入用户名";
				} 
				isDuplicate("uname", $scope.user.uname);
			};
			
			$scope.blurMail = function() {
				if (!$scope.user.mail) {
					return $scope.userMsg.mail = "请输入邮箱";
				}
				isDuplicate("mail", $scope.user.mail);
			};
			
			$scope.blurPwd = function() {
				if (!$scope.user.pwd) {
					return $scope.userMsg.pwd = "请输入密码";
				}
			};
			
			$scope.blurPwdSure = function() {
				if (!$scope.user.pwdSure) {
					return $scope.userMsg.pwdSure = "请再次输入密码";
				}
			};
			
			var isDuplicate = function (k, v) {
				$http.post("./isDuplicate?k=" + k +"&v=" + v).then(function(data) {
					if (data.data.status == "ok"){
						$scope.userMsg[k] = "可以使用";
					} else {
						$scope.userMsg[k] = "已被使用";
					}
				}).catch(function() {
					alert("网络错误");
				});
			}
			
			var registUser = function () {
				$http.post("./registUser", $scope.user).then(function(data) {
					if (data.data.count == 1){
						alert("注册成功！");
						location.href = "./login";
					} else{
						alert("注册失败！");
					}
				}).catch(function() {
					alert("网络错误");
				});
			}
			
			$scope.regist = function () {
				location.href = "./login";
			}

		});
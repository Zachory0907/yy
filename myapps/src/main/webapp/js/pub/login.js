var app = angular.module('app', []).controller('loginController',
		function($scope, $http, queue) {
			$scope.msg = "登录";
			$scope.user = {};
			$scope.userMsg = {"uname":"", "pwd":""};
			$scope.status = 1;
			
			$scope.loginCheck = function() {
				var user = $scope.user;
				if (!user.uname)
					return $scope.userMsg.uname = "请输入用户名";
				if (!user.pwd)
					return $scope.userMsg.pwd = "请输入密码";
				$http.post("./loginCheck", user).then(function(data) {
					if (data.data.status == "ok"){
						alert("登录成功！");
						location.href = "../index";
					} else if (data.data.status == "error") {
						alert("用户名或密码输入错误！");
					} else if (data.data.status == "nocheck") {
						alert("登录成功！");
						$scope.msg = "账户激活";
						$scope.userid = data.data.id;
						$scope.usermail = data.data.mail;
						$scope.status = 2;
					} else if (data.data.status == "fatal"){
						alert("网络异常！");
					}
				}).catch(function() {
					alert("网络错误");
				});
			};
			
			$scope.activate = function () {
				$http.get("./activate?code=" + $scope.activationCode + "&userid=" + $scope.userid).then(function(data) {
					if (data.data.status == "ok"){
						alert("激活成功！");
						location.href = "../index";
					} else if (data.data.status == "outtime"){
						alert("激活码超时失效，请重新获取激活码");
					} else if (data.data.status == "wrong"){
						alert("您输入的激活码有误，请确认后重新输入！");
					} else if (data.data.status == "error"){
						alert("网络异常");
					} 
				}).catch(function() {
					alert("网络错误");
				});
			};
			
			$scope.resend = function() {
				var user = $scope.user;
				$http.get("./resend?uname="+user.uname+"&mail="+$scope.usermail).then(function(data) {
					if (data.data.status == "ok"){
						alert("激活码已重新发送到您的邮箱，请及时查收！");
					} else {
						alert("网络异常！");
					}
				}).catch(function() {
					alert("网络错误");
				});
			};
			
			$scope.regist = function () {
				location.href = "./regist";
			}

		});
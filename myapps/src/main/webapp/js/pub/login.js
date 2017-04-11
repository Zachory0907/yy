var app = angular.module('app', []).controller('loginController',
		function($scope, $http, queue, yymodal) {
			$scope.msg = "登录";
			$scope.user = {};
			$scope.userMsg = {"uname":"", "pwd":""};
			$scope.status = 1;
			$scope.alert_error = 0;
			yymodal.alert("你是猪么？", "就问你");
			yymodal.alert("是！");
			
			$scope.loginCheck = function() {
				$scope.alert_error = 0;
				var user = $scope.user;
				if (!user.uname)
					return $scope.userMsg.uname = "请输入用户名";
				if (!user.pwd)
					return $scope.userMsg.pwd = "请输入密码";
				$http.post("./loginCheck", user).then(function(data) {
					if (data.data.status == "ok"){
						location.href = "../index";
					} else if (data.data.status == "error") {
						$scope.alert_error = 1;
					} else if (data.data.status == "nocheck") {
						yymodal.alert("登录成功");
						$scope.msg = "账户激活";
						$scope.userid = data.data.id;
						$scope.usermail = data.data.mail;
						$scope.status = 2;
					} else if (data.data.status == "fatal"){
						yymodal.alert("网络异常！");
					}
				}).catch(function() {
					yymodal.alert("网络错误");
				});
			};
			
			$scope.activate = function () {
				$http.get("./activate?code=" + $scope.activationCode + "&userid=" + $scope.userid).then(function(data) {
					if (data.data.status == "ok"){
						yymodal.alert("激活成功！");
						location.href = "../index";
					} else if (data.data.status == "outtime"){
						yymodal.alert("激活码超时失效，请重新获取激活码");
					} else if (data.data.status == "wrong"){
						yymodal.alert("您输入的激活码有误，请确认后重新输入！");
					} else if (data.data.status == "error"){
						yymodal.alert("网络异常");
					} 
				}).catch(function() {
					yymodal.alert("网络错误");
				});
			};
			
			$scope.resend = function() {
				var user = $scope.user;
				$http.get("./resend?uname="+user.uname+"&mail="+$scope.usermail).then(function(data) {
					if (data.data.status == "ok"){
						yymodal.alert("激活码已重新发送到您的邮箱，请及时查收！");
					} else {
						yymodal.alert("网络异常！");
					}
				}).catch(function() {
					yymodal.alert("网络错误");
				});
			};
			
			$scope.regist = function () {
				location.href = "./regist";
			}

		});
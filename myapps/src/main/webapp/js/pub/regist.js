var app = angular.module('app', []).controller('registController',
		function($scope, $http, queue) {
			$scope.msg = "注册";
			$scope.user = {};
			$scope.userMsg = {"uname":"", "pwd":"", "mail":"", "pwdSure":""};
			
			$scope.registCheck = function() {
				var user = $scope.user;
				var i = 0;
				if (!user.uname) {
					$("#uname").addClass("has-error");
					$scope.userMsg.uname = "请输入用户名";
					i++;
				}
				if (!user.mail) {
					$("#mail").addClass("has-error");
					$scope.userMsg.mail = "请输入邮箱";
					i++;
				}
				if (!user.pwd) {
					$("#pwd").addClass("has-error");
					$scope.userMsg.pwd = "请输入密码";
					i++;
				} else {
					$("#pwd").removeClass("has-error");
					$("#pwd").addClass("has-success");
				}
				if (!user.pwdSure){
					$("#pwdSure").addClass("has-error");
					$scope.userMsg.pwdSure = "请再次输入密码";
					i++;
				} else {
					$("#pwdSure").removeClass("has-error");
					$("#pwdSure").addClass("has-success");
				}
				if (i > 0) {
					if (!$scope.$$phase)
						$scope.$apply();
					return;
				}
				if (user.pwd != user.pwdSure) {
					$("#pwdSure").addClass("has-error");
					return $scope.userMsg.pwdSure = "两次输入的密码不一致！";
				}
				if ($scope.userMsg.uname != "可以使用" || $scope.userMsg.mail != "可以使用")
					return;
//				registUser();
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
				var regex = /^([0-9A-Za-z\-_\.]+)@([0-9a-z]+\.[a-z]{2,3}(\.[a-z]{2})?)$/g;
				if (!regex.test($scope.user.mail)) {
					$("#mail").removeClass("has-success");
					$("#mail").addClass("has-error");
					return $scope.userMsg.mail = "请输入正确的邮箱格式";
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
					var dom = "#" + k;
					if (data.data.status == "ok"){
						$(dom).removeClass("has-error");
						$(dom).addClass("has-success");
						$(dom).addClass("has-feedback");
						$scope.userMsg[k] = "可以使用";
					} else {
						$(dom).removeClass("has-success");
						$(dom).addClass("has-error");
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
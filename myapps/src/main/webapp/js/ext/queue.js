//任务队列支持
//该service主要为多个$http访问（有顺序要求）准备的，通过add任务，最后start启动即可。
app.factory('queue', function() {
	var Queue = function() {
		this.jobs = [];
		this.end = null;
		this.count = 1;
	};

	Queue.prototype = {
		construct : Queue,
		ending : function(fn) {
			this.count += 1;
		},
		// 添加任务到队列中
		add : function(job) {
			this.jobs.push(job);
			this.count ++;
			return this;
		},
		// 注册结束事件，可能是正常结束，也可能是中断后结束，每一个方法完成都会执行一遍finish
		// 改了一下，结束的作用为当对列全部正常执行完毕，才执行finish --Zachory 2017.妇女节
		finish : function(fn) {
			if (fn) {
				this.end = fn;
			}
			return this;
		},
		// 反馈任务成功
		success : function(data) {
			this.exec("success", data);
			return this;
		},
		// 反馈任务失败
		error : function(data) {
			this.exec("error", data);
			return this;
		},
		// 开始执行任务，这个函数总会出现在queue最后，它会中断链式调用规则
		start : function(fn) {
			if (fn) {
				fn();
			}
			this.exec("start");
		},
		// 实际工作函数
		exec : function(code, data) {
			this.count --;
			// 如果错误，则清空任务队列
			if (code == "error") {
				this.jobs = [];
			}
			// 如果任务池有任务，取出来执行
			if (this.jobs[0]) {
				var curr = this.jobs.shift();
				curr(this, data); // 注意，任务的函数签名第一个参数必须是queue对象，第二个是data，上次返回的数据，其中第二个是可选的
			}
			// 如果执行完毕（可能是正常结束，也可能是错误中断），触发结束事件
			if (this.jobs.length == 0 && this.end && this.count == 0) {
				this.end();
			}
		}
	};
	return new Queue();
});

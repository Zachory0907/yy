//重写alert
//1、把参数拿来，拼成modal；2、给dom添加进这个model；3、触发弹出
// 第一个参数是提示内容，第二个参数是头部信息
app.factory('yymodal', function() {
	var yymodal = function(v) {
		this.i = 1;
		this.md = {};
		this.makeModal = function (content, head) {
			if (!content) 
				content = "";
			if (!head)
				head = "提示"
			var altmodal = "<div class=\"modal fade\" tabindex=\"-1\" id=\"modalPanel" + this.i + "\" role=\"dialog\">" +
			"<div class=\"modal-dialog\" role=\"document\">" +
			"<div class=\"modal-content\">" +
			"<div class=\"modal-header\">" +
			"<button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-label=\"Close\"><span aria-hidden=\"true\">&times;</span></button>" +
			"<h4 class=\"modal-title\" id=\"myModalLabel\">" + head + "</h4>" +
			"</div>" +
			"<div class=\"modal-body\">" +
			content +             
			"</div>" +
			"<div class=\"modal-footer\">" +
			"<button type=\"button\" class=\"btn btn-default\" data-dismiss=\"modal\">关闭</button>" +
			"</div>" +
			"</div>" +
			"</div>";
			return altmodal;
		}
		
		this.alert = function (cont, head) {
			this.md = $(this.makeModal(cont, head));
			this.md.modal({keyboard: true, backdrop:"static"});
			var k = this.i;
			this.md.on('show.bs.modal', function () {
				var id = "#" + this.id;
				$(id).css("z-index", 10000-k);
			})
			this.md.on('hide.bs.modal', function () {
				var id = "#" + this.id;
				$(id).remove();
			})
			this.i ++;
		}
	};
	return new yymodal();
});

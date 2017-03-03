app.config([ "$httpProvider", function($httpProvider) {
	$httpProvider.interceptors.push(function() {
		return {
			'request' : function(config) {
				if (config.method == "GET") {
					var url = config.url;
					if (/.*?\?.*/.test(url)) {
						url = url + "&t=" + new Date().getTime();
					} else {
						url = url + "?t=" + new Date().getTime();
					}
					config.url = url;
				}
				return config;
			}

		};
	});
} ]);
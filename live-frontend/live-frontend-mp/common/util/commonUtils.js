export default {

	objectToParamString(data) {
		var result = [];
		for (var key in data) {
			var value = data[key];
			if (value.constructor == Array) {
				value.forEach(function(v) {
					result.push(key + "=" + v);
				});
			} else {
				result.push(key + '=' + value);
			}
		}
		return result.join('&');
	},

}

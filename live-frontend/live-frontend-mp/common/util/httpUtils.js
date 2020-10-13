export default {

	// 发送post请求，参数为json，响应为json 
	async postJson(apiUrl, data, fromPage = "/pages/index/index") {
		let loginUser = uni.getStorageSync("loginUser");
		if (!loginUser || !loginUser.token) {
			console.log("本地无loginUser");
			uni.redirectTo({
				url: `/pages/login/login?fromPage=${fromPage}`
			});
			return [null, "请重新登录"];
		}

		let [error, res] = await uni.request({
			url: getApp().globalData.domain + apiUrl,
			method: "POST",
			data: data,
			dataType: "json",
			header: {
				"token": loginUser.token
			}
		});

		console.log(apiUrl, error, res);
		if (error || !res) {
			uni.showToast({
				title: "请求异常",
				duration: 1000,
			});
			return [null, "请求异常"];
		}

		if (res.statusCode !== 200) {
			uni.showToast({
				title: "服务器内部错误",
				duration: 1000,
			});
			return [null, "服务器内部错误"];
		}
		if (res.data.retcode !== 0) {
			// token失效
			if (res.data.retcode === 100001 || res.data.retcode === 100002) {
				console.log("token校验失败");
				uni.redirectTo({
					url: `/pages/login/login?fromPage=${fromPage}`
				});
				uni.setStorageSync("loginUser", null);
				uni.showToast({
					title: "请重新登录",
					duration: 2000,
				});
				return [null, "请重新登录"];
			}

			uni.showToast({
				title: "接口异常" + res.data.retcode,
				duration: 1000,
			});
			return [null, "接口异常" + res.data.retcode];
		}

		return [res.data, null];
	},

	// 上传文件
	async uploadFile(tempFilePath) {
		let loginUser = uni.getStorageSync("loginUser");
		if (!loginUser || !loginUser.token) {
			uni.reLaunch({
				url: "/pages/login/login"
			});
		}

		let [error, res] = await uni.uploadFile({
			url: getApp().globalData.domain + "/oss/uploadImageOrVideo",
			filePath: tempFilePath,
			name: "file",
			header: {
				"token": loginUser.token
			}
		});

		if (error || !res) {
			uni.showToast({
				title: "上传图片失败",
				duration: 1000,
			});
			return;
		}

		var data = JSON.parse(res.data);

		if (res.statusCode !== 200) {
			uni.showToast({
				title: "服务器内部错误",
				duration: 1000,
			});
			return;
		}
		if (data.retcode !== 0) {
			uni.showToast({
				title: "接口异常" + data.retcode,
				duration: 1000,
			});
			// token失效
			if (data.retcode === 100001 || data.retcode === 100002) {
				uni.reLaunch({
					url: "/pages/login/login"
				});
				uni.setStorageSync("loginUser", null);
				uni.showToast({
					title: "请重新登录",
					duration: 2000,
				});
			}
			return;
		}

		return [data.body];
	}
}

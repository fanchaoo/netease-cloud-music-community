import Vue from 'vue'
import App from './App'

import commonUtils from 'common/util/commonUtils.js';
import httpUtils from 'common/util/httpUtils.js';
import format from 'date-fns/format'
import subDays from 'date-fns/subDays'

Vue.config.productionTip = false

// 日期格式化
Vue.filter('dateFormat', function(value, formatStr = 'yyyy-MM-dd') {
	value = new Date(value);
	return format(value, formatStr);
});

// 日期中文格式化
Vue.filter('chineseDateFormat', function(value) {
	value = new Date(value);
	let dateStr = format(value, 'yyyy-MM-dd');

	let today = new Date();
	let todayStr = format(today, 'yyyy-MM-dd');

	let yesterday = subDays(today, 1);
	let yesterdayStr = format(yesterday, 'yyyy-MM-dd');

	if (dateStr === todayStr) {
		dateStr = "今天";
	} else if (dateStr === yesterdayStr) {
		dateStr = "昨天";
	} else if (dateStr.slice(0, 4) === todayStr.slice(0, 4)) {
		dateStr = format(value, 'MM月dd日');
	} else {
		dateStr = format(value, 'yyyy年MM月dd日');
	}
	return dateStr;
});

// 时间中文格式化
Vue.filter('chineseTimeFormat', function(value) {
	value = new Date(value);
	let dateStr = format(value, 'yyyy-MM-dd');

	let today = new Date();
	let todayStr = format(today, 'yyyy-MM-dd');

	let yesterday = subDays(today, 1);
	let yesterdayStr = format(yesterday, 'yyyy-MM-dd');

	if (dateStr === todayStr) {
		dateStr = format(value, 'HH:mm');
	} else if (dateStr === yesterdayStr) {
		dateStr = "昨天" + format(value, 'HH:mm');
	} else if (dateStr.slice(0, 4) === todayStr.slice(0, 4)) {
		dateStr = format(value, 'MM月dd日');
	} else {
		dateStr = format(value, 'yyyy年MM月dd日');
	}
	return dateStr;
});

// 获取登录用户
Vue.prototype.getLoginUser = function() {
	return uni.getStorageSync("loginUser");
}

// 设置登录用户
Vue.prototype.setLoginUser = function(loginUser) {
	uni.setStorageSync("loginUser", loginUser);
}

// 获取当前页面路径
Vue.prototype.getCurrentPage = function(pages) {
	let page = pages[pages.length - 1];
	let route = page.route;
	let options = page.options;
	let result = "/" + route;
	if (options && Object.keys(options).length > 0) {
		result = result + "?" + commonUtils.objectToParamString(options);
	}
	return result;
}

// 点击用户头像
Vue.prototype.clickUser = function(entityType, entity) {
	let str = "";
	if (entity.hasFollowed) {
		str = "取消关注";
	} else {
		str = "关注";
	}
	uni.showActionSheet({
		itemList: [str],
		success: async res => {
			if (entity.hasFollowed) {
				await httpUtils.postJson("/follow/cancelFollow", {
					targetType: 'USER',
					targetId: entity.userId
				});
				uni.showToast({
					title: "已取消关注",
					duration: 2000
				});
			} else {
				await httpUtils.postJson("/follow/follow", {
					targetType: 'USER',
					targetId: entity.userId
				});
				uni.showToast({
					title: "已关注",
					duration: 2000
				});
			}
			let messageName = "";
			if (entityType === "MOMENT") {
				messageName = "updateMomentHasFollowed";
			}
			if (entityType === "COMMENT") {
				messageName = "updateCommentHasFollowed";
			}
			uni.$emit(messageName, {
				id: entity.id,
				hasFollowed: !entity.hasFollowed
			});
		}
	});
}

App.mpType = 'app'

const app = new Vue({
	...App
})
app.$mount()

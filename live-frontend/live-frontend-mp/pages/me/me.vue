<template>
	<view class="me-root">
		<view class="me-user-wrapper">
			<!-- 头像 -->
			<image class="me-user-avatar" mode="aspectFill" src="https://tvax1.sinaimg.cn/crop.0.0.1002.1002.180/644c31cbly8fus4o17o9wj20ru0rutbb.jpg?KID=imgbed,tva&Expires=1590514391&ssig=k4anARnCHE"></image>

			<view class="me-user-info">
				<!-- 用户名 -->
				<view class="me-user-user-name">
					<text>{{userInfo.name}}</text>
				</view>
				<!-- 个人简介 -->
				<view class="me-user-description">
					{{userInfo.description}}
				</view>
			</view>
			<view class="me-go-to">
			</view>
		</view>

		<view class="me-list">
			<navigator class="me-item me-item-margin" url="/pages/friendList/friendList">
				<image class="me-item-icon" src="../../static/icon/me-friend.png" mode="aspectFill"></image>
				<view class="me-item-title">
					好友
				</view>
				<view class="me-item-go-to">
				</view>
			</navigator>

			<navigator class="me-item" url="/pages/videoList/videoList?pageType=COLLECT_VIDEO_LIST">
				<image class="me-item-icon" src="../../static/icon/me-collection.png" mode="aspectFill"></image>
				<view class="me-item-title me-item-title-border">
					收藏
				</view>
				<view class="me-item-go-to">
				</view>
			</navigator>

			<navigator class="me-item" url="/pages/activityList/activityList?pageType=JOINED">
				<image class="me-item-icon" src="../../static/icon/discover-activity.png" mode="aspectFill"></image>
				<view class="me-item-title me-item-title-border">
					演出
				</view>
				<view class="me-item-go-to">
				</view>
			</navigator>

			<navigator class="me-item me-item-margin" url="/pages/notification/notification">
				<image class="me-item-icon" src="../../static/icon/me-notification.png" mode="aspectFill"></image>
				<view class="me-item-title">
					消息
				</view>
				<view class="me-item-go-to">
				</view>
			</navigator>

			<navigator class="me-item" url="/pages/settingList/settingList">
				<image class="me-item-icon" src="../../static/icon/me-setting.png" mode="aspectFill"></image>
				<view class="me-item-title">
					设置
				</view>
				<view class="me-item-go-to">
				</view>
			</navigator>
		</view>
	</view>
</template>

<script>
	import httpUtils from '../../common/util/httpUtils.js';

	export default {
		data() {
			return {
				userInfo: {}
			}
		},

		async onShow() {
			// 加载数据
			await this.loadUserInfo();
		},

		methods: {
			async loadUserInfo() {
				let [userInfoData] = await httpUtils.postJson("/user/getUserInfo", {});
				this.userInfo = userInfoData.body;
			},
		}
	}
</script>

<style>
	.me-root {
		background-color: #EDEDED;
		height: 100%;
	}

	/* 用户信息 */
	.me-user-wrapper {
		background-color: white;
		padding-left: 46rpx;
		padding-right: 30rpx;
		padding-bottom: 70rpx;
		display: flex;
		flex-direction: row;
		padding-top: 20rpx;
		position: relative;
		margin-bottom: 16rpx;
	}

	.me-user-avatar {
		width: 116rpx;
		height: 116rpx;
		flex-shrink: 0;
		border-radius: 10rpx;
		margin-right: 30rpx;
	}

	.me-user-info {
		width: 580rpx;
	}

	.me-user-user-name {
		margin-top: 10rpx;
		font-size: 40rpx;
		color: #1C1C1C;
		position: relative;
	}

	.me-user-gender {
		position: absolute;
		width: 28rpx;
		height: 28rpx;
		top: 12rpx;
		left: 90rpx;
	}

	.me-user-description {
		margin-top: 6rpx;
		font-size: 28rpx;
		color: #7B7B7B;
	}

	.me-go-to {
		position: absolute;
		top: 107rpx;
		right: 30rpx;
		width: 14rpx;
		height: 14rpx;
		border-top: 4rpx solid #ACACAC;
		border-right: 4rpx solid #ACACAC;
		transform: rotate(45deg);
	}

	/* 功能列表 */
	.me-item-margin {
		margin-bottom: 16rpx;
	}

	.me-item {
		position: relative;
		padding-left: 30rpx;
		display: flex;
		flex-direction: row;
		align-items: center;
		background-color: white;
		color: #111111;
	}

	.me-item:active {
		background-color: #D5D5D5;
	}

	.me-item-icon {
		width: 50rpx;
		height: 50rpx;
		margin-right: 30rpx;
	}

	.me-item-title {
		font-size: 32rpx;
		width: 640rpx;
		height: 108rpx;
		line-height: 108rpx;
	}

	.me-item-title-border {
		border-bottom: 1rpx solid #f3f3f3;
	}

	.me-item-go-to {
		position: absolute;
		top: 46rpx;
		right: 30rpx;
		width: 14rpx;
		height: 14rpx;
		border-top: 4rpx solid #ACACAC;
		border-right: 4rpx solid #ACACAC;
		transform: rotate(45deg);
	}
</style>

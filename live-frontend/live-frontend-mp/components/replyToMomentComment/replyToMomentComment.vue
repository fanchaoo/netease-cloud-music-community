<template>
	<view class="moment-root moment-root-border" @click="goToMomentDetail(notification.momentId)">
		<!-- 头像 -->
		<image class="moment-avatar moment-avatar-list-margin" :src="notification.sendUserAvatarUrl" mode="aspectFill"></image>

		<view class="moment-list-content">

			<view class="moment-user-info">
				<!-- 用户名，发布时间 -->
				<view class="moment-user-time-wrapper">
					<view class="moment-user-name-wrapper">
						<view class="moment-user-name">
							{{notification.sendUserName}}
						</view>
					</view>
					<view class="moment-create-time moment-create-time-right">
						{{notification.createTime | chineseTimeFormat}}
					</view>
				</view>
			</view>

			<!-- 动态内容 -->
			<view class="moment-text-content moment-text-content-no-margin-top">
				<u-parse v-if="notification.commentContent" :content="notification.commentContent"></u-parse>
			</view>

			<!-- 转发区域 -->
			<view class="moment-repost-area">
				<view class="moment-repost-user-text-content">
					<text class="moment-repost-text-content">
						<u-parse v-if="notification.parentCommentContent" :content="'我的评论：'+notification.parentCommentContent"></u-parse>
					</text>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import uParse from "@/components/gaoyia-parse/parse.vue";
	import httpUtils from '../../common/util/httpUtils.js';

	export default {

		components: {
			uParse
		},

		data() {
			return {

			};
		},

		props: {
			notification: {
				type: Object,
			}
		},

		methods: {

			goToMomentDetail(momentId) {
				uni.navigateTo({
					url: `../../pages/momentDetail/momentDetail?momentId=${momentId}`
				});
			},
			goToVideoDetail(momentId) {
				uni.navigateTo({
					url: `../../pages/videoDetail/videoDetail?momentId=${momentId}`
				});
			}
		}
	}
</script>

<style>
	@import url("../gaoyia-parse/parse.css");

	.moment-root {
		display: flex;
		flex-direction: row;
		margin-top: 40rpx;
		padding: 0 30rpx 30rpx 30rpx;
		font-size: 30rpx;
	}

	.moment-root-border {
		border-bottom: 1rpx solid #f3f3f3;
	}

	/* 头像 */
	.moment-avatar {
		width: 80rpx;
		height: 80rpx;
		flex-shrink: 0;
		border-radius: 50%;
		transition: transform 1s;
	}

	.moment-avatar:active {
		transform: rotateZ(1800deg)
	}

	.moment-avatar-list-margin {
		margin-right: 24rpx;
	}

	.moment-avatar-detail-margin {
		margin-right: 20rpx;
	}

	/* 用户信息 */
	.moment-user-info {
		display: flex;
		flex-direction: row;
	}

	/* 内容 */
	.moment-list-content {
		width: 590rpx;
	}

	.moment-detail-content {
		width: 690rpx;
	}

	.moment-user-time-wrapper {
		height: 80rpx;
		width: 100%;
		position: relative;
	}

	.moment-user-name-wrapper {
		display: flex;
		flex-direction: row;
		justify-content: flex-start;
		margin-top: 20rpx;
	}

	.moment-user-name {
		color: #577C9F;
		height: 44rpx;
		line-height: 44rpx;
	}

	.moment-notification-type {
		height: 44rpx;
		line-height: 42rpx;
		font-size: 26rpx;
		color: #333;
		margin-left: 10rpx;
		font-weight: 400;
	}

	.moment-create-time {
		color: #A1A1A1;
		font-size: 26rpx;
		height: 40rpx;
		line-height: 40rpx;
	}

	.moment-create-time-right {
		position: absolute;
		right: 0;
		top: 2rpx;
		font-size: 22rpx;
	}

	.moment-text-content {
		margin-top: 10rpx;
		margin-bottom: 12rpx;
		color: #333333;
		line-height: 46rpx;
	}

	.moment-text-content-no-margin-top {
		margin-top: 0;
	}

	/* 转发 */
	.moment-repost-area {
		background-color: #F4F4F4;
		border-radius: 8rpx;
		padding: 14rpx 14rpx 14rpx 14rpx;
		font-size: 28rpx;
	}

	.moment-repost-user-text-content {
		margin-bottom: 10rpx;
	}

	.moment-repost-user-name {
		color: #577C9F;
		line-height: 46rpx;
	}

	.moment-repost-text-colon {
		margin: 0 -8rpx 0 4rpx;
		line-height: 46rpx;
	}

	.moment-repost-text-content {
		color: #666;
		line-height: 46rpx;
	}

	/* 操作 */
	.moment-operation-area {
		display: flex;
		flex-direction: row;
		justify-content: space-between;
		margin: 30rpx 0 0 0;
		color: #A1A1A1;
		font-size: 25rpx;
	}

	.moment-operation-left {
		display: flex;
		flex-direction: row;
	}

	.moment-operation-item {
		display: flex;
		flex-direction: row;
		align-items: center;
		margin-right: 62rpx;
	}

	.moment-operation-like-text-active {
		color: #db301f;
	}

	.moment-operation-share {
		margin-right: 0;
		background-color: #ffffff;
		padding: 0;
	}

	.moment-operation-share image {
		margin-right: 0;
	}

	.moment-operation-share:after {
		width: 38rpx;
		height: 38rpx;

		border: none;
	}

	.moment-operation-icon {
		width: 38rpx;
		height: 38rpx;
		margin-right: 8rpx;
	}
</style>

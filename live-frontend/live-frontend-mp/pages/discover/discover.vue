<template>
	<view class="discover-root">
		<view class="discover-item" @click="goToActivityList">
			<image class="discover-item-icon" src="../../static/icon/discover-activity.png" mode="aspectFill"></image>
			<view class="discover-item-title">
				演出
			</view>
			<view class="discover-item-go-to">
			</view>
		</view>

		<view class="discover-item" @click="goToTopicList">
			<image class="discover-item-icon" src="../../static/icon/discover-topic.png" mode="aspectFill"></image>
			<view class="discover-item-title">
				话题
			</view>
			<view class="discover-item-go-to">
			</view>
		</view>

		<view class="discover-item" @click="goToFriendMomentList">
			<image class="discover-item-icon" src="../../static/icon/discover-friend-moment.png" mode="aspectFill"></image>
			<view class="discover-item-title">
				好友动态
			</view>
			<view class="discover-item-go-to">
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {

			}
		},
		methods: {

			goToActivityList() {
				uni.navigateTo({
					url: `/pages/activityList/activityList?pageType=ALL`
				});
			},
			goToFriendMomentList() {
				uni.navigateTo({
					url: `/pages/momentList/momentList?pageType=FRIEND_MOMENT_LIST`
				});
			},
			goToTopicList() {
				uni.navigateTo({
					url: `/pages/topicList/topicList`
				});
			},

		}
	}
</script>

<style>
	.discover-root {
		background-color: #EDEDED;
		height: 100%;
	}

	.discover-item {
		position: relative;
		padding: 0 30rpx;
		display: flex;
		flex-direction: row;
		align-items: center;
		height: 108rpx;
		margin-bottom: 16rpx;
		background-color: white;
		color: #111111;
	}

	.discover-item:active {
		background-color: #D5D5D5;
	}

	.discover-item-icon {
		width: 50rpx;
		height: 50rpx;
		margin-right: 30rpx;
	}

	.discover-item-title {
		font-size: 32rpx;
	}

	.discover-item-go-to {
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

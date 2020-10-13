<template>
	<view class="activity-list-root common-padding">

		<view class="activity-item" v-for="(activity,index) in activityInfo.activityList" @click="goToActivityMomentList(activity.id)">
			<image class="activity-item-image" :src="activity.posterImage" mode="aspectFill" :data-image-index="index"></image>
			<view class="activity-item-info">
				<view class="activity-item-name">
					{{activity.name}}
				</view>
				<view class="activity-item-time-address">
					{{activity.venueCity}} {{activity.venueName}}
				</view>
				<view class="activity-item-time-address">
					{{activity.venueAddress}}
				</view>
				<view class="activity-item-time-address">
					{{activity.showStartTime}}
				</view>
				<view :class="activity.hasJoined ? 'activity-item-has-joined-true' : 'activity-item-has-joined-false' " @click.stop="joinedActivity(activity)">
					{{activity.hasJoined ? '已去过' : '去过'}}
				</view>
			</view>
		</view>

	</view>
</template>

<script>
	import httpUtils from '../../common/util/httpUtils.js';

	export default {
		data() {
			return {
				// ALL，JOINED
				pageType: "",

				// 动态数据
				activityInfo: {
					pageNo: 1,
					pageSize: 20,
					activityList: []
				},

			}
		},
		async onLoad(options) {
			this.pageType = options.pageType;
			// 加载演出数据
			await this.loadActivityInfo();
		},

		async onPullDownRefresh() {
			this.activityInfo = {
				pageNo: 1,
				pageSize: 20,
				activityList: []
			};
			await this.loadActivityInfo();
			uni.stopPullDownRefresh();
		},

		async onReachBottom() {
			console.log("触底");
			await this.loadActivityInfo();
		},

		methods: {
			// 事件相关函数
			goToActivityMomentList(activityId) {
				uni.navigateTo({
					url: `/pages/momentList/momentList?pageType=ACTIVITY_MOMENT_LIST&activityId=${activityId}`
				});
			},

			// 数据相关函数
			// 加载演出数据
			async loadActivityInfo() {
				uni.showLoading({
					title: '数据加载中...'
				});

				let api = "";
				if (this.pageType === "ALL") {
					api = "/activity/queryActivity";
				} else {
					api = "/activity/queryJoinedActivity";
				}

				let [data] = await httpUtils.postJson(api, {
					pageNo: this.activityInfo.pageNo,
					pageSize: this.activityInfo.pageSize
				});
				this.activityInfo.activityList = this.activityInfo.activityList.concat(data.body);
				this.activityInfo.pageNo++;

				uni.hideLoading();
			},

			async joinedActivity(activity) {
				let [data] = await httpUtils.postJson("/activity/joinedActivity", {
					activityId: activity.id,
					hasJoined: !activity.hasJoined
				});
				activity.hasJoined = !activity.hasJoined;
			},

		}
	}
</script>

<style>
	.activity-list-root {
		padding-top: 30rpx;
	}

	.activity-item {
		display: flex;
		flex-direction: row;
		margin-bottom: 76rpx;
	}

	.activity-item-image {
		width: 260rpx;
		height: 360rpx;
		flex-shrink: 0;
		margin-right: 30rpx;
		border-radius: 6rpx;
	}

	.activity-item-info {
		width: 370rpx;
		position: relative;
	}

	.activity-item-name {
		font-size: 34rpx;
		color: #090A0C;
		margin-bottom: 20rpx;
		max-height: 146rpx;
		overflow: hidden;
	}

	.activity-item-time-address {
		height: 42rpx;
		font-size: 28rpx;
		color: #ADAEAF;
		white-space: nowrap;
		overflow: hidden;
		text-overflow: ellipsis;
	}

	.activity-item-has-joined-false {
		position: absolute;
		bottom: 0;
		left: 0;

		background-color: white;
		color: #DA261B;
		border: 2rpx solid #DA261B;

		width: 118rpx;
		height: 50rpx;
		font-size: 28rpx;
		letter-spacing: 2rpx;
		line-height: 50rpx;
		text-align: center;
		border-radius: 26rpx;
		padding: 0 6rpx;
	}

	.activity-item-has-joined-true {
		position: absolute;
		bottom: 0;
		left: 0;

		background-color: white;
		color: #999999;
		border: 2rpx solid #d8d8d8;

		width: 118rpx;
		height: 50rpx;
		font-size: 28rpx;
		letter-spacing: 2rpx;
		line-height: 50rpx;
		text-align: center;
		border-radius: 26rpx;
		padding: 0 6rpx;
	}
</style>

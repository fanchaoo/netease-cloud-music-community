<template>
	<view class="friend-list-root">
		<!-- 评论，@我，通知 -->
		<view class="tab-nav-main-wrapper">
			<view class="tab-nav-main">
				<view class="tab-nav-list" ref="tabNavList">
					<view class="tab-nav-item" :class="curTabIndex==0 ? 'tab-nav-item-active' : ''" :data-index="0" @click="clikeTabNav">
						评论
						<text class="tab-nav-item-num" v-if="countInfo.commentUnread > 0">{{countInfo.commentUnread > 99 ? 99 : countInfo.commentUnread}}</text>
					</view>
					<view class="tab-nav-item" :class="curTabIndex==1 ? 'tab-nav-item-active' : ''" :data-index="1" @click="clikeTabNav">
						@我
						<text class="tab-nav-item-num" v-if="countInfo.atUnread > 0">{{countInfo.atUnread > 99 ? 99: countInfo.atUnread}}</text>
					</view>
					<view class="tab-nav-item" :class="curTabIndex==2 ? 'tab-nav-item-active' : ''" :data-index="2" @click="clikeTabNav">
						通知
						<text class="tab-nav-item-num" v-if="countInfo.notificationUnread > 0">{{countInfo.notificationUnread > 99 ? 99 : countInfo.notificationUnread}}</text>
					</view>
				</view>
			</view>
		</view>
		<view class="line"></view>
		<swiper class="tab-content" :duration="300" :current="curTabIndex" @change="swiperChange" acceleration="false">
			<swiper-item class="tab-content-item">
				<view v-for="(notification) in commentInfo.notificationList">
					<moment v-if="notification.type === 'COMMENT_TO_MOMENT'" :moment="notification.moment" momentPageType="LIST"
					 :fromNotification="true" :notificationType="notification.type">
					</moment>
					<replyToMomentComment v-if="notification.type === 'REPLY_TO_MOMENT_COMMENT'" :notification="notification">

					</replyToMomentComment>
				</view>
			</swiper-item>
			<swiper-item class="tab-content-item">
				<moment v-for="(notification) in atInfo.notificationList" :moment="notification.moment" momentPageType="LIST"
				 :fromNotification="true" :notificationType="notification.type">
				</moment>
			</swiper-item>

			<swiper-item class="tab-content-item">

			</swiper-item>
		</swiper>
	</view>
</template>

<script>
	import httpUtils from '../../common/util/httpUtils.js';
	import moment from '../../components/moment/moment.vue';
	import replyToMomentComment from '../../components/replyToMomentComment/replyToMomentComment.vue';

	export default {
		components: {
			moment,
			replyToMomentComment
		},

		data() {
			return {
				// 当前tab索引
				curTabIndex: 0,
				// 数量
				countInfo: {
					commentUnread: 0,
					atUnread: 0,
					notificationUnread: 0
				},

				// 评论数据
				commentInfo: {
					notificationList: [],
					pageNo: 1,
					pageSize: 20
				},

				// @我数据
				atInfo: {
					notificationList: [],
					pageNo: 1,
					pageSize: 20
				},

				// 通知数据
				notificationInfo: {
					notificationList: [],
					pageNo: 1,
					pageSize: 20
				}
			}
		},

		async onLoad(options) {
			uni.showLoading({
				title: '数据加载中...'
			});
			await this.loadCountInfo();
			await this.loadCommentInfo();
			uni.hideLoading();
		},

		async onPullDownRefresh() {
			await this.loadCountInfo();
			if (this.curTabIndex === 0) {
				this.commentInfo = {
					notificationList: [],
					pageNo: 1,
					pageSize: 20
				};
				await this.loadCommentInfo();
			} else if (this.curTabIndex === 1) {
				this.atInfo = {
					notificationList: [],
					pageNo: 1,
					pageSize: 20
				};
				await this.loadAtInfo();
			} else {
				this.notificationInfo = {
					notificationList: [],
					pageNo: 1,
					pageSize: 20
				};
				await this.loadNotificationInfo();
			}
			uni.stopPullDownRefresh();
		},

		async onReachBottom() {
			if (this.curTabIndex === 0) {
				await this.loadCommentInfo();
			} else if (this.curTabIndex === 1) {
				await this.loadAtInfo();
			} else {
				await this.loadNotificationInfo();
			}
		},

		methods: {
			// 界面相关函数
			// 点击tab导航
			clikeTabNav(e) {
				let newIndex = e.currentTarget.dataset.index;
				this.updateCurTabIndex(newIndex);
			},

			// 左右滑屏
			swiperChange(e) {
				console.log("swiperChange：" + e.target.source);
				if (e.target.source !== 'touch') {
					return;
				}

				let newIndex = e.target.current;
				newIndex = newIndex > this.curTabIndex ? this.curTabIndex + 1 : this.curTabIndex - 1;
				this.updateCurTabIndex(newIndex);
			},

			async updateCurTabIndex(newIndex) {
				newIndex = parseInt(newIndex);
				(newIndex !== this.curTabIndex) && (this.curTabIndex = newIndex);
				console.log(this)
				if (this.curTabIndex === 0) {
					if (this.comentInfo.notificationList.length === 0) {
						await this.loadCommentInfo();
					}
				} else if (this.curTabIndex === 1) {
					if (this.atInfo.notificationList.length === 0) {
						await this.loadAtInfo();
					}
				} else {
					if (this.notificationInfo.notificationList.length === 0) {
						await this.loadNotificationInfo();
					}
				}
			},

			// 数据相关函数
			// 加载个数数据
			async loadCountInfo() {
				let [countData] = await httpUtils.postJson("/user/getUserInfo", {});
				this.countInfo = countData.body;
			},

			// 加载评论数据
			async loadCommentInfo() {
				let [data] = await httpUtils.postJson("/notification/queryCommentInfo", {
					pageNo: this.commentInfo.pageNo,
					pageSize: this.commentInfo.pageSize
				});
				this.commentInfo.notificationList = this.commentInfo.notificationList.concat(data.body);
				this.commentInfo.pageNo++;

				await httpUtils.postJson("/notification/clearNotificationCount", {
					category: 'COMMENT',
				});
				this.countInfo.commentUnread = 0;
			},

			// 加载@我数据
			async loadAtInfo() {
				let [data] = await httpUtils.postJson("/notification/queryAtInfo", {
					pageNo: this.atInfo.pageNo,
					pageSize: this.atInfo.pageSize
				});
				this.atInfo.notificationList = this.atInfo.notificationList.concat(data.body);
				this.atInfo.pageNo++;

				console.log("at信息", this.atInfo)

				await httpUtils.postJson("/notification/clearNotificationCount", {
					category: 'AT',
				});
				this.countInfo.atUnread = 0;
			},

			// 加载通知数据
			async loadNotificationInfo() {
				let [data] = await httpUtils.postJson("/notification/queryNotificationInfo", {
					pageNo: this.notificationInfo.pageNo,
					pageSize: this.notificationInfo.pageSize
				});
				this.notificationInfo.notificationList = this.notificationInfo.notificationList.concat(data.body);
				this.notificationInfo.pageNo++;

				await httpUtils.postJson("/notification/clearNotificationCount", {
					category: 'NOTIFICATION',
				});
				this.countInfo.notificationUnread = 0;
			}
		}
	}
</script>


<style>
	.friend-list-root {
		height: 100%;
	}

	/* 用户信息 */
	.friend-user-wrapper {
		background-color: white;
		padding-left: 46rpx;
		padding-right: 30rpx;
		display: flex;
		flex-direction: row;
		padding-top: 20rpx;
		position: relative;
		margin-bottom: 42rpx;
	}

	.friend-user-avatar {
		width: 100rpx;
		height: 100rpx;
		flex-shrink: 0;
		border-radius: 10rpx;
		margin-right: 24rpx;
		border-radius: 50%;
	}

	.friend-user-info {
		width: 580rpx;
	}

	.friend-user-user-name {
		margin-top: 10rpx;
		font-size: 32rpx;
		color: #262626;
		position: relative;
	}

	.friend-user-gender {
		margin-left: 10rpx;
		width: 28rpx;
		height: 28rpx;

	}

	.friend-user-description {
		margin-top: 6rpx;
		font-size: 24rpx;
		color: #7E7E7E;
	}

	.friend-user-has-followed-false {
		position: absolute;
		top: 48rpx;
		right: 30rpx;

		background-color: white;
		color: #DA261B;
		border: 2rpx solid #DA261B;

		width: 126rpx;
		height: 50rpx;
		font-size: 26rpx;
		letter-spacing: 2rpx;
		line-height: 50rpx;
		text-align: center;
		border-radius: 32rpx;
		padding: 0 6rpx;
	}

	.friend-user-has-followed-true {
		position: absolute;
		top: 48rpx;
		right: 30rpx;

		background-color: white;
		color: #999999;
		border: 2rpx solid #d8d8d8;

		width: 126rpx;
		height: 50rpx;
		font-size: 26rpx;
		letter-spacing: 2rpx;
		line-height: 50rpx;
		text-align: center;
		border-radius: 32rpx;
		padding: 0 6rpx;
	}

	.line {
		height: 2rpx;
		background-color: #f3f3f3;
		margin-top: -4rpx;
	}

	/* tab导航 */
	.tab-nav-main-wrapper {
		display: flex;
		flex-direction: row;
		justify-content: center;
		border-bottom: 2rpx solid #f3f3f3;
		margin-top: 10rpx;
	}

	.tab-nav-main {
		display: inline-flex;
		flex-direction: row;
		justify-content: center;
		position: relative;
	}

	.tab-nav-list {
		display: flex;
		flex-direction: row;
		justify-content: center;
		font-size: 0;
	}

	.tab-nav-item {
		box-sizing: border-box;
		font-size: 30rpx;
		padding-bottom: 20rpx;
		padding-left: 6rpx;
		padding-right: 6rpx;
		margin: 0 80rpx;
		position: relative;
	}

	.tab-nav-item-num {
		font-size: 24rpx;
		color: white;
		font-weight: 300;
		position: absolute;
		right: -32rpx;
		top: 3rpx;
		width: 40rpx;
		height: 40rpx;
		line-height: 40rpx;
		text-align: center;
		border-radius: 50%;
		background-color: #f6423d;
		transform: scale(0.7);
	}

	.tab-nav-item-active {
		font-weight: bold;
		color: #f6423d;
		border-bottom: 4rpx solid #f6423d;

	}

	.tab-nav-underline {
		position: absolute;
		bottom: -2rpx;
		left: 8rpx;
		height: 4rpx;
		border-radius: 2rpx;
		background-color: #f6423d;
	}

	.tab-nav-underline-animation {
		transition-duration: 200ms;
		transition-property: left;
	}

	/* tab内容 */
	.tab-content {
		height: 100%;
	}

	.tab-content-item {
		overflow-y: auto;
	}
</style>

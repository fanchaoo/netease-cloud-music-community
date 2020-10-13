<template>
	<view class="friend-list-root">
		<!-- 评论，转发，点赞 -->
		<view class="tab-nav-main-wrapper">
			<view class="tab-nav-main">
				<view class="tab-nav-list" ref="tabNavList">
					<view class="tab-nav-item" :class="curTabIndex==0 ? 'tab-nav-item-active' : ''" :data-index="0" @click="clikeTabNav">
						关注
						<text class="tab-nav-item-num">{{countInfo.followCount}}</text>
					</view>
					<view class="tab-nav-item" :class="curTabIndex==1 ? 'tab-nav-item-active' : ''" :data-index="1" @click="clikeTabNav">
						粉丝
						<text class="tab-nav-item-num">{{countInfo.fansCount}}</text>
					</view>
				</view>
			</view>
		</view>
		<view class="line"></view>
		<swiper class="tab-content" :duration="300" :current="curTabIndex" @change="swiperChange" acceleration="false">
			<swiper-item class="tab-content-item">
				<view class="friend-user-wrapper" v-for="(user) in followInfo.userList">
					<!-- 头像 -->
					<image class="friend-user-avatar" mode="aspectFill" :src="user.avatarUrl"></image>
					<view class="friend-user-info">
						<!-- 用户名 -->
						<view class="friend-user-user-name">
							<text>{{user.name}}</text>
							<image class="friend-user-gender" src="../../static/icon/gender-boy.png" mode="aspectFill"></image>
						</view>
						<!-- 个人简介 -->
						<view class="friend-user-description">
							暂无简介
						</view>
					</view>
					<!-- 关注按钮 -->
					<view :class="user.hasFollowed ? 'friend-user-has-followed-true' : 'friend-user-has-followed-false' " @click.stop="clickFollow(user)">
						{{user.hasFollowed ? '已关注' : '+关注'}}
					</view>
				</view>
			</swiper-item>
			<swiper-item class="tab-content-item">
				<view class="friend-user-wrapper" v-for="(user) in fansInfo.userList">
					<!-- 头像 -->
					<image class="friend-user-avatar" mode="aspectFill" :src="user.avatarUrl"></image>
					<view class="friend-user-info">
						<!-- 用户名 -->
						<view class="friend-user-user-name">
							<text>{{user.name}}</text>
							<image class="friend-user-gender" src="../../static/icon/gender-boy.png" mode="aspectFill"></image>
						</view>
						<!-- 个人简介 -->
						<view class="friend-user-description">
							暂无简介
						</view>
					</view>
					<!-- 关注按钮 -->
					<view :class="user.hasFollowed ? 'friend-user-has-followed-true' : 'friend-user-has-followed-false' " @click.stop="clickFollow(user)">
						{{user.hasFollowed ? '已关注' : '+关注'}}
					</view>
				</view>
			</swiper-item>
		</swiper>
	</view>
</template>

<script>
	import httpUtils from '../../common/util/httpUtils.js';

	export default {


		data() {
			return {
				// 当前tab索引
				curTabIndex: 0,
				// 好友数量
				countInfo: {
					followCount: 0,
					fansCount: 0
				},
				// 关注数据
				followInfo: {
					userList: [],
					pageNo: 1,
					pageSize: 20
				},
				// 粉丝数据
				fansInfo: {
					userList: [],
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
			await this.loadFollowInfo();
			await this.loadFansInfo();
			uni.hideLoading();
		},

		async onPullDownRefresh() {
			await this.loadCountInfo();

			if (this.curTabIndex === 0) {
				this.followInfo = {
					userList: [],
					pageNo: 1,
					pageSize: 20
				};
				await this.loadFollowInfo();
			} else {
				this.fansInfo = {
					userList: [],
					pageNo: 1,
					pageSize: 20
				};
				await this.loadFansInfo();
			}
			uni.stopPullDownRefresh();
		},

		async onReachBottom() {
			if (this.curTabIndex === 0) {
				await this.loadFollowInfo();
			} else {
				await this.loadFansInfo();
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

			updateCurTabIndex(newIndex) {
				newIndex = parseInt(newIndex);
				(newIndex !== this.curTabIndex) && (this.curTabIndex = newIndex);
			},

			// 数据相关函数
			// 加载个数数据
			async loadCountInfo() {
				let [countData] = await httpUtils.postJson("/follow/countFriend", {});
				this.countInfo = countData.body;
			},
			// 加载关注数据
			async loadFollowInfo() {
				let [followData] = await httpUtils.postJson("/follow/queryFriend", {
					friendType: 'FOLLOW',
					pageNo: this.followInfo.pageNo,
					pageSize: this.followInfo.pageSize
				});
				this.followInfo.userList = this.followInfo.userList.concat(followData.body);
				this.followInfo.pageNo++;
			},
			// 加载粉丝数据
			async loadFansInfo() {
				let [fansData] = await httpUtils.postJson("/follow/queryFriend", {
					friendType: 'FANS',
					pageNo: this.fansInfo.pageNo,
					pageSize: this.fansInfo.pageSize
				});
				this.fansInfo.userList = this.fansInfo.userList.concat(fansData.body);
				this.fansInfo.pageNo++;
			},
			// 点赞
			async clickFollow(user) {
				if (user.hasFollowed) {
					await httpUtils.postJson("/follow/cancelFollow", {
						targetType: 'USER',
						targetId: user.id
					});
				} else {
					await httpUtils.postJson("/follow/follow", {
						targetType: 'USER',
						targetId: user.id
					});
				}
				user.hasFollowed = !user.hasFollowed;
			},
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
		margin: 0 120rpx;
		position: relative;
	}

	.tab-nav-item-num {
		font-size: 20rpx;
		color: #9A9A9A;
		position: absolute;
		right: -20rpx;
		top: 7rpx;
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

<template>
	<view class="root-padding">

		<view class="login-mask" @click="login"></view>

		<!-- 搜索框 -->
		<view class="search">
			<image src="../../static/icon/search.png" class="search-icon"></image>
			<input placeholder="搜索喜欢的内容" placeholder-class="search-input-placeholder" maxlength="20" class="search-input"
			 confirm-type="search" @confirm="searchMe" />
		</view>

		<!-- 轮播图 -->
		<swiper :indicator-dots="true" :autoplay="true" class="swiper" circular="true">
			<swiper-item v-for="swiper in swiperList">
				<navigator open-type="navigate" :url="'../video?trailerId=' + swiper.movieId">
					<image :src="swiper.coverUrl" mode="aspectFill" class="swiper-image"></image>
				</navigator>
			</swiper-item>
		</swiper>

		<!-- 板块 -->
		<view class="block">
			<view class="block-title">
				最近热门
			</view>
			<view class="block-item-list">
				<view class="block-item">
					<image class="block-item-image" :src="swiperList[0].coverUrl" mode="aspectFill"></image>
					<view class="block-item-title">
						我是余欢水
					</view>
					<view class="block-item-desc">
						官方吐槽不止
					</view>
				</view>
				<view class="block-item">
					<image class="block-item-image" :src="swiperList[0].coverUrl" mode="aspectFill"></image>
					<view class="block-item-title">
						我是余欢水
					</view>
					<view class="block-item-desc">
						官方吐槽不止
					</view>
				</view>
				<view class="block-item">
					<image class="block-item-image" :src="swiperList[0].coverUrl" mode="aspectFill"></image>
					<view class="block-item-title">
						我是余欢水
					</view>
					<view class="block-item-desc">
						官方吐槽不止
					</view>
				</view>
				<view class="block-item">
					<image class="block-item-image" :src="swiperList[0].coverUrl" mode="aspectFill"></image>
					<view class="block-item-title">
						我是余欢水
					</view>
					<view class="block-item-desc">
						官方吐槽不止
					</view>
				</view>
				<view class="block-item">
					<image class="block-item-image" :src="swiperList[0].coverUrl" mode="aspectFill"></image>
					<view class="block-item-title">
						我是余欢水
					</view>
					<view class="block-item-desc">
						官方吐槽不止
					</view>
				</view>
				<view class="block-item">
					<image class="block-item-image" :src="swiperList[0].coverUrl" mode="aspectFill"></image>
					<view class="block-item-title">
						我是余欢水
					</view>
					<view class="block-item-desc">
						官方吐槽不止
					</view>
				</view>
				<view class="block-item">
					<image class="block-item-image" :src="swiperList[0].coverUrl" mode="aspectFill"></image>
					<view class="block-item-title">
						我是余欢水
					</view>
					<view class="block-item-desc">
						官方吐槽不止
					</view>
				</view>
				<view class="block-item">
					<image class="block-item-image" :src="swiperList[0].coverUrl" mode="aspectFill"></image>
					<view class="block-item-title">
						我是余欢水
					</view>
					<view class="block-item-desc">
						官方吐槽不止
					</view>
				</view>
				<view class="block-item">
					<image class="block-item-image" :src="swiperList[0].coverUrl" mode="aspectFill"></image>
					<view class="block-item-title">
						我是余欢水
					</view>
					<view class="block-item-desc">
						官方吐槽不止
					</view>
				</view>
				<view class="block-item">
					<image class="block-item-image" :src="swiperList[0].coverUrl" mode="aspectFill"></image>
					<view class="block-item-title">
						我是余欢水
					</view>
					<view class="block-item-desc">
						官方吐槽不止
					</view>
				</view>
			</view>
		</view>
		<view class="block">
			<view class="block-title">
				最新发布
			</view>
			<view class="block-item-list">
				<view class="block-item">
					<image class="block-item-image" :src="swiperList[0].coverUrl" mode="aspectFill"></image>
					<view class="block-item-title">
						我是余欢水
					</view>
					<view class="block-item-desc">
						官方吐槽不止
					</view>
				</view>
				<view class="block-item">
					<image class="block-item-image" :src="swiperList[0].coverUrl" mode="aspectFill"></image>
					<view class="block-item-title">
						我是余欢水
					</view>
					<view class="block-item-desc">
						官方吐槽不止
					</view>
				</view>
				<view class="block-item">
					<image class="block-item-image" :src="swiperList[0].coverUrl" mode="aspectFill"></image>
					<view class="block-item-title">
						我是余欢水
					</view>
					<view class="block-item-desc">
						官方吐槽不止
					</view>
				</view>
				<view class="block-item">
					<image class="block-item-image" :src="swiperList[0].coverUrl" mode="aspectFill"></image>
					<view class="block-item-title">
						我是余欢水
					</view>
					<view class="block-item-desc">
						官方吐槽不止
					</view>
				</view>
				<view class="block-item">
					<image class="block-item-image" :src="swiperList[0].coverUrl" mode="aspectFill"></image>
					<view class="block-item-title">
						我是余欢水
					</view>
					<view class="block-item-desc">
						官方吐槽不止
					</view>
				</view>
				<view class="block-item">
					<image class="block-item-image" :src="swiperList[0].coverUrl" mode="aspectFill"></image>
					<view class="block-item-title">
						我是余欢水
					</view>
					<view class="block-item-desc">
						官方吐槽不止
					</view>
				</view>
				<view class="block-item">
					<image class="block-item-image" :src="swiperList[0].coverUrl" mode="aspectFill"></image>
					<view class="block-item-title">
						我是余欢水
					</view>
					<view class="block-item-desc">
						官方吐槽不止
					</view>
				</view>
				<view class="block-item">
					<image class="block-item-image" :src="swiperList[0].coverUrl" mode="aspectFill"></image>
					<view class="block-item-title">
						我是余欢水
					</view>
					<view class="block-item-desc">
						官方吐槽不止
					</view>
				</view>
				<view class="block-item">
					<image class="block-item-image" :src="swiperList[0].coverUrl" mode="aspectFill"></image>
					<view class="block-item-title">
						我是余欢水
					</view>
					<view class="block-item-desc">
						官方吐槽不止
					</view>
				</view>
				<view class="block-item">
					<image class="block-item-image" :src="swiperList[0].coverUrl" mode="aspectFill"></image>
					<view class="block-item-title">
						我是余欢水
					</view>
					<view class="block-item-desc">
						官方吐槽不止
					</view>
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
				swiperList: []
			}
		},

		async onLoad() {
			// 判断是否登录
			let currentPage = this.getCurrentPage(getCurrentPages());
			let [checkLoginTokenData, checkLoginTokenDataError] = await httpUtils.postJson("/login/checkLoginToken", {},
				currentPage);
			if (checkLoginTokenDataError === "请重新登录") {
				currentPage = encodeURIComponent(currentPage);
				uni.redirectTo({
					url: `/pages/login/login?fromPage=${currentPage}`
				});
				return;
			}

			// 轮播图
			let [data] = await httpUtils.postJson("/index/queryIndexSwiper");
			this.swiperList = data.body;
		},

		async onPullDownRefresh() {
			this.swiperList = [];
			// 轮播图
			let [data] = await httpUtils.postJson("/index/queryIndexSwiper");
			this.swiperList = data.body;
			uni.stopPullDownRefresh();
		},

		async onReachBottom() {
			console.log("触底");
		},

		async onShareAppMessage(res) {
			await httpUtils.postJson("/share/share", {
				pageType: "INDEX"
			});

			let loginUser = uni.getStorageSync("loginUser");
			return {
				title: getApp().globalData.appName,
				path: `/pages/index/index?shareUserId=${loginUser.userId}`
			}
		},

		methods: {
			login() {
				console.log("login");
			}

		}
	}
</script>

<style>
	/* 搜索框 */
	.search {
		display: flex;
		flex-direction: row;
		margin: 8rpx 0 12rpx 0;
	}

	.search-icon {
		width: 46rpx;
		height: 46rpx;
		margin-left: -8rpx;
	}

	.search-input {
		flex: 1;
		padding-left: 20rpx;
	}

	.search-input:focus {
		padding-left: 20rpx;
	}

	/* 轮播图 */
	.swiper {
		width: 690rpx;
		height: 388rpx;
		border-radius: 6rpx;
		margin-bottom: 32rpx;
		overflow: hidden;
	}

	.swiper-image {
		width: 690rpx;
		height: 388rpx;
	}

	/* 板块 */
	.block {
		margin-bottom: 22rpx;
	}

	.block-title {
		font-size: 34rpx;
		font-weight: bold;
		margin: 0 0 32rpx 0;
	}

	.block-item-list {
		display: flex;
		flex-direction: row;
		justify-content: space-between;
		flex-wrap: wrap;
	}

	.block-item {
		width: 337rpx;
		border-radius: 2rpx;
		margin: 0 0 22rpx 0;
	}

	.block-item-image {
		width: 337rpx;
		height: 190rpx;
		border-radius: 2rpx;
	}

	.block-item-title {
		font-size: 29rpx;
		color: #151516;
		line-height: 52rpx;
	}

	.block-item-desc {
		font-size: 24rpx;
		color: #9e9ca5;
	}
</style>

<template>
	<view class="repost-user-root">

		<view class="repost-user-wrapper" :style="index === 0 ? 'padding-top:30rpx' : ''">
			<!-- 头像 -->
			<image class="repost-user-avatar" mode="aspectFill" :src="repostUser.userAvatarUrl"></image>

			<view class="repost-user-info">
				<!-- 用户名 -->
				<view class="repost-user-user-name">
					{{repostUser.userName}}
				</view>

				<!-- 发布时间 -->
				<view class="repost-user-create-time">
					{{repostUser.repostTime}}
				</view>

				<!-- 评论内容 -->
				<view class="repost-user-content">
					<u-parse :content="repostUser.textContent"></u-parse>
				</view>
			</view>
		</view>

		<view class="repost-user-occupy-position-wrapper">
			<view class="repost-user-occupy-position"></view>
		</view>

	</view>
</template>

<script>
	import uParse from "@/components/gaoyia-parse/parse.vue";
	import httpUtils from '../../common/util/httpUtils.js';

	export default {

		components: {
			uParse,
		},

		data() {
			return {

			};
		},
		props: {
			repostUser: {
				type: Object,
			},
			index: {
				type: Number
			}
		},
		methods: {

		}
	}
</script>

<style>
	@import url("../gaoyia-parse/parse.css");

	.repost-user-root {}

	.repost-user-wrapper {
		padding: 0 30rpx;
		display: flex;
		flex-direction: row;
		padding-top: 20rpx;
		position: relative;
	}

	.repost-user-occupy-position-wrapper {
		display: flex;
		flex-direction: row;
		justify-content: flex-end;
		width: 100%;
	}

	.repost-user-occupy-position {
		padding-bottom: 30rpx;
		width: 640rpx;
		border-bottom: 1rpx solid #f3f3f3;
	}

	.repost-user-avatar {
		width: 60rpx;
		height: 60rpx;
		flex-shrink: 0;
		border-radius: 50%;
		margin-right: 20rpx;
	}

	.repost-user-info {
		width: 580rpx;
	}

	.repost-user-user-name {
		font-size: 26rpx;
		color: #555;
	}

	.repost-user-create-time {
		-webkit-transform: scale(0.8);
		font-size: 28rpx;
		margin-left: -66rpx;
		margin-top: -8rpx;
	}

	.repost-user-content {
		margin-top: 8rpx;
		font-size: 28rpx;
		line-height: 36rpx;
	}
</style>

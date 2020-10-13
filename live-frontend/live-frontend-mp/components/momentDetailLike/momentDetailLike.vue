<template>
	<view class="like-user-root">

		<view class="like-user-wrapper" :style="index === 0 ? 'padding-top:30rpx' : ''">
			<!-- 头像 -->
			<image class="like-user-avatar" mode="aspectFill" :src="likeUser.avatarUrl"></image>

			<view class="like-user-info">
				<!-- 用户名 -->
				<view class="like-user-user-name">
					{{likeUser.name}}
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
			uParse,
		},

		data() {
			return {

			};
		},
		props: {
			likeUser: {
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

	.like-user-root {}

	.like-user-wrapper {
		padding: 0 30rpx;
		display: flex;
		flex-direction: row;
		padding-top: 20rpx;
		position: relative;
	}

	.like-user-occupy-position-wrapper {
		display: flex;
		flex-direction: row;
		justify-content: flex-end;
		width: 100%;
	}

	.like-user-avatar {
		width: 60rpx;
		height: 60rpx;
		flex-shrink: 0;
		border-radius: 50%;
		margin-right: 20rpx;
	}

	.like-user-info {
		width: 580rpx;

	}

	.like-user-user-name {
		font-size: 28rpx;
		line-height: 60rpx;
		color: #333;
	}
</style>

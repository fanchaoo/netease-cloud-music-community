<template>
	<view class="comment-list">
		<view class="comment-title">
			{{title}}
		</view>
		<comment v-for="(c) in commentList" :comment="c" :parentId="parentId">
		</comment>
	</view>
</template>

<script>
	import httpUtils from '../../common/util/httpUtils.js';
	import comment from '../../components/comment/comment.vue';

	export default {

		components: {
			comment
		},

		data() {
			return {

			};
		},
		props: {
			commentList: {
				type: Array,
			},
			parentId: {
				type: Number,
			},
			title: {
				String
			}

		}
	}
</script>

<style>
	.comment-list{
		padding-bottom: 90rpx;
	}
	.comment-title {
		padding: 0 30rpx;
		font-size: 30rpx;
		font-weight: bold;
		margin-top: 30rpx;
		margin-bottom: 10rpx;
		color: #444;
	}
</style>

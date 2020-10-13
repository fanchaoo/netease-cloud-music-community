<template>
	<view class="comment-root" @click="goToReplyComment">

		<view class="comment-wrapper">
			<!-- 头像 -->
			<image class="comment-avatar" mode="aspectFill" :src="comment.userAvatarUrl" @click.stop="clickUser('COMMENT',comment)"></image>

			<view class="comment-info">
				<!-- 用户名 -->
				<view class="comment-user-name">
					{{comment.userName}}
				</view>

				<!-- 发布时间 -->
				<view class="comment-create-time">
					{{comment.createTime}}
				</view>

				<!-- 评论内容 -->
				<view class="comment-content">
					<u-parse :content="comment.content"></u-parse>
				</view>

				<!-- 回复个数 -->
				<view class="comment-reply-count" v-if="!single && parentId === 0 && comment.replyCount > 0" @click="goToChildrenComment">
					{{comment.replyCount}}条回复 >
				</view>

				<!-- 回复哪个评论 -->
				<view class="comment-reply-to" v-if="!single && parentId !== 0 && comment.replyToId">
					<text class="comment-reply-to-user-name">@{{comment.replyToComment.userName}}</text>
					<text class="comment-reply-to-content">：{{comment.replyToComment.content}}</text>
				</view>

				<!-- 点赞 -->
				<view class="comment-like-wrapper" @click.stop="clickLike">
					<view>
						<text v-if="comment.likeCount > 0" :class="comment.hasLiked ? 'comment-like-text-active' : ''">{{comment.likeCount}}</text>
					</view>
					<image class="comment-like-image" mode="aspectFill" :src="comment.hasLiked ? '../../static/icon/moment-operation-like-active.png' : '../../static/icon/moment-operation-like.png'"></image>
				</view>
			</view>
		</view>

		<view class="comment-occupy-position-wrapper" v-if="!single">
			<view class="comment-occupy-position"></view>
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
			comment: {
				type: Object,
			},
			parentId: {
				type: Number,
			},
			single: {
				type: Boolean
			}
		},
		methods: {
			goToReplyComment() {
				uni.$emit("goToReplyComment", this.comment);
				console.log("emit goToReplyComment");
			},
			goToChildrenComment() {
				uni.navigateTo({
					url: `../childrenComment/childrenComment?momentId=${this.comment.momentId}&parentId=${this.comment.id}`
				});
			},

			// 数据相关函数
			async clickLike() {
				if (this.comment.hasLiked) {
					await httpUtils.postJson("/like/cancelLike", {
						targetType: 'COMMENT',
						targetId: this.comment.id
					});
					this.comment.likeCount--;
				} else {
					await httpUtils.postJson("/like/like", {
						targetType: 'COMMENT',
						targetId: this.comment.id
					});
					this.comment.likeCount++;
				}
				this.comment.hasLiked = !this.comment.hasLiked;
				uni.$emit('updateCommentLikeCount', {
					id: this.comment.id,
					likeCount: this.comment.likeCount
				});
				uni.$emit('updateCommentHasLiked', {
					id: this.comment.id,
					hasLiked: this.comment.hasLiked
				});
				console.log(this.comment.likeCount, this.comment.hasLiked);
			}
		}
	}
</script>

<style>
	@import url("../gaoyia-parse/parse.css");

	.comment-root {}

	.comment-wrapper {
		padding: 0 30rpx;
		display: flex;
		flex-direction: row;
		padding-top: 20rpx;
		position: relative;
	}

	.comment-occupy-position-wrapper {
		display: flex;
		flex-direction: row;
		justify-content: flex-end;
		width: 100%;
	}

	.comment-occupy-position {
		padding-bottom: 30rpx;
		width: 640rpx;
		border-bottom: 1rpx solid #f3f3f3;
	}

	.comment-avatar {
		width: 60rpx;
		height: 60rpx;
		flex-shrink: 0;
		border-radius: 50%;
		margin-right: 20rpx;
	}

	.comment-info {
		width: 580rpx;
	}

	.comment-user-name {
		font-size: 26rpx;
		color: #555;
	}

	.comment-create-time {
		-webkit-transform: scale(0.8);
		font-size: 28rpx;
		margin-left: -66rpx;
		margin-top: -8rpx;
	}

	.comment-content {
		margin-top: 10rpx;
		font-size: 28rpx;
		line-height: 36rpx;
	}

	.comment-reply-count {
		display: inline-flex;
		font-size: 26rpx;
		color: #577C9F;
	}

	.comment-reply-count:active {
		color: #377C9F;
	}

	.comment-reply-to {
		padding-top: 6rpx;
		padding-bottom: 6rpx;
		padding-left: 12rpx;
		border-left: 4rpx solid #f2f2f2;
		font-size: 26rpx;
	}

	.comment-reply-to-user-name {
		color: #577C9F;
	}

	.comment-like-wrapper {
		position: absolute;
		right: 30rpx;
		top: 20rpx;
		font-size: 24rpx;
		display: flex;
		flex-direction: row;
	}

	.comment-like-image {
		width: 34rpx;
		height: 34rpx;
		flex-shrink: 0;
		margin-left: 8rpx;
	}

	.comment-like-text-active {
		color: #db301f;
	}
</style>

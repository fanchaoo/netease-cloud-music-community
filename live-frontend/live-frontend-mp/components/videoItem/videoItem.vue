<template>
	<view class="video-item-root" @click="goToVideoDetail(moment.id)">

		<!-- 视频区域 -->
		<view class="video-item-cover">
			<momentVideo :moment="moment"></momentVideo>
		</view>

		<view class="video-item-title">
			{{moment.video.title}}
		</view>

		<!-- 操作区域 -->
		<view class="video-item-operation-area">
			<view class="video-item-operation-left">
				<!-- 头像 -->
				<image class="video-item-user-avatar" :src="moment.userAvatarUrl" mode="aspectFill" @click.stop="clickUser('MOMENT',moment)"></image>

				<view class="video-item-user-name">
					{{moment.userName}}
				</view>
			</view>

			<view class="video-item-operation-right">
				<view class="video-item-operation-like video-item-operation-item" @click.stop="clickLike">
					<image :src="moment.hasLiked ? '../../static/icon/moment-operation-like-active.png' : '../../static/icon/moment-operation-like.png'"
					 class="video-item-operation-icon"></image>
					<text v-if="moment.likeCount > 0" :class="moment.hasLiked ? 'video-item-operation-like-text-active' : ''">{{moment.likeCount}}</text>
				</view>
				<view class="video-item-operation-comment video-item-operation-item" @click.stop="goToVideoDetail(moment.id)">
					<image src="../../static/icon/moment-operation-comment.png" class="video-item-operation-icon"></image>
					<text v-if="moment.commentCount > 0">{{moment.commentCount}}</text>
				</view>
				<button class="video-item-operation-share video-item-operation-item" open-type="share" @click.stop="clickShare">
					<image src="../../static/icon/moment-operation-share.png" class="video-item-operation-icon"></image>
				</button>
			</view>
		</view>

		<view class="video-item-occupy-position">
		</view>
	</view>
</template>

<script>
	import uParse from "@/components/gaoyia-parse/parse.vue";
	import httpUtils from '../../common/util/httpUtils.js';
	import momentVideo from '../momentVideo/momentVideo.vue';
	import momentImage from '../momentImage/momentImage.vue';

	export default {

		components: {
			uParse,
			momentVideo,
			momentImage
		},

		data() {
			return {

			};
		},
		props: {
			moment: {
				type: Object,
			}
		},
		methods: {
			// 转发
			clickRepost() {
				let repostMomentId;
				if (this.moment.repostMomentId === 0) {
					repostMomentId = this.moment.id;
				} else {
					repostMomentId = this.moment.repostMomentId;
				}
				uni.navigateTo({
					url: `../publishMoment/publishMoment?publishType=REPOST&repostMomentId=${repostMomentId}`
				});
			},

			// 点赞
			async clickLike() {
				if (this.moment.hasLiked) {
					await httpUtils.postJson("/like/cancelLike", {
						targetType: 'MOMENT',
						targetId: this.moment.id
					});
					this.moment.likeCount--;
				} else {
					await httpUtils.postJson("/like/like", {
						targetType: 'MOMENT',
						targetId: this.moment.id
					});
					this.moment.likeCount++;
				}
				this.moment.hasLiked = !this.moment.hasLiked;
				this.$emit('updateMomentLikeCount', this.moment.likeCount);
				this.$emit('updateMomentHasLiked', this.moment.hasLiked);
				console.log(this.moment.likeCount, this.moment.hasLiked);
			},

			// 分享
			async clickShare(e) {
				console.log(e)
				await httpUtils.postJson("/share/share", {
					pageType: "VIDEO_DETAIL",
					momentId: this.moment.id
				});
				this.moment.shareCount++;
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

	.video-item-root {
		margin-top: 40rpx;
		padding: 0 30rpx;
		font-size: 30rpx;
	}

	.video-item-root-border {
		border-bottom: 1rpx solid #f3f3f3;
	}

	.video-item-cover {
		width: 690rpx;
		border-radius: 10rpx;
		overflow: hidden;
	}

	.video-item-title {
		padding-top: 32rpx;
		padding-bottom: 30rpx;
		border-bottom: 1rpx solid #f3f3f3;
		font-size: 30rpx;
		color: #333;
	}

	/* 操作 */
	.video-item-operation-area {
		display: flex;
		flex-direction: row;
		justify-content: space-between;
		margin: 20rpx 0 20rpx 0;
		color: #A1A1A1;
		font-size: 25rpx;
	}

	/* 头像和用户名 */
	.video-item-operation-left {
		display: flex;
		flex-direction: row;
		align-items: center;
	}

	.video-item-user-avatar {
		width: 60rpx;
		height: 60rpx;
		flex-shrink: 0;
		border-radius: 50%;
		transition: transform 2s;
	}

	.video-item-user-name {
		font-size: 30rpx;
		color: #333;
		line-height: 32rpx;
		margin-left: 14rpx;
	}

	/* 点赞，评论，转发 */
	.video-item-operation-right {
		display: flex;
		flex-direction: row;
	}

	.video-item-operation-item {
		display: flex;
		flex-direction: row;
		align-items: center;
		margin-right: 36rpx;
	}

	.video-item-operation-like-text-active {
		color: #db301f;
	}

	.video-item-operation-share {
		margin-right: 0;
		margin-left: 8rpx;
		background-color: #ffffff;
		padding: 0;
	}

	.video-item-operation-share image {
		margin-right: 0;
	}

	.video-item-operation-share:after {
		width: 40rpx;
		height: 40rpx;
		border: none;
	}

	.video-item-operation-icon {
		width: 40rpx;
		height: 40rpx;
		margin-right: 8rpx;
	}

	.video-item-occupy-position {
		background-color: #F3F3F4;
		height: 12rpx;
		margin-top: 14rpx;
		margin-left: -30rpx;
		margin-right: -30rpx;
	}
</style>

<template>
	<view class="moment-root" :class="momentPageType === 'LIST' ? 'moment-root-border' : '' " @click="momentPageType === 'LIST' && goToMomentDetail(moment.id)">
		<!-- 头像 -->
		<image class="moment-avatar" :class="momentPageType === 'LIST' ? 'moment-avatar-list-margin' : '' " :src="moment.userAvatarUrl"
		 mode="aspectFill" v-if="momentPageType === 'LIST'" @click.stop="clickUser('MOMENT',moment)"></image>

		<view :class="momentPageType === 'LIST' ? 'moment-list-content' : 'moment-detail-content' ">

			<view class="moment-user-info">
				<!-- 头像 -->
				<image class="moment-avatar" :class="momentPageType === 'DETAIL' ? 'moment-avatar-detail-margin' : '' " :src="moment.userAvatarUrl"
				 mode="aspectFill" v-if="momentPageType === 'DETAIL' " @click.stop="clickUser('MOMENT',moment)"></image>

				<!-- 用户名，发布时间 -->
				<view class="moment-user-time-wrapper">
					<view class="moment-user-name-wrapper">
						<view class="moment-user-name">
							{{moment.userName}}
						</view>
						<view class="moment-notification-type">
							{{notificationTypeDesc}}
						</view>
					</view>
					<view class="moment-create-time" :class="fromNotification ? 'moment-create-time-right' : '' ">
						{{moment.createTime | chineseTimeFormat}}
					</view>
				</view>
			</view>

			<!-- 动态内容 -->
			<view class="moment-text-content" :class="fromNotification ? 'moment-text-content-no-margin-top' : '' ">
				<u-parse v-if="moment.textContent" :content="moment.textContent"></u-parse>
			</view>

			<!-- 转发区域 -->
			<view class="moment-repost-area" v-if="moment.repostMomentId !== 0" @click.stop="goToMomentDetail(moment.repostMomentId)">
				<view class="moment-repost-user-text-content">
					<text class="moment-repost-user-name">
						@{{moment.repostedMoment.userName}}
					</text>
					<text class="moment-repost-text-colon">：</text>
					<text class="moment-repost-text-content">
						<u-parse v-if="moment.repostedMoment.textContent" :content="moment.repostedMoment.textContent"></u-parse>
					</text>
				</view>
				<!-- 转发视频区域 -->
				<view class="moment-video-area" v-if="moment.type === 'VIDEO'" @click.stop="goToMomentDetail(moment.repostedMoment.id)">
					<momentVideo :moment="moment.repostedMoment"></momentVideo>
				</view>
				<!-- 转发图片区域 -->
				<view class="moment-image-area" v-if="moment.type === 'IMAGE' ">
					<momentImage :moment="moment.repostedMoment"></momentImage>
				</view>
			</view>

			<!-- 视频区域 -->
			<view class="moment-video-area" v-if="moment.repostMomentId === 0 && moment.type === 'VIDEO'" @click.stop="momentPageType === 'LIST' && goToMomentDetail(moment.id) || momentPageType === 'DETAIL' && goToVideoDetail(moment.id)">
				<momentVideo :moment="moment"></momentVideo>
			</view>
			<!-- 图片区域 -->
			<view class="moment-image-area" v-if="moment.repostMomentId === 0 && moment.type === 'IMAGE' ">
				<momentImage :moment="moment"></momentImage>
			</view>

			<!-- 操作区域 -->
			<view class="moment-operation-area" v-if="momentPageType === 'LIST' && !fromNotification">
				<view class="moment-operation-left">
					<view class="moment-operation-repost moment-operation-item" @click.stop="clickRepost">
						<image src="../../static/icon/moment-operation-repost.png" class="moment-operation-icon"></image>
						<text v-if="moment.repostCount === 0">转发</text>
						<text v-if="moment.repostCount > 0">{{moment.repostCount}}</text>
					</view>
					<view class="moment-operation-comment moment-operation-item" @click.stop="goToMomentDetail(moment.id)">
						<image src="../../static/icon/moment-operation-comment.png" class="moment-operation-icon"></image>
						<text v-if="moment.commentCount === 0">评论</text>
						<text v-if="moment.commentCount > 0">{{moment.commentCount}}</text>
					</view>
					<view class="moment-operation-like moment-operation-item" @click.stop="clickLike">
						<image :src="moment.hasLiked ? '../../static/icon/moment-operation-like-active.png' : '../../static/icon/moment-operation-like.png'"
						 class="moment-operation-icon"></image>
						<text v-if="moment.likeCount === 0" :class="moment.hasLiked ? 'moment-operation-like-text-active' : ''">赞</text>
						<text v-if="moment.likeCount > 0" :class="moment.hasLiked ? 'moment-operation-like-text-active' : ''">{{moment.likeCount}}</text>
					</view>
				</view>
				<button class="moment-operation-share moment-operation-item" open-type="share" @click.stop="clickShare">
					<image src="../../static/icon/moment-operation-share.png" class="moment-operation-icon"></image>
				</button>
			</view>

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
			},
			momentPageType: {
				type: String
			},
			fromNotification: {
				type: Boolean
			},
			notificationType: {
				type: String
			}
		},

		computed: {
			notificationTypeDesc() {
				if (this.notificationType === "REPOST") {
					return "转发：";
				}
				if (this.notificationType === "AT_ON_COMMENT") {
					return "评论：";
				}
				if (this.notificationType === "AT_ON_CHILDREN_COMMENT") {
					return "评论：";
				}
				return "";
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
					pageType: "MOMENT_DETAIL",
					momentId: this.moment.id
				});
				this.moment.shareCount++;
			},
			goToMomentDetail(momentId) {
				uni.navigateTo({
					url: `../../pages/momentDetail/momentDetail?momentId=${momentId}`
				});
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
	@import url("moment.css");
	@import url("../gaoyia-parse/parse.css");
</style>

<template>
	<view>
		<!-- 播放器 -->
		<view class="video-detail-player-wrapper">
			<video class="video-detail-player" :src="moment.video.url" controls></video>
		</view>

		<!-- 标题，描述 -->
		<view class="video-detail-head common-padding">
			<view class="video-detail-title">
				{{moment.video.title}}
			</view>
			<view class="video-detail-view-count">
				{{moment.viewCount}}次观看
			</view>
			<view class="video-detail-tag">
			</view>
			<view class="video-detail-create-time" v-show="textContentShow">
				{{moment.createTime | dateFormat('yyyy-MM-dd')}} 发布
			</view>
			<view class="video-detail-text-content" v-show="textContentShow">
				{{moment.textContent}}
			</view>
			<view class="view-detail-toggle-wrapper">
				<view :class="textContentShow ? 'view-detail-toggle-text-content-up' : 'view-detail-toggle-text-content-down' "
				 @click="toggleTextContent">
				</view>
			</view>
		</view>

		<!-- 操作：点赞，收藏，评论，分享 -->
		<view class="video-detail-operation-area">
			<view class="video-detail-operation-item" @click="clickLike">
				<image :src="moment.hasLiked ? '../../static/icon/video-like-active.png' : '../../static/icon/video-like.png' "
				 class="video-detail-operation-icon"></image>
				<view class="video-detail-operation-count" :class="moment.hasLiked ? 'active-color' : '' ">{{moment.likeCount}}</view>
			</view>
			<view class="video-detail-operation-item" @click="clickCollect">
				<image :src="moment.video.hasCollected ? '../../static/icon/video-collect-active.png' : '../../static/icon/video-collect.png' "
				 class="video-detail-operation-icon"></image>
				<view class="video-detail-operation-count" :class="moment.video.hasCollected ? 'active-color' : '' ">{{moment.video.collectCount}}</view>
			</view>
			<view class="video-detail-operation-item">
				<image src="../../static/icon/video-comment.png" class="video-detail-operation-icon"></image>
				<view class="video-detail-operation-count">{{moment.commentCount}}</view>
			</view>
			<view class="video-detail-operation-item">
				<image src="../../static/icon/video-share.png" class="video-detail-operation-icon"></image>
				<view class="video-detail-operation-count">{{moment.shareCount}}</view>
			</view>
		</view>

		<!-- 用户信息 -->
		<view class="video-detail-user-info common-padding">
			<image class="video-detail-user-avatar" :src="moment.userAvatarUrl" mode="aspectFill" @click.stop="clickUser('MOMENT',moment)"></image>
			<view class="video-detail-user-name">
				{{moment.userName}}
			</view>
			<view :class="moment.hasFollowed ? 'video-detail-user-unfollow' : 'video-detail-user-follow' " @click="followUser">
				{{moment.hasFollowed ? '已关注' : '+关注'}}
			</view>
		</view>

		<!-- 评论 -->
		<commentList :commentList="commentInfo.commentList" :parentId="parseInt('0')" title="最新评论"></commentList>

		<!-- 发评论 -->
		<publishComment :parentComment="parentComment" :momentId="moment.id"></publishComment>
	</view>
</template>

<script>
	import httpUtils from '../../common/util/httpUtils.js';
	import commentList from '../../components/commentList/commentList.vue';
	import publishComment from '../../components/publishComment/publishComment.vue';

	export default {

		comments: {
			commentList,
			publishComment,
		},

		data() {
			return {
				// 文本展示
				textContentShow: false,
				// 发评论
				parentComment: null,

				// moment数据
				momentId: 0,
				moment: {},

				// comment数据
				commentInfo: {
					commentList: [],
					pageNo: 1,
					pageSize: 20
				},
			}
		},

		async onLoad(options) {
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

			this.momentId = options.momentId;
			uni.showLoading({
				title: '数据加载中...'
			});
			// 加载moment数据
			let [momentDetailData] = await httpUtils.postJson("/moment/getMomentDetail", {
				momentId: options.momentId
			});
			this.moment = momentDetailData.body;
			uni.hideLoading();

			await this.loadCommentInfo();

			// 监听事件
			uni.$on('goToReplyComment', comment => {
				this.parentComment = comment;
				console.log("on goToReplyComment")
			});
			uni.$on('afterPublishComment', data => {
				if (data.parentId) {
					let c = this.commentInfo.commentList.filter(x => x.id === data.parentId);
					c[0].replyCount++;
				} else {
					this.commentInfo.commentList.unshift(data.newComment);
				}

				this.parentComment = null;
				console.log("on afterPublishComment")
			});
			uni.$on('updateMomentHasFollowed', event => {
				this.moment.hasFollowed = event.hasFollowed;
			});
			uni.$on('updateCommentHasFollowed', event => {
				for (let i in this.commentInfo.commentList) {
					let comment = this.commentInfo.commentList[i];
					if (comment.id === event.id) {
						comment.hasFollowed = event.hasFollowed;
						return;
					}
				}
			});
		},

		async onReachBottom() {
			console.log("触底");
			await this.loadCommentInfo();
		},

		async onShareAppMessage(res) {
			await httpUtils.postJson("/share/share", {
				pageType: "VIDEO_DETAIL",
				momentId: this.moment.id
			});

			let loginUser = uni.getStorageSync("loginUser");
			return {
				title: this.moment.video.title,
				path: `/pages/videoDetail/videoDetail?shareUserId=${loginUser.userId}&momentId=${this.moment.id}`
			}
		},

		methods: {
			// 界面相关函数
			toggleTextContent() {
				this.textContentShow = !this.textContentShow;
			},

			updateMomentHasFollowed(event, moment) {
				moment.hasFollowed = event.hasFollowed;
			},

			// 数据相关函数
			// 加载评论数据
			async loadCommentInfo() {
				let [commentListData] = await httpUtils.postJson("/comment/queryMomentComment", {
					momentId: this.momentId,
					parentId: 0,
					pageNo: this.commentInfo.pageNo,
					pageSize: this.commentInfo.pageSize
				});
				this.commentInfo.commentList = this.commentInfo.commentList.concat(commentListData.body);
				this.commentInfo.pageNo++;
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
				console.log(this.moment.likeCount, this.moment.hasLiked);
			},
			// 收藏
			async clickCollect() {
				if (this.moment.video.hasCollected) {
					await httpUtils.postJson("/collect/cancelCollect", {
						targetType: 'VIDEO',
						targetId: this.moment.video.id
					});
					this.moment.video.collectCount--;
				} else {
					await httpUtils.postJson("/collect/collect", {
						targetType: 'VIDEO',
						targetId: this.moment.video.id
					});
					this.moment.video.collectCount++;
				}
				this.moment.video.hasCollected = !this.moment.video.hasCollected;
				console.log(this.moment.video.collectCount, this.moment.video.hasCollected);
			},

			// 关注
			async followUser() {
				if (this.moment.hasFollowed) {
					await httpUtils.postJson("/follow/cancelFollow", {
						targetType: 'USER',
						targetId: this.moment.userId
					});
				} else {
					await httpUtils.postJson("/follow/follow", {
						targetType: 'USER',
						targetId: this.moment.userId
					});
				}
				this.moment.hasFollowed = !this.moment.hasFollowed;
				console.log(this.moment.hasFollowed);
			},
		}
	}
</script>

<style>
	/* 播放器 */
	.video-detail-player-wrapper {
		width: 100%;
	}

	.video-detail-player {
		width: 100%;
	}

	/* 标题，描述 */
	.video-detail-head {
		position: relative;
	}

	.video-detail-title {
		padding-right: 68rpx;
		font-size: 34rpx;
		font-weight: bold;
		color: #333;
		margin: 18rpx 0;
		letter-spacing: 2rpx;
	}

	.video-detail-view-count {
		font-size: 26rpx;
		color: #929292;
	}

	.video-detail-create-time {
		font-size: 26rpx;
		line-height: 38rpx;
		color: #5a5a5a;
		padding: 12rpx 0;
	}

	.video-detail-text-content {
		font-size: 28rpx;
		line-height: 38rpx;
		color: #4b4b4b;
	}

	.view-detail-toggle-wrapper {
		position: absolute;
		top: 12rpx;
		right: 30rpx;
		width: 60rpx;
		height: 60rpx;
	}

	.view-detail-toggle-text-content-down {
		position: absolute;
		top: 2rpx;
		right: 12rpx;
		width: 0;
		height: 0;
		border-top: 20rpx solid #7f7f7f;
		border-left: 20rpx solid transparent;
		transform: rotate(135deg);
	}

	.view-detail-toggle-text-content-up {
		position: absolute;
		top: 6rpx;
		right: 12rpx;
		width: 0;
		height: 0;
		border-top: 20rpx solid #7f7f7f;
		border-left: 20rpx solid transparent;
		transform: rotate(-45deg);
	}

	/* 操作 */
	.video-detail-operation-area {
		display: flex;
		flex-direction: row;
		justify-content: space-between;
		padding: 30rpx 70rpx;
	}

	.video-detail-operation-item {
		display: flex;
		flex-direction: column;
		align-items: center;
	}

	.video-detail-operation-icon {
		width: 52rpx;
		height: 52rpx;
	}

	.video-detail-operation-count {
		font-size: 30rpx;
		color: #757575;
	}

	/* 用户信息 */
	.video-detail-user-info {
		position: relative;
		display: flex;
		flex-direction: row;
		align-items: center;
		padding-top: 20rpx;
		padding-bottom: 20rpx;
		border-top: 1rpx solid #F1F1F1;
		border-bottom: 12rpx solid #F5F5F5;
	}

	.video-detail-user-avatar {
		width: 60rpx;
		height: 60rpx;
		flex-shrink: 0;
		border-radius: 50%;
		margin-right: 20rpx;
	}

	.video-detail-user-name {
		color: #222;
		font-size: 32rpx;
	}

	.video-detail-user-follow {
		position: absolute;
		right: 30rpx;
		top: 30rpx;
		background-color: #FF3A3E;
		width: 104rpx;
		height: 46rpx;
		color: white;
		font-size: 26rpx;
		letter-spacing: 2rpx;
		line-height: 46rpx;
		text-align: center;
		border-radius: 26rpx;
		padding: 0 6rpx;
	}

	.video-detail-user-unfollow {
		position: absolute;
		right: 30rpx;
		top: 30rpx;
		background-color: #ddd;
		width: 104rpx;
		height: 46rpx;
		color: #666;
		font-size: 26rpx;
		letter-spacing: 2rpx;
		line-height: 46rpx;
		text-align: center;
		border-radius: 26rpx;
		padding: 0 6rpx;
	}
</style>

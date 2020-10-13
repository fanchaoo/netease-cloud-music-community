<template>
	<view style="height: 100%;">
		<!-- moment信息 -->
		<moment :moment="moment" momentPageType="DETAIL" v-if="moment && moment.id">
		</moment>

		<!-- 评论，转发，点赞 -->
		<view class="tab-nav-main-wrapper">
			<view class="tab-nav-main">
				<view class="tab-nav-list" ref="tabNavList">
					<view class="tab-nav-item" :class="curTabIndex==0 ? 'tab-nav-item-active' : ''" :data-index="0" @click="clikeTabNav">
						评论
						<text class="tab-nav-item-num">{{moment.commentCount}}</text>
					</view>
					<view class="tab-nav-item" :class="curTabIndex==1 ? 'tab-nav-item-active' : ''" :data-index="1" @click="clikeTabNav">
						转发
						<text class="tab-nav-item-num">{{moment.repostCount}}</text>
					</view>
					<view class="tab-nav-item" :class="curTabIndex==2 ? 'tab-nav-item-active' : ''" :data-index="2" @click="clikeTabNav">
						赞
						<text class="tab-nav-item-num">{{moment.likeCount}}</text>
					</view>
				</view>
			</view>
		</view>
		<view class="line"></view>
		<swiper class="tab-content" :duration="300" :current="curTabIndex" @change="swiperChange" acceleration="false">
			<swiper-item class="tab-content-item">
				<commentList :commentList="commentInfo.commentList" :parentId="parseInt('0')" title="最新评论"></commentList>
			</swiper-item>
			<swiper-item class="tab-content-item">
				<momentDetailRepost v-for="(repostUser, index) in repostInfo.repostUserList" :repostUser="repostUser" :index="index"></momentDetailRepost>
			</swiper-item>
			<swiper-item class="tab-content-item">
				<momentDetailLike v-for="(likeUser, index) in likeInfo.likeUserList" :likeUser="likeUser" :index="index"></momentDetailLike>
			</swiper-item>
		</swiper>

		<publishComment :parentComment="parentComment" :momentId="moment.id"></publishComment>
	</view>
</template>

<script>
	import httpUtils from '../../common/util/httpUtils.js';
	import moment from '../../components/moment/moment.vue';
	import commentList from '../../components/commentList/commentList.vue';
	import publishComment from '../../components/publishComment/publishComment.vue';
	import momentDetailRepost from '../../components/momentDetailRepost/momentDetailRepost.vue';
	import momentDetailLike from '../../components/momentDetailLike/momentDetailLike.vue';

	export default {
		components: {
			moment,
			commentList,
			publishComment,
			momentDetailRepost,
			momentDetailLike
		},

		data() {
			return {
				// 当前tab索引
				curTabIndex: 0,

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
				// repost数据
				repostInfo: {
					repostUserList: [],
					pageNo: 1,
					pageSize: 20
				},
				// like数据
				likeInfo: {
					likeUserList: [],
					pageNo: 1,
					pageSize: 20
				}
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
			await this.loadRepostInfo();
			await this.loadLikeInfo();

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
			uni.$on('updateCommentLikeCount', event => {
				for (let i in this.commentInfo.commentList) {
					let comment = this.commentInfo.commentList[i];
					if (comment.id === event.id) {
						comment.likeCount = event.likeCount;
						return;
					}
				}
			});
			uni.$on('updateCommentHasLiked', event => {
				for (let i in this.commentInfo.commentList) {
					let comment = this.commentInfo.commentList[i];
					if (comment.id === event.id) {
						comment.hasLiked = event.hasLiked;
						return;
					}
				}
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
			console.log("momentDetail触底");
			if (this.curTabIndex === 0) {
				await this.loadCommentInfo();
			} else if (this.curTabIndex === 1) {
				await this.loadRepostInfo();
			} else {
				await this.loadLikeInfo();
			}
		},

		async onShareAppMessage(res) {
			await httpUtils.postJson("/share/share", {
				pageType: "MOMENT_DETAIL",
				momentId: this.moment.id
			});

			let loginUser = uni.getStorageSync("loginUser");
			return {
				title: this.moment.userName + "的动态",
				path: `/pages/momentDetail/momentDetail?shareUserId=${loginUser.userId}&momentId=${this.moment.id}`
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
				(newIndex !== this.curTabIndex) && (this.curTabIndex = newIndex);
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
			// 加载转发数据
			async loadRepostInfo() {
				let [repostUserData] = await httpUtils.postJson("/repost/queryRepostUser", {
					repostMomentId: this.momentId,
					pageNo: this.repostInfo.pageNo,
					pageSize: this.repostInfo.pageSize
				});
				this.repostInfo.repostUserList = this.repostInfo.repostUserList.concat(repostUserData.body);
				this.repostInfo.pageNo++;
			},
			// 加载点赞数据
			async loadLikeInfo() {
				let [likeUserData] = await httpUtils.postJson("/like/queryLikeUser", {
					targetType: "MOMENT",
					targetId: this.momentId,
					pageNo: this.likeInfo.pageNo,
					pageSize: this.likeInfo.pageSize
				});
				this.likeInfo.likeUserList = this.likeInfo.likeUserList.concat(likeUserData.body);
				this.likeInfo.pageNo++;
			}
		}
	}
</script>

<style>
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
		margin-top: 32rpx;
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
		margin: 0 74rpx;
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

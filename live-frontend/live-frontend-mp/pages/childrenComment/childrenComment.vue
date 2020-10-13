<template>
	<view>
		<view>
			<comment :comment="comment" :parentId="0" :single="Boolean(true)" v-if="comment && comment.content">
			</comment>
		</view>

		<view class="children-comment-line">
		</view>

		<view>
			<commentList :commentList="commentInfo.commentList" :parentId="parentId" title="全部评论"></commentList>
		</view>

		<publishComment :newComment="newComment" :parentComment="parentComment" :momentId="momentId" pageType="childrenComment"
		 :parentId="parentId">
		</publishComment>

	</view>
</template>

<script>
	import httpUtils from '../../common/util/httpUtils.js';
	import comment from '../../components/comment/comment.vue';
	import commentList from '../../components/commentList/commentList.vue';
	import publishComment from '../../components/publishComment/publishComment.vue';
	export default {

		components: {
			comment,
			commentList,
			publishComment
		},
		data() {
			return {
				// options数据
				parentId: 0,
				momentId: 0,

				// comment数据
				comment: {},

				// commentList数据
				commentInfo: {
					commentList: [],
					pageNo: 1,
					pageSize: 20
				},

				// 发评论
				parentComment: null,
				newComment: {
					momentId: null,
					content: "",
					parentId: 0,
					replyToId: 0
				},
			}
		},

		async onLoad(options) {
			this.parentId = new Number(options.parentId);
			this.momentId = new Number(options.momentId);
			// 加载comment数据
			let [commentDetailData] = await httpUtils.postJson("/comment/getCommentDetail", {
				commentId: this.parentId
			});
			this.comment = commentDetailData.body;
			// 加载commentList数据
			await this.loadCommentInfo();

			// 监听事件
			uni.$on('goToReplyComment', comment => {
				this.parentComment = comment;
			});
			uni.$on('afterPublishComment', data => {
				this.commentInfo.commentList.unshift(data.newComment);
				this.newComment.content = "";
				this.parentComment = null;
			});
			uni.$on('updateCommentLikeCount', event => {
				for (let i in this.commentInfo.commentList) {
					let comment = this.commentInfo.commentList[i];
					if (comment.id === event.id) {
						comment.likeCount = event.likeCount;
						console.log("更新" + comment.likeCount)
						return;
					}
				}
			});
			uni.$on('updateCommentHasLiked', event => {
				console.log("更新" + event)
				for (let i in this.commentInfo.commentList) {
					let comment = this.commentInfo.commentList[i];
					if (comment.id === event.id) {
						comment.hasLiked = event.hasLiked;
						console.log("更新" + comment.hasLiked)
						return;
					}
				}
			});
			uni.$on('updateCommentHasFollowed', event => {
				if (this.comment.id === event.id) {
					this.comment.hasFollowed = event.hasFollowed;
				}
				for (let i in this.commentInfo.commentList) {
					let comment = this.commentInfo.commentList[i];
					if (comment.id === event.id) {
						comment.hasFollowed = event.hasFollowed;
						break;
					}
				}
			});
		},

		async onReachBottom() {
			console.log("触底");
			await this.loadCommentInfo();
		},

		methods: {

			// 数据相关函数
			// 加载commentList数据
			async loadCommentInfo() {
				let [commentListData] = await httpUtils.postJson("/comment/queryMomentComment", {
					momentId: this.momentId,
					parentId: this.parentId,
					pageNo: this.commentInfo.pageNo,
					pageSize: this.commentInfo.pageSize
				});
				this.commentInfo.commentList = this.commentInfo.commentList.concat(commentListData.body);
				this.commentInfo.pageNo++;
			}

		}
	}
</script>

<style>
	.children-comment-line {
		background-color: #F5F5F5;
		height: 12rpx;
		margin-top: 14rpx;
	}
</style>

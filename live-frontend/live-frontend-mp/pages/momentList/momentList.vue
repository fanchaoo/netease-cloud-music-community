<template>
	<view class="moment-list-root">

		<view class="moment-list-topic" :style="{ backgroundImage: 'url(' + topic.coverUrl + ')' }" v-if="pageType === 'TOPIC_MOMENT_LIST' "
		 @click="updateTopicCoverUrl">
			<view class="moment-list-topic-name">
				<text>#{{topic.name}}#</text>
			</view>
			<view class="moment-list-topic-user-count">
				{{topic.userCount}}人参与
			</view>
		</view>
		<view class="moment-list-title" v-if="pageType === 'TOPIC_MOMENT_LIST' ">
			最新动态
		</view>

		<!-- 动态列表 -->
		<moment v-for="(moment) in momentInfo.momentList" :moment="moment" momentPageType="LIST" v-on:updateMomentLikeCount="updateMomentLikeCount($event,moment)"
		 v-on:updateMomentHasLiked="updateMomentHasLiked($event,moment)">
		</moment>
	</view>
</template>

<script>
	import httpUtils from '../../common/util/httpUtils.js';
	import moment from '../../components/moment/moment.vue';

	export default {
		components: {
			moment
		},

		data() {
			return {
				// 页面类型：FRIEND_MOMENT_LIST，ACTIVITY_MOMENT_LIST，TOPIC_MOMENT_LIST
				pageType: "",

				// 演出id
				activityId: null,

				// 话题
				topicName: null,
				topic: {},

				// 动态数据
				momentInfo: {
					pageNo: 1,
					pageSize: 20,
					momentList: []
				},
			}
		},

		async onLoad(options) {
			this.pageType = options.pageType;
			this.activityId = options.activityId;
			this.topicName = options.topicName;

			// 加载moment数据
			await this.loadMomentInfo();
			
			uni.$on('updateMomentHasFollowed', event => {
				for (let i in this.momentInfo.momentList) {
					let moment = this.momentInfo.momentList[i];
					if (moment.id === event.id) {
						moment.hasFollowed = event.hasFollowed;
						break;
					}
				}
			});
		},

		async onPullDownRefresh() {
			this.momentInfo = {
				pageNo: 1,
				pageSize: 20,
				momentList: []
			};
			await this.loadMomentInfo();
			uni.stopPullDownRefresh();
		},

		async onReachBottom() {
			console.log("触底");
			await this.loadMomentInfo();
		},

		methods: {
			// 事件相关函数
			updateMomentLikeCount(event, moment) {
				moment.likeCount = event;
			},

			updateMomentHasLiked(event, moment) {
				moment.hasLiked = event;
			},

			updateTopicCoverUrl() {
				let loginUser = uni.getStorageSync("loginUser");
				if (this.topic.createUserId !== loginUser.userId) {
					return;
				}

				uni.chooseImage({
					count: 1,
					sizeType: ['original'],
					sourceType: ['album'],
					success: async (res) => {
						let tempFilePath = res.tempFilePaths[0];

						uni.showLoading({
							title: '上传中...'
						});
						let [url] = await httpUtils.uploadFile(tempFilePath);
						uni.hideLoading();

						await httpUtils.postJson("/topic/updateTopicCoverUrl", {
							topicId: this.topic.id,
							coverUrl: url
						});
						this.topic.coverUrl = url;
					}
				});
			},

			// 数据相关函数
			// 加载moment数据
			async loadMomentInfo() {
				if (this.pageType === 'FRIEND_MOMENT_LIST') {
					let [data] = await httpUtils.postJson("/discover/queryFriendMoment", {
						pageNo: this.momentInfo.pageNo,
						pageSize: this.momentInfo.pageSize
					});
					this.momentInfo.momentList = this.momentInfo.momentList.concat(data.body);
					this.momentInfo.pageNo++;
				}

				if (this.pageType === 'ACTIVITY_MOMENT_LIST') {
					let [data] = await httpUtils.postJson("/square/querySquareMoment", {
						pageNo: this.momentInfo.pageNo,
						pageSize: this.momentInfo.pageSize,
						activityId: this.activityId
					});
					this.momentInfo.momentList = this.momentInfo.momentList.concat(data.body);
					this.momentInfo.pageNo++;
				}

				if (this.pageType === 'TOPIC_MOMENT_LIST') {
					if (!this.topic.id) {
						let [topicDetailData] = await httpUtils.postJson("/topic/getTopicDetail", {
							name: this.topicName
						});
						this.topic = topicDetailData.body;
					}

					let [data] = await httpUtils.postJson("/topic/queryTopicMoment", {
						pageNo: this.momentInfo.pageNo,
						pageSize: this.momentInfo.pageSize,
						topicId: this.topic.id
					});
					this.momentInfo.momentList = this.momentInfo.momentList.concat(data.body);
					this.momentInfo.pageNo++;
				}
			}
		}
	}
</script>

<style>
	.moment-list-root {
		padding-top: 10rpx;
	}

	.moment-list-topic {
		height: 470rpx;
		background-position: center;
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
		border-bottom: 12rpx solid #F5F5F5;
	}

	.moment-list-topic-name {
		text-align: center;
		position: relative;
		font-size: 36rpx;
		color: white;
		padding: 20rpx 30rpx;
		border-top: 4rpx solid hsla(0, 0%, 100%, .39);
		border-bottom: 4rpx solid hsla(0, 0%, 100%, .39);
	}

	.moment-list-topic-user-count {
		font-size: 28rpx;
		color: white;
		margin-top: -20rpx;
		z-index: 1;
	}

	.moment-list-title {
		padding: 0 30rpx;
		font-size: 30rpx;
		font-weight: bold;
		margin-top: 30rpx;
		margin-bottom: 10rpx;
		color: #444;
	}
</style>

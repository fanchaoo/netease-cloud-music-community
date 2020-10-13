<template>
	<view class="topic-list-root common-padding">

		<!-- 搜索框 -->
		<view class="search">
			<image src="../../static/icon/search.png" class="search-icon"></image>
			<input placeholder="搜索话题" placeholder-class="search-input-placeholder" maxlength="20" class="search-input"
			 confirm-type="search" v-model="name" @confirm="reloadTopicInfo" @keyup="reloadTopicInfo" />
		</view>

		<!-- 话题列表 -->
		<view class="topic-list">
			<view class="topic-item" v-for="(topic) in topicInfo.topicList" @click="goToTopicMomentList(topic)">
				<image class="topic-cover" :src="topic.coverUrl" mode="aspectFill">
				</image>
				<view class="topic-info">
					<view class="topic-name">
						#{{topic.name}}#
					</view>
					<view class="topic-create-user">
						创建人 @{{topic.createUserName}}
					</view>
					<view class="topic-moment-count">
						{{topic.momentCount}}个讨论 @{{topic.lastUpdateUserName}} 刚刚讨论
					</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import httpUtils from '../../common/util/httpUtils.js';

	export default {
		data() {
			return {

				name: "",
				// 话题数据
				topicInfo: {
					pageNo: 1,
					pageSize: 20,
					topicList: []
				},
			}
		},

		async onLoad(options) {
			// 加载话题数据
			await this.loadTopicInfo();
		},

		async onPullDownRefresh() {
			await this.reloadTopicInfo()
			uni.stopPullDownRefresh();
		},

		async onReachBottom() {
			await this.loadTopicInfo();
		},

		methods: {

			goToTopicMomentList(topic) {
				uni.navigateTo({
					url: `/pages/momentList/momentList?topicName=${topic.name}&pageType=TOPIC_MOMENT_LIST`
				});
			},

			async loadTopicInfo() {
				let [data] = await httpUtils.postJson("/topic/queryTopic", {
					name: this.name,
					pageNo: this.topicInfo.pageNo,
					pageSize: this.topicInfo.pageSize
				});
				this.topicInfo.topicList = this.topicInfo.topicList.concat(data.body);
				this.topicInfo.pageNo++;
			},

			async reloadTopicInfo(e) {
				console.log(e)
				this.topicInfo = {
					pageNo: 1,
					pageSize: 20,
					topicList: []
				};
				await this.loadTopicInfo();
			}
		}
	}
</script>

<style>
	/* 搜索框 */
	.search {
		display: flex;
		flex-direction: row;
		margin: 8rpx 0 12rpx 0;
		font-size: 30rpx;
	}

	.search-icon {
		width: 46rpx;
		height: 46rpx;
		margin-left: -8rpx;
	}

	.search-input {
		flex: 1;
		padding-left: 20rpx;
		padding-top: 2rpx;
	}

	.search-input:focus {
		padding-left: 20rpx;
	}

	.search-input-placeholder {
		font-size: 30rpx;
		padding-top: 2rpx;
	}

	/* 话题 */
	.topic-list {
		margin-top: 30rpx;
	}

	.topic-item {
		display: flex;
		flex-direction: row;
		margin-bottom: 30rpx;
	}

	.topic-cover {
		width: 140rpx;
		height: 140rpx;
		flex-shrink: 0;
		margin-right: 20rpx;
	}

	.topic-info {
		width: 530rpx;
	}

	.topic-name {
		font-size: 32rpx;
		color: #333;
		margin-bottom: 12rpx;
		overflow-x: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
	}

	.topic-create-user {
		font-size: 24rpx;
		color: #3F3F3F;
		margin-bottom: 12rpx;
		overflow-x: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
	}

	.topic-moment-count {
		font-size: 24rpx;
		color: #828282;
		overflow-x: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
	}
</style>

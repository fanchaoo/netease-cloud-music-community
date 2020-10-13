<template>
	<view class="video-list-root">
		<videoItem v-for="(moment) in videoInfo.momentList" :moment="moment" v-on:updateMomentLikeCount="updateMomentLikeCount($event,moment)"
		 v-on:updateMomentHasLiked="updateMomentHasLiked($event,moment)"></videoItem>
	</view>
</template>

<script>
	import httpUtils from '../../common/util/httpUtils.js';
	import videoItem from '../../components/videoItem/videoItem.vue';

	export default {
		components: {
			videoItem
		},

		data() {
			return {
				// 页面类型：COLLECT_VIDEO_LIST
				pageType: "",

				// 视频数据
				videoInfo: {
					pageNo: 1,
					pageSize: 20,
					momentList: []
				},
			}
		},

		async onLoad(options) {
			this.pageType = options.pageType;

			// 加载moment数据
			await this.loadVideoInfo();

			uni.$on('updateMomentHasFollowed', event => {
				for (let i in this.videoInfo.momentList) {
					let moment = this.videoInfo.momentList[i];
					if (moment.id === event.id) {
						moment.hasFollowed = event.hasFollowed;
						break;
					}
				}
			});
		},

		async onPullDownRefresh() {
			this.videoInfo = {
				pageNo: 1,
				pageSize: 20,
				momentList: []
			};
			await this.loadVideoInfo();
			uni.stopPullDownRefresh();
		},

		async onReachBottom() {
			console.log("触底");
			await this.loadVideoInfo();
		},

		methods: {
			// 事件相关函数
			updateMomentLikeCount(event, moment) {
				moment.likeCount = event;
			},

			updateMomentHasLiked(event, moment) {
				moment.hasLiked = event;
			},

			// 数据相关函数
			// 加载视频数据
			async loadVideoInfo() {

				if (this.pageType === 'COLLECT_VIDEO_LIST') {
					let [data] = await httpUtils.postJson("/collect/queryCollectVideo", {
						pageNo: this.videoInfo.pageNo,
						pageSize: this.videoInfo.pageSize
					});
					this.videoInfo.momentList = this.videoInfo.momentList.concat(data.body);
					this.videoInfo.pageNo++;
				}
			}
		}
	}
</script>

<style>
	.video-list-root {
		padding-top: 10rpx;
	}
</style>

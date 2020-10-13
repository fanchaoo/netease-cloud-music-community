<template>
	<view>

		<!-- 视频上传 -->
		<view class="video-area" v-if="publishType==='VIDEO'">
			<view class="video-cover-wrapper">
				<image class="video-cover" v-if="video && video.coverUrl" :src="video.coverUrl" mode="aspectFill"></image>
				<view class="video-cover-alternate" v-if="!video || !video.coverUrl">
					暂未添加封面
				</view>
			</view>
			<view class="video-upload-wrapper">
				<view class="video-upload-original-title" v-if="videoUploadComplete">
					{{video.title ? video.title : videoOriginalTitle}}
				</view>
				<view class="video-upload-result" v-if="videoUploadComplete">
					<image src="../../static/icon/video-upload-complete.png"></image>
					<text>视频上传成功！</text>
				</view>
				<view class="video-upload-progress" v-if="!videoUploadComplete">
					<view class="video-upload-progress-total">
						<view class="video-upload-progress-part" :style="{width:videoUploadPercent * 3.2 +'rpx'}">
						</view>
					</view>
					<view class="video-upload-progress-percnet">
						{{videoUploadPercent}}%
					</view>
				</view>
			</view>
		</view>

		<!-- 视频选择封面 -->
		<view class="video-cover-area" v-if="publishType==='VIDEO'">
			<view class="video-choose-cover" @click="chooseVideoCover">
				上传自定义封面
			</view>
			<view class="video-generated-cover" v-if="videoCoverList && videoCoverList.length > 0" v-for="v in videoCoverList">
				<image :src="v.url" :data-video-cover-url="v.url" :data-video-cover-width="v.width" :data-video-cover-height="v.height"
				 @click="selectVideoCover"></image>
			</view>
		</view>

		<!-- 视频标题 -->
		<view class="video-title" v-if="publishType==='VIDEO'">
			<input class="video-title-input" v-model="video.title" type="text" value="" placeholder="给视频取个标题吧~" />
		</view>
		<!-- 文本内容 -->
		<view class="textarea-wrapper">
			<textarea v-model="textContent" class="textarea" :placeholder="publishType === 'REPOST' ? '说说我的看法...' : '一起聊聊吧~' "
			 @click="textareaFocused=true"></textarea>
		</view>

		<!-- 图片区域 -->
		<view class="image-area" v-if="publishType === 'MOMENT'">
			<view class="image-wrapper" v-for="(tempImage, index) in imageTempList">
				<image class="image" mode="aspectFill" :src="tempImage.path">
				</image>
				<view class="remove-image" @click="removeImage" :data-image-index="index">
					<view class="remove-image-horizontal">
					</view>
					<view class="remove-image-vertical">
					</view>
				</view>
			</view>

			<view class="add-image" @click="chooseImage">
				<view class="add-image-horizontal">
				</view>
				<view class="add-image-vertical">
				</view>
			</view>
		</view>

		<!-- 图片，@，话题，标签等编辑信息-->
		<view class="edit-info">
			<view class="edit-info-image-wrapper">
				<view class="edit-info-image-view">
					<image src="../../static/icon/publish_photo.png" class="edit-info-image" @click="chooseImage"></image>
				</view>
				<view class="edit-info-image-view">
					<image src="../../static/icon/publish_at.png" class="edit-info-image"></image>
				</view>
				<view class="edit-info-image-view">
					<image src="../../static/icon/publish_topic.png" class="edit-info-image"></image>
				</view>
				<view class="edit-info-image-view">
					<image src="../../static/icon/publish_tag.png" class="edit-info-image"></image>
				</view>
			</view>

			<view class="edit-info-publish-wrapper">
				<view class="edit-info-publish-word-count">
					{{textContent.length}}
				</view>
				<view class="edit-info-publish-button" @click="publish">
					发布
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
				// 发布类型：MOMENT，VIDEO，REPOST
				publishType: "MOMENT",

				// 视频原始名称
				videoOriginalTitle: "",
				// 视频上传结果
				videoUploadComplete: false,
				videoUploadPercent: 10,
				// 视频信息
				video: {
					title: "",
					url: "",
					width: 0,
					height: 0,
					coverUrl: "",
					coverWidth: "",
					coverHeight: ""
				},
				videoCoverList: [],

				// 临时图片列表
				imageTempList: [],
				// 图片列表
				imageList: [],
				// textarea文本内容
				textContent: "",

				// 转发信息
				repostMomentId: null
			}
		},
		computed: {
			momentType() {
				if (this.imageList && this.imageList.length > 0) {
					return "IMAGE";
				} else if (this.video && this.video.url) {
					return "VIDEO";
				} else {
					return "TEXT";
				}
			}
		},
		onLoad(options) {
			this.publishType = options.publishType;

			// 视频
			if (this.publishType === 'VIDEO') {
				this.videoOriginalTitle = options.videoOriginalTitle;
				this.video.width = options.videoWidth;
				this.video.height = options.videoHeight;
				// 上传视频
				const uploadTask = uni.uploadFile({
					url: getApp().globalData.domain + "/oss/uploadImageOrVideo",
					filePath: options.tempVideoPath,
					name: 'file',
					header: {
						"token": uni.getStorageSync("loginUser").token
					},
					success: async (uploadFileRes) => {
						let uploadData = JSON.parse(uploadFileRes.data);
						this.video.url = uploadData.body;
						console.log("上传完成：" + uploadFileRes.data);
						let [data] = await httpUtils.postJson("/video/queryVideoCover", {
							videoPath: uploadFileRes.data
						});
						this.videoCoverList = data.body;
					}
				});

				uploadTask.onProgressUpdate((res) => {
					console.log('上传进度：' + res.progress);
					this.videoUploadPercent = res.progress;
					if (this.videoUploadPercent === 100) {
						this.videoUploadComplete = true;
					}
				});
			}
			if (this.publishType === 'REPOST') {
				this.repostMomentId = options.repostMomentId;
			}

		},
		methods: {
			// 选择图片
			chooseImage() {
				uni.chooseImage({
					count: 9,
					sizeType: ['original'],
					sourceType: ['album'],
					success: async (res) => {
						if (this.imageTempList.length + res.tempFilePaths.length > 9) {
							uni.showToast({
								title: "最多上传9张图片",
								duration: 2000
							})
							return;
						}

						for (let i in res.tempFilePaths) {
							let tempFilePath = res.tempFilePaths[i];

							let [error, imageInfo] = await uni.getImageInfo({
								src: tempFilePath
							});
							this.imageTempList.push({
								path: tempFilePath,
								width: imageInfo.width,
								height: imageInfo.height
							});
						}
					}
				});
			},

			// 移除图片
			removeImage(e) {
				let imageIndex = e.currentTarget.dataset.imageIndex;
				this.imageTempList.splice(imageIndex, 1);
			},

			// 选择视频封面
			chooseVideoCover() {
				uni.chooseImage({
					count: 1,
					sizeType: ['original'],
					sourceType: ['album'],
					success: async (res) => {
						let tempFilePath = res.tempFilePaths[0];
						let [error, imageInfo] = await uni.getImageInfo({
							src: tempFilePath
						});

						let [url] = await httpUtils.uploadFile(tempFilePath);
						this.video.coverUrl = url;
						this.video.coverWidth = imageInfo.width;
						this.video.coverHeight = imageInfo.height;
					}
				});
			},
			// 选择视频封面
			selectVideoCover(e) {
				console.log(e.currentTarget.dataset)
				this.video.coverUrl = e.currentTarget.dataset.videoCoverUrl;
				this.video.coverWidth = e.currentTarget.dataset.videoCoverWidth;
				this.video.coverHeight = e.currentTarget.dataset.videoCoverHeight;
			},

			// 发动态，转发动态
			async publish() {

				if (this.publishType === 'REPOST') {
					// 转发
					let param = {
						repostMomentId: this.repostMomentId,
						textContent: this.textContent
					};
					let [repostMomentData, repostMomentError] = await httpUtils.postJson("/repost/repost", param);

					if (!repostMomentError) {
						uni.reLaunch({
							url: "../square/square"
						});
					}

				} else {
					// 发动态或发视频
					uni.showLoading({
						title: "发布中..."
					});
					for (let i in this.imageTempList) {

						let imageTemp = this.imageTempList[i];
						let [url] = await httpUtils.uploadFile(imageTemp.path);
						console.log("已上传：" + url);
						this.imageList.push({
							url,
							width: imageTemp.width,
							height: imageTemp.height
						});
					}

					let param = {
						momentType: this.momentType,
						textContent: this.textContent
					};
					if (this.momentType === 'TEXT') {
						// check
					}
					if (this.momentType === 'IMAGE') {
						param.imageList = this.imageList;
					}
					if (this.momentType === 'VIDEO') {
						param.video = this.video;
					}

					let [publishMomentData, publishMomentError] = await httpUtils.postJson("/moment/publishMoment", param);
					uni.hideLoading();

					if (!publishMomentError) {
						uni.reLaunch({
							url: "../square/square"
						});
					}
				}

			}
		}
	}
</script>

<style>
	/* 视频区域 */
	.video-area {
		padding: 0 30rpx;
		margin: 20rpx 0 30rpx 0;
		display: flex;
		flex-direction: row;
		border-radius: 10rpx;
		overflow: hidden;
	}

	.video-cover-wrapper {
		width: 312rpx;
		height: 176rpx;
	}

	.video-cover {
		width: 312rpx;
		height: 176rpx;
	}

	.video-cover-alternate {
		width: 312rpx;
		height: 176rpx;
		background-color: #d8d8d8;
		color: #fff;
		font-size: 30rpx;
		line-height: 146rpx;
		text-align: center;
	}

	.video-upload-wrapper {
		font-size: 30rpx;
		color: #333333;
		background-color: #f9fafb;
		flex: 1;
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
	}

	.video-upload-original-title {
		width: 220rpx;
		text-align: center;
		text-overflow: ellipsis;
		white-space: nowrap;
		overflow: hidden;
	}

	.video-upload-result {
		color: #999999;
		font-size: 28rpx;
		display: flex;
		flex-direction: row;
		align-items: center;
		margin-top: 20rpx;
	}

	.video-upload-result image {
		width: 30rpx;
		height: 30rpx;
		border-radius: 50%;
		background-color: #ea524e;
		margin-right: 10rpx;
	}

	.video-upload-progress {
		display: flex;
		flex-direction: row;
		align-items: center;
		font-size: 28rpx;
		color: #999999;
	}

	.video-upload-progress-total {
		width: 320rpx;
		height: 14rpx;
		background-color: #E8EAF3;
		border-radius: 280rpx;
		overflow: hidden;
		margin-right: 10rpx;
	}

	.video-upload-progress-part {
		width: 100rpx;
		height: 14rpx;
		background-color: #EE6762;
	}

	/* 视频封面 */
	.video-cover-area {
		margin: 0 30rpx;
		height: 146rpx;
		display: flex;
		flex-direction: row;
		flex-wrap: nowrap;
		overflow-x: auto;
		overflow-y: hidden;
		height: 146rpx;
	}

	.video-choose-cover {
		flex-shrink: 0;
		width: 260rpx;
		height: 146rpx;
		margin-right: 20rpx;
		font-size: 28rpx;
		text-align: center;
		line-height: 146rpx;
		color: #999;
		background-color: #F9FAFB;
	}

	.video-choose-cover:active {
		background-color: #d7d7d7;
	}

	.video-generated-cover {
		width: 260rpx;
		height: 146rpx;
		margin-right: 20rpx;
	}

	.video-generated-cover image {
		width: 260rpx;
		height: 146rpx;
	}

	/* 视频标题 */
	.video-title {
		padding: 20rpx 30rpx 20rpx 30rpx;
	}

	.video-title-input {
		font-size: 30rpx;
		line-height: 70rpx;
		letter-spacing: 1rpx;
		width: 100%;
		height: 80rpx;
		/* border-top: 1rpx solid #efefef; */
		border-bottom: 1rpx solid #efefef;
		padding-bottom: 10rpx;
	}

	/* 文本域 */
	.textarea-wrapper {
		padding: 0 30rpx;
	}

	.textarea {
		font-size: 30rpx;
		line-height: 48rpx;
		letter-spacing: 1rpx;
		height: 280rpx;
		width: 100%;
	}

	/* 图片区域 */
	.image-area {
		padding: 30rpx 30rpx;
		display: flex;
		flex-direction: row;
		flex-wrap: wrap;
	}

	.image-wrapper {
		position: relative;
		width: 216rpx;
		height: 216rpx;
		margin-bottom: 22rpx;
		margin-right: 14rpx;
	}

	.image {
		width: 216rpx;
		height: 216rpx;
	}

	/* 删除图片按钮 */
	.remove-image {
		position: absolute;
		width: 26rpx;
		height: 26rpx;
		background-color: #000000;
		opacity: 0.5;
		top: 6rpx;
		right: 6rpx;
		border-radius: 50%;
		transform: rotate(45deg);
	}

	.remove-image-horizontal {
		width: 14rpx;
		height: 2rpx;
		background-color: #ffffff;
		opacity: 1;
		position: absolute;
		left: 0;
		right: 0;
		top: 0;
		bottom: 0;
		margin: auto;
	}

	.remove-image-vertical {
		width: 2rpx;
		height: 14rpx;
		background-color: #ffffff;
		opacity: 1;
		position: absolute;
		left: 0;
		right: 0;
		top: 0;
		bottom: 0;
		margin: auto;
	}

	/* 添加图片按钮 */
	.add-image {
		position: relative;
		width: 216rpx;
		height: 216rpx;
		background-color: #f7f7f7;
		margin-bottom: 22rpx;
		margin-right: 14rpx;
		transition: background-color 0.3s;
	}

	.add-image:active {
		background-color: #d7d7d7;
	}

	.add-image-horizontal {
		width: 60rpx;
		height: 4rpx;
		background-color: #aeaeae;
		position: absolute;
		left: 0;
		right: 0;
		top: 0;
		bottom: 0;
		margin: auto;
		transition: background-color 0.5s;
	}

	.add-image-vertical {
		width: 4rpx;
		height: 60rpx;
		background-color: #aeaeae;
		position: absolute;
		left: 0;
		right: 0;
		top: 0;
		bottom: 0;
		margin: auto;
		transition: background-color 0.5s;
	}

	.add-image-horizontal:active {
		background-color: #7b7b7b;
	}

	.add-image-vertical:active {
		background-color: #7b7b7b;
	}


	/* 图片，@，话题，标签等编辑信息 */
	.edit-info {

		display: flex;
		flex-direction: row;
		align-items: center;
		justify-content: space-between;
		height: 82rpx;
		padding: 0 30rpx;
		border-top: 2rpx solid #EAEAEA;
		border-bottom: 2rpx solid #EAEAEA;
	}

	.edit-info-image-wrapper {
		display: flex;
		flex-direction: row;
	}

	.edit-info-image-view {
		width: 66rpx;
		height: 66rpx;
		margin-right: 10rpx;
		display: flex;
		flex-direction: row;
		justify-content: center;
		align-items: center;
	}

	.edit-info-image-view:active {
		background-color: #fbfbfb;
	}

	.edit-info-image {
		width: 40rpx;
		height: 40rpx;
	}

	.edit-info-publish-wrapper {
		display: flex;
		flex-direction: row;
		height: 56rpx;
		font-size: 26rpx;
		line-height: 56rpx;
	}

	.edit-info-publish-word-count {
		color: #9C9C9C;
		margin-right: 16rpx;
	}

	.edit-info-publish-button {
		width: 126rpx;
		border-radius: 34rpx;
		background-color: #f6423d;
		text-align: center;
		color: #ffffff;
		transition: background-color 0.2;
	}

	.edit-info-publish-button:active {
		background-color: #fa0021;
	}
</style>

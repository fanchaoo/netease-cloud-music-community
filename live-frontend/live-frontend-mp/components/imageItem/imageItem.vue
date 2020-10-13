<template>
	<view class="image-root">
		<view class="image-date">
			{{date | chineseDateFormat}}
		</view>
		<view class="image-item-list">
			<image class="image-item" :class="index % 3 !== 2 ? 'image-item-margin-right' : ''" v-for="(image,index) in imageList"
			 :src="image.url" mode="aspectFill" :data-image-index="index"  @click.stop="previewImage"></image>
		</view>

	</view>
</template>

<script>
	export default {

		components: {},

		data() {
			return {

			};
		},

		props: {
			imageList: {
				type: Array,
			},
			date: {
				type: String
			}
		},

		methods: {
			// 图片预览
			previewImage(e) {
				uni.previewImage({
					urls: this.imageList.map(image => image.url),
					current: e.currentTarget.dataset.imageIndex
				});
			}
		}
	}
</script>

<style>
	.image-root {}

	.image-date {
		height: 56rpx;
		box-sizing: border-box;
		padding-top: 10rpx;
		padding-left: 6rpx;
		font-size: 26rpx;
		color: #8A8A8A;

	}

	.image-item-list {
		display: flex;
		flex-direction: row;
		justify-content: flex-start;
		flex-wrap: wrap;
	}

	.image-item {
		width: 246rpx;
		height: 246rpx;
		margin-bottom: 6rpx;
	}

	.image-item-margin-right {
		margin-right: 6rpx;
	}
</style>

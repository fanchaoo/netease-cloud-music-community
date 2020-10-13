<template>
	<view class="moment-image-container">
		<view class="moment-image-list" v-if="moment.imageList.length===1">
			<image class="moment-image-one-horizontal" v-if="image.width >= image.height" v-for="(image,imageIndex) in moment.imageList"
			 :src="image.url" mode="aspectFill" :data-image-index="imageIndex" :data-image-url="image.url" @click.stop="previewImage"></image>
			<image class="moment-image-one-vertical" v-if="image.width < image.height" v-for="(image,imageIndex) in moment.imageList"
			 :src="image.url" mode="aspectFill" :data-image-index="imageIndex" :data-image-url="image.url" @click.stop="previewImage"></image>
		</view>
		<view class="moment-image-list" v-if="moment.imageList.length===2 || moment.imageList.length===4">
			<view class="moment-image-divide-two-wrapper" v-for="(image,imageIndex) in moment.imageList">
				<image class="moment-image-divide-two" :src="image.url" mode="aspectFill" :data-image-index="imageIndex"
				 :data-image-url="image.url" @click.stop="previewImage"></image>
			</view>
		</view>
		<view class="moment-image-list" v-if="moment.imageList.length===3||moment.imageList.length===6||moment.imageList.length===9">
			<view class="moment-image-divide-three-wrapper" v-for="(image,imageIndex) in moment.imageList">
				<image class="moment-image-divide-three" :src="image.url" mode="aspectFill" :data-image-index="imageIndex"
				 :data-image-url="image.url" @click.stop="previewImage"></image>
			</view>
		</view>
		<view class="moment-image-list" v-if="moment.imageList.length===5">
			<view class="moment-image-divide-two-wrapper" v-for="(image,imageIndex) in moment.imageList.slice(0,2)">
				<image class="moment-image-divide-two" :src="image.url" mode="aspectFill" :data-image-index="imageIndex"
				 :data-image-url="image.url" @click.stop="previewImage"></image>
			</view>
			<view class="moment-image-divide-three-wrapper" v-for="(image,imageIndex) in moment.imageList.slice(2)">
				<image class="moment-image-divide-three" :src="image.url" mode="aspectFill" :data-image-index="imageIndex"
				 :data-image-url="image.url" @click.stop="previewImage"></image>
			</view>
		</view>
		<view class="moment-image-list" v-if="moment.imageList.length===7">
			<view class="moment-image-divide-three-wrapper" v-for="(image,imageIndex) in moment.imageList.slice(0,3)">
				<image class="moment-image-divide-three" :src="image.url" mode="aspectFill" :data-image-index="imageIndex"
				 :data-image-url="image.url" @click.stop="previewImage"></image>
			</view>
			<view class="moment-image-divide-four-wrapper" v-for="(image,imageIndex) in moment.imageList.slice(3)">
				<image class="moment-image-divide-four" :src="image.url" mode="aspectFill" :data-image-index="imageIndex"
				 :data-image-url="image.url" @click.stop="previewImage"></image>
			</view>
		</view>
		<view class="moment-image-list" v-if="moment.imageList.length===8">
			<view class="moment-image-divide-four-wrapper" v-for="(image,imageIndex) in moment.imageList">
				<image class="moment-image-divide-four" :src="image.url" mode="aspectFill" :data-image-index="imageIndex"
				 :data-image-url="image.url" @click.stop="previewImage"></image>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {

			}
		},
		props: {
			moment: {
				type: Object
			}

		},
		methods: {
			// 图片预览
			previewImage(e) {
				uni.previewImage({
					urls: this.moment.imageList.map(image => image.url),
					current: e.currentTarget.dataset.imageIndex
				});
			}
		}
	}
</script>

<style>
	/* 图片 */
	.moment-image-list {
		display: flex;
		flex-direction: row;
		flex-wrap: wrap;
		justify-content: space-between;
	}

	.moment-image-list image {
		border-radius: 6rpx;
	}

	.moment-image-divide-two-wrapper {
		position: relative;
		width: 49.5%;
		height: 0;
		padding-bottom: 49.5%;
		margin-bottom: 6rpx;
	}

	.moment-image-divide-three-wrapper {
		position: relative;
		width: 32.76%;
		height: 0;
		padding-bottom: 32.76%;
		margin-bottom: 6rpx;
	}

	.moment-image-divide-four-wrapper {
		position: relative;
		width: 24.23%;
		height: 0;
		padding-bottom: 24.23%;
		margin-bottom: 6rpx;
	}

	.moment-image-divide-two-wrapper image,
	.moment-image-divide-three-wrapper image,
	.moment-image-divide-four-wrapper image {
		position: absolute;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
	}

	.moment-image-one-horizontal {
		width: 500rpx;
		height: 286rpx;
	}

	.moment-image-one-vertical {
		width: 250rpx;
		height: 400rpx;
	}

	.moment-image-divide-two {
		width: 290rpx;
		height: 290rpx;
	}

	.moment-image-divide-three {
		width: 192rpx;
		height: 192rpx;
	}

	.moment-image-divide-four {
		width: 142rpx;
		height: 142rpx;
	}
</style>

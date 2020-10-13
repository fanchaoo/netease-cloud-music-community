<template>
	<view class="moment-video-cover-container">
		<view class="moment-video-cover-wrapper">
			<image class="moment-video-cover" :src="moment.video.coverUrl" mode="aspectFill"></image>
		</view>
		<image class="moment-video-play" src="../../static/icon/video-play.png" mode="aspectFill"></image>
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

		}
	}
</script>

<style>
	.moment-video-cover-container {
		position: relative;
	}

	.moment-video-cover-wrapper {
		position: relative;
		width: 99.88%;
		height: 0;
		padding-bottom: 56%;
	}

	.moment-video-cover {
		width: 100%;
		height: 100%;
		position: absolute;
		top: 0;
		left: 0;
		border-radius: 6rpx;
	}

	.moment-video-play {
		position: absolute;
		margin: auto;
		top: 0;
		left: 0;
		right: 0;
		bottom: 0;
		width: 70rpx;
		height: 70rpx;
		opacity: 0.95;
	}
</style>

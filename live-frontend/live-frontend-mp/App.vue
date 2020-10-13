<script>
	export default {
		globalData: {
			domain: 'http://localhost:30000',
			randomKey: "OQlRb3WW89yfycQb97Rrt1Ef2OprRyKp",
			appName: "Live"
		},
		onLaunch: function() {
			console.log('App Launch')
		},
		onShow: function() {
			console.log('App Show')
		},
		onHide: function() {
			console.log('App Hide')
		}
	}
</script>

<style>
	@import url("/components/gaoyia-parse/parse.css");

	/*每个页面公共css */
	page {
		height: 100%;
	}

	.root-padding {
		padding: 0 30rpx;
		height: 100%;
	}

	.common-padding {
		padding: 0 30rpx;
	}

	.active-color {
		color: #d81e06 !important;
	}
</style>

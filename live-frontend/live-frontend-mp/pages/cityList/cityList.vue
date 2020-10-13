<template>
	<view>
		<city-select @cityClick="cityClick" :formatName="formatName" :obtainCitys="obtainCitys" :isSearch="true" ref="citys"></city-select>
	</view>
</template>


<script>
	import citySelect from '@/components/city-select/city-select.vue'
	import httpUtils from '../../common/util/httpUtils.js';

	export default {
		data() {
			return {
				formatName: 'name',
				activeCity: {},
				obtainCitys: []
			}
		},
		components: {
			citySelect
		},
		async onLoad() {
			//修改需要构建索引参数的名称
			this.formatName = 'name'

			// 加载数据
			uni.showLoading({
				title: '数据加载中...'
			});
			let [cityData] = await httpUtils.postJson("/setting/queryCity", {});
			this.obtainCitys = cityData.body;
			uni.hideLoading();
		},
		methods: {
			async cityClick(item) {
				await httpUtils.postJson("/user/updateUserInfo", {
					cityId: item.id
				});
				uni.navigateBack({
					delta: 1
				});
			}
		}
	}
</script>


<style></style>

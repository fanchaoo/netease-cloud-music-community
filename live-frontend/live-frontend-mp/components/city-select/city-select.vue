<template>
	<!-- 城市选择-->
	<view class="city-select">
		<scroll-view :scroll-top="scrollTop" scroll-y="true" class="city-select-main" id="city-select-main" :scroll-into-view="toView">
			<!-- 预留搜索-->
			<view class="city-serach" v-if="isSearch"><input @input="keyInput" placeholder="请输入城市名称" class="city-serach-input" /></view>
			<!-- 当前定位城市 -->
			<view class="hot-title" v-if="activeCity && !serachCity">当前定位城市</view>
			<view class="hot-city" v-if="activeCity && !serachCity">
				<view class="hot-item" @click="cityTrigger(activeCity)">{{ activeCity[formatName] }}</view>
			</view>
			<!-- 热门城市 -->
			<view class="hot-title" v-if="hotCity.length > 0 && !serachCity">热门城市</view>
			<view class="hot-city" v-if="hotCity.length > 0 && !serachCity">
				<template v-for="(item, index) in hotCity">
					<view :key="index" @click="cityTrigger(item, 'hot')" class="hot-item">{{ item[formatName] }}</view>
				</template>
			</view>
			<!-- 城市列表(搜索前) -->
			<view class="citys" v-if="!serachCity">
				<view v-for="(city, index) in sortItems" :key="index" v-show="city.isCity">
					<view class="citys-item-letter" :id="'city-letter-' + (city.name === '#' ? '0' : city.name)">{{ city.name }}</view>
					<view class="citys-item" v-for="(item, inx) in city.citys" :key="inx" @click="cityTrigger(item)">{{ item.cityName }}</view>
				</view>
			</view>
			<!-- 城市列表(搜索后)  -->
			<view class="citys" v-if="serachCity">
				<view v-for="(item, index) in searchDatas" :key="index">
					<view class="citys-item" :key="inx" @click="cityTrigger(item)">{{ item.name }}</view>
				</view>
			</view>
		</scroll-view>
		<!-- 城市选择索引-->
		<view class="city-indexs-view" v-if="!serachCity">
			<view class="city-indexs">
				<view v-for="(cityIns, index) in handleCity" v-show="cityIns.isCity" :key="index" @click="cityindex(cityIns.forName)">{{ cityIns.name }}</view>
			</view>
		</view>
	</view>
</template>

<script>
import citySelect from './citySelect.js'
export default {
	props: {
		//传入要排序的名称
		formatName: {
			type: String,
			default: 'cityName'
		},
		//当前定位城市
		activeCity: {
			type: Object,
			default: () => null
		},
		//热门城市
		hotCity: {
			type: Array,
			default: () => []
		},
		//城市数据
		obtainCitys: {
			type: Array,
			default: () => []
		},
		//是否有搜索
		isSearch: {
			type: Boolean,
			default: true
		}
	},
	data() {
		return {
			toView: 'city-letter-Find', //锚链接 初始值
			scrollTop: 0, //scroll-view 滑动的距离
			cityindexs: [], // 城市索引
			activeCityIndex: '', // 当前所在的城市索引
			handleCity: [], // 处理后的城市数据
			serachCity: '', // 搜索的城市
			cityData: []
		}
	},
	computed: {
		/**
		 * @desc 城市列表排序
		 * @return  Array
		 */
		sortItems() {
			for (let index = 0; index < this.handleCity.length; index++) {
				if (this.handleCity[index].isCity) {
					let cityArr = this.handleCity[index].citys
					cityArr = cityArr.sort(function(a, b) {
						var value1 = a.unicode
						var value2 = b.unicode
						return value1 - value2
					})
				}
			}
			return this.handleCity
		},
		/**
		 * @desc 搜索后的城市列表
		 * @return Array
		 */
		searchDatas() {
			var searchData = []
			for (let i = 0; i < this.cityData.length; i++) {
				if (this.cityData[i][this.formatName].indexOf(this.serachCity) !== -1) {
					searchData.push({
						oldData: this.cityData[i],
						name: this.cityData[i][this.formatName]
					})
				}
			}
			return searchData
		}
	},
	created() {
		// 初始化城市数据
		this.cityData = this.obtainCitys
		this.initializationCity()
		this.buildCityindexs()
	},
	watch: {
		obtainCitys(newData) {
			this.updateCitys(newData)
		}
	},
	methods: {
		/**
		 * @desc 初始化
		 */
		updateCitys(data) {
			if (data && data.length) {
				this.cityData = data
				this.initializationCity()
				this.buildCityindexs()
			}
		},
		/**
		 * @desc 监听输入框的值
		 */
		keyInput(event) {
			this.serachCity = event.detail.value
			console.log(this.serachCity)
		},
		/**
		 * @desc 初始化城市数据
		 * @return undefind
		 */
		initializationCity() {
			this.handleCity = []
			const cityLetterArr = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '#']
			for (let index = 0; index < cityLetterArr.length; index++) {
				this.handleCity.push({
					name: cityLetterArr[index],
					isCity: false, // 用于区分是否含有当前字母开头的城市
					citys: [], // 存放城市首字母含是此字母的数组
					forName: 'city-letter-' + cityLetterArr[index] //label的绑定
				})
			}
		},
		/**
		 * @desc 得到城市的首字母
		 * @param str String
		 */
		getLetter(str) {
			return citySelect.getFirstLetter(str[0])
		},
		/**
		 * @desc 构建城市索引
		 * @return undefind
		 */
		buildCityindexs() {
			this.cityindexs = []
			for (let i = 0; i < this.cityData.length; i++) {
				// 获取首字母
				const cityLetter = this.getLetter(this.cityData[i][this.formatName]).firstletter
				// 获取当前城市首字母的unicode，用作后续排序
				const unicode = this.getLetter(this.cityData[i][this.formatName]).unicode

				const index = this.cityIndexPosition(cityLetter)
				if (this.cityindexs.indexOf(cityLetter) === -1) {
					this.handleCity[index].isCity = true
					this.cityindexs.push(cityLetter)
				}

				this.handleCity[index].citys.push({
					cityName: this.cityData[i][this.formatName],
					unicode: unicode,
					oldData: this.cityData[i]
				})
			}
		},
		/**
		 * @desc 滑动到城市索引所在的地方
		 * @param id String 城市索引
		 */
		cityindex(id) {
			this.toView = id
			// //创建节点查询器
			// const query = uni.createSelectorQuery().in(this)
			// var that = this
			// that.scrollTop = 0
			// //滑动到指定位置(解决方法:重置到顶部，重新计算，影响:页面会闪一下)
			// setTimeout(() => {
			// 	query
			// 		.select('#city-letter-' + (id === '#' ? '0' : id))
			// 		.boundingClientRect(data => {
			// 			// console.log("得到布局位置信息" + JSON.stringify(data));
			// 			// console.log("节点离页面顶部的距离为" + data.top);
			// 			data ? (that.scrollTop = data.top) : void 0
			// 		})
			// 		.exec()
			// }, 0)
		},
		/**
		 * @desc 获取城市首字母的unicode
		 * @param letter String 城市索引
		 */
		cityIndexPosition(letter) {
			if (!letter) {
				return ''
			}
			const ACode = 65
			return letter === '#' ? 26 : letter.charCodeAt(0) - ACode
		},
		/** @desc 城市列表点击事件
		 *  @param Object
		 */
		cityTrigger(item) {
			// 传值到父组件
			this.$emit('cityClick', item.oldData ? item.oldData : item)
		}
	}
}
</script>

<style lang="scss">
//宽度转换vw
@function vww($number) {
	@return ($number / 375) * 750 + rpx;
}

view {
	box-sizing: border-box;
}

.city-serach {
	width: 100%;
	color: #4a4a4a;
	padding: 0 vww(10);

	&-input {
		margin: vww(10) 0;
		height: vww(40);
		line-height: vww(40);
		font-size: vww(14);
		padding: 0 vww(5);
		border: 1px solid #EDEDED;
		border-radius: 3px;
	}
}

.city-select-main {
	position: relative;
	// overflow: scroll;
	// -webkit-overflow-scrolling: touch;
	width: 100%;
	height: 100%;
	background: #f6f5fa;
	// overflow-y: auto;
}

.city-select {
	position: relative;
	width: 100vw;
	height: 100vh;
	background: #f6f5fa;

	// 热门城市
	.hot-title {
		padding-left: vww(23);
		width: 100vw;
		font-size: 14px;
		line-height: vww(40);
		color: #9b9b9b;
	}

	.hot-city {
		padding-left: vww(23);
		padding-right: vww(20);
		overflow: hidden;
		width: 100vw;

		.hot-item {
			float: left;
			padding: 0 vww(5);
			margin-right: vww(16);
			margin-bottom: vww(6);
			overflow: hidden;
			width: vww(100);
			height: vww(31);
			font-size: 14px;
			text-align: center;

			display: -webkit-box;
			-webkit-box-orient: vertical;
			-webkit-line-clamp: 1;

			line-height: vww(31);
			color: #4a4a4a;
			background: #fff;
			border: 1px solid #ebebf0;

			&:nth-child(3n) {
				margin-right: 0;
			}
		}

		.hot-hidden {
			display: none;
			margin-right: 0;
		}
	}

	.citys {
		> view {
			padding-left: vww(18);
			width: 100%;
			font-size: 14px;
			background: #fff;

			.citys-item-letter {
				margin-left: vww(-18);
				padding-left: vww(18);
				margin-top: -1px;
				width: 100vw;
				line-height: vww(30);
				color: #9b9b9b;
				background: #f6f5fa;
				border-top: none;
			}

			.citys-item {
				width: 100%;
				line-height: vww(50);
				color: #4a4a4a;
				border-bottom: 1px solid #ebebf0;

				&:last-child {
					border: none;
				}
			}
		}
	}

	.city-indexs-view {
		position: absolute;
		right: 0;
		top: 0;
		z-index: 999;
		display: flex;
		width: vww(20);
		height: 100%;
		text-align: center;

		.city-indexs {
			width: vww(20);
			text-align: center;
			vertical-align: middle;
			align-self: center;

			> view {
				margin-bottom: vww(10);
				width: vww(20);
				font-size: 12px;
				color: #4a4a4a;

				&:last-child {
					margin-bottom: 0;
				}
			}
		}
	}
}
</style>

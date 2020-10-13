<template>
	<view class="feedback-root">

		<!-- 类型 -->
		<view class="feedback-type-wrapper common-padding feedback-item-margin-top">
			<view class="feedback-type-title">
				您在哪方面遇到了问题
			</view>
			<radio-group class="feedback-type">
				<label class="feedback-type-radio">
					<radio value="QUESTION" v-model="type" checked /><text>问题</text>
				</label>
				<label class="feedback-type-radio">
					<radio value="SEGGESTION" v-model="type" /><text>建议</text>
				</label>
				<label class="feedback-type-radio">
					<radio value="OTHER" v-model="type" /><text>其它</text>
				</label>
			</radio-group>
		</view>

		<!-- 内容 -->
		<view class="feedback-content-wrapper common-padding feedback-item-margin-top">
			<view class="feedback-content-title">
				意见反馈
			</view>
			<textarea class="feedback-content" v-model="content" required placeholder-class="feedback-content-placeholder" value=""
			 placeholder="我们将不断为您改进" />
			</view>
		
		<view class="feedback-other-wrapper common-padding feedback-item-margin-top">
			<view class="feedback-other-title">
				联系方式（选填）
			</view>
			<input v-model="contactInfo" placeholder-class="feedback-content-placeholder" type="text" value="" placeholder="您的联系方式" />
		</view>
		
		<view class="feedback-submit" @click="openPopup">
			提交
		</view>
		
		<!-- 弹窗 -->
		<uni-popup ref="popup" type="center">
			<view class="popup-content">
				<view class="popup-title">
					意见反馈
				</view>
				<view class="popup-desc">
					是否确认提交
				</view>
				<view class="popup-button">
					<view class="popup-cancel" @click="cancelPopup">
						取消
					</view>
					<view class="popup-confirm"  @click="confirmPopup">
						确认
					</view>
				</view>
			</view>
		</uni-popup>

	</view>
</template>

<script>
	import httpUtils from '../../common/util/httpUtils.js';
	import uniPopup from '@/components/uni-popup/uni-popup.vue';
	
	export default {
		comments:{
			uniPopup
		},
		
		data() {
			return {
				type:"QUESTION",
				content:"",
				contactInfo:""
			}
		},
		methods: {
			// 打开弹层
			openPopup() {
				this.$refs.popup.open();
			},
			
			cancelPopup(){
				this.$refs.popup.close();
			},
			
			async confirmPopup(){
				this.$refs.popup.close();
				await this.addFeedback();
			},
			
			async addFeedback(){
				if (!this.type) {
					uni.showToast({
						title: "类型不能为空",
						duration: 2000
					});
					return;
				}
				
				if (!this.content) {
					uni.showToast({
						title: "内容不能为空",
						duration: 2000
					});
					return;
				}
				
				await httpUtils.postJson("/setting/addFeedback", {
					type:this.type,
					content:this.content,
					contactInfo:this.contactInfo
				});
				uni.showToast({
					title: "提交成功",
					duration: 10000,
					mask:true,
					success() {
						setTimeout(uni.navigateBack,1000);
					}
				});
			}

		}
	}
</script>

<style>
	.feedback-root{
		background-color: #F2F3F7;
		height: 100%;
		font-size: 32rpx;
		color: #0B0B0B;
	}
	
	.feedback-item-margin-top{
		margin-top: 16rpx;
		background-color: white;
	}
	
	.feedback-type-wrapper{
		padding-bottom: 40rpx;
	}
	.feedback-type{
		font-size: 30rpx;
		color: #3D3D3D;
		display: flex;
		flex-direction: row;
		justify-content: flex-start;
		margin-top: 30rpx;
	}
	.feedback-type-radio{
		margin-right: 100rpx;
	}
	.feedback-type-radio radio{
		transform:scale(0.7)
	}
	
	.feedback-content-wrapper{
		padding-top: 30rpx;
	}
	.feedback-content-title{
		font-size: 30rpx;
		color: #3D3D3D;
		padding-bottom: 10rpx;
	}
	.feedback-content{
		width: 100%;
		height: 160rpx;
		font-size: 30rpx;
		line-height: 48rpx;
		letter-spacing: 1rpx;
	}
	.feedback-content-placeholder{
		color: #959595;
	}
	
	.feedback-other-wrapper{
		font-size: 30rpx;
		padding-top: 30rpx;
		padding-bottom: 30rpx;
		display: flex;
		flex-direction: row;
	}
	
	.feedback-other-title{
		width: 240rpx;
	}
	.feedback-other-wrapper input{
		width: 450rpx
	}
	
	.feedback-submit{
		font-size: 34rpx;
		display: flex;
		justify-content: center;
		align-items: center;
		color: white;
		margin: 80rpx 0;
		width: 750rpx;
		height: 100rpx;
		background-color: #0fb700;
	}
	
	/* 弹窗 */
	.popup-content{
		position: relative;
		background-color: #ffffff;
		width: 602rpx;
		border-radius: 12rpx;
	}
	
	.popup-title{
		font-size: 30rpx;
		font-weight: bold;
		height: 130rpx;
		box-sizing: border-box;
		text-align: center;
		padding-top: 60rpx;
		color: #121212;
	}
	
	.popup-desc{
		font-size: 32rpx;
		padding-bottom: 188rpx;
		padding-left: 50rpx;
		padding-right: 50rpx;
		display: flex;
		flex-direction: row;
		justify-content: center;
		color: #1C1C1C;
	}
	
	.popup-button{
		position: absolute;
		bottom: 0;
		left: 0;
		display: flex;
		flex-direction: row;
		border-top: 2rpx solid #F1F1F1;
	}
	.popup-cancel{
		font-size: 32rpx;
		width: 300rpx;
		height: 110rpx;
		line-height: 110rpx;
		text-align: center;
		border-right: 2rpx solid #F1F1F1;
		color: #0B0B0B;
		font-weight: bold;
	}
	
	.popup-confirm{
		font-size: 32rpx;
		width: 300rpx;
		height: 110rpx;
		line-height: 110rpx;
		text-align: center;
		color: #f6423d;
		font-weight: bold;
	}
	.popup-cancel:active,.popup-confirm:active{
		background-color: #f0f0f0;
	}

</style>

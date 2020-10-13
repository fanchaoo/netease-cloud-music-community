<template>
	<view class="user-info-root">
		<view class="user-info-item user-info-item-avatar" @click="updateAvatarUrl">
			<view class="user-info-item-title">
				头像
			</view>
			<view class="user-info-item-value">
				<image class="user-info-item-value-avatar" :src="userInfo.avatarUrl" mode="aspectFill"></image>
			</view>
			<view class="user-info-item-go-to">
			</view>
		</view>

		<view class="user-info-item" @click="goToUpdateName">
			<view class="user-info-item-title">
				昵称
			</view>
			<view class="user-info-item-value">
				{{userInfo.name}}
			</view>
			<view class="user-info-item-go-to">
			</view>
		</view>

		<view class="user-info-item" @click="goToUpdateGender">
			<view class="user-info-item-title">
				性别
			</view>
			<view class="user-info-item-value">
				{{userInfo.gender === 1 ? '男' : '女'}}
			</view>
			<view class="user-info-item-go-to">
			</view>
		</view>

		<navigator class="user-info-item" url="/pages/cityList/cityList">
			<view class="user-info-item-title">
				地区
			</view>
			<view class="user-info-item-value">
				{{userInfo.province}} {{userInfo.city}}
			</view>
			<view class="user-info-item-go-to">
			</view>
		</navigator>

		<view class="user-info-item" @click="goToUpdateUniversity">
			<view class="user-info-item-title">
				大学
			</view>
			<view class="user-info-item-value">
				{{userInfo.university}}
			</view>
			<view class="user-info-item-go-to">
			</view>
		</view>

		<view class="user-info-item" @click="goToUpdateDescription">
			<view class="user-info-item-title">
				个人签名
			</view>
			<view class="user-info-item-value">
				{{userInfo.description}}
			</view>
			<view class="user-info-item-go-to">
			</view>
		</view>

		<!-- 弹窗 -->
		<uni-popup ref="popup" type="center">
			<view class="popup-content">
				<view class="popup-title">
					{{currentFieldDesc}}
				</view>
				<input ref="inputValue" :focus="inputFocus" class="popup-desc" type="text" v-model="currentFieldValue" v-if="currentFieldName !== 'GENDER' " />
				<radio-group class="gender-input popup-desc" @change="radioChange" v-if="currentFieldName === 'GENDER' ">
					<label class="gender-input-radio">
						<radio value="MAN" :checked="gender==='MAN' " /><text>男</text>
					</label>
					<label class="gender-input-radio">
						<radio value="WOMAN" :checked="gender==='WOMAN' " /><text>女</text>
					</label>
				</radio-group>
				<view class="popup-button">
					<view class="popup-cancel" @click="cancelPopup">
						取消
					</view>
					<view class="popup-confirm" @click="confirmPopup">
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
		comments: {
			uniPopup
		},

		data() {
			return {
				inputFocus: false,
				currentFieldName: "",
				currentFieldDesc: "",
				currentFieldValue: "",
				userInfo: {},
				gender: ""
			}
		},

		async onShow() {
			// 加载数据
			await this.loadUserInfo();
			this.gender = this.userInfo.gender === 1 ? 'MAN' : 'WOMAN';
		},

		methods: {
			radioChange: function(event) {
				this.gender = event.target.value;
			},

			async loadUserInfo() {
				uni.showLoading({
					title: '数据加载中...'
				});
				let [userInfoData] = await httpUtils.postJson("/user/getUserInfo", {});
				this.userInfo = userInfoData.body;
				uni.hideLoading();
			},

			cancelPopup() {
				this.$refs.popup.close();
			},

			async confirmPopup() {
				if (this.currentFieldName === "NAME") {
					if (this.currentFieldValue && this.currentFieldValue.length > 50) {
						uni.showToast({
							title: "昵称不能多于50字符",
							duration: 2000
						});
						return;
					}
					await httpUtils.postJson("/user/updateUserInfo", {
						name: this.currentFieldValue
					});
				}

				if (this.currentFieldName === "UNIVERSITY") {
					if (this.currentFieldValue && this.currentFieldValue.length > 50) {
						uni.showToast({
							title: "大学不能多于50字符",
							duration: 2000
						});
						return;
					}
					await httpUtils.postJson("/user/updateUserInfo", {
						university: this.currentFieldValue
					});
				}

				if (this.currentFieldName === "DESCRIPTION") {
					if (this.currentFieldValue && this.currentFieldValue.length > 255) {
						uni.showToast({
							title: "个人签名不能多于50字符",
							duration: 2000
						});
						return;
					}
					await httpUtils.postJson("/user/updateUserInfo", {
						description: this.currentFieldValue
					});
				}

				if (this.currentFieldName === "GENDER") {
					await httpUtils.postJson("/user/updateUserInfo", {
						gender: this.gender
					});
				}

				await this.loadUserInfo();
				this.$refs.popup.close();
			},

			goToUpdateName() {
				this.inputFocus = true;
				this.currentFieldName = "NAME";
				this.currentFieldDesc = "修改昵称";
				this.currentFieldValue = this.userInfo.name;
				this.$refs.popup.open();
			},

			goToUpdateUniversity() {
				this.inputFocus = true;
				this.currentFieldName = "UNIVERSITY";
				this.currentFieldDesc = "修改大学";
				this.currentFieldValue = this.userInfo.university;;
				this.$refs.popup.open();
			},

			goToUpdateDescription() {
				this.inputFocus = true;
				this.currentFieldName = "DESCRIPTION";
				this.currentFieldDesc = "修改个人签名";
				this.currentFieldValue = this.userInfo.description;;
				this.$refs.popup.open();
			},

			goToUpdateGender() {
				this.inputFocus = true;
				this.currentFieldName = "GENDER";
				this.currentFieldDesc = "修改性别";
				this.$refs.popup.open();
			},

			updateAvatarUrl() {
				uni.chooseImage({
					count: 1,
					sizeType: ['original'],
					sourceType: ['album'],
					success: async (res) => {
						let tempFilePath = res.tempFilePaths[0];

						uni.showLoading({
							title: '头像上传中...'
						});
						let [url] = await httpUtils.uploadFile(tempFilePath);
						uni.hideLoading();

						await httpUtils.postJson("/user/updateUserInfo", {
							avatarUrl: url
						});
						await this.loadUserInfo();
						this.$refs.popup.close();
					}
				});
			},

		}
	}
</script>

<style>
	.user-info-root {
		background-color: #EDEDED;
		height: 100%;
	}

	.user-info-item {
		position: relative;
		padding: 30rpx 30rpx;
		display: flex;
		flex-direction: row;
		justify-content: space-between;
		align-items: center;
		margin-bottom: 16rpx;
		background-color: white;
		color: #030303;
	}

	.user-info-item-avatar {
		padding-top: 10rpx;
		padding-bottom: 2rpx;
	}

	.user-info-item:active {
		background-color: #D5D5D5;
	}

	.user-info-item-title {
		font-size: 32rpx;
	}

	.user-info-item-value {
		font-size: 32rpx;
		margin-right: 30rpx;
		color: #797979;
	}

	.user-info-item-value-avatar {
		width: 116rpx;
		height: 116rpx;
		flex-shrink: 0;
		border-radius: 10rpx;
	}

	.user-info-item-go-to {
		position: absolute;
		top: 46rpx;
		right: 30rpx;
		width: 14rpx;
		height: 14rpx;
		border-top: 4rpx solid #ACACAC;
		border-right: 4rpx solid #ACACAC;
		transform: rotate(45deg);
	}

	/* 弹窗 */
	.popup-content {
		position: relative;
		background-color: #ffffff;
		width: 602rpx;
		border-radius: 12rpx;
	}

	.popup-title {
		font-size: 30rpx;
		font-weight: bold;
		height: 130rpx;
		box-sizing: border-box;
		text-align: center;
		padding-top: 60rpx;
		color: #121212;
	}

	.popup-desc {
		text-align: center;
		font-size: 32rpx;
		padding-bottom: 188rpx;
		padding-left: 50rpx;
		padding-right: 50rpx;
		display: flex;
		flex-direction: row;
		justify-content: center;
		color: #1C1C1C;
		border-bottom: 2rpx solid #F1F1F1;
	}

	.popup-button {
		position: absolute;
		bottom: 0;
		left: 0;
		display: flex;
		flex-direction: row;
		border-top: 2rpx solid #F1F1F1;
	}

	.popup-cancel {
		font-size: 32rpx;
		width: 300rpx;
		height: 110rpx;
		line-height: 110rpx;
		text-align: center;
		border-right: 2rpx solid #F1F1F1;
		color: #0B0B0B;
		font-weight: bold;
	}

	.popup-confirm {
		font-size: 32rpx;
		width: 300rpx;
		height: 110rpx;
		line-height: 110rpx;
		text-align: center;
		color: #f6423d;
		font-weight: bold;
	}

	.popup-cancel:active,
	.popup-confirm:active {
		background-color: #f0f0f0;
	}

	.gender-input {}

	.gender-input-radio {
		margin-left: 30rpx;
		margin-right: 30rpx;
	}

	.gender-input-radio radio {
		transform: scale(0.7)
	}
</style>

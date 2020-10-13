package me.fanchaoo.web.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import lombok.extern.slf4j.Slf4j;
import me.fanchaoo.constant.JwtConstant;
import me.fanchaoo.constant.RedisKey;
import me.fanchaoo.dbo.ArtistDBO;
import me.fanchaoo.dbo.UserDBO;
import me.fanchaoo.enums.EnumProvider;
import me.fanchaoo.exception.BusinessException;
import me.fanchaoo.util.AssertUtils;
import me.fanchaoo.util.HttpUtils;
import me.fanchaoo.util.MD5Utils;
import me.fanchaoo.util.WeixinUtils;
import me.fanchaoo.web.dao.mapper.ArtistQueryMapper;
import me.fanchaoo.web.dao.mapper.UserMapper;
import me.fanchaoo.web.dao.mapper.UserQueryMapper;
import me.fanchaoo.web.dto.LoginUserDTO;
import me.fanchaoo.web.request.LoginRequest;
import me.fanchaoo.web.service.LoginService;
import me.fanchaoo.web.util.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Objects;

@Slf4j
@Service
public class LoginServiceImpl implements LoginService {


    @Resource
    private UserQueryMapper userQueryMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private ArtistQueryMapper artistQueryMapper;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    @Transactional
    public LoginUserDTO login(LoginRequest request) throws Exception {

        ArtistDBO artistDBO = artistQueryMapper.getByRandomKey(request.getRandomKey());
        AssertUtils.notNull(artistDBO, "未找到appId配置");

        String appId = "";
        String appSecret = "";
        JSONObject unionInfo;
        EnumProvider provider = request.getProvider();
        if (Objects.equals(provider, EnumProvider.weixin)) {
            appId = artistDBO.getMpWeixinAppId();
            appSecret = artistDBO.getMpWeixinAppSecret();
        }
        if (Objects.equals(provider, EnumProvider.qq)) {
            appId = artistDBO.getMpQqAppId();
            appSecret = artistDBO.getMpQqAppSecret();
        }
        String sessionKey = getSessionKey(provider, request.getCode(), appId, appSecret);
        unionInfo = JSON.parseObject(WeixinUtils.decryptData(request.getEncryptedData(), sessionKey, request.getIv()));

        UserDBO userDBO = addOrUpdateUser(provider, request.getUserInfo(), unionInfo, artistDBO.getId());

        return createToken(userDBO, provider);
    }

    private UserDBO addOrUpdateUser(EnumProvider provider, LoginRequest.UserInfo userInfo, JSONObject unionInfo, Long artistId) {

        UserDBO userDBO = null;
        if (Objects.equals(provider, EnumProvider.weixin)) {
            userDBO = userQueryMapper.getByMiniWeixinUnionId(artistId, unionInfo.getString("unionId"), unionInfo.getString("openId"));
        }
        if (Objects.equals(provider, EnumProvider.qq)) {
            userDBO = userQueryMapper.getByMiniQQUnionId(artistId, unionInfo.getString("unionId"), unionInfo.getString("openId"));
        }

        if (Objects.isNull(userDBO)) {
            userDBO = new UserDBO();
            userDBO.setArtistId(artistId);
            userDBO.setName(userInfo.getNickName());
            userDBO.setGender(userInfo.getGender());
            userDBO.setAvatarUrl(userInfo.getAvatarUrl());
            userDBO.setCountry(userInfo.getCountry());
            userDBO.setProvince(userInfo.getProvince());
            userDBO.setCity(userInfo.getCity());

            if (Objects.equals(provider, EnumProvider.weixin)) {
                userDBO.setMiniWeixinUnionId(unionInfo.getString("unionId"));
                userDBO.setMiniWeixinOpenId(unionInfo.getString("openId"));
            }
            if (Objects.equals(provider, EnumProvider.qq)) {
                userDBO.setMiniQqUnionId(unionInfo.getString("unionId"));
                userDBO.setMiniQqOpenId(unionInfo.getString("openId"));
            }

            userMapper.insertSelective(userDBO);
        } else {
            userDBO.setGender(userInfo.getGender());
            userDBO.setAvatarUrl(userInfo.getAvatarUrl());
            userDBO.setCountry(userInfo.getCountry());
            userDBO.setProvince(userInfo.getProvince());
            userDBO.setCity(userInfo.getCity());
            userMapper.updateByPrimaryKeySelective(userDBO);
        }

        return userDBO;
    }


    private String getSessionKey(EnumProvider provider, String jsCode, String appId, String appSecret) {

        String qqUrl = "https://api.q.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";
        String weixinUrl = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appId + "&secret=" + appSecret + "&js_code=" + jsCode + "&grant_type=authorization_code";

        String url = "";
        if (Objects.equals(provider, EnumProvider.weixin)) {
            url = String.format(weixinUrl, appId, appSecret, jsCode);
        }
        if (Objects.equals(provider, EnumProvider.qq)) {
            url = String.format(qqUrl, appId, appSecret, jsCode);
        }
        String unionInfoStr = HttpUtils.getJSON(url);

        JSONObject openInfo = JSON.parseObject(unionInfoStr);
        String errCode = openInfo.getString("errcode");
        if (!(StringUtils.isBlank(errCode) || Objects.equals(errCode, "0"))) {
            throw new BusinessException("获取unionId出错：" + unionInfoStr);
        }
        return openInfo.getString("session_key");
    }

    public LoginUserDTO createToken(UserDBO userDBO, EnumProvider provider) throws Exception {

        ArtistDBO artistDBO = artistQueryMapper.selectByPrimaryKey(userDBO.getArtistId());
        String artistName = artistDBO.getName();

        String unionId = "";
        if (Objects.equals(provider, EnumProvider.weixin)) {
            unionId = userDBO.getMiniWeixinUnionId();
        }
        if (Objects.equals(provider, EnumProvider.qq)) {
            unionId = userDBO.getMiniQqUnionId();
        }

        net.minidev.json.JSONObject jsonObject = new net.minidev.json.JSONObject();
        jsonObject.put("iss", "me.fanchaoo");
        jsonObject.put("iat", Calendar.getInstance().getTimeInMillis());

        // 自定义属性
        jsonObject.put("artistId", userDBO.getArtistId());
        jsonObject.put("artistName", artistDBO.getName());
        jsonObject.put("userId", userDBO.getId());
        jsonObject.put("userName", userDBO.getName());
        jsonObject.put("unionId", unionId);

        // 生成token串
        JWSObject jwsObject = new JWSObject(new JWSHeader(JWSAlgorithm.HS256), new Payload(jsonObject));
        byte[] sharedKey = JwtConstant.SIGNATURE.getBytes();
        jwsObject.sign(new MACSigner(sharedKey));
        String tokenStr = jwsObject.serialize();

        // 添加到redis
        String userKey = artistName + RedisKey.LOGIN_USER + ":" + userDBO.getId();
        String tokenMd5 = MD5Utils.md5Hex(tokenStr);
        redisUtils.set(userKey, tokenMd5, JwtConstant.EXPIRE_SECOND);

        return LoginUserDTO.builder()
                .artistId(userDBO.getArtistId())
                .artistName(artistName)
                .userId(userDBO.getId())
                .userName(userDBO.getName())
                .userAvatarUrl(userDBO.getAvatarUrl())
                .unionId(unionId)
                .token(tokenStr)
                .build();
    }
}

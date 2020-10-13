package me.fanchaoo.web.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.fanchaoo.dbo.RegionCityDBO;
import me.fanchaoo.dbo.RegionProvinceDBO;
import me.fanchaoo.dbo.UserDBO;
import me.fanchaoo.enums.EnumGender;
import me.fanchaoo.util.BeanCopyUtils;
import me.fanchaoo.util.UserContext;
import me.fanchaoo.web.dao.mapper.*;
import me.fanchaoo.web.dto.LoginUserDTO;
import me.fanchaoo.web.dto.UserDTO;
import me.fanchaoo.web.request.GetUserInfoRequest;
import me.fanchaoo.web.request.UpdateUserInfoRequest;
import me.fanchaoo.web.response.BaseResponse;
import me.fanchaoo.web.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private UserQueryMapper userQueryMapper;
    @Resource
    private RegionCityQueryMapper regionCityQueryMapper;
    @Resource
    private RegionProvinceQueryMapper regionProvinceQueryMapper;

    @Override
    public BaseResponse<UserDTO> getUserInfo(GetUserInfoRequest request) {
        BaseResponse<UserDTO> response = new BaseResponse<>();
        LoginUserDTO loginUser = UserContext.getLoginUser();
        Long userId = loginUser.getUserId();
        Long artistId = loginUser.getArtistId();

        if (request.getUserId() != null) {
            userId = request.getUserId();
        }

        UserDBO userDBO = userQueryMapper.getById(artistId, userId);
        UserDTO dto = BeanCopyUtils.copy(userDBO, UserDTO.class);
        response.setBody(dto);
        return response;
    }

    @Override
    public BaseResponse updateUserInfo(UpdateUserInfoRequest request) {
        BaseResponse response = new BaseResponse<>();
        LoginUserDTO loginUser = UserContext.getLoginUser();
        Long userId = loginUser.getUserId();
        Long artistId = loginUser.getArtistId();

        UserDBO dbo = BeanCopyUtils.copy(request, UserDBO.class);
        dbo.setArtistId(artistId);
        dbo.setId(userId);

        EnumGender gender = request.getGender();
        if (Objects.nonNull(gender)) {
            dbo.setGender(gender.getValue());
        }

        String cityId = request.getCityId();
        if (StringUtils.isNotBlank(cityId)) {
            RegionCityDBO city = regionCityQueryMapper.selectByPrimaryKey(cityId);
            if (Objects.nonNull(city)) {
                String provinceId = city.getProvinceId();
                RegionProvinceDBO province = regionProvinceQueryMapper.selectByPrimaryKey(provinceId);
                if (Objects.nonNull(province)) {
                    dbo.setProvince(province.getName());
                    dbo.setCity(city.getName());
                }
            }
        }

        userMapper.updateByPrimaryKeySelective(dbo);
        return response;
    }
}

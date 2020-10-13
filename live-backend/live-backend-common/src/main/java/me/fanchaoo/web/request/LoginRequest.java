package me.fanchaoo.web.request;

import lombok.Data;
import me.fanchaoo.enums.EnumProvider;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class LoginRequest {

    @NotBlank
    @Length(max = 50)
    private String code;

    @NotBlank
    @Length(max = 255)
    private String randomKey;

    @NotNull
    private EnumProvider provider;

    @NotNull
    @Valid
    private UserInfo userInfo;

    @NotBlank
    private String encryptedData;

    @NotBlank
    private String iv;

    @Data
    public static class UserInfo {

        @NotNull
        private String nickName;

        private int gender;
        private String city;
        private String province;
        private String country;
        private String avatarUrl;
    }
}

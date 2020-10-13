package me.fanchaoo.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginDTO {

    private String id;

    private String uri;

    private String operation;

    private Object request;

    private Object response;

    private Long artistId;

    private Long userId;

    private String userName;
}

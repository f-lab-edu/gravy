package kr.gravy.gravy.email.vo;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class RefreshTokenVO {

    private Long userId;
    private String token;
    private Date expiredAt;
}

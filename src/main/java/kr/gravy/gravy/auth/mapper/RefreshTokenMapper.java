package kr.gravy.gravy.auth.mapper;

import kr.gravy.gravy.auth.vo.UserVO;
import kr.gravy.gravy.email.vo.RefreshTokenVO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.Optional;

public interface RefreshTokenMapper {
    void insertRefreshToken(RefreshTokenVO refreshTokenVO);

    Optional<Date> getRefreshTokenExpiredAt(@Param("refreshToken") String refreshToken);

    Optional<UserVO> getUserByRefreshToken(@Param("refreshToken") String refreshToken);

}

package kr.gravy.gravy.mapper;

import kr.gravy.gravy.vo.RefreshTokenVO;
import kr.gravy.gravy.vo.UserVO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.Optional;

public interface RefreshTokenMapper {
    void insertRefreshToken(RefreshTokenVO refreshTokenVO);

    Optional<Date> getRefreshTokenExpiredAt(@Param("refreshToken") String refreshToken);

    Optional<UserVO> getUserByRefreshToken(@Param("refreshToken") String refreshToken);

}

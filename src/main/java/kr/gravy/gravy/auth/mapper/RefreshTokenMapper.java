package kr.gravy.gravy.auth.mapper;

import kr.gravy.gravy.auth.entity.RefreshToken;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface RefreshTokenMapper {
    void insertRefreshToken(RefreshToken refreshToken);

    Optional<LocalDateTime> getRefreshTokenExpiredAt(@Param("refreshToken") String refreshToken);

}

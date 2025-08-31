package kr.gravy.gravy.auth.mapper;

import kr.gravy.gravy.auth.entity.User;
import kr.gravy.gravy.auth.model.Grade;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;
import java.util.UUID;

public interface UserMapper {

    void userSignUp(User user);

    Optional<User> getUserByEmail(@Param("email") String email);

    boolean existsAlreadyEmail(@Param("email") String email);

    Optional<Grade> getUserGradeByPublicId(@Param("publicId") UUID userPublicId);

    Optional<User> getUserByRefreshToken(@Param("refreshToken") String refreshToken);
}

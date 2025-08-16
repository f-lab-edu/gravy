package kr.gravy.gravy.mapper;

import kr.gravy.gravy.enumeration.Grade;
import kr.gravy.gravy.vo.UserVO;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;
import java.util.UUID;

public interface UserMapper {

    void userSignUp(UserVO userVO);

    Optional<UserVO> getUserByEmail(@Param("email") String email);

    boolean existsAlreadyEmail(@Param("email") String email);

    Optional<Grade> getUserGradeByPublicId(@Param("publicId") UUID userPublicId);
}

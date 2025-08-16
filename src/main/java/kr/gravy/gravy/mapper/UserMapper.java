package kr.gravy.gravy.mapper;

import kr.gravy.gravy.vo.UserVO;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

public interface UserMapper {

    void userSignUp(UserVO userVO);

    Optional<UserVO> getUserByEmail(@Param("email") String email);

    boolean existsAlreadyEmail(@Param("email") String email);

}

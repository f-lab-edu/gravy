package kr.gravy.gravy.configuration;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("kr.gravy.gravy.mapper") // mapper 인터페이스 로딩
public class MyBatisConfiguration {

}

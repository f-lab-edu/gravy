package kr.gravy.gravy.infrastructure.configuration;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@MapperScan(basePackages = "kr.gravy.gravy.**.mapper")
public class MyBatisConfiguration {

}

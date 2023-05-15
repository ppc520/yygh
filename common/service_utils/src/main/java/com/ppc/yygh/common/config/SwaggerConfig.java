package com.ppc.yygh.common.config;

import com.google.common.base.Predicates;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootConfiguration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket getAdminDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("admin")
                .apiInfo(getAdminApiInfo())
                .select()
                //只显示admin路径下的页面
                .paths(Predicates.and(PathSelectors.regex("/admin/.*")))
                .build();
    }
    private ApiInfo getAdminApiInfo() {
        return new ApiInfoBuilder()
                .title("ppc管理员使用")
                .description("医药挂号管理员系统")
                .version("1.0")
                .contact(new Contact("ppc","","0638298212@qq.com"))
                .build();
    }

    @Bean
    public Docket getUserDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("user")
                .apiInfo(getUserApiInfo())
                .select()
                //只显示admin路径下的页面
                .paths(Predicates.and(PathSelectors.regex("/user/.*")))
                .build();
    }

    private ApiInfo getUserApiInfo() {
        return new ApiInfoBuilder()
                .title("用户使用")
                .version("1.0")
                .build();
    }

    @Bean
    public Docket getApiDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Api")
                .apiInfo(getApiInfo())
                .select()
                //只显示admin路径下的页面
                .paths(Predicates.and(PathSelectors.regex("/api/.*")))
                .build();
    }
    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .title("api使用")
                .description("第三方对接系统")
                .version("1.0")
                .contact(new Contact("ppc","","2638298212@qq.com"))
                .build();
    }
}

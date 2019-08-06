package com.alekseysamoylov.crawler.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType.SWAGGER_2
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2


@Configuration
@EnableSwagger2
class SwaggerConfig {

    @Bean
    fun api(): Docket {
        return Docket(SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.alekseysamoylov.crawler.controller"))
            .paths(PathSelectors.any())
            .build().apiInfo(
                ApiInfo(
                    "Team Recognition Project",
                    "Documentation automatically generated", "v1.0", null,
                    Contact(
                        "Aleksey Samoylov", "alekseysamoylov.com",
                        "alekseysamoylov89@gmail.com"
                    ), null, null, emptyList()
                )
            )
    }
}

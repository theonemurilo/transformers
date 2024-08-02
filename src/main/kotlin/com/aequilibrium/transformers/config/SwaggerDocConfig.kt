package com.aequilibrium.transformers.config

import com.aequilibrium.transformers.response.BattleResponse
import com.aequilibrium.transformers.response.NoBattleResponse
import com.aequilibrium.transformers.response.TiedBattleResponse
import com.aequilibrium.transformers.response.WinLossBattleResponse
import com.fasterxml.classmate.TypeResolver
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2


@Configuration
@EnableSwagger2
class SwaggerDocConfig {

    @Bean
    fun swagger(apiInfo: ApiInfo, typeResolver: TypeResolver): Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .additionalModels(
                        typeResolver.resolve(BattleResponse::class.java),
                        typeResolver.resolve(NoBattleResponse::class.java),
                        typeResolver.resolve(TiedBattleResponse::class.java),
                        typeResolver.resolve(WinLossBattleResponse::class.java)
                )
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.aequilibrium.transformers"))
                .paths(PathSelectors.any())
                .build()
    }

    @Bean
    @ConditionalOnMissingBean(ApiInfo::class)
    fun apiInfo(): ApiInfo {
        return ApiInfo.DEFAULT
    }
}

fun main() {
    println("Só de brinks #1")
    println("Só de brinks #2")
    println("Só de brinks #3")
    println("Só de brinks #4")
}

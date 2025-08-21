package com.herbology.handler

import com.herbology.config.Loggable
import com.herbology.annotation.Auth
import com.herbology.entity.Authority
import com.herbology.service.IAuthorityService
import lombok.RequiredArgsConstructor
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.core.Ordered
import org.springframework.stereotype.Component
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping
import java.time.LocalDateTime

/**
 * @author Roxy2222
 * @date 2025/7/27
 * @description
 */
@Component
@RequiredArgsConstructor
class UserAuthHandler(
	private val authorityService: IAuthorityService,
	private val urlMapping: RequestMappingHandlerMapping,
) : ApplicationRunner, Ordered, Loggable {
	override fun run(args: ApplicationArguments?) {
		val authList: MutableList<Authority> = mutableListOf<Authority>()
		urlMapping.handlerMethods.forEach { (info, method) ->
			method.getMethodAnnotation<Auth>(Auth::class.java)?.let { authAnnotation ->
				info?.pathPatternsCondition?.patternValues?.forEach { path ->
					val auth = Authority().apply {
						name = authAnnotation.value.ifBlank { path }
						status = authAnnotation.status
						auth = if (!authAnnotation.requireAuth) 1 else 0
						description = authAnnotation.description
						updateTime = LocalDateTime.now()
					}
					authList.add(auth)
				}
			}
		}
		authorityService.saveOrUpdateAuth(authList)
		log.info("动态权限更新完成, 共{}条", authList.size)
	}


	override fun getOrder(): Int = Ordered.HIGHEST_PRECEDENCE
}

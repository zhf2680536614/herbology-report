package com.herbology

import org.slf4j.LoggerFactory

/**
 * @author Roxy2222
 * @date 2025/7/27
 * @description
 */
interface Loggable {
	val log get() = LoggerFactory.getLogger(this::class.java)
}

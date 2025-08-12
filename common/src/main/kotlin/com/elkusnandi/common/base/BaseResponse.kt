package com.elkusnandi.common.base

import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity

class BaseResponse<DATA>(
    data: DATA? = null,
    status: HttpStatusCode,
    success: Boolean = true,
    message: String = ""
) : ResponseEntity<BaseResponseObject<DATA>>(BaseResponseObject(data, success, message), status)

data class BaseResponseObject<DATA>(
    val data: DATA? = null,
    val success: Boolean = true,
    val message: String = ""
)
package com.wzg.core.model

/**
 * 类描述:
 * 创建人:    wzg
 * 创建时间:  2018/2/1
 * 修改时间:  2018/2/1
 * 修改备注:  说明本次修改内容
 */
class ErrorModel {
    private var code: Int? = -1
    private var errorMsg: String? = ""

    constructor(code: Int) {
        this.code = code
    }

    constructor(errorMsg: String) {
        this.errorMsg = errorMsg
    }

    constructor(code: Int, errorMsg: String) {
        this.code = code
        this.errorMsg = errorMsg;
    }
}
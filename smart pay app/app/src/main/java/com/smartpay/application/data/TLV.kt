package com.smartpay.application.data

public class Tlv(
    var tag: Int,
    var length: Int,
    var value: String
) {

    override fun toString(): String {
        return "tag=[$tag],length=[$length],value=[$value]"
    }
}
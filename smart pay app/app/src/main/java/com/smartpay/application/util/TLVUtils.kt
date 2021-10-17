package com.smartpay.application.util

import android.util.Log
import com.smartpay.application.data.Tlv
import java.lang.StringBuilder

object TLVUtils {
    fun initHexStringByTLV(tlvList: List<Tlv>): String{
        var result = "0100"
        if (tlvList.isEmpty()) return result
        for(i in tlvList.indices){
            tlvList[i].let{
                result += String.format("%02X", it.tag.toByte())
                result += String.format("%02X", it.length.toByte())
                for(c in it.value){
                    c.code.toByte().let{
                        result += String.format("%02X", it)
                    }
                }
            }
        }
        return result
    }

    fun initTlvListByHexString(hexString: String): List<Tlv> {
        val tlvList: MutableList<Tlv> = ArrayList()
        var position = 0
        if (hexString.substring(0,4) == "0100") position += 4
        while (position != hexString.length) {
            val tag = hexString.substring( position, position + 2).toInt(16)
            position += 2
            val length = hexString.substring( position, position + 2).toInt(16)
            position += 2
            val stringBuilder = StringBuilder()
            for(i in 0 until length) {
                stringBuilder.append(hexString.substring( position, position + 2).toInt(16).toChar())
                position += 2
            }
            tlvList.add(Tlv(tag,length,stringBuilder.toString()))
        }
        return tlvList
    }
}
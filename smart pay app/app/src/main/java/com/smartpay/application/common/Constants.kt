package com.smartpay.application.common

class Constants {
    companion object{

        const val IS_SINGLE_MOTO_TYPE = "IS_SINGLE_MOTO_TYPE"
        const val TLV_MESSAGE_TYPE = "0100"
        const val TLV_TAG_PAN = 2
        const val TLV_TAG_PROCESSING_CODE = 3
        const val TLV_VALUE_PROCESSING_CODE = "009000"
        const val TLV_TAG_TRANSACTION = 4
        const val TLV_TAG_SYSTEM_TRACK_NUMBER = 11
        const val TLV_TAG_EXPIRY_DATE = 14
        const val TLV_TAG_SERVICE_ENTRY_MODE = 22
        const val TLV_VALUE_MANUAL_ENTRY_WITH_CVV = "015"
        const val TLV_VALUE_MANUAL_ENTRY_WITHOUT_CVV = "016"
        const val TLV_TAG_CVV = 60
        const val TLV_TAG_LOCAL_TIME = 12
        const val TLV_TAG_LOCAL_DATE = 13
        const val TLV_TAG_RESPONSE_CODE = 39
        const val TLV_VALUE_RESPONSE_CODE_APPROVED = "00"
        const val TLV_VALUE_RESPONSE_CODE_APPROVED_WITH_SIGNATURE = "08"
        const val TLV_VALUE_CVV_NOT_PRESENT = "CV0"
        const val TLV_TAG_CVV_PRESENT = "CV1"
        const val TLV_TAG_CVV_NOT_LEGIBLE = "CV2"
        const val TLV_TAG_CREDENTIAL_DESCRIPTOR = 120
        const val TLV_VALUE_NEW_CARD = "N"
        const val TLV_VALUE_STORED_CARD = "S"
        const val STX = 0x02
        const val ETX = 0x03
        const val TLV_REQUEST_PROCESS = "TLV_REQUEST_PROCESS"
        const val REQUEST_DATA = "TLV_REQUEST_PROCESS"
        const val RESPONSE_DATA = "TLV_REQUEST_PROCESS"
        const val TLV_RESPONSE = "TLV_RESPONSE"
    }
}
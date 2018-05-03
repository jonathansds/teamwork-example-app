package com.teamwork.example.util;

import com.teamwork.example.R;

//This class is not necessary for this project but I created as a way to show how I would do in a bigger project
public class EnumUtil {

    public enum RESPONSE_CODE{
        SUCCESS("TW200", R.string.message_success),
        UNKNOWN_ERROR("TW100", R.string.message_unknown_error),

        CONNECTION_ERROR("TW300", R.string.message_no_internet),
        TIMEOUT_ERROR("TW301", R.string.message_timeout),

        UNAUTHORIZED_ACCESS("TW401", R.string.message_unauthorized_access);


        private String code;
        private int message;

        RESPONSE_CODE(String code, int message){
            this.code = code;
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public int getMessage() {
            return message;
        }

        public void setMessage(int message) {
            this.message = message;
        }

        public static RESPONSE_CODE fromIntCode(int code){
            switch (code){
                case 200:
                    return SUCCESS;

                case 300:
                    return CONNECTION_ERROR;

                case 301:
                    return TIMEOUT_ERROR;

                case 401:
                    return UNAUTHORIZED_ACCESS;

                default:
                    return UNKNOWN_ERROR;
            }
        }
    }

    public enum EXTRAS{
        PROJECT("PROJECT");


        private String id;

        EXTRAS(String id){
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }
}

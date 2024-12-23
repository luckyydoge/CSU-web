package edu.csu.web_last.trigger.http.type;

import lombok.Data;

@Data
public class ResponseData {
    String code;
    String message;
    Object data;

    public ResponseData(Object data) {
        this.data = data;
        this.message = "ok";
        this.code = "0";
    }
}

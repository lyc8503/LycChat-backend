package site.lyc8503.chat.model;

import lombok.Data;

@Data
public class CommonResponse<T> {
    private int code;
    private String msg;
    private T data;
}

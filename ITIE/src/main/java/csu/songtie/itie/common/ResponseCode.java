package csu.songtie.itie.common;

import lombok.Getter;

@Getter
public enum ResponseCode {
    SUCCESS(0,"SUCCESS"),
    ERROR(1,"ERROR"),
    NO_LINEITEM(5,"NO_LINEITEM");

    private final int code;
    private final String description;

    ResponseCode(int code, String description) {
        this.code = code;
        this.description = description;
    }

}

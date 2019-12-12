package com.cartisan.responses;

import com.cartisan.utils.DateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * @author colin
 */
@Getter
public class GenericResponseWithData<T> {
    @ApiModelProperty(value = "错误编码", required = true)
    private Integer status;

    @ApiModelProperty(value = "提示信息", required = true)
    private String message;

    @ApiModelProperty(value = "运行时间截", required = true)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = DateUtil.Pattern.DATETIME_SSS)
    private LocalDateTime timestamp;

    @ApiModelProperty(value = "返回结果", required = true)
    private T data;

    public GenericResponseWithData(Integer status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;

        this.timestamp = LocalDateTime.now();
    }
}

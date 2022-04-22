package com.dwk.design.result;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Description:
 *  @JsonInclude(Include.NON_NULL)  :实体类与json互转的时候属性值为null的不参与序列化
 *  即值为null的属性不会返回给前端
 * @author: mushi
 * @Date: 2021/3/4 9:01
 */

@JsonInclude(Include.NON_NULL)
public class Result<T> {
    public static final String SUCCESSFUL_CODE = "200";
    public static final String SUCCESSFUL_MESG = "操作成功";
    private String code;
    private String message;
    private String timestamp;
    private Integer pageCount;
    private Long totalCount;
    @JsonInclude(Include.NON_NULL)
    private T data;

    public Integer getPageCount() {
        return this.pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Long getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Result() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.timestamp = df.format(LocalDateTime.now());
    }

    /**枚举异常类型*/
    public Result(ErrorType errorType) {
        this.code = errorType.getCode();
        this.message = errorType.getMessage();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.timestamp = df.format(LocalDateTime.now());
    }

    public Result(ErrorType errorType, T data) {
        this(errorType);
        this.data = data;
    }

    /**状态码、异常信息、*/
    private Result(String code, String mesg, T data) {
        this.code = code;
        this.message = mesg;
        this.data = data;
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.timestamp = df.format(LocalDateTime.now());
    }

    public static Result success(Object data) {
        return new Result("200", "传输完成", data);
    }

    public static Result success() {
        return success(true);
    }

    public static Result fail() {
        return new Result(ErrorType.SYSTEM_ERROR);
    }

    public static Result fail(String code, String message) {
        return new Result(code, message, "");
    }

    public static Result fail(ErrorType errorType, Object data) {
        return new Result(errorType, data);
    }

    public static Result fail(OssException exception) {
        return new Result(exception.getHttpCode() + "", exception.getErrorMessage(), "");
    }

    public static Result fail(ErrorType errorType, String message, Object data) {
        return new Result(errorType.getCode(), message, data);
    }

    public static Result fail(ErrorType errorType) {
        return fail((ErrorType)errorType, (Object)null);
    }

    public static Result fail(Object data) {
        return new Result(ErrorType.SYSTEM_ERROR, data);
    }

    @JsonIgnore
    public boolean isSuccess() {
        return "200".equals(this.code);
    }

    @JsonIgnore
    public boolean isFail() {
        return !this.isSuccess();
    }
}

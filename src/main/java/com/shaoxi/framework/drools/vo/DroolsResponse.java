package com.shaoxi.framework.drools.vo;

import java.io.Serializable;
import java.util.List;
import lombok.Data;
import lombok.ToString;

/**
 * @author: QXY
 * @classDescribe:
 * @createTime: 2019/1/28
 * @version: 1.0
 */
@ToString
@Data
public class DroolsResponse<T> implements Serializable{

    public DroolsResponse() {
    }

    private String code;
    private String message;
    private List<String> ruleName;
    private T result;
    private Boolean resultFlag;

}

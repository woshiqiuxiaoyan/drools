package com.shaoxi.framework.drools.vo;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class DroolsConfigVO {
    private String serverUrl;
    private String kieContainerId;
    private String userName;
    private String passWord;
}
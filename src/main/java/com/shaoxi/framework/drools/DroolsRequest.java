package com.shaoxi.framework.drools;

import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: zhongjie
 * date: 2019/1/18 0018
 * time: 17:23
 * description:
 */
@Data
public class DroolsRequest<T,K>{

    private Map<Object,Object> extParams;

    private Object param1;

    private String stringParam1;

    private String stringParam2;

    private List<T> listParam1;

    private List<K> listParam2;

    private Integer integerParam1;

    private Integer integerParam2;

    private Float floatParam1;

    private Float floatParam2;

    private Double doubleParam1;

    private Double doubleParam2;

    private Boolean booleanParam1;

    private Boolean booleanParam2;

    public void add(Object key,Object value){
        if(null==extParams){
            extParams=new HashMap<>();
        }
        extParams.put(key,value);
    }

    public Object get(Object key){
        if(null==extParams){
            return null;
        }
        return extParams.get(key);
    }

}

package com.shaoxi.framework.drools;

import com.shaoxi.framework.commons.response.Response;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: zhongjie
 * date: 2019/1/18 0018
 * time: 17:26
 * description:
 */
@Data
public class DroolsResponse<T,K>{

    public DroolsResponse(){

    }

    public DroolsResponse(String code,String message){
        response=new Response(code,message);
    }

    private Map<Object,Object> extContents;

    private Object content;

    private List<T> list1;

    private List<K> list2;

    private Response response;

    private List<String> matchRules;

    private List<String> unMatchRules;

    public void addMatchRule(String rule){
        if(null==matchRules){
            matchRules=new ArrayList<>();
        }
        matchRules.add(rule);
    }

    public void addUnMatchRule(String rule){
        if(null==unMatchRules){
            unMatchRules=new ArrayList<>();
        }
        unMatchRules.add(rule);
    }

    public void add(Object key,Object value){
        if(null==extContents){
            extContents=new HashMap<>();
        }
        extContents.put(key,value);
    }

    public Object get(String key){
        if(null==extContents){
            return null;
        }
        return extContents.get(key);
    }

}

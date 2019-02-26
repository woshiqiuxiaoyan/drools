package com.shaoxi.framework.drools.service;


import com.shaoxi.framework.drools.vo.DroolsResponse;

/**
 * @author: QXY
 * @classDescribe:
 * @createTime: 2019/1/28
 * @version: 1.0
 */

public interface IDroolsClient  {
      <T>  DroolsResponse droolsDeal(T result,String knowledgeBaseId);

    <T> boolean droolsSimpleDeal(T result,String knowledgeBaseId);

    <T> DroolsResponse<T> droolsDealWithResult(T result,String knowledgeBaseId);
}

package com.shaoxi.framework.drools.service.impl;


import com.shaoxi.framework.drools.exception.DroolException;
import com.shaoxi.framework.drools.service.IDroolsClient;
import com.shaoxi.framework.drools.vo.DroolsConfigVO;
import com.shaoxi.framework.drools.vo.DroolsResponse;
import java.util.LinkedList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.kie.api.KieServices;
import org.kie.api.command.Command;
import org.kie.api.command.KieCommands;
import org.kie.api.runtime.ExecutionResults;
import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.api.model.ServiceResponse;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.kie.server.client.RuleServicesClient;


/**
 * @author: QXY
 * @classDescribe:
 * @createTime: 2019/1/28
 * @version: 1.0
 */

@Slf4j
public class   DroolsClientServiceImpl<T> implements IDroolsClient {

    private DroolsConfigVO droolsConfigVO;

    private long timeout;

    private RuleServicesClient ruleServicesClient;

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public DroolsConfigVO getDroolsConfigVO() {
        return droolsConfigVO;
    }

    public void setDroolsConfigVO(DroolsConfigVO droolsConfigVO) {
        this.droolsConfigVO = droolsConfigVO;
    }


    /**
     * 创建规则服务客户端
     */

    private KieServicesClient createKieServiceClient(DroolsConfigVO droolsConfigVO) {


        /**
         * KisService 配置信息设置
         */

        KieServicesConfiguration kieServicesConfiguration = KieServicesFactory.newRestConfiguration(droolsConfigVO.getServerUrl(),droolsConfigVO.getUserName(),droolsConfigVO.getPassWord(),timeout);

        kieServicesConfiguration.setMarshallingFormat(MarshallingFormat.JSON);

        KieServicesClient kieServicesClient = KieServicesFactory.newKieServicesClient(kieServicesConfiguration);

        return kieServicesClient;
    }


    public void initKieServiceClient() {

        if (null == ruleServicesClient) {
            try {
                KieServicesClient kieServicesClient = createKieServiceClient(droolsConfigVO);
                ruleServicesClient = kieServicesClient.getServicesClient(RuleServicesClient.class);
            } catch (Exception e) {
                log.error(String.format("=============KisService config error:%s ",droolsConfigVO),e);
                throw new DroolException(String.format("drools inital  error ",e.getMessage()));
            }
        }
    }


    public final String DROOLS_RESPONSE="droolsResponse";

    public final String PARAM="param";

    public final String SUCCESS_CODE="000000000";


    @Override
    public<T>  DroolsResponse droolsDeal(T result,String knowledgeBaseId) {

        /**
         *  命令定义，包含插入数据，执行规则
         */

        KieCommands kieCommands = KieServices.Factory.get().getCommands();

        DroolsResponse droolsResponse = new DroolsResponse();
        droolsResponse.setCode(SUCCESS_CODE);
        droolsResponse.setMessage("允许通过");
        droolsResponse.setResultFlag(true);
        List<Command<?>> commands = new LinkedList<>();
        commands.add(kieCommands.newInsert(droolsResponse, DROOLS_RESPONSE));
        commands.add(kieCommands.newInsert(result, PARAM));
        commands.add(kieCommands.newFireAllRules());
        ServiceResponse<ExecutionResults> results = ruleServicesClient.executeCommandsWithResults(droolsConfigVO.getKieContainerId(),
                kieCommands.newBatchExecution(commands, knowledgeBaseId));

        droolsResponse = (DroolsResponse)results.getResult().getValue(DROOLS_RESPONSE);
        result =(T)results.getResult().getValue(PARAM);
        droolsResponse.setResult(result);
        return  droolsResponse;
    }



    @Override
    public<T> boolean droolsSimpleDeal(T result,String knowledgeBaseId) {
        DroolsResponse<T> droolsResponse =  droolsDeal(result, knowledgeBaseId);
        if(SUCCESS_CODE.equalsIgnoreCase(droolsResponse.getCode())){
            return true;
        }
        return false;
    }
    @Override
    public<T> DroolsResponse<T> droolsDealWithResult(T result,String knowledgeBaseId) {
        return  droolsDeal(result, knowledgeBaseId);
    }

}


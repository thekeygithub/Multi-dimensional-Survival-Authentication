package com.xcuni.guizhouyl.fabric;

import com.alibaba.fastjson.JSONObject;
import com.fuliang.hyperledger.fabric.sdk.core.ChaincodeManager;
import com.fuliang.hyperledger.fabric.sdk.core.ChannelManager;
import com.fuliang.hyperledger.fabric.sdk.core.TransactionManager;
import com.fuliang.hyperledger.fabric.sdk.core.UserManager;
import com.fuliang.hyperledger.fabric.sdk.entity.SendTransactionEntity;
import com.fuliang.hyperledger.fabric.sdk.entity.TransactionEntity;
import com.fuliang.hyperledger.fabric.sdk.model.Organization;
import com.fuliang.hyperledger.fabric.sdk.model.OrganizationUser;
import com.fuliang.hyperledger.fabric.sdk.persistence.KeyValueFileStore;
import com.google.common.collect.Maps;
import com.xcuni.guizhouyl.config.ChaincodeData;
import com.xcuni.guizhouyl.config.ChannelData;
import com.xcuni.guizhouyl.config.ConfigurationManager;
import com.xcuni.guizhouyl.config.FabricAppData;
import org.apache.commons.lang3.StringUtils;
import org.hyperledger.fabric.sdk.*;
import org.hyperledger.fabric.sdk.exception.TransactionEventException;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.format;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * 集成Fabric网络中的org，peer，user，channel实体
 */
@Component
public class FabricManager {
    private final Logger LOGGER = LoggerFactory.getLogger(FabricManager.class);
    private static final File storeFile = new File("HFCSampletest.properties");
    private static final String EXPECTED_EVENT_NAME = "event";
    private static final byte[] EXPECTED_EVENT_DATA = "!".getBytes(UTF_8);
    private static final String ADMIN_NAME = "admin";
    private static final TransactionRequest.Type CHAIN_CODE_LANG = TransactionRequest.Type.GO_LANG;
    private static final String SUCCESS = "200";
    private static final String ERROR = "500";

    @Autowired
    private ConfigurationManager config;
    @Autowired
    private FabricAppData fabricAppData;
    private KeyValueFileStore store;
    private HFClient client;
    private UserManager userManager;
    private ChannelManager channelManager;
    private List<Channel> channels = new ArrayList<>();
    private HashMap<String, Channel> channelHashMap = new HashMap<>();
    private ChaincodeManager chaincodeManager;
    private TransactionManager transactionManager;

    private Collection<Organization> organizations;

    private void loadConfiguration() {
        config.loadNetworkConfiguration();
    }

    private HFClient getTheClient() throws Exception {

        String clientOrgName = config.getClientOrgName();
        HFClient client = HFClient.createNewInstance();
        client.setCryptoSuite(CryptoSuite.Factory.getCryptoSuite());

        OrganizationUser peerAdmin = userManager.getOrgAdmin(clientOrgName, ADMIN_NAME);
        client.setUserContext(peerAdmin);

        return client;
    }

    public void setup() {
        store = new KeyValueFileStore(storeFile);
        LOGGER.debug("创建KV存储文件.");
        loadConfiguration();
        LOGGER.debug("读取配置文件结束.");

        //获取client所在的org，和使用的用户 --- org目前只有基本信息，sdkconfigur还没有peer和orderer
        organizations = config.getOrgnizations();
        LOGGER.debug("初始化用户信息");
        userManager = new UserManager(config.getSdkConfiguration(), store);//TODO 暂时仅使用admin

        //创建HFClient
        try {
            client = getTheClient();
        } catch (Exception e) {
            LOGGER.error("创建Fabric client失败.");
        }

        //根据配置初始化channel；
        //在application.yaml配置的为本应用要连接的channel，必须要在network-config中存在
        channelManager = new ChannelManager(config.getSdkConfiguration(), store, client);
        try {
            List<String> channelNames = fabricAppData.getChannelNameList();//得到  通道名称  channel-name: "gzylchannel"，这种写法可以应用于多通道
            if (channelNames != null) {
                channelNames.forEach(eachCh -> {
                    try {
                        Channel channel = channelManager.constructChannelFromConfig(eachCh);
                        channels.add(channel);
                        channelHashMap.put(eachCh, channel);//存放通道名称  和对应的通道
                    } catch (Exception e) {
                        LOGGER.error("重建channel信息,exception:{},cause:{}！", e.getMessage(), e.getCause());
                    }
                });
            }
            //channel = channelManager.constructChannelFromConfig(fabricAppData.getFabricChannel().getChannelName());
        } catch (Exception e) {
            LOGGER.error("重建channel信息失败！");
        }

        //创建transactionManager
        transactionManager = new TransactionManager(config.getSdkConfiguration(), client);

        //userManager.enrollOrgnizationUsers(store);
        LOGGER.debug("准备用户信息结束");
    }

    public void enrollOrgnizationUsers(KeyValueFileStore store) {
        LOGGER.debug("开始加入各组织用户认证信息");
        for (Organization org : organizations) {
            String orgName = org.getName();
            OrganizationUser admin = store.getMember(ADMIN_NAME, orgName);

        }
    }

    public List<ChaincodeData> getChannelChaincodeList(String channelName) {
        ChannelData channelData = fabricAppData.getChannelData(channelName);
        if (channelData == null) {
            return null;
        }
        return channelData.getChaincodes();
    }

    //TODO THIS IS FOR TESTING
    public JSONObject queryChainCode(String channleName, ChaincodeData chaincodeData) {
        FabricChaincodeResponse queryResp = new FabricChaincodeResponse();
        String code = SUCCESS;
        String txId = null; //记录transactionId
        String resultAsString = null;

        ChaincodeID id_11 = ChaincodeID.newBuilder().setName(chaincodeData.getName()).setPath(chaincodeData.getPath()).setVersion(chaincodeData.getVersion()).build();
        TransactionEntity transaction = new TransactionEntity(id_11, CHAIN_CODE_LANG);
        transaction.setArgs(new String[]{"a"});
        transaction.setFunc("query");

        try {
            Channel targetCh = channelHashMap.get(channleName);
            resultAsString = transactionManager.queryChaincode(targetCh, transaction);
        } catch (Exception e) {
            LOGGER.error("查询chaincode {}失败：{}", chaincodeData.getName(), e.getMessage());
            code = ERROR;
            queryResp.setMessage(e.getMessage());
        }
        queryResp.setCode(code);
        queryResp.setTxId(txId);
        queryResp.setResultString(resultAsString);
        return queryResp.buildJsonResult();
    }

    //query   3333333
    public JSONObject queryChaincode(String channleName, TransactionEntity reqTrans) {
        FabricChaincodeResponse queryResp = new FabricChaincodeResponse();
        String code = SUCCESS;
        String txId = null; //记录transactionId
        String resultAsString = null;

        try {
            Channel targetCh = channelHashMap.get(channleName);
            resultAsString = transactionManager.queryChaincode(targetCh, reqTrans);//这里 查询结果 只返回 结果值
        } catch (Exception e) {
            LOGGER.error("查询chaincode {}失败：{}", reqTrans.getChaincodeId(), e.getMessage());
            code = ERROR;
            queryResp.setMessage(e.getMessage());
        }
        queryResp.setCode(code);
        queryResp.setTxId(txId);
        queryResp.setResultString(resultAsString);
        return queryResp.buildJsonResult();
    }


    //TODO THIS IS FOR TESTING
    public JSONObject invokeChaincode(String channleName, ChaincodeData chaincodeData) {
        ChaincodeID id = ChaincodeID.newBuilder().setName(chaincodeData.getName()).setPath(chaincodeData.getPath()).setVersion(chaincodeData.getVersion()).build();
        TransactionEntity transaction = new TransactionEntity(id, CHAIN_CODE_LANG);
        //transaction.setArgs(new String[] { "a", "b", "10" });
        //transaction.setFunc("invoke");
        transaction.setArgs(new String[]{"a"});
        transaction.setFunc("query");

        Map<String, byte[]> transientMap = Maps.newHashMap();
        transientMap.put(EXPECTED_EVENT_NAME, EXPECTED_EVENT_DATA);//用于设置chaincode event
        transaction.setTransientMap(transientMap);

        return invokeChaincode(channleName, transaction);
    }

    public JSONObject invokeChaincode(String channleName, TransactionEntity reqTrans) {
        //执行chaincode的返回结果
        FabricChaincodeResponse invokeResp = new FabricChaincodeResponse();
        String code = SUCCESS;
        String txId = null; //记录transactionId
        String resultAsString = null;
        try {
            Channel targetCh = channelHashMap.get(channleName);
            //向背书节点发起invoke chaincode
            Collection<ProposalResponse> responses = transactionManager.invokeChaincode(targetCh, reqTrans);

            //获取合约执行的结果
            ProposalResponse response = responses.iterator().next();
            txId = response.getTransactionID();//取出交易ID
            byte[] chaincodeBytes = response.getChaincodeActionResponsePayload(); // 链码返回的数据
            if (chaincodeBytes != null) {
                resultAsString = new String(chaincodeBytes, UTF_8);
            }

            SendTransactionEntity sendTransaction = new SendTransactionEntity();
            sendTransaction.setUser(client.getUserContext());
            //发送交易至orderer
            CompletableFuture<BlockEvent.TransactionEvent> future = transactionManager.sendTransaction(responses, sendTransaction, targetCh);
            //处理transactionEvent
            Object result = future.thenApply((BlockEvent.TransactionEvent transactionEvent) -> {
                // 必须是有效交易事件
                checkArgument(transactionEvent.isValid(), "没有签名的交易事件");
                // 必须有签名
                checkNotNull(transactionEvent.getSignature(), "没有签名的交易事件");
                // 必须有交易区块事件发生
                BlockEvent blockEvent = checkNotNull(transactionEvent.getBlockEvent(), "交易事件的区块事件对象为空");

                try {
                    LOGGER.info("成功交易，本次实例化交易ID：{}", transactionEvent.getTransactionID());
                    checkArgument(StringUtils.equals(blockEvent.getChannelId(), targetCh.getName()), "事件名称和对应通道名称不一致");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                return "success";
            }).exceptionally(e -> {
                if (e instanceof CompletionException && e.getCause() != null) {
                    e = e.getCause();
                }
                if (e instanceof TransactionEventException) {
                    BlockEvent.TransactionEvent te = ((TransactionEventException) e).getTransactionEvent();
                    if (te != null) {
                        //e.printStackTrace(System.err);
                        LOGGER.error("Transaction with txid {} failed. {}", te.getTransactionID(), e.getMessage());
                    }
                }
                //e.printStackTrace(System.err);
                LOGGER.error("Transaction failed with {} exception {}", e.getClass().getName(), e.getMessage());
                return e.getMessage(); //event处理返回null给invoke的Object result
            }).get(config.getSdkConfiguration().getTransactionWaitTime(), TimeUnit.SECONDS);//默认120s

            //System.out.println(result);
            if (result != null) {
                invokeResp.setMessage(result.toString());
            }
            if (result == null || !result.toString().equals("success")) {
                code = ERROR;
            }
        } catch (Exception e) {
            //e.printStackTrace();
            code = ERROR;
            invokeResp.setMessage(e.toString());
            LOGGER.error("Invoke chaincode failed with {} exception {}", e.getClass().getName(), e.getMessage());
        }


        if (code.equals(ERROR)) {

        }
        invokeResp.setCode(code);
        invokeResp.setTxId(txId);
        invokeResp.setResultString(resultAsString);
        return invokeResp.buildJsonResult();
    }
}

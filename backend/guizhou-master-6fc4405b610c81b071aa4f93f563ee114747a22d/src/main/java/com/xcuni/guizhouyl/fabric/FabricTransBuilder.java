package com.xcuni.guizhouyl.fabric;

import org.hyperledger.fabric.sdk.TransactionRequest;
import org.springframework.stereotype.Component;

//TODO 通用的区块链请求构造器
@Component
public class FabricTransBuilder {
    private static final TransactionRequest.Type CHAIN_CODE_LANG = TransactionRequest.Type.GO_LANG;

}

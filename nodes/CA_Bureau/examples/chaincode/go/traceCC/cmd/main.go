package main

import (
	"github.com/hyperledger/fabric/core/chaincode/shim"
	"fmt"
	"github.com/hyperledger/fabric/protos/peer"
)
/*
	增加贷款记录
	增加还款记录
	根据账户名称查询相应的信息（包含该账户的历史记录信息）
 */
type TraceChainCode struct {
	
}

func (t *TraceChainCode) Init (stub shim.ChaincodeStubInterface) peer.Response {
	return shim.Success(nil)
}
func (t *TraceChainCode) Invoke (stub shim.ChaincodeStubInterface) peer.Response {
	fun,args := stub.GetFunctionAndParameters()
	if fun == "loan"{
		return loan(stub,args)
	}else if fun == "repayment"{
		return repayment(stub,args)
	}else if fun == "queryAccountByCardNo"{
		return queryAccountByCardNo(stub,args)
	}
	return shim.Error("非法操作")
}
func main()  {
		err := shim.Start(new(TraceChainCode))
	if err != nil{
		fmt.Printf("启动链码失败：%v",err)
	}
}
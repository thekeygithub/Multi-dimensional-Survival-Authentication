package main

import (
	"github.com/hyperledger/fabric/core/chaincode/shim"
	"github.com/hyperledger/fabric/protos/peer"
	"strconv"
	"encoding/json"
)

func saveAccount(stub shim.ChaincodeStubInterface,account Account) bool {
	//将对象进行序列化
	accByte,err :=json.Marshal(account)
	if err != nil{
		return  false
	}
	err =stub.PutState(account.CardNo,accByte)
	if err != nil{
		return  false
	}
	return true
}
func GetAccountByCaidNo(stub shim.ChaincodeStubInterface,caidNo string) (Account ,bool) {
	var account Account
	value,err := stub.GetState(caidNo)
	if err != nil{
		return account,false
	}
	if value == nil{
		return account,false
	}
	//反序列化对象
	err = json.Unmarshal(value,&account)
	if err != nil{
		return  account,false
	}
	return account,true
}

//实现贷款功能
//-c '{"Args":"["loan","身份证号码","银行名称","贷款金额"]"}'
func loan(stub shim.ChaincodeStubInterface,args[] string) peer.Response {
	//贷款金额进行转换
	am,err :=strconv.Atoi(args[2])
	if err !=nil{
		shim.Error("金额转换失败")
	}
	bank :=Bank{
		BankName:args[1],
		Flag:Bank_Flag_loan,
		Amount:am,
		StartDate:"20190226",
		EndDate:"20190526",
	}
	account := Account{
		CardNo:args[0],
		Aname:"Tom",
		Age:26,
		Gender:"男",
		Mobil:"2662355135",
		Bank:bank,
	}
	bl :=saveAccount(stub,account)
	if !bl {
		shim.Error("保存贷款记录失败")
	}
	return shim.Success([]byte("贷款成功"))
}
//实现还款功能
//-c '{"Args":"["loan","身份证号码","银行名称","贷款金额"]"}'
func repayment(stub shim.ChaincodeStubInterface,args[] string) peer.Response {
	//还款金额进行转换
	am,err :=strconv.Atoi(args[2])
	if err !=nil{
		shim.Error("还款金额转换失败")
	}
	bank :=Bank{
		BankName:args[1],
		Flag:Bank_Flag_Repayment,
		Amount:am,
		StartDate:"20190326",
		EndDate:"20190526",
	}
	account := Account{
		CardNo:args[0],
		Aname:"Tom",
		Age:26,
		Gender:"男",
		Mobil:"2662355135",
		Bank:bank,
	}
	bl :=saveAccount(stub,account)
	if !bl {
		shim.Error("保存还款记录失败")
	}
	return shim.Success([]byte("还款成功"))
}
//历史记录查询
//-c '{"Args":"["queryAccountByCardNo","身份证号码"]"}'
func queryAccountByCardNo(stub shim.ChaincodeStubInterface,args[] string) peer.Response {
	if len(args) !=1{
		shim.Error("必须指定身份证号码")
	}
	account,bl :=GetAccountByCaidNo(stub,args[0])
	if !bl{
		shim.Error("查询失败")
	}
	//查询历史信息
	accIterator ,err :=stub.GetHistoryForKey(account.CardNo)//返回一个迭代器
	if err != nil{
		shim.Error("查询历史信息出错")
	}
	defer accIterator.Close()//延迟关闭
	//遍历查询到的历史信息
	var historys []HistoryItem
	var acc Account
	for accIterator.HasNext(){
		hisData ,err :=accIterator.Next()
		if err != nil{
			shim.Error("遍历迭代器出错")
		}
		var hisItem HistoryItem
		hisItem.TxId=hisData.TxId//获取交易ID
		//获取此条交易状态信息
		err = json.Unmarshal(hisData.Value,&acc)
		if err != nil{
			return shim.Error("反序列化历史状态时出现错误")
		}
		//处理当前记录为空的情况
		if hisData.Value == nil{
			var empty Account
			hisItem.Account= empty
		}else {
			hisItem.Account=acc
		}
		historys =append(historys,hisItem)
	}
	account.Historys=historys
	accbyte,err :=json.Marshal(account)
	if err != nil{
		return shim.Error("序列化失败")
	}
	return shim.Success(accbyte)
}

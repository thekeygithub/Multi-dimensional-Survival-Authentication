package main

import (
	"fmt"
	"github.com/hyperledger/fabric/core/chaincode/shim"
	pb "github.com/hyperledger/fabric/protos/peer"
	"encoding/json"

)

// 资产目录数据结构 {
//	委办局ID
//	目录Struct数组
// }
//
// 目录数据结构 {
//	目录数据ID
//	目录数据名称
//	目录数据权限
//	目录数据有效期
// }
//
// 各委办局可上传资产信息
// 各委办局可修改资产信息
// 通过传入委办局名称及权限，查看目录信息

type DoubleVaultsCode struct{
}

type AssetContentInfo struct{
	AssetContentId string 			`json:"contentid"`				// 资产目录ID
	ContentInfomation  	[]ContentInfo `json:"contentinfo"`			// 目录数据结构
//	ContentPermission   string   	`json:"permission"`				// 目录权限
}

type ContentInfo struct {
	ContentId   		string    	`json:"id"`				// 目录ID
	ContentName 		string 		`json:"name"`				// 目录名称
	ContentVaild 		string 		`json:"vaild"`			// 目录效期
}


func (t *DoubleVaultsCode) Init(stub shim.ChaincodeStubInterface) pb.Response {
	return shim.Success(nil)
}

func (t *DoubleVaultsCode) Invoke(stub shim.ChaincodeStubInterface) pb.Response {
	fn,args := stub.GetFunctionAndParameters()
	if fn == "ContentUpload"{ // 各委办局可上传资产信息
		return t.ContentUpload(stub,args)
	} else if fn == "ContentDelete"{ // 各委办局可删除资产信息
		return t.ContentDelete(stub,args)
	} else if fn == "ContentSearchById"{ // 通过传入委办局名称及权限，查看目录信息
		return t.ContentSearchById(stub,args)
	}

	return shim.Error("Recevied unkown function invocation")
}

func (t *DoubleVaultsCode)ContentUpload(stub shim.ChaincodeStubInterface, args []string) pb.Response {
	if len(args) !=1{
		return shim.Error("The asset information must be specified")
	}
	var inputdata AssetContentInfo
	// 解析入参
	err := json.Unmarshal([]byte(args[0]), &inputdata)
	if err != nil {
		return shim.Error("fail to decode JSON of inputdata!")
	}
	// 检查数据库(资产目录+目录ID是否存在、资产目录+目录名称是否存在)
	ContentIdAsBytes, err := stub.GetState(inputdata.AssetContentId)
	if err != nil {
		return shim.Error("check nil error"+err.Error())
	}
	if ContentIdAsBytes != nil {
		return shim.Error("该委办局目录信息已存在")
	}
	err = stub.PutState(inputdata.AssetContentId,[]byte(args[0]))
	if err != nil {
		return shim.Error("putstate error"+err.Error())
	}
	return shim.Success(nil)
}

func (t *DoubleVaultsCode)ContentDelete(stub shim.ChaincodeStubInterface, args []string) pb.Response {
	// 检查参数
	if len(args) !=1{
		return shim.Error("The asset catalog id must be specified")
	}
	// 根据ID查找目录对象
	assetContentInfo, err := stub.GetState(args[0])
	if err != nil {
		return shim.Error("check nil error"+err.Error())
	}
	if assetContentInfo == nil {
		return shim.Error("The asset id information is not available")
	}
	//删除信息
	err =stub.DelState(args[0])
	if err != nil{
		return shim.Error("DelState error "+err.Error())
	}
	return shim.Success(nil)
}

// 通过传入委办局名称及权限，查看目录信息
func (t *DoubleVaultsCode)ContentSearchById(stub shim.ChaincodeStubInterface, args []string) pb.Response {
	// 检查参数(资产目录ID)
	if len(args) !=1{
		return shim.Error("The asset catalog id must be specified")
	}
	// 根据ID查找目录对象
	assetContentInfo, err := stub.GetState(args[0])
	if err != nil {
		return shim.Error("check nil error"+err.Error())
	}
	if assetContentInfo == nil {
		return shim.Error("The asset id information is not available")
	}
	return shim.Success(assetContentInfo)

	//如果需要筛选权限 和 有效期
	// 解析Json格式
	//var inputdata AssetContentInfo
	//err = json.Unmarshal(assetContentInfo, &inputdata)
	//if err != nil {
	//	return shim.Error("fail to decode JSON of inputdata!")
	//}
	// 筛选权限
	//if inputdata.ContentPermission != args[1]{
	//	return shim.Error(""+err.Error())
	//}
	//筛选有效期
	//for i:=0;i<len(inputdata.ContentInfomation);i++ {
	//
	//}
	//return shim.Success(assetContentInfo)
}
func main(){
	err := shim.Start(new(DoubleVaultsCode))
	if err != nil {
		fmt.Printf("Error starting Food chaincode: %s ",err)
	}
}



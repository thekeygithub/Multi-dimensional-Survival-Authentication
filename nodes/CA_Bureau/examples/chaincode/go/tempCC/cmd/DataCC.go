package main

import (
	"fmt"
	"time"
	"encoding/json"
	pb "github.com/hyperledger/fabric/protos/peer"
	"github.com/hyperledger/fabric/core/chaincode/shim"
)


type DoubleVaultsStruct struct{
}

type AssetContentInfo struct{
	AssetContentId	string	`json:"contentid"`
	ContentInfomation	[]ContentInfo	`json:"contentinfo"`
}

type ContentInfo struct {
	ContentId	string	`json:"id"`
	ContentName	string	`json:"name"`
	ContentVaild	string	`json:"vaild"`
}


type  DemandInfo struct {
	DepartmentId	string	`json:"departmentid"`
	HashInfo	string	`json:"hashinfo"`
	HashDate	string	`json:"hashdate"`
}

func (t *DoubleVaultsStruct) Init(stub shim.ChaincodeStubInterface) pb.Response {
	return shim.Success(nil)
}

func (t *DoubleVaultsStruct) Invoke(stub shim.ChaincodeStubInterface) pb.Response {
	fn,args := stub.GetFunctionAndParameters()
	if fn == "ContentUpload"{
		return t.ContentUpload(stub,args)
	} else if fn == "ContentDelete"{
		return t.ContentDelete(stub,args)
	} else if fn == "ContentSearchById"{
		return t.ContentSearchById(stub,args)
	} else if fn == "RequirementUpload"{
		return t.RequirementUpload1(stub,args)
	} else if fn == "RequirementDelete"{
		return t.RequirementDelete(stub,args)
	}else if fn == "RequirementSearchById"{
		return t.RequirementSearchById(stub,args)
	}

	return shim.Error("Recevied unkown function invocation")
}

func (t *DoubleVaultsStruct) ContentUpload(stub shim.ChaincodeStubInterface, args []string) pb.Response {
	if len(args) !=1{
		return shim.Error("The asset information must be specified")
	}

	var inputdata AssetContentInfo
	err := json.Unmarshal([]byte(args[0]), &inputdata)
	if err != nil {
		return shim.Error("fail to decode JSON of inputdata!")
	}

	ContentIdAsBytes, err := stub.GetState(inputdata.AssetContentId)
	if err != nil {
		return shim.Error("check nil error" + err.Error())
	}
	if ContentIdAsBytes != nil {
		return shim.Error("Content info has already exist")
	}

	err = stub.PutState(inputdata.AssetContentId,[]byte(args[0]))
	if err != nil {
		return shim.Error("putstate error" + err.Error())
	}
	
	return shim.Success(nil)
}

func (t *DoubleVaultsStruct) ContentDelete(stub shim.ChaincodeStubInterface, args []string) pb.Response {
	if len(args) !=1{
		return shim.Error("The asset catalog id must be specified")
	}

	assetContentInfo, err := stub.GetState(args[0])
	if err != nil {
		return shim.Error("check nil error"+err.Error())
	}
	if assetContentInfo == nil {
		return shim.Error("The asset id information is not available")
	}
	

	err =stub.DelState(args[0])
	if err != nil{
		return shim.Error("DelState error "+err.Error())
	}
	
	return shim.Success(nil)
}

func (t *DoubleVaultsStruct) ContentSearchById(stub shim.ChaincodeStubInterface, args []string) pb.Response {
	if len(args) !=1{
		return shim.Error("The asset catalog id must be specified")
	}

	assetContentInfo, err := stub.GetState(args[0])
	if err != nil {
		return shim.Error("check nil error"+err.Error())
	}
	if assetContentInfo == nil {
		return shim.Error("The asset id information is not available")
	}
	
	return shim.Success(assetContentInfo)
}
func (t *DoubleVaultsStruct) RequirementUpload1(stub shim.ChaincodeStubInterface, args []string) pb.Response {
	if len(args) !=3{
		return shim.Error("The demand catalog id and hash must be specified")
	}
	demandInfo, err := stub.GetState(args[0])
	if err != nil {
		return shim.Error("check nil error"+err.Error())
	}
	if demandInfo != nil{
		return shim.Success([]byte("已存在"))
	}
	newdepartid := args[0]
	newhashinfo := args[1]
	newhashdate := args[2]
	requireinfo := &DemandInfo{	newdepartid,
		newhashinfo,
		newhashdate}
	requireJsonAsBytes, err := json.Marshal(requireinfo)
	if err != nil {
		return shim.Error("json Marshal error "+err.Error())
	}
	err = stub.PutState(newdepartid,requireJsonAsBytes)
	if err != nil{
		return shim.Error("PutState error" + err.Error())
	}
	return shim.Success(nil)
}
func (t *DoubleVaultsStruct) RequirementUpload(stub shim.ChaincodeStubInterface, args []string) pb.Response {
	if len(args) !=2{
		return shim.Error("The demand catalog id and hash must be specified")
	}
	demandInfo, err := stub.GetState(args[0])
	if err != nil {
		return shim.Error("check nil error"+err.Error())
	}
	
	if demandInfo == nil {
		newdepartid := args[0]
		newhashinfo := args[1]
		newhashdate := time.Unix(time.Now().Unix(), 0)

		requireinfo := &DemandInfo{	newdepartid,
																newhashinfo,
																newhashdate.String()}

		requireJsonAsBytes, err := json.Marshal(requireinfo)
		if err != nil {
				return shim.Error(err.Error())
		}
		err = stub.PutState(newdepartid,requireJsonAsBytes)
		if err != nil{
			return shim.Error("PutState error" + err.Error())
		}
	} else {
		var dInfo DemandInfo
		err = json.Unmarshal(demandInfo, &dInfo)
		if err != nil{
			return  shim.Error("demandInfo Unmarshal error"+err.Error())
		}
		dInfo.HashInfo = args[1]
		dInfo.HashDate = time.Now().String()
		dinfobyte,err :=json.Marshal(dInfo)
		if err != nil{
			return shim.Error("dInfo Marshal error" + err.Error())
		}
		err = stub.PutState(dInfo.DepartmentId,dinfobyte)
		if err != nil{
			return shim.Error("PutState error" + err.Error())
		}
	}
	return shim.Success(nil)
}

func (t *DoubleVaultsStruct) RequirementDelete(stub shim.ChaincodeStubInterface, args []string) pb.Response {
	if len(args) !=1{
		return shim.Error("The asset catalog id must be specified")
	}
	demandInfo, err := stub.GetState(args[0])
	if err != nil {
		return shim.Error("check nil error"+err.Error())
	}
	if demandInfo == nil {
		return shim.Error("The asset id information is not available")
	}

	err =stub.DelState(args[0])
	if err != nil{
		return shim.Error("DelState error "+err.Error())
	}
	
	return shim.Success(nil)
}

func (t *DoubleVaultsStruct) RequirementSearchById(stub shim.ChaincodeStubInterface, args []string) pb.Response {
	if len(args) !=1{
		return shim.Error("The demand catalog id must be specified")
	}
	demandInfo, err := stub.GetState(args[0])
	if err != nil {
		return shim.Error("check nil error"+err.Error())
	}
	if demandInfo == nil {
		return shim.Error("The demand id information is not available")
	}
	
	return shim.Success(demandInfo)
}

func main(){
	err := shim.Start(new(DoubleVaultsStruct))
	if err != nil {
		fmt.Printf("Error starting DoubleVaults chaincode: %s ",err)
	}
}

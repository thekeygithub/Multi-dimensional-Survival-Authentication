/*
Copyright IBM Corp. All Rights Reserved.

SPDX-License-Identifier: Apache-2.0
*/

package recordCC

import (
	"bytes"
	"encoding/base64"
	"encoding/json"
	"fmt"
	"github.com/hyperledger/fabric/core/chaincode/shim"
	pb "github.com/hyperledger/fabric/protos/peer"
	"time"
)

// SimpleChaincode example simple Chaincode implementation
type SimpleChaincode struct {
}

//type VerifyResult struct { //返回服务端
//	ID          string
//	Time        string
//	ModelOutput string
//}

type ResultInfo struct {
	UserInfo         UserInfo
	AccountDate      string
	VerificationInfo VerificationInfo
	AliveStatus      AliveStatus
}

type UserInfo struct {
	ID   string
	Name string
}

type VerificationInfo struct {
	Point_S     int `json:"PointS"`
	Point_T     int `json:"PointT"`
	ModelOutput string
	OutputDesc  string
	UpdateTime  string
}

type AliveStatus struct {
	IsAlive    			  int
	DataSrc     		  string
	DataID       		  string
	SurvivalStatusDate    string
	CauseOfDeath 		  string
	UpdateTime   		  string
}

//type Abnormity struct {
//	Data    string
//	Date    string
//	DataSrc string
//}

const BASE64TABLE = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_"

var coder = base64.NewEncoding(BASE64TABLE)


func (t *SimpleChaincode) Init(stub shim.ChaincodeStubInterface) pb.Response {
	fmt.Printf("recordCC Init\n")
	return shim.Success(nil)
}

func (t *SimpleChaincode) Invoke(stub shim.ChaincodeStubInterface) pb.Response {
	//fmt.Printf("recordCC Invoke\n")
	function, args := stub.GetFunctionAndParameters()
	if function == "verify" {
		return t.verify(stub, args)
	} else if function == "callTimes" {
		return t.callTimes(stub, args)
	} else if function == "recordData" {
		return t.recordData(stub, args)
	} else if function == "queryVerify" {
		return t.queryVerify(stub, args)
	} else if function == "queryResult" {
		return t.queryResult(stub, args)
	} else if function == "queryInterface" {
		return t.queryInterface(stub, args)
	} else if function == "querybyModelOutput" {
		return t.querybyModelOutput(stub, args)
	} else if function == "querybySelector" {
		return t.querybySelector(stub, args)
	} else if function == "queryCalltimes" {
		return t.queryCalltimes(stub, args)
	}

	return shim.Error("Invalid invoke function name. Expecting \"verify\" \"callTimes\"")
}

// 进行验证
func (t *SimpleChaincode) verify(stub shim.ChaincodeStubInterface, args []string) pb.Response {
	var ID, upTime, verifyJson string   // 入参
	var originalKey, resultKey string // 上链Key
	//var verifyResult VerifyResult
	var originalInfo ResultInfo
	var err, errDecode error
	var preResult []byte

	if len(args) != 3 {
		return shim.Error("Incorrect number of arguments. Expecting 3")
	}

	ID = args[0]
	upTime = args[1]
	verifyJson = args[2]

	//查询链上原有状态
	originalResult, err := stub.GetState(ID)

	if err != nil {
		resp := "{\"Error\":\"Failed to get state for " + ID + "\"}"
		return shim.Error(resp)
	} else if originalResult == nil { //若不存在输入默认情况
		originalInfo.AliveStatus.IsAlive = 1
		originalInfo.AliveStatus.UpdateTime = time.Now().Format("2006-01-02")
		originalInfo.VerificationInfo.Point_S = 0
		originalInfo.VerificationInfo.Point_T = 9
		originalInfo.AccountDate = upTime
		preResult, err = json.Marshal(originalInfo)
		if err != nil {
			return shim.Error(err.Error())
		}

	} else if originalResult != nil {
		tempResult := originalResult
		originalResult, errDecode = base64Decode(originalResult)
		if errDecode != nil {
			originalResult = tempResult
			//return shim.Error(errDecode.Error())
		}

		err = json.Unmarshal(originalResult, &originalInfo)
		if err != nil {
			return shim.Error(err.Error())
		}
		if originalInfo.AccountDate == upTime { //出现日期重复的情况直接返回结果
			return shim.Success(originalResult)
		}

		preResult = originalResult
	}


	//调用模型合约

	queryChaincodeName := "modelCC"
	modelCCFunc := "verify"
	channelName := ""
	verifyArgs := toChaincodeArgs(modelCCFunc, verifyJson, string(preResult), upTime)
	//fmt.Println(verifyArgs)
	modelCCResponse := stub.InvokeChaincode(queryChaincodeName, verifyArgs, channelName)
	if modelCCResponse.Status != shim.OK {
		errStr := fmt.Sprintf("Failed to query modelCC. Got error: %s", modelCCResponse.GetMessage())
		//fmt.Printf(errStr)
		return shim.Error(errStr)
	}
	//fmt.Printf("ModelCC call success!\n")

	//原始查询信息上链

	originalKey = ID + "verify" + upTime
	err = stub.PutState(originalKey, []byte(base64Encode([]byte(verifyJson))))//查询信息加密上链
	if err != nil {
		fmt.Printf("Original message putstate fail!\n")
		return shim.Error(err.Error())
	}
	//fmt.Printf("Original message putstate success!\n")
	//fmt.Printf(originalKey + "\n")

	//返回结果上链并通知调用方

	resultKey = ID
	modelResult := modelCCResponse.Payload
	//modelResult := []byte("{\"UserInfo\":{\"ID\":\"123456789\",\"Name\":\"张三\"},\"VerificationInfo\":{\"PointS\":12,\"PointT\":3,\"ModelOutput\":\"5\",\"UpdateTime\":\"2018-10-10\"},\"AliveStatus\":{\"IsAlive\":1,\"DataSrc\":\"\",\"DeathDate\":\"2018-09-01\",\"CauseOfDeath\":\"意外\",\"UpdateTime\":\"2018-09-10\"},\"Abnormity\":[{\"Data\":\"qwe\",\"Date\":\"2018-10-02\",\"DataSrc\":\"04\"},{\"Data\":\"123\",\"Date\":\"2018-10-02\",\"DataSrc\":\"04\"}]}")
	err = stub.PutState(resultKey, []byte(base64Encode(modelResult))) //结果加密上链
	if err != nil {
		fmt.Printf("Result message putstate fail!\n")
		return shim.Error(err.Error())
	}
	//fmt.Printf("Result message putstate success!\n")
	//err = json.Unmarshal(modelResult, &resultInfo)
	//if err != nil {
	//	return shim.Error(err.Error())
	//}
	//verifyResult.ID = resultInfo.UserInfo.ID
	//verifyResult.Time = time
	//verifyResult.ModelOutput = resultInfo.VerificationInfo.ModelOutput
	//returnResult, err := json.Marshal(verifyResult)
	//if err != nil {
	//	return shim.Error(err.Error())
	//}
	//fmt.Println(resultInfo.VerificationInfo.PointT)
	//fmt.Println(resultInfo.Abnormity[0].Data)
	//fmt.Println(verifyResult)

	return shim.Success(modelResult)

}

// 上传接口调用次数信息
func (t *SimpleChaincode) callTimes(stub shim.ChaincodeStubInterface, args []string) pb.Response {
	var interfaceName, upTime, callTimes string
	var interfaceKey string
	var err error

	if len(args) != 3 {
		return shim.Error("Incorrect number of arguments. Expecting 1")
	}

	interfaceName = args[0]
	upTime = args[1]
	callTimes = args[2]
	interfaceKey = interfaceName + upTime

	err = stub.PutState(interfaceKey, []byte(callTimes))
	if err != nil {
		fmt.Printf("Interface callTimes putstate fail!\n")
		return shim.Error(err.Error())
	}
	return shim.Success(nil)
}

//上传key-value
func (t *SimpleChaincode) recordData(stub shim.ChaincodeStubInterface, args []string) pb.Response {
	var recordKey, recordValue string
	var err error

	if len(args) != 2 {
		return shim.Error("Incorrect number of arguments. Expecting 1")
	}

	recordKey = args[0]
	recordValue = args[1]

	err = stub.PutState(recordKey, []byte(recordValue))
	if err != nil {
		fmt.Printf("Key-Value putstate fail!\n")
		return shim.Error(err.Error())
	}
	return shim.Success(nil)
}

//------------------------查询部分---------------------------

// 查询原始验证信息
func (t *SimpleChaincode) queryVerify(stub shim.ChaincodeStubInterface, args []string) pb.Response {
	var verifyInfoKey string // Entities\
	var resp string
	var verifyInfo []byte
	var err, errDecode error

	if len(args) != 1 {
		return shim.Error("Incorrect number of arguments. Expecting 3")
	}

	verifyInfoKey = args[0]
	verifyInfo, err = stub.GetState(verifyInfoKey)
	if err != nil {
		resp = "{\"Error\":\"Failed to get state for " + verifyInfoKey + "\"}"
		fmt.Printf(resp)
		return shim.Error(resp)
	} else if verifyInfo == nil {
		resp = "{\"Error\":\"Data does not exist: " + verifyInfoKey + "\"}"
		fmt.Printf(resp + "\n")
		return shim.Error(resp)
	}
	//decode
	tempResult := verifyInfo
	verifyInfo, errDecode = base64Decode(verifyInfo)
	if errDecode != nil {
		verifyInfo = tempResult
		//return shim.Error(errDecode.Error())
	}
	//fmt.Printf(string(verifyInfo) + "\n")
	return shim.Success(verifyInfo)
}

// 查询验证结果
func (t *SimpleChaincode) queryResult(stub shim.ChaincodeStubInterface, args []string) pb.Response {
	var resultInfoKey string
	var resultInfo []byte
	var resp string
	var err,errDecode error

	if len(args) != 1 {
		return shim.Error("Incorrect number of arguments. Expecting 1")
	}

	resultInfoKey = args[0]
	resultInfo, err = stub.GetState(resultInfoKey)
	if err != nil {
		resp = "{\"Error\":\"Failed to get state for " + resultInfoKey + "\"}"
		return shim.Error(resp)
	} else if resultInfo == nil {
		resp = "{\"Error\":\"Data does not exist: " + resultInfoKey + "\"}"
		return shim.Error(resp)
	}
	//decode
	tempResult := resultInfo
	resultInfo, errDecode = base64Decode(resultInfo)
	if errDecode != nil {
		resultInfo = tempResult
		//return shim.Error(errDecode.Error())
	}

	//fmt.Printf(string(resultInfo) + "\n")
	return shim.Success(resultInfo)
}

// 查询验证接口调用次数
func (t *SimpleChaincode) queryInterface(stub shim.ChaincodeStubInterface, args []string) pb.Response {
	var InterfaceKey string
	var InterfaceInfo []byte
	var resp string
	var err error

	if len(args) != 1 {
		return shim.Error("Incorrect number of arguments. Expecting 1")
	}

	InterfaceKey = args[0]
	InterfaceInfo, err = stub.GetState(InterfaceKey)
	if err != nil {
		resp = "{\"Error\":\"Failed to get state for " + InterfaceKey + "\"}"
		return shim.Error(resp)
	} else if InterfaceInfo == nil {
		resp = "{\"Error\":\"Data does not exist: " + InterfaceKey + "\"}"
		return shim.Error(resp)
	}
	//fmt.Printf(string(InterfaceInfo) + "\n")
	return shim.Success(InterfaceInfo)
}

// 通过模型输出状态查询验证结果
func (t *SimpleChaincode) querybyModelOutput(stub shim.ChaincodeStubInterface, args []string) pb.Response {
	var ModelOutput string // Entities
	var queryResults []byte
	var err error

	if len(args) != 1 {
		return shim.Error("Incorrect number of arguments. Expecting name of the person to query")
	}

	ModelOutput = args[0]
	queryString := fmt.Sprintf("{\"selector\":{\"VerificationInfo\":{\"ModelOutput\":\"%s\"}}}", ModelOutput)
	queryResults, err = getQueryResultForQueryString(stub, queryString)
	if err != nil {
		return shim.Error(err.Error())
	}
	//fmt.Printf(string(queryResults) + "\n")
	return shim.Success(queryResults)

}

// 通过查询表达式查询验证结果
func (t *SimpleChaincode) querybySelector(stub shim.ChaincodeStubInterface, args []string) pb.Response {
	var ModelOutput string // Entities
	var queryResults []byte
	var err error

	if len(args) != 1 {
		return shim.Error("Incorrect number of arguments. Expecting name of the person to query")
	}

	ModelOutput = args[0]
	queryString := ModelOutput

	queryResults, err = getQueryResultForQueryString(stub, queryString)
	if err != nil {
		return shim.Error(err.Error())
	}
	//fmt.Printf(string(queryResults) + "\n")
	return shim.Success(queryResults)

}

// 查询调用次数结果
func (t *SimpleChaincode) queryCalltimes(stub shim.ChaincodeStubInterface, args []string) pb.Response {
	var interfaceKey string
	var interfaceInfo []byte
	var resp string
	var err error

	if len(args) != 1 {
		return shim.Error("Incorrect number of arguments. Expecting 1")
	}

	interfaceKey = args[0]
	interfaceInfo, err = stub.GetState(interfaceKey)
	if err != nil {
		resp = "{\"Error\":\"Failed to get state for " + interfaceKey + "\"}"
		return shim.Error(resp)
	} else if interfaceInfo == nil {
		resp = "{\"Error\":\"Data does not exist: " + interfaceKey + "\"}"
		return shim.Error(resp)
	}
	//fmt.Printf(string(interfaceInfo) + "\n")
	return shim.Success(interfaceInfo)
}

func toChaincodeArgs(args ...string) [][]byte {
	bargs := make([][]byte, len(args))
	for i, arg := range args {
		bargs[i] = []byte(arg)
	}
	return bargs
}

func constructQueryResponseFromIterator(resultsIterator shim.StateQueryIteratorInterface) (*bytes.Buffer, error) {
	// buffer is a JSON array containing QueryResults
	var buffer bytes.Buffer
	buffer.WriteString("[")

	bArrayMemberAlreadyWritten := false
	for resultsIterator.HasNext() {
		queryResponse, err := resultsIterator.Next()
		if err != nil {
			return nil, err
		}
		// Add a comma before array members, suppress it for the first array member
		if bArrayMemberAlreadyWritten == true {
			buffer.WriteString(",")
		}
		buffer.WriteString("{\"Key\":")
		buffer.WriteString("\"")
		buffer.WriteString(queryResponse.Key)
		buffer.WriteString("\"")

		buffer.WriteString(", \"Record\":")
		// Record is a JSON object, so we write as-is
		buffer.WriteString(string(queryResponse.Value))
		buffer.WriteString("}")
		bArrayMemberAlreadyWritten = true
	}
	buffer.WriteString("]")

	return &buffer, nil
}

func getQueryResultForQueryString(stub shim.ChaincodeStubInterface, queryString string) ([]byte, error) {

	fmt.Printf("- getQueryResultForQueryString queryString:\n%s\n", queryString)

	resultsIterator, err := stub.GetQueryResult(queryString)
	if err != nil {
		return nil, err
	}
	defer resultsIterator.Close()

	buffer, err := constructQueryResponseFromIterator(resultsIterator)
	if err != nil {
		return nil, err
	}
	fmt.Printf("- getQueryResultForQueryString queryResult:\n%s\n", buffer.String())

	return buffer.Bytes(), nil
}


//encode and decode

func base64Encode(src []byte) string {
	base64EncodeResult := coder.EncodeToString(src)
	fmt.Println("base64Encode Success!")
	return base64EncodeResult
}


func base64Decode(src []byte) ([]byte, error) {
	return coder.DecodeString(string(src))
}

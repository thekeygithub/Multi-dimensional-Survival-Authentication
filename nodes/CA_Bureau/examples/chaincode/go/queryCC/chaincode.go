/*
Copyright IBM Corp. All Rights Reserved.

SPDX-License-Identifier: Apache-2.0
*/

package queryCC

import (
	"fmt"
	"github.com/hyperledger/fabric/core/chaincode/shim"
	pb "github.com/hyperledger/fabric/protos/peer"
)

// SimpleChaincode example simple Chaincode implementation
type SimpleChaincode struct {
}

func toChaincodeArgs(args ...string) [][]byte {
	bargs := make([][]byte, len(args))
	for i, arg := range args {
		bargs[i] = []byte(arg)
	}
	return bargs
}

func (t *SimpleChaincode) Init(stub shim.ChaincodeStubInterface) pb.Response {
	fmt.Printf("queryCC Init\n")
	return shim.Success(nil)
}

func (t *SimpleChaincode) Invoke(stub shim.ChaincodeStubInterface) pb.Response {
	fmt.Printf("queryCC Invoke\n")
	function, args := stub.GetFunctionAndParameters()
	if function == "queryCC" {
		return t.queryCC(stub, args)
	}

	return shim.Error("Invalid invoke function name. Expecting \"queryCC\" ")
}

// 查询信息
func (t *SimpleChaincode) queryCC(stub shim.ChaincodeStubInterface, args []string) pb.Response {

	if len(args) != 2 {
		return shim.Error("Incorrect number of arguments. Expecting 2")
	}

	queryChaincodeName := "recordCC"
	modelCCFunc := args[0]
	channelName := ""
	verifyJson := args[1]
	verifyArgs := toChaincodeArgs(modelCCFunc, verifyJson)

	modelCCResponse := stub.InvokeChaincode(queryChaincodeName, verifyArgs, channelName)
	if modelCCResponse.Status != shim.OK {
		errStr := fmt.Sprintf("Failed to query queryCC. Got error: %s", modelCCResponse.GetMessage())
		//fmt.Println(modelCCResponse)
		return shim.Error(errStr)
	}
	fmt.Printf("queryCC call success!\n")

	return shim.Success(modelCCResponse.Payload)
}

//
//// 查询原始验证信息
//func (t *SimpleChaincode) queryVerify(stub shim.ChaincodeStubInterface, args []string) pb.Response {
//	var verifyInfoKey string // Entities\
//	var resp string
//	var verifyInfo []byte
//	var err error
//
//	if len(args) != 1 {
//		return shim.Error("Incorrect number of arguments. Expecting 3")
//	}
//
//	verifyInfoKey = args[0]
//	verifyInfo, err = stub.GetState(verifyInfoKey)
//	if err != nil {
//		resp = "{\"Error\":\"Failed to get state for " + verifyInfoKey + "\"}"
//		return shim.Error(resp)
//	} else if verifyInfo == nil {
//		resp = "{\"Error\":\"Data does not exist: " + verifyInfoKey + "\"}"
//		return shim.Error(resp)
//	}
//	return shim.Success(verifyInfo)
//}
//
//// 查询验证结果
//func (t *SimpleChaincode) queryResult(stub shim.ChaincodeStubInterface, args []string) pb.Response {
//	var resultInfoKey string
//	var resultInfo []byte
//	var resp string
//	var err error
//
//	if len(args) != 1 {
//		return shim.Error("Incorrect number of arguments. Expecting 1")
//	}
//
//	resultInfoKey = args[0]
//	resultInfo, err = stub.GetState(resultInfoKey)
//
//	fmt.Printf(resultInfoKey + "!\n\n")
//	if err != nil {
//		resp = "{\"Error\":\"Failed to get state for " + resultInfoKey + "\"}"
//		return shim.Error(resp)
//	} else if resultInfo == nil {
//		resp = "{\"Error\":\"Data does not exist: " + resultInfoKey + "\"}"
//		fmt.Printf( "Data does not exist: !\n\n")
//		return shim.Error(resp)
//	}
//	return shim.Success(resultInfo)
//}
//
//// 查询验证接口调用次数
//func (t *SimpleChaincode) queryInterface(stub shim.ChaincodeStubInterface, args []string) pb.Response {
//	var InterfaceKey string
//	var InterfaceInfo []byte
//	var resp string
//	var err error
//
//	if len(args) != 1 {
//		return shim.Error("Incorrect number of arguments. Expecting 1")
//	}
//
//	InterfaceKey = args[0]
//	InterfaceInfo, err = stub.GetState(InterfaceKey)
//	if err != nil {
//		resp = "{\"Error\":\"Failed to get state for " + InterfaceKey + "\"}"
//		return shim.Error(resp)
//	} else if InterfaceInfo == nil {
//		resp = "{\"Error\":\"Data does not exist: " + InterfaceKey + "\"}"
//		return shim.Error(resp)
//	}
//	return shim.Success(InterfaceInfo)
//}
//
//// 通过模型输出状态查询验证结果
//func (t *SimpleChaincode) querybyModelOutput(stub shim.ChaincodeStubInterface, args []string) pb.Response {
//	var ModelOutput string // Entities
//	var queryResults []byte
//	var err error
//
//	if len(args) != 1 {
//		return shim.Error("Incorrect number of arguments. Expecting name of the person to query")
//	}
//
//	ModelOutput = args[0]
//	queryString := fmt.Sprintf("{\"selector\":{\"VerificationInfo\":{\"ModelOutput\":\"%s\"}}}", ModelOutput)
//
//	queryResults, err = getQueryResultForQueryString(stub, queryString)
//	if err != nil {
//		return shim.Error(err.Error())
//	}
//	return shim.Success(queryResults)
//
//}
//
//// 通过查询表达式查询验证结果
//func (t *SimpleChaincode) querybySelector(stub shim.ChaincodeStubInterface, args []string) pb.Response {
//	var ModelOutput string // Entities
//	var queryResults []byte
//	var err error
//
//	if len(args) != 1 {
//		return shim.Error("Incorrect number of arguments. Expecting name of the person to query")
//	}
//
//	ModelOutput = args[0]
//	queryString := ModelOutput
//
//	queryResults, err = getQueryResultForQueryString(stub, queryString)
//	if err != nil {
//		return shim.Error(err.Error())
//	}
//	return shim.Success(queryResults)
//
//}
//
//func constructQueryResponseFromIterator(resultsIterator shim.StateQueryIteratorInterface) (*bytes.Buffer, error) {
//	// buffer is a JSON array containing QueryResults
//	var buffer bytes.Buffer
//	buffer.WriteString("[")
//
//	bArrayMemberAlreadyWritten := false
//	for resultsIterator.HasNext() {
//		queryResponse, err := resultsIterator.Next()
//		if err != nil {
//			return nil, err
//		}
//		// Add a comma before array members, suppress it for the first array member
//		if bArrayMemberAlreadyWritten == true {
//			buffer.WriteString(",")
//		}
//		buffer.WriteString("{\"Key\":")
//		buffer.WriteString("\"")
//		buffer.WriteString(queryResponse.Key)
//		buffer.WriteString("\"")
//
//		buffer.WriteString(", \"Record\":")
//		// Record is a JSON object, so we write as-is
//		buffer.WriteString(string(queryResponse.Value))
//		buffer.WriteString("}")
//		bArrayMemberAlreadyWritten = true
//	}
//	buffer.WriteString("]")
//
//	return &buffer, nil
//}
//
//func getQueryResultForQueryString(stub shim.ChaincodeStubInterface, queryString string) ([]byte, error) {
//
//	fmt.Printf("- getQueryResultForQueryString queryString:\n%s\n", queryString)
//
//	resultsIterator, err := stub.GetQueryResult(queryString)
//	if err != nil {
//		return nil, err
//	}
//	defer resultsIterator.Close()
//
//	buffer, err := constructQueryResponseFromIterator(resultsIterator)
//	if err != nil {
//		return nil, err
//	}
//	fmt.Printf("- getQueryResultForQueryString queryResult:\n%s\n", buffer.String())
//
//	return buffer.Bytes(), nil
//}

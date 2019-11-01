/*
Copyright IBM Corp. All Rights Reserved.

SPDX-License-Identifier: Apache-2.0
*/

package main

import (
	"fmt"

	"github.com/hyperledger/fabric/core/chaincode/shim"
	//在部署的时候注释下面的GZchaincode的import，使用上面hyperledger的import
	"github.com/hyperledger/fabric/examples/chaincode/go/modelCC"
	//"github.com/GZchaincode/go/modelCC"
)

func main() {
	err := shim.Start(new(modelCC.SimpleChaincode))
	if err != nil {
		fmt.Printf("Error starting Simple chaincode: %s", err)
	}
}

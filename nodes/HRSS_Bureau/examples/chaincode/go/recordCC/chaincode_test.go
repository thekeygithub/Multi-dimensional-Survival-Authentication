/*
Copyright IBM Corp. All Rights Reserved.

SPDX-License-Identifier: Apache-2.0
*/

package recordCC

import (
	"fmt"
	"github.com/GZchaincode/go/queryCC"
	"testing"

	"github.com/hyperledger/fabric/core/chaincode/shim"
)

func checkInit(t *testing.T, stub *shim.MockStub, args [][]byte) {
	res := stub.MockInit("1", args)
	if res.Status != shim.OK {
		fmt.Println("Init failed", string(res.Message))
		t.FailNow()
	}
}

func checkState(t *testing.T, stub *shim.MockStub, name string, value string) {
	bytes := stub.State[name]
	if bytes == nil {
		fmt.Println("State", name, "failed to get value")
		t.FailNow()
	}
	if string(bytes) != value {
		fmt.Println("State value", name, "was not", value, "as expected")
		t.FailNow()
	}
}

func checkQuery(t *testing.T, stub *shim.MockStub, name string, value string) {
	res := stub.MockInvoke("1", [][]byte{[]byte("query"), []byte(name)})
	if res.Status != shim.OK {
		fmt.Println("Query", name, "failed", string(res.Message))
		t.FailNow()
	}
	if res.Payload == nil {
		fmt.Println("Query", name, "failed to get value")
		t.FailNow()
	}
	if string(res.Payload) != value {
		fmt.Println("Query value", name, "was not", value, "as expected")
		t.FailNow()
	}
}

func checkInvoke(t *testing.T, stub *shim.MockStub, args [][]byte) {
	res := stub.MockInvoke("1", args)
	if res.Status != shim.OK {
		fmt.Println("Invoke", args, "failed", string(res.Message))
		t.FailNow()
	}
}

func TestExample02_Init(t *testing.T) {
	scc := new(SimpleChaincode)
	stub := shim.NewMockStub("recordCC", scc)

	// Init A=123 B=234
	checkInit(t, stub, [][]byte{[]byte("init")})

	checkState(t, stub, "A", "123")
	checkState(t, stub, "B", "234")
}

func TestExample02_Query(t *testing.T) {
	scc := new(SimpleChaincode)
	stub := shim.NewMockStub("ex02", scc)

	// Init A=345 B=456
	checkInit(t, stub, [][]byte{[]byte("init"), []byte("A"), []byte("345"), []byte("B"), []byte("456")})

	// Query A
	checkQuery(t, stub, "A", "345")

	// Query B
	checkQuery(t, stub, "B", "456")
}

func TestExample02_Invoke(t *testing.T) {
	scc := new(SimpleChaincode)
	stub := shim.NewMockStub("recordCC", scc)

	ccEx2 := new(queryCC.SimpleChaincode)
	stubEx2 := shim.NewMockStub("queryCC", ccEx2)
	checkInit(t, stubEx2, [][]byte{[]byte("init")})
	// Init A=567 B=678
	stub.MockPeerChaincode("queryCC", stubEx2)
	checkInit(t, stub, [][]byte{[]byte("init")})

	// Invoke A->B for 123
	checkInvoke(t, stub, [][]byte{[]byte("verify"), []byte("12131312"), []byte("2018-1-1"), []byte("{\"name\":\"张三\",\"Age\":\"18\",\"HIgh\":true,\"class\":{\"Name\":\"1班\",\"Grade\":\"3\"}}")})
	checkInvoke(t, stub, [][]byte{[]byte("verify"), []byte("12131312"), []byte("2018-1-2"), []byte("{\"name\":\"张三\",\"Age\":\"18\",\"HIgh\":true,\"class\":{\"Name\":\"1班\",\"Grade\":\"3\"}}")})

	//checkInvoke(t, stub, [][]byte{[]byte("queryCC"), []byte("queryResult"), []byte("12131312")})
	//checkQuery(t, stub, "A", "444")
	//checkQuery(t, stub, "B", "801")
	//
	//// Invoke B->A for 234
	//checkInvoke(t, stub, [][]byte{[]byte("invoke"), []byte("B"), []byte("A"), []byte("234")})
	//checkQuery(t, stub, "A", "678")
	//checkQuery(t, stub, "B", "567")
	//checkQuery(t, stub, "A", "678")
	//checkQuery(t, stub, "B", "567")
}

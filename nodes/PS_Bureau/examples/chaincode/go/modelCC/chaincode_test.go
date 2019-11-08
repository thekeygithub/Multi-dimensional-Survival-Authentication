/*
Created by YYB
2018-11-05
*/

package modelCC

import (
	"encoding/json"
	"fmt"
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

func checkInvoke(t *testing.T, stub *shim.MockStub, args [][]byte) {
	res := stub.MockInvoke("1", args)
	if res.Status != shim.OK {
		fmt.Println("Invoke", args, "failed", string(res.Message))
		t.FailNow()
	}
}

func checkVerify(t *testing.T, stub *shim.MockStub, name []string, value string) {
	res := stub.MockInvoke("1", [][]byte{[]byte("verify"), []byte(name[0]),[]byte(name[1]),[]byte(name[2])})
	if res.Status != shim.OK {
		fmt.Println("verify:\n", name, "\nfailed,res.Message=", string(res.Message))
		t.FailNow()
	}
	if res.Payload == nil {
		fmt.Println("verify", name, "failed to get value")
		t.FailNow()
	}
	if string(res.Payload) != value {
		fmt.Println("verify value:", name, "\nexpect result is\n", value, "\nbut returned\n", string(res.Payload))
		t.FailNow()
	}
}

func checkVerifyFormatByte(t *testing.T,stub *shim.MockStub,input [][]byte,value string ){
	res := stub.MockInvoke("1",[][]byte{[]byte("verify"),input[0],input[1],input[2]})
	if res.Status != shim.OK {
		fmt.Println("verify:\n", string(res.Message))
		t.FailNow()
	}
	if res.Payload == nil {
		fmt.Println("verify", "failed to get value")
		t.FailNow()
	}
	if string(res.Payload) != value {
		fmt.Println("verify value:", "\nwas not\n", value, "\nas expected\n", string(res.Payload))
		t.FailNow()
	}
}

func TestModelCC_Init(t *testing.T) {
	scc := new(SimpleChaincode)
	stub := shim.NewMockStub("model", scc)

	//Init
	checkInit(t, stub, [][]byte{[]byte("init")})
}

func TestModelCC_Invoke(t *testing.T) {
	scc := new(SimpleChaincode)
	stub := shim.NewMockStub("model", scc)

	//var Input inputFormat
	//var Output outputFormat
	//var Input2 inputFormat
	//var Output2 outputFormat
	//var Input3 inputFormat
	//var Output3 outputFormat
	var Input4 inputFormat
	var Output4 outputFormat
	//Input = inputFormat{
	//	userInfo{
	//		"123456789012345678",
	//		"zhangsan",
	//	},
	//	"2018-11-02",
	//	[]departmentInputDetail{
	//		departmentInputDetail{
	//			"02",
	//			[]datalist{
	//				datalist{
	//					"02",
	//					"1",
	//					"treated at outpatient service",
	//					"2018-09-30",
	//				},
	//				datalist{
	//					"03",
	//					"1",
	//					"purchase drug",
	//					"2018-09-25",
	//				},
	//			},
	//		},
	//		departmentInputDetail{
	//			"03",
	//			[]datalist{
	//				datalist{
	//					"01",
	//					"0",
	//					"has been alive",
	//					"2018-09-30",
	//				},
	//			},
	//		},
	//	},
	//}
	//
	//Output = outputFormat{
	//	userInfo{
	//		"123456789012345678",
	//		"zhangsan",
	//	},
	//	verificationInfo{
	//		9,
	//		11,
	//		"200006",
	//		"2018-11-07",
	//	},
	//	aliveStatus{
	//		1,
	//		"",
	//		"",
	//		"",
	//		"2018-11-07",
	//	},
	//	[]abnormal{
	//		abnormal{
	//			"",
	//			"",
	//			"",
	//		},
	//	},
	//}
	//Input2 = inputFormat{
	//	userInfo{
	//		"987654321123456789",
	//		"lisi",
	//	},
	//	"2018-10-15",
	//	[]departmentInputDetail{
	//		departmentInputDetail{
	//			"02",
	//			[]datalist{
	//				datalist{
	//					"01",
	//					"1",
	//					"has been in hosptial",
	//					"2018-10-04",
	//				},
	//				datalist{
	//					"02",
	//					"1",
	//					"has been in outpatient service",
	//					"2018-10-05",
	//				},
	//				datalist{
	//					"03",
	//					"1",
	//					"has bought some drug",
	//					"2018-10-07",
	//				},
	//			},
	//		},
	//		departmentInputDetail{
	//			"08",
	//			[]datalist{
	//				datalist{
	//					"02",
	//					"1",
	//					"keep in  touch with others",
	//					"2018-10-02",
	//				},
	//			},
	//		},
	//	},
	//}
	//Output2 =outputFormat {
	//	userInfo{
	//		"987654321123456789",
	//		"lisi",
	//	},
	//	verificationInfo{
	//		0,
	//		12,
	//		"200006",
	//		"2018-11-08",
	//	},
	//	aliveStatus{
	//		1,
	//		"",
	//		"",
	//		"",
	//		"2018-11-08",
	//	},
	//	[]abnormal{
	//		abnormal{
	//			"",
	//			"",
	//			"",
	//		},
	//	},
	//}
	//Input3 = inputFormat{
	//	userInfo{
	//		"987654321123456789",
	//		"lisi",
	//	},
	//	"2018-10-15",
	//	[]departmentInputDetail{
	//		departmentInputDetail{
	//			"02",
	//			[]datalist{},
	//		},
	//		departmentInputDetail{
	//			"08",
	//			[]datalist{
	//				datalist{
	//					"02",
	//					"1",
	//					"keep in  touch with others",
	//					"2018-10-02",
	//				},
	//			},
	//		},
	//	},
	//}
	//Output3 = outputFormat{
	//	userInfo{
	//		"987654321123456789",
	//		"lisi",
	//	},
	//	verificationInfo{
	//		2,
	//		11,
	//		"1",
	//		"normal",
	//		"2018-11-18",
	//	},
	//	aliveStatus{
	//		1,
	//		"",
	//		"",
	//		"",
	//		"2018-11-18",
	//	},
	//	[]abnormal{
	//		abnormal{
	//			"",
	//			"",
	//			"",
	//		},
	//	},
	//}

	Input4 = inputFormat{
		userInfo{
			"52252619271226421X",
			"王建全",
		},
		"2018-11-17",
		[]departmentInputDetail{
			departmentInputDetail{
				"02",
				[]datalist{},
			},
			departmentInputDetail{
				"01",
				[]datalist{
					datalist{
						"02",
						"1",
						"hi",
						"2018-11-1",
					},
				},
			},
			departmentInputDetail{
				"01",
				[]datalist{
					datalist{
						"03",
						"1",
						"hi",
						"2018-10-9",
					},
				},
			},
		},
	}





	predata := outputFormat{
		userInfo{
			"",
			"",
		},
		"201811",
		verificationInfo{
			0,
			12,
			"",
			"",
			"",
		},
		aliveStatus{
			1,
			"",
			"",
			"",
			"",
			"2018-9-10",
		},
	}

	Output4 = outputFormat{
		userInfo{
			"52252619271226421X",
			"王建全",
		},
		"20181",
		verificationInfo{
			0,
			0,
			"2",
			"1",
			"2018-11-27",
		},
		aliveStatus{
			0,
			"01",
			"03",
			"2018-10-9",
			"hi",
			"2018-11-27",
		},
	}


	InputAsBytes, err := json.Marshal(Input4)
	if err != nil {
		fmt.Println("test case: input data marshal failed")
	}

	OutputAsBytes, err := json.Marshal(Output4)
	if err != nil {
		fmt.Println("test case: output data marshal failed")
	}

	predataAsBytes, err := json.Marshal(predata)
	if err != nil {
		fmt.Println("test case: output data marshal failed")
	}

	fmt.Println("***************************************")
	fmt.Println(string(InputAsBytes))
	fmt.Println("***************************************")
	//checkInvoke(t, stub, [][]byte{[]byte("invoke"), []byte("A"), []byte("B"), []byte("123")})
	var str =[]string{string(InputAsBytes),string(predataAsBytes),"201816"}
	checkVerify(t,stub,str,string(OutputAsBytes))
	//checkVerify(t, stub, []string(string(InputAsBytes),"0","12"), string(OutputAsBytes))

	//data := []byte(`{"UserInfo":{"ID":"52252619271226421X","Name":"王建全"},"Date":"2018-11-17","UserDataList":[{"DataList":[],"DataSrc":"02"},{"DataList":[{"DataId":"01","Message":"","DateTime":"2018/1/19","Result":"1"}],"DataSrc":"03"},{"DataList":[],"DataSrc":"04"}]}`)
	//checkVerifyFormatByte(t,stub,[][]byte{data,[]byte("0"),[]byte("12")},string(OutputAsBytes))
}

//测试数据
/*
{
	"UserInfo":{
		"ID":"123456789",
		"Name":"张三"
	},
	"Date":"2018-11-02",
	"UserDataList":[
		{
			"DataSrc":"03",
			"DataList":[
				{
					"DataId":"01",
					"Result":"0",
					"Message":"has been alive",
					"DateTime":"2018-09-30"
				},
				{
					"DataId":"",
					"Result":"",
					"Message":"",
					"DateTime":""
				}
			]
		},
		{
			"DataSrc":"02",
			"DataList":[
				{
					"DataId":"02",
					"Result":"1",
					"Message":"treated at outpatient service",
					"DateTime":"2018-09-30"
				},
				{
					"DataId":"03",
					"Result":"1",
					"Message":"purchase drug",
					"DateTime":"2018-09-30"
				},
			]
		}
	]
}

返回：
{
		"UserInfo":{
			"ID":"123456789",
			"Name":"张三"
		},
		"VerificationInfo":{
			"Point-S":9,
			"Point-T":0,
			"ModelOutput":"200004",
			"UpdateTime":"2018-11-05"
		},
		"AliveStatus":{
			"IsAlive":1,
			"DataSrc":"",
			"DeathDate":"",
			"CauseOfDeath":"",
			"UpdateTime":"2018-09-30"
		},
		"Abnormity":""
}
*/

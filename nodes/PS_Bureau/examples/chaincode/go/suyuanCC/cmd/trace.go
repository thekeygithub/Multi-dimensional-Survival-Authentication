package main

import (
	"fmt"
	"github.com/hyperledger/fabric/core/chaincode/shim"
	pb "github.com/hyperledger/fabric/protos/peer"
	"encoding/json"
	"strconv"
	"bytes"
)

type TraceWineChainCode struct{
}
const (
	n_used = "1"
	y_used = "0"
)
type WineInfo struct{
	WineObjectType string `json:"objectType"`	// CouchDB的字段
	WineProductId string `json:"id"`			//酒产品编码
	WineName string	`json:"name"`				// 酒名字
	WinePicHash string `json:"pichash"`			// 酒图片
	WineVolume string `json:"volume"`			// 酒容量
	WineProductDate string `json:"date"`		// 生产日期
	WineBatchNumber string `json:"batchno"`		// 批号
	WineIsUsed string `json:"isused"`			// 是否可用
	WineBottomInfo string `json:"bottomid"`		// 酒瓶标签信息
	WineCapInfo string `json:"capid"`			// 瓶盖标签信息
	WineSearchInfo WineSearchInfo `json:"searchinfo"`
	Historys []HistoryItem
}

type WineSearchInfo struct {
	WineSrchNumber string `json:"sreachno"`		// 查询次数
	WineSrchAddress string `json:"sreachad"`    //扫描地点
	WineSrchData string `json:"sreachda"`    //扫描时间
}

type HistoryItem struct {
	TxId string
	WineInfo WineInfo
}

var WineInfoArray = new(WineInfo)


func (t *TraceWineChainCode) Init(stub shim.ChaincodeStubInterface) pb.Response {
	return shim.Success(nil)
}

func (t *TraceWineChainCode) Invoke(stub shim.ChaincodeStubInterface) pb.Response {
	fn,args := stub.GetFunctionAndParameters()
	if fn == "WinePrdtById"{ // 酒厂出厂
		return t.WinePrdtById(stub,args)
	} else if fn == "WineSearchByBottom"{ // 用户查询
		return t.WineSearchByBottom(stub,args)
	} else if fn == "WineSearchByCapid"{ // 扫内码
		return t.WineSearchByCapid(stub,args)
	}else if fn == "WineBottomUpdate"{ // 扫外码更新
		return t.WineBottomUpdate(stub,args)
	}else if fn == "WineCapUpdate"{ // 更新扫描信息
		return t.WineCapUpdate(stub,args)
	}else if fn == "SearchHisByBottom"{ // 查询历史信息
		return t.SearchHisByBottom (stub,args)
	}else if fn == "Reset"{ // 查询历史信息
		return t.Reset (stub,args)
	}

	return shim.Error("Recevied unkown function invocation")
}
//查询历史信息
func (t *TraceWineChainCode)SearchHisByBottom(stub shim.ChaincodeStubInterface, args []string) pb.Response {
	if len(args) !=1{
		return shim.Error("Incorrect arguments. Expecting a key and a value")
	}
	var winInfo WineInfo
	value,err := stub.GetState(args[0])
	if err != nil{
		return shim.Error(err.Error())
	}
	if value == nil{
		return shim.Error("GetState value is empty")
	}
	err = json.Unmarshal(value,&winInfo)
	if err != nil{
		return shim.Error(err.Error())
	}
	//查询历史信息
	winIterator ,err :=stub.GetHistoryForKey(winInfo.WineProductId)//返回一个迭代器
	if err != nil{
		return shim.Error("GetHistoryForKey error "+err.Error())
	}
	defer winIterator.Close()//延迟关闭
	//遍历查询到的历史信息
	var historys []HistoryItem
	var wininfo WineInfo
	for winIterator.HasNext(){
		hisData ,err :=winIterator.Next()
		if err != nil{
			return shim.Error("遍历迭代器出错")
		}
		var hisItem HistoryItem
		hisItem.TxId=hisData.TxId//获取交易ID
		//获取此条交易状态信息
		err = json.Unmarshal(hisData.Value,&wininfo)
		if err != nil{
			return shim.Error("反序列化历史状态时出现错误")
		}
		//处理当前记录为空的情况
		if hisData.Value == nil{
			var empty WineInfo
			hisItem.WineInfo= empty
		}else {
			hisItem.WineInfo=wininfo
		}
		historys =append(historys,hisItem)
	}
	winInfo.Historys =historys
	winbyte,err :=json.Marshal(winInfo)
	if err != nil{
		return shim.Error("序列化失败")
	}
	return shim.Success(winbyte)
}
func (t *TraceWineChainCode)WinePrdtById(stub shim.ChaincodeStubInterface, args []string) pb.Response {
	if len(args) != 9 {
		return shim.Error("Incorrect arguments. Expecting a key and a value")
	}
	// 检查数据库
	wineIdAsBytes, err := stub.GetState(args[0])
	if err != nil {
		return shim.Error(err.Error())
	}
	if wineIdAsBytes != nil {
		return shim.Error("该Marble已存在~")
	}
	// 赋值
	wineobjecttype := "wine"
	wineprdtid := args[0]
	winename := args[1]
	winepichash := args[2]
	winevolume := args[3]
	wineprdtdate := args[4]
	winebatch := args[5]
	wineused := args[6]
	winebottom := args[7]
	winecap := args[8]
	// 创建对象
	wineobject := WineInfo{
		WineObjectType:wineobjecttype,
		WineProductId:wineprdtid,
		WineName:winename,
		WinePicHash:winepichash,
		WineVolume:winevolume,
		WineProductDate:wineprdtdate,
		WineBatchNumber:winebatch,
		WineIsUsed:wineused,
		WineBottomInfo:winebottom,
		WineCapInfo:winecap,
	}
	// 转换为Json格式的内容
	wineJsonAsBytes, err := json.Marshal(wineobject)
	if err != nil {
			return shim.Error(err.Error())
		}
	// 写入到账本中
	err = stub.PutState(wineprdtid, wineJsonAsBytes)
	if err != nil {
		return shim.Error(err.Error())
	}
	return shim.Success(nil)
}
//扫外码
func (t *TraceWineChainCode)WineSearchByBottom(stub shim.ChaincodeStubInterface, args []string) pb.Response {
	if len(args) != 1{
		return shim.Error("必须指定二维码编号")
	}
	arg :="{\"selector\":{\"bottomid\":\""+args[0]+"\"}}"
	resultsIterator, err := stub.GetQueryResult(arg)
	defer resultsIterator.Close()
	if err != nil {
		return shim.Error("丰富查询出现异常")
	}
	var buffer bytes.Buffer
	buffer.WriteString("[")
	bArrayMemberAlreadyWritten :=false
	for resultsIterator.HasNext() {
		queryResponse, err :=resultsIterator.Next()
		fmt.Println(queryResponse.String())
		if err != nil{
			return shim.Error("迭代器遍历出错")
		}
		if bArrayMemberAlreadyWritten == true {
			buffer.WriteString(",")
		}
		buffer.WriteString("{\"Key\":")
		buffer.WriteString("\"")
		buffer.WriteString(queryResponse.Key)
		buffer.WriteString("\"")
		buffer.WriteString(", \"Record\":")
		buffer.WriteString(string(queryResponse.Value))
		buffer.WriteString("}")
		bArrayMemberAlreadyWritten = true
	}
	buffer.WriteString("]")
	fmt.Printf("- getQueryResultForQueryString queryResult:\n%s\n", buffer.String())
	return shim.Success(buffer.Bytes())
}
//扫内码
func (t *TraceWineChainCode)WineSearchByCapid(stub shim.ChaincodeStubInterface, args []string) pb.Response {
	if len(args) != 1 {
		return shim.Error("Incorrect number of arguments. Expecting 2")
	}
	arg :="{\"selector\":{\"capid\":\""+args[0]+"\"}}"
	resultsIterator, err := stub.GetQueryResult(arg)
	defer resultsIterator.Close()
	if err != nil {
		return shim.Error("丰富查询出现异常")
	}
	var buffer bytes.Buffer
	buffer.WriteString("[")
	bArrayMemberAlreadyWritten :=false
	for resultsIterator.HasNext() {
		queryResponse, err :=resultsIterator.Next()
		if err != nil{
			return shim.Error("迭代器遍历出错")
		}
		if bArrayMemberAlreadyWritten == true {
			buffer.WriteString(",")
		}
		buffer.WriteString("{\"Key\":")
		buffer.WriteString("\"")
		buffer.WriteString(queryResponse.Key)
		buffer.WriteString("\"")
		buffer.WriteString(", \"Record\":")
		buffer.WriteString(string(queryResponse.Value))
		buffer.WriteString("}")
		bArrayMemberAlreadyWritten = true
	}
	buffer.WriteString("]")
	return shim.Success(buffer.Bytes())
}
//扫外码更新
func (t *TraceWineChainCode )WineBottomUpdate(stub shim.ChaincodeStubInterface, args []string) pb.Response {
		if len(args) != 3 {
			return shim.Error("Incorrect number of arguments. Expecting 2")
		}
		wineprdtid := args[0]
		// 查询账本中是否已经存在信息
		winIdAsBytes, err := stub.GetState(wineprdtid)
		if err != nil {
			return shim.Error(err.Error())
		} else if winIdAsBytes == nil {
			return shim.Error("该信息不存在~")
		}
		wineobject := &WineInfo{}
		err = json.Unmarshal(winIdAsBytes, &wineobject)
		if err != nil {
			return shim.Error(err.Error())
		}
		//修改扫码信息
		if wineobject.WineSearchInfo.WineSrchNumber == ""{
			wineobject.WineSearchInfo.WineSrchNumber ="0"
		}
		srchN,err:=strconv.Atoi(wineobject.WineSearchInfo.WineSrchNumber)
		if err != nil{
			return shim.Error(err.Error())
		}
		srchS:=strconv.Itoa(srchN+1)
		wineobject.WineSearchInfo.WineSrchNumber=srchS
		wineobject.WineSearchInfo.WineSrchAddress=args[1]
		wineobject.WineSearchInfo.WineSrchData=args[2]
		wineJsonAsBytes, err := json.Marshal(wineobject)
		if err != nil {
			return shim.Error(err.Error())
		}
		// 写入到账本中
		err = stub.PutState(wineprdtid, wineJsonAsBytes)
		if err != nil {
			return shim.Error(err.Error())
		}
		return shim.Success(nil)
	}
//扫内码更新
func (t *TraceWineChainCode )WineCapUpdate(stub shim.ChaincodeStubInterface, args []string) pb.Response {
	if len(args) != 3 {
		return shim.Error("Incorrect number of arguments. Expecting 2")
	}
	wineprdtid := args[0]
	// 查询账本中是否已经存在信息
	winIdAsBytes, err := stub.GetState(wineprdtid)
	if err != nil {
		return shim.Error(err.Error())
	} else if winIdAsBytes == nil {
		return shim.Error("该信息不存在~")
	}
	wineobject := &WineInfo{}
	err = json.Unmarshal(winIdAsBytes, &wineobject)
	if err != nil {
		return shim.Error(err.Error())
	}
	//修改次数和是否可用
	srchN,err:=strconv.Atoi(wineobject.WineSearchInfo.WineSrchNumber)
	if err != nil{
		return shim.Error(err.Error())
	}
	srchS:=strconv.Itoa(srchN+1)
	wineobject.WineSearchInfo.WineSrchNumber=srchS
	wineobject.WineIsUsed = n_used
	wineobject.WineSearchInfo.WineSrchAddress=args[1]
	wineobject.WineSearchInfo.WineSrchData=args[2]
	wineJsonAsBytes, err := json.Marshal(wineobject)
	if err != nil {
		return shim.Error(err.Error())
	}
	// 写入到账本中
	err = stub.PutState(wineprdtid, wineJsonAsBytes)
	if err != nil {
		return shim.Error(err.Error())
	}
	return shim.Success(nil)
}

func (t *TraceWineChainCode )Reset(stub shim.ChaincodeStubInterface, args []string) pb.Response {
	if len(args) != 1 {
		return shim.Error("Incorrect number of arguments. Expecting 2")
	}
	wineprdtid := args[0]
	// 查询账本中是否已经存在信息
	winIdAsBytes, err := stub.GetState(wineprdtid)
	if err != nil {
		return shim.Error(err.Error())
	} else if winIdAsBytes == nil {
		return shim.Error("该信息不存在~")
	}
	wineobject := &WineInfo{}
	err = json.Unmarshal(winIdAsBytes, &wineobject)
	if err != nil {
		return shim.Error(err.Error())
	}

	wineobject.WineIsUsed = y_used

	wineJsonAsBytes, err := json.Marshal(wineobject)
	if err != nil {
		return shim.Error(err.Error())
	}
	// 写入到账本中
	err = stub.PutState(wineprdtid, wineJsonAsBytes)
	if err != nil {
		return shim.Error(err.Error())
	}
	return shim.Success(nil)
}
func main(){
	err := shim.Start(new(TraceWineChainCode))
	if err != nil {
		fmt.Printf("Error starting Food chaincode: %s ",err)
	}
}





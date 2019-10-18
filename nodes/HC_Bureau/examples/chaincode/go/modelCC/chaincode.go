/*
Created by YYB
2018-11-01
*/
package modelCC

import (
	"encoding/json"
	"github.com/hyperledger/fabric/core/chaincode/shim"
	pb "github.com/hyperledger/fabric/protos/peer"
	"strconv"
	"strings"
	"time"
)

// SimpleChaincode example simple Chaincode implementation
type SimpleChaincode struct {
}

//Data Sources
const SOCSEU string = "01"     //人社部门
const YIBAO string = "02"      //医保部门
const CIVIL string = "03"      //民政部门
const POLICE string = "04"     //公安部门
const WEIJIANWEI string = "05" //卫健委部门
const COURT string = "06"      //法院部门
const TRAFFIC string = "07"    //交通部门
const TELIOPE string = "08"    //通信运营商
const BANK string = "09"       //银行

//data type
const SOCSEU_DATASTATUS_SUSPECTED string = "01"
const SOCSEU_SUSPECTEDDATA_CHECK_FAIl string = "0"
const SOCSEU_SUSPECTEDDATA_CHECK_SUCCESS string = "1"
const SOCSEU_SUSPECTEDDATA_CALLERROR string = "9"
const SOCSEU_DATASTATUS_DEATH string = "02"
const SOCSEU_DEATHDATA_STILLALIVE string = "0"
const SOCSEU_DEATHDATA_HASDEATH string = "1"
const SOCSEU_DEATHDATA_CALLERROR string = "9"
const SOCSEU_MANUAL_INTERVENTION string = "03"
const SOCSEU_MANUAL_RECORRECT_ALIVE  string = "0"      //人工干预，生存状态为0，存活
const SOCSEU_MANUAL_RECORRECT_DEATH  string = "1"      //人工干预，生存状态为1，死亡
const SOCSEU_MANUAL_RECORRECT_CALLERROR  string = "9"  //人工干预，调用出错

const YIBAO_ZHUYUAN string = "01"
const YIBAO_ZHUYUAN_NOT string = "0"
const YIBAO_ZHUYUAN_YES string = "1"
const YIBAO_ZHUYAUN_CALLERROR string = "9"
const YIBAO_MENZHEN string = "02"
const YIBAO_MENZHEN_NOT string = "0"
const YIBAO_MENZHEN_YES string = "1"
const YIBAO_MENZHEN_CALLERROR string = "9"
const YIBAO_DRUGS string = "03"
const YIBAO_DRUGS_NOT string = "0"
const YIBAO_DRUGS_YES string = "1"
const YIBAO_DRUGS_CALLERROR string = "9"

const CIVIL_HUOHUADATA string = "01"
const CIVIL_HUOHUADATA_NOTFOUND string = "0"
const CIVIL_HUOHUADATA_FOUND string = "1"
const CIVIL_HUOHUADATA_CALLERROR string = "9"

const POLICE_SIWANGHUJIZHUXIAO string = "01"
const POLICE_SWHJZX_NOT string = "0"
const POLICE_SWHJZX_YES string = "1"
const POLICE_SWHJZX_CALLERROR string = "9"
const POLICE_YIWAISIWANG string = "02"
const POLICE_YWSW_NOT string = "0"
const POLICE_YWSW_YES string = "1"
const POLICE_YWSW_CALLERROR string = "9"
const POLICE_PANXING string = "03"
const POLICE_PANXING_NOT string = "0"
const POLICE_PANXING_YES string = "1"
const POLICE_PANXING_CALLERROR string = "9"
const POLICE_ZHUSUJILU string = "04"
const POLICE_ZHUSUJILU_NOT string = "0"
const POLICE_ZHUSUJILU_YES string = "1"
const POLICE_ZHUSUJILU_CALLERROR string = "9"

const WEIJIANWEI_YYSW string = "01"
const WEIJIANWEI_YYSW_NOT string = "0"
const WEIJIANWEI_YYSW_YES string = "1"
const WEIJIANWEI_YYSW_CALLERROR string = "9"
const WEIJIANWEI_HEALTHEXAM string = "02"
const WEIJIANWEI_HEALTHEXAM_NOT string = "0"
const WEIJIANWEI_HEALTHEXAM_YES string = "1"
const WEIJIANWEI_HEALTHEXAM_CALLERROR string = "9"
const WEIJIANWEI_CHRONICDISEASE string = "03"
const WEIJIANWEI_CHRONICDISEASE_NOT string = "0"
const WEIJIANWEI_CHRONICDISEASE_YES string = "1"
const WEIJIANWEI_CHRONICDISEASE_CALLERROR string = "9"

const COURT_SENTENCE string = "01"
const COURT_SENTENCE_NOT string = "0"
const COURT_SENTENCE_YES string = "1"
const COURT_SENTENCE_CALLERROR string = "9"

const TRAFFIC_TRAVEL_RECORDS string = "01"
const TRAFFIC_TRAVEL_RECORDS_NOT string = "0"
const TRAFFIC_TRAVEL_RECORDS_YES string = "1"
const TRAFFIC_TRAVEL_RECORDS_CALLERROR string = "9"

const TELIOPE_ACCOUNT string = "01" //运营商开户
const TELIOPE_ACCOUNT_NOT string = "0"
const TELIOPE_ACCOUNT_YES string = "1"
const TELIOPE_ACCOUNT_CALLERROR string = "9"
const TELIOPE_COMM string = "02" //运营商通讯记录
const TELIOPE_COMM_NOT string = "0"
const TELIOPE_COMM_YES string = "1"
const TELIOPE_COMM_CALLERROR string = "9"

const BANK_ACCOUNT string = "01"
const BANK_ACCOUNT_NOT string = "0"
const BANK_ACCOUNT_YES string = "1"
const BANK_ACCOUNT_CALLERROR string = "9"
const BANK_CAPITAL string = "02"
const BANK_CAPITAL_NOT string = "0"
const BANK_CAPITAL_YES string = "1"
const BANK_CAPITAL_CALLERROR string = "9"

//result
const POSITIVE_RESULT string = "0"
const NEGATIVE_RESULT string = "1"

const GREATER int = 1
const LESS int = -1
const EQUAL int = 0

//Output Status
const DEATH string = "2"
const CRIMINAL string = "3"
//const EXPLICITVERIFY string = "200003"
//const IMPLICITVERITY string = "200004"
const SUSPECTED string = "5"
const NORMAL string = "1"

//Scores Detail
const MENZHENSCORE int = 5
const DRUGSCORE int = 3

const COMMSCORE int = 2
const CAPTIALSCORE int = 1

type userInfo struct {
	IdNumber string `json:"ID"`
	Name     string `json:"Name"`
}

type datalist struct {
	DataID   string `json:"DataID"`
	Result   string `json:"Result"`
	Message  string `json:"Message"`
	DateTime string `json:"DateTime"`
}

type departmentInputDetail struct {
	DataSrc  string     `json:DataSrc`
	DataList []datalist `json:"DataList"`
}

type inputFormat struct {
	UserInfo         userInfo                `json:"UserInfo"`
	Date             string                  `json:"Date"` //需进行通配符验证
	UserDataList     []departmentInputDetail `json:"UserDataList"`
}

const ALIVESTATUS int = 1
const DEATHSTATUS int = 0

type aliveStatus struct {
	IsAlive            int    `json:"IsAlive"`
	DataSrc            string `json:"DataSrc"`
	DataID             string `json:"DataID"`
	SurvivalStatusDate string `json:"SurvivalStatusDate"`
	CauseOfDeath       string `json:"CauseOfDeath"`
	UpdateTime         string `json:"UpdateTime"`
}

type verificationInfo struct {
	Point_S           int    `json:"PointS""`
	Point_T           int    `json:"PointT""`
	ModelOutputStatus string `json:"ModelOutput"`
	OutputDesc        string  `json:OutputDesc`
	Updatetime        string `json:"UpdateTime"`
}

//type abnormal struct {
//	Data    string `json:"Data"`
//	Date    string `json:"Date"`
//	DataSrc string `json:"DataSrc"`
//}

type outputFormat struct {
	UserInfo         userInfo         `json:"UserInfo"`
	AccountDate      string
	VerificationInfo verificationInfo `json:"VerificationInfo"`
	AliveStatus      aliveStatus      `json:"AliveStatus"`
	//Abnormity        []abnormal       `json:"Abnormity"`
}

type ScoreRecord struct {
	IDNumber string `json:"idnumber"`
	S        int    `json:"s"`
	T        int    `json:"t"`
}


// Init is a no-op
func (t *SimpleChaincode) Init(stub shim.ChaincodeStubInterface) pb.Response {
	return shim.Success(nil)
}

func (t *SimpleChaincode) Invoke(stub shim.ChaincodeStubInterface) pb.Response {
	//fmt.Println("Model.chaincode Invoke")
	function, args := stub.GetFunctionAndParameters()
	if function == "verify" {
		if len(args) != 3 {      //2个值：输入JSON的字符串格式、上次的值、以及时间　
			return shim.Error("invalid paramenters，please input 3 string")
		}
		return t.verify(stub, args)
	}
	return shim.Error("Invalid invoke function name. Expecting \"verify\"")
}

func (t *SimpleChaincode) verify(stub shim.ChaincodeStubInterface, args []string) pb.Response {
	var inputdata inputFormat
	var outputdata,predata outputFormat
	err := json.Unmarshal([]byte(args[0]), &inputdata)
	if err != nil {
		return shim.Error("fail to decode JSON of inputdata!")
	}
	var UserInfo userInfo
	UserInfo = inputdata.UserInfo //resolve the user information
	var IDNumber string
	IDNumber = UserInfo.IdNumber
	if len(IDNumber) != 18 {
		return shim.Error("please input the correct IDNumber,expecting 18 digits!")
	}
	if len(UserInfo.Name) <= 0 {
		return shim.Error("The name must not empty,please retry again.Do not forget to input your name")
	}
	//reg1 :=regexp.MustCompile(`(\d{18})`)
	//target1 := reg1.FindAllString(IDNumber,-1)
	//if target1 == nil {
	//	return shim.Error("IDNumber must contain 18 digits!")
	//}

	//reg2 := regexp.MustCompile(`(\d{4})-((1[0-2])|(0?[1-9]))-(([12][0-9])|(3[01])|(0?[1-9]))`)
	//target2 := reg2.FindAllString(inputdata.Date,-1)
	//if target2 == nil{
	//	return shim.Error("Date format is xxxx-xx-xx,please input again")
	//}
	//if len(inputdata.Date) != 10 {
	//	return shim.Error("Date format is xxxx-xx-xx,please input again    " + strconv.Itoa(len(inputdata.Date)))
	//}
	var departments []departmentInputDetail
	departments = inputdata.UserDataList
	if len(departments) <= 0 {
		return shim.Error("cannot detect the data to be compute!please input again!")
	}

	var VerifyInfo verificationInfo
	var AliveStatus,tempAliveStatus  aliveStatus
	err = json.Unmarshal([]byte(args[1]), &predata)
	if err != nil {
		return shim.Error("fail to decode JSON of predata!")
	}
	preAliveStatus := predata.AliveStatus
	AliveStatus.IsAlive = 1
	tempAliveStatus.IsAlive =1

	//var stScore = getScoreState(stub, IDNumber)
	//var scoreRecord ScoreRecord
	//err = json.Unmarshal([]byte(stScore), &scoreRecord)
	//if err != nil {
	//	return shim.Error("exec getScoreState error:" + err.Error() + "\n" + stScore)
	//}
	var isCrime bool

	for i := 0; i < len(departments); i++ {
		department := departments[i]
		var dataSrc string
		var dataLists []datalist
		dataSrc = department.DataSrc // resolve the specified data source
		if len(dataSrc) != 2 {
			return shim.Error("The number " + strconv.Itoa(i+1) + " datasource record is wrong")
		}
		dataLists = department.DataList //resolve the data item
			tempAliveStatus = checkDeath(stub, dataSrc, dataLists)
		if tempAliveStatus.DataID == SOCSEU_MANUAL_INTERVENTION && tempAliveStatus.IsAlive == DEATHSTATUS {//人工干预认为死亡时必死
			AliveStatus = tempAliveStatus
			break
		}
		isGreater := compareDate(AliveStatus.SurvivalStatusDate, tempAliveStatus.SurvivalStatusDate) //比较前后时间
		if isGreater == LESS {  //若当前的时间比原始的时间新，更新原始状态
			AliveStatus = tempAliveStatus
		}
		if dataSrc == POLICE {
			isCrime = checkCriminal(stub, dataSrc, dataLists)
		}
	}

	//只讨论当前状态继承以前状态的情况
	if preAliveStatus.IsAlive == ALIVESTATUS { //当以前状态是存活时
		if AliveStatus.IsAlive == ALIVESTATUS { //在以前为存活时，当前状态也为存活时
			AliveStatus = preAliveStatus       //当前状态继承以前状态
		} else if AliveStatus.IsAlive == DEATHSTATUS { //当以前状态为存活，当前状态为死亡时
			if AliveStatus.DataID != SOCSEU_MANUAL_INTERVENTION { //若当前死亡状态不是由于人工干预造成
				isGreater := compareDate(preAliveStatus.SurvivalStatusDate, AliveStatus.SurvivalStatusDate) //比较以前和当前的生存状态时间
				if isGreater == GREATER && (preAliveStatus.DataID == SOCSEU_DATASTATUS_SUSPECTED ||
					preAliveStatus.DataID == SOCSEU_MANUAL_INTERVENTION) {//当以前的时间比当前的时间新，同时原来状态为疑似核查或者人工干预造成的存活时
					AliveStatus = preAliveStatus  //当前状态继承以前状态
				}
			}
		}
	} else if preAliveStatus.IsAlive == DEATHSTATUS { //当以前状态为死亡时
		if AliveStatus.IsAlive == DEATHSTATUS {  //在以前状态为死亡，当前状态也为死亡时
			AliveStatus = preAliveStatus
		} else if AliveStatus.IsAlive == ALIVESTATUS{  //当以前状态为死亡，当前状态为存活时
			if preAliveStatus.DataID == SOCSEU_MANUAL_INTERVENTION { //若以前状态为人工干预造成时
				AliveStatus =preAliveStatus
			} else {
				isGreater := compareDate(preAliveStatus.SurvivalStatusDate, AliveStatus.SurvivalStatusDate)
				if !(isGreater == LESS && (AliveStatus.DataID == SOCSEU_DATASTATUS_SUSPECTED ||
					AliveStatus.DataID == SOCSEU_MANUAL_INTERVENTION)) { //当不满足当前的时间比以前的时间新，同时当前状态为疑似核查或者人工干预造成的存活时
					AliveStatus = preAliveStatus
				}
			}
		}
	}
	if AliveStatus != preAliveStatus { //当当前状态与以前状态不相同时，更新更新时间
		AliveStatus.UpdateTime = time.Now().Format("2006-01-02")
	}


	var S, T int = 0, 1
	var newRecord ScoreRecord
	newRecord.IDNumber = IDNumber
	newRecord.S = predata.VerificationInfo.Point_S
	newRecord.T = predata.VerificationInfo.Point_T

	//var str string
	//str = getScoreState(stub, IDNumber)
	//err = json.Unmarshal([]byte(str), &newRecord)
	//if err != nil {
	//	return shim.Error(err.Error())
	//}
	var isExplicitProve = false
	var s, tt int
	for i := 0; i < len(departments); i++ {
		department := departments[i]
		var dataSrc string
		var dataLists []datalist
		dataSrc = department.DataSrc    // resolve the specified data source
		dataLists = department.DataList //resolve the data item
		isExplicitProve, s, tt = checkExpOrImpFeature(dataSrc, dataLists, isExplicitProve)//把上次的标志也写入
		if tt == 100 { //如果疑似核查未通过直接全0
			S = 0
			T = 0
			newRecord.S = S
			newRecord.T = T
			tt = 0
			break
		}
		S = S + s
	}
	if S > 10 { //一次最多加10分
		S = 10
	}
	if isExplicitProve == true {
		S = 0   //S=0 T=12
		T = 12
		newRecord.S = S
		newRecord.T = T
	} else {
		newRecord.S += S
		newRecord.T -= 1
		if newRecord.T < 0 {
			newRecord.T = 0
		}
	}
	if newRecord.S >= 100 {
		newRecord.S = 0
		newRecord.T = 12
	}
	//putScoreState(stub, IDNumber, newRecord)
	VerifyInfo = initialVerifyInfo(AliveStatus, isCrime, isExplicitProve, newRecord)

	//var Abnormal []abnormal = []abnormal{
	//	abnormal{
	//		"",
	//		"",
	//		"",
	//	},
	//}
	outputdata = initialOutput(UserInfo, VerifyInfo, AliveStatus)
	outputdata.AccountDate = args[2]
	outputdataAsByte, err := json.Marshal(outputdata)
	if err != nil {
		return shim.Error("outputdataAsByte marshal failed!")
	}
	return shim.Success(outputdataAsByte)
}

//func getScoreState(stub shim.ChaincodeStubInterface, args string) string {
//	var QueryIDNumber string
//	var err error
//	//if len(args) != 1 {
//	//	return "Incorrect number of arguments. Expecting IDNumber of the person to query\n"+strconv.Itoa(len(args))
//	//}
//	QueryIDNumber = args
//	valAsbytes, err := stub.GetState(QueryIDNumber)
//	if err != nil { //failed to get S and T score
//		return " exception has occured,failed to get S and T score"
//	} else if valAsbytes == nil { //the specified record is not exist
//		var S int
//		S = 0
//		var T int
//		T = 12
//		scoreRecord := &ScoreRecord{QueryIDNumber, S, T}
//		recordJSONasBytes, err := json.Marshal(scoreRecord)
//		if err != nil {
//			return "marshal failed"
//		}
//		return string(recordJSONasBytes)
//	}
//	return string(valAsbytes)
//}
//
//func putScoreState(stub shim.ChaincodeStubInterface, IDNumber string, scoreAsStruct ScoreRecord) string {
//
//	QueryIDNumber := IDNumber
//	scoreRecord := scoreAsStruct
//	scoreAsByte, err := json.Marshal(scoreRecord)
//	if err != nil {
//		return "while putstate,json marshal failed"
//	}
//	err1 := stub.PutState(QueryIDNumber, scoreAsByte)
//	if err1 != nil {
//		return "exception has occured,failed to put S and T score"
//	} else {
//		return "success restore S and T score"
//	}
//}
//
//// Deletes an entity from state
//func (t *SimpleChaincode) delete(stub shim.ChaincodeStubInterface, args []string) string {
//	if len(args) != 1 {
//		return "Incorrect number of arguments. Expecting 1"
//	}
//
//	IDNumber := args[0]
//	// Delete the key from the state in ledger
//	err := stub.DelState(IDNumber)
//	if err != nil {
//		return "Failed to delete state"
//	}
//
//	return "delete succeed"
//}

func checkDeath(stub shim.ChaincodeStubInterface, dataSrc string, dataLists []datalist) aliveStatus {

	var deathResult aliveStatus
	deathResult.IsAlive = 1
	/*
	若民政数据包含火化数据，则判定为死亡状态，否则返回空结构体
	*/
	if dataSrc == CIVIL {  //民政相关
		for i := 0; i < len(dataLists); i++ {
			if dataLists[i].DataID == CIVIL_HUOHUADATA && dataLists[i].Result == CIVIL_HUOHUADATA_FOUND { //当有火化数据且有数据结果时
				deathResult.DataID = CIVIL_HUOHUADATA
				deathResult.IsAlive = DEATHSTATUS
				deathResult.SurvivalStatusDate = dataLists[i].DateTime
				deathResult.CauseOfDeath = dataLists[i].Message
				deathResult.UpdateTime = ""
				deathResult.DataSrc = CIVIL
				return deathResult //直接返回结果
			}
		}
		//if deathResult.IsAlive == 0 && deathResult.Datasrc == "" && deathResult.SurvivalStatusDate == "" && deathResult.CauseOfDeath == "" && deathResult.UpdateTime == "" {
		//	deathResult.IsAlive = ALIVESTATUS
		//	deathResult.UpdateTime = time.Now().Format("2006-01-02")
		//}
		return deathResult
	} else if dataSrc == SOCSEU { //人社相关
		for i := 0; i < len(dataLists); i++ {
			//当前疑似核查只检查活人是否被误判定为死亡
			if dataLists[i].DataID == SOCSEU_DATASTATUS_SUSPECTED && dataLists[i].Result == SOCSEU_SUSPECTEDDATA_CHECK_SUCCESS { //当疑似核查通过时
				isGreater := compareDate(deathResult.SurvivalStatusDate, dataLists[i].DateTime) //比较死亡时间和当前存活数据的上传时间
				if isGreater != GREATER { //如果当前时间为最新时间则认为存活
					deathResult.DataID = SOCSEU_DATASTATUS_SUSPECTED
					deathResult.IsAlive = ALIVESTATUS
					deathResult.DataSrc = SOCSEU
					deathResult.SurvivalStatusDate = dataLists[i].DateTime
					deathResult.CauseOfDeath = ""
					deathResult.UpdateTime = ""
				}
			}
			if dataLists[i].DataID == SOCSEU_DATASTATUS_DEATH && dataLists[i].Result == SOCSEU_DEATHDATA_HASDEATH { //当有死亡数据时

				isGreater := compareDate(deathResult.SurvivalStatusDate, dataLists[i].DateTime) //比较之前的存活时间和当前的死亡时间
				if isGreater != GREATER {//如果死亡时间为最新则认为死亡
					deathResult.DataID = SOCSEU_DATASTATUS_DEATH
					deathResult.IsAlive = DEATHSTATUS
					deathResult.SurvivalStatusDate = dataLists[i].DateTime
					deathResult.CauseOfDeath = dataLists[i].Message
					deathResult.DataSrc = SOCSEU
					deathResult.UpdateTime = ""
				}

			}
			if dataLists[i].DataID == SOCSEU_MANUAL_INTERVENTION && dataLists[i].Result == SOCSEU_MANUAL_RECORRECT_ALIVE{ //当人工干预认为存活时
				isGreater := compareDate(deathResult.SurvivalStatusDate, dataLists[i].DateTime)
				if isGreater != GREATER {
					deathResult.DataID = SOCSEU_MANUAL_INTERVENTION
					deathResult.IsAlive = ALIVESTATUS
					deathResult.SurvivalStatusDate = dataLists[i].DateTime
					deathResult.CauseOfDeath = ""
					deathResult.DataSrc = SOCSEU
					deathResult.UpdateTime = ""
				}

			}
			if dataLists[i].DataID == SOCSEU_MANUAL_INTERVENTION && dataLists[i].Result == SOCSEU_MANUAL_RECORRECT_DEATH{ //当人工干预认为死亡时必死无疑
				deathResult.DataID = SOCSEU_MANUAL_INTERVENTION
				deathResult.IsAlive = DEATHSTATUS
				deathResult.SurvivalStatusDate = dataLists[i].DateTime
				deathResult.CauseOfDeath = dataLists[i].Message
				deathResult.UpdateTime = ""
				deathResult.DataSrc = SOCSEU
				return deathResult
			}
		}
		return deathResult
	} else if dataSrc == POLICE { //公安相关
		for i := 0; i < len(dataLists); i++ {
			if dataLists[i].DataID == POLICE_SIWANGHUJIZHUXIAO && dataLists[i].Result == POLICE_SWHJZX_YES { //若出现死亡数据

				isGreater := compareDate(deathResult.SurvivalStatusDate, dataLists[i].DateTime) //比较之前的死亡时间和当前的死亡时间
				if isGreater != GREATER {
					deathResult.DataID = POLICE_SIWANGHUJIZHUXIAO
					deathResult.IsAlive = DEATHSTATUS
					deathResult.SurvivalStatusDate = dataLists[i].DateTime
					deathResult.CauseOfDeath = dataLists[i].Message
					deathResult.UpdateTime = ""
					deathResult.DataSrc = POLICE
				}

			}
			if dataLists[i].DataID == POLICE_YIWAISIWANG && dataLists[i].Result == POLICE_YWSW_YES {

				isGreater := compareDate(deathResult.SurvivalStatusDate, dataLists[i].DateTime) //比较之前的死亡时间和当前的死亡时间
				if isGreater != GREATER {
					deathResult.DataID = POLICE_YIWAISIWANG
					deathResult.IsAlive = DEATHSTATUS
					deathResult.SurvivalStatusDate = dataLists[i].DateTime
					deathResult.CauseOfDeath = dataLists[i].Message
					deathResult.UpdateTime = ""
					deathResult.DataSrc = POLICE
				}

			}
		}

		return deathResult
	} else if dataSrc == WEIJIANWEI { //卫建委相关
		for i := 0; i < len(dataLists); i++  {
			if dataLists[i].DataID == WEIJIANWEI_YYSW && dataLists[i].Result == WEIJIANWEI_YYSW_YES {
				isGreater := compareDate(deathResult.SurvivalStatusDate, dataLists[i].DateTime)
				if isGreater != GREATER {
					deathResult.DataID = WEIJIANWEI_YYSW
					deathResult.IsAlive = DEATHSTATUS
					deathResult.SurvivalStatusDate = dataLists[i].DateTime
					deathResult.CauseOfDeath = dataLists[i].Message
					deathResult.UpdateTime = ""
					deathResult.DataSrc = WEIJIANWEI
				}
			}

		}
	}

	return deathResult
}

func checkCriminal(stub shim.ChaincodeStubInterface, datasrc string, dataLists []datalist) bool {
	//检查出本月存在一次犯罪记录即可以返回
	for i := 0; i < len(dataLists); i++ {
		if datasrc == POLICE && dataLists[i].DataID == POLICE_PANXING && dataLists[i].Result == NEGATIVE_RESULT {
			return true
		} else if datasrc == COURT && dataLists[i].DataID == COURT_SENTENCE && dataLists[i].Result == COURT_SENTENCE_YES {
			return true
		}
	}
	return false
}

func checkExpOrImpFeature(datasrc string, dataLists []datalist, preIsExplicitProve bool) (bool, int, int) {
	var result  = false
	var s, t  = 0, 0
	for i := 0; i < len(dataLists); i++ {
		switch datasrc {
		case SOCSEU:
			if dataLists[i].DataID == SOCSEU_DATASTATUS_SUSPECTED && dataLists[i].Result == SOCSEU_SUSPECTEDDATA_CHECK_SUCCESS {
				return  true, 0, 12
			}
			if dataLists[i].DataID == SOCSEU_DATASTATUS_SUSPECTED && dataLists[i].Result == SOCSEU_SUSPECTEDDATA_CHECK_FAIl { //疑似核查未通过直接0分
				return false, 0, 100
			}
			break
		case YIBAO:
			if dataLists[i].DataID == YIBAO_ZHUYUAN && dataLists[i].Result == YIBAO_ZHUYUAN_YES { //强验证
				return true, 0, 12
			}
			if dataLists[i].DataID == YIBAO_MENZHEN && dataLists[i].Result == YIBAO_MENZHEN_YES {
				s = s + MENZHENSCORE
			}
			if dataLists[i].DataID == YIBAO_DRUGS && dataLists[i].Result == YIBAO_DRUGS_YES {
				s = s + DRUGSCORE
			}
			break
		case POLICE:
			if dataLists[i].DataID == POLICE_ZHUSUJILU && dataLists[i].Result == POLICE_ZHUSUJILU_YES {
				return true, 0, 12
			}
			break
		case WEIJIANWEI:
			if dataLists[i].DataID == WEIJIANWEI_HEALTHEXAM && dataLists[i].Result == WEIJIANWEI_HEALTHEXAM_YES {
				return true, 0, 12
			}
			break
		case TRAFFIC:
			if dataLists[i].DataID == TRAFFIC_TRAVEL_RECORDS && dataLists[i].Result == TRAFFIC_TRAVEL_RECORDS_YES{
				return true, 0, 12
			}
			break
		case TELIOPE:
			if dataLists[i].DataID == TELIOPE_ACCOUNT && dataLists[i].Result == TELIOPE_ACCOUNT_YES {
				return true, 0, 12
			}
			if dataLists[i].DataID == TELIOPE_COMM && dataLists[i].Result == TELIOPE_COMM_YES {
				s += COMMSCORE
			}
			break
		case BANK:
			if dataLists[i].DataID == BANK_ACCOUNT && dataLists[i].Result == BANK_ACCOUNT_YES {
				return true, 0, 12
			}
			if dataLists[i].DataID == BANK_CAPITAL && dataLists[i].Result == BANK_CAPITAL_YES {
				s += CAPTIALSCORE
			}
			break
		}
	}
	if preIsExplicitProve == true {
		return true, 0, 12
	}
	t = 1
	return result, s, t
}

func compareDate(date1 string, date2 string) int {
	if date1 == "" {
		return LESS
	}
	if date2 == ""{
		return GREATER
	}
	date1 = strings.Replace(date1,"/","-",-1)
	date2 = strings.Replace(date2,"/","-",-1)

	var str1 = strings.Split(date1, "-")
	var str2 = strings.Split(date2, "-")
	if len(str1) != 3 || len(str2) != 3 {
		shim.Error("the date format included in the UserDataList is wrong ,please input again follow the format:xxxx-xx-xx")
	}
	year1, _ := strconv.ParseInt(str1[0], 10, 32)
	year2, _ := strconv.ParseInt(str2[0], 10, 32)
	month1, _ := strconv.ParseInt(str1[1], 10, 32)
	month2, _ := strconv.ParseInt(str2[1], 10, 32)
	day1, _ := strconv.ParseInt(str1[2], 10, 32)
	day2, _ := strconv.ParseInt(str2[2], 10, 32)

	if year1 != year2 {
		if year1 < year2 {
			return LESS
		} else {
			return GREATER
		}
	} else if month1 != month2 {
		if month1 < month2 {
			return LESS
		} else {
			return GREATER
		}
	} else if day1 != day2 {
		if day1 < day2 {
			return LESS
		} else {
			return GREATER
		}
	} else {
		return EQUAL
	}
}

func initialOutput(UserInfo userInfo, VerificationInfo verificationInfo, AliveStastus aliveStatus) outputFormat {
	var output outputFormat
	output.UserInfo = UserInfo
	output.AliveStatus = AliveStastus
	output.VerificationInfo = VerificationInfo
	//output.Abnormity = Abnormity
	return output
}

func initialVerifyInfo(AliveStatus aliveStatus, isCrime bool, isExplicitProve bool, record ScoreRecord) verificationInfo {
	var VerifyInfo verificationInfo
	if AliveStatus.IsAlive == 0 {
		VerifyInfo.Point_S = 0
		VerifyInfo.Point_T = 0
		VerifyInfo.ModelOutputStatus = DEATH
		VerifyInfo.OutputDesc = "4"
	} else if isCrime == true {
		VerifyInfo.Point_S = 0
		VerifyInfo.Point_T = 0
		VerifyInfo.ModelOutputStatus = CRIMINAL
		VerifyInfo.OutputDesc = "5"
	} else if isExplicitProve == true {
		VerifyInfo.Point_S = 0
		VerifyInfo.Point_T = 12
		VerifyInfo.ModelOutputStatus = NORMAL
		VerifyInfo.OutputDesc = "2" //强验证
	} else if record.T <= 0 {
		VerifyInfo.Point_T = 0
		VerifyInfo.Point_S = 0
		VerifyInfo.ModelOutputStatus = SUSPECTED
		VerifyInfo.OutputDesc = ""
	} else {
		VerifyInfo.Point_S = record.S
		VerifyInfo.Point_T = record.T
		VerifyInfo.ModelOutputStatus = NORMAL
		VerifyInfo.OutputDesc = "3" //分值验证
	}
	if AliveStatus.DataSrc == SOCSEU && AliveStatus.DataID == SOCSEU_MANUAL_INTERVENTION {
		VerifyInfo.OutputDesc = "1"   //人工干预
	}
	VerifyInfo.Updatetime = time.Now().Format("2006-01-02")
	return VerifyInfo
}

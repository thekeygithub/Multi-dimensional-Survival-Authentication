package main
/*
	贷款/还款  结构体
	通过账户对象查询所有的贷款/还款记录
 */
const (
	Bank_Flag_loan = 1 //贷款
	Bank_Flag_Repayment = 2 //还款
)
type Bank struct {
	BankName string `json:"BankName"`
	Flag int `json:"Flag"`
	Amount int `json:"Amount"`
	StartDate string `json:"StartDate"`
	EndDate string `json:"EndDate"`
}

type Account struct {
	CardNo string `json:"CardNo"`
	Aname string `json:"Aname"`
	Age int `json:"Age"`
	Gender string `json:"Gender"`
	Mobil string `json:"Mobil"`
	Bank Bank `json:"Bank"`
	Historys []HistoryItem
}

type HistoryItem struct {
	TxId string
	Account Account
} 
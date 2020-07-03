#/bin/bash
# Copyright London Stock Exchange Group All Rights Reserved.
#
# SPDX-License-Identifier: Apache-2.0
#
echo
echo " ____    _____      _      ____    _____           _____   ____    _____ "
echo "/ ___|  |_   _|    / \    |  _ \  |_   _|         | ____| |___ \  | ____|"
echo "\___ \    | |     / _ \   | |_) |   | |    _____  |  _|     __) | |  _|  "
echo " ___) |   | |    / ___ \  |  _ <    | |   |_____| | |___   / __/  | |___ "
echo "|____/    |_|   /_/   \_\ |_| \_\   |_|           |_____| |_____| |_____|"
echo


CHANNEL_NAME="$1"
: ${CHANNEL_NAME:="channel"}
: ${TIMEOUT:="60"}

COUNTER=1
MAX_RETRY=5
ORDERER_CA=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/ordererOrganizations/guizhou.com/orderers/orderer0.guizhou.com/msp/tlscacerts/tlsca.guizhou.com-cert.pem
ORDERER_CA1=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/ordererOrganizations/guizhou.com/orderers/orderer1.guizhou.com/msp/tlscacerts/tlsca.guizhou.com-cert.pem
ORDERER_CA2=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/ordererOrganizations/guizhou.com/orderers/orderer2.guizhou.com/msp/tlscacerts/tlsca.guizhou.com-cert.pem
PEER0_ORG1_CA=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/org1.guizhou.com/peers/peer0.org1.guizhou.com/tls/ca.crt
PEER0_ORG2_CA=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/org2.guizhou.com/peers/peer0.org2.guizhou.com/tls/ca.crt
PEER0_ORG3_CA=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/org3.guizhou.com/peers/peer0.org3.guizhou.com/tls/ca.crt
ORDERER_SYSCHAN_ID=e2e-orderer-syschan
#ORDERER_SYSCHAN_ID=base_default
echo "Channel name : "$CHANNEL_NAME

	if [ $# -eq 3 ] ; then
		CHAINCODE_NAME="$2"
		: ${CHANNEL_NAME:="dataCC"}
		CHAINCODE_PATH="$3"
		: ${CHANNEL_PATH:="github.com/hyperledger/fabric/examples/chaincode/go/testCC/cmd"}
	fi
echo $CHAINCODE_NAME
echo $CHAINCODE_PATH

verifyResult () {
	if [ $1 -ne 0 ] ; then
		echo "!!!!!!!!!!!!!!! "$2" !!!!!!!!!!!!!!!!"
                echo "================== ERROR !!! FAILED to execute End-2-End Scenario =================="
		echo
		echo $CHANNEL_NAME
		echo $CHAINCODE_NAME
		echo $CHAINCODE_PATH
   		exit 1
	

	fi
}

setGlobals () {
	PEER=$1
	ORG=$2
	if [ $ORG -eq 1 ] ; then
		CORE_PEER_LOCALMSPID="Org1MSP"
		CORE_PEER_TLS_ROOTCERT_FILE=$PEER0_ORG1_CA
		CORE_PEER_MSPCONFIGPATH=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/org1.guizhou.com/users/Admin@org1.guizhou.com/msp
		if [ $PEER -eq 0 ]; then
			CORE_PEER_ADDRESS=peer0.org1.guizhou.com:7051
		else
			CORE_PEER_ADDRESS=peer1.org1.guizhou.com:8051
		fi
	elif [ $ORG -eq 3 ] ; then
		CORE_PEER_LOCALMSPID="Org3MSP"
		CORE_PEER_TLS_ROOTCERT_FILE=$PEER0_ORG3_CA
		CORE_PEER_MSPCONFIGPATH=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/org3.guizhou.com/users/Admin@org3.guizhou.com/msp
		if [ $PEER -eq 0 ]; then
			CORE_PEER_ADDRESS=peer0.org3.guizhou.com:7051
		else
			CORE_PEER_ADDRESS=peer1.org3.guizhou.com:8051
		fi	
	else
		CORE_PEER_LOCALMSPID="Org2MSP"
		CORE_PEER_TLS_ROOTCERT_FILE=$PEER0_ORG2_CA
		CORE_PEER_MSPCONFIGPATH=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/org2.guizhou.com/users/Admin@org2.guizhou.com/msp
		if [ $PEER -eq 0 ]; then
			CORE_PEER_ADDRESS=peer0.org2.guizhou.com:7051
		else
			CORE_PEER_ADDRESS=peer1.org2.guizhou.com:8051
		fi
	fi

	env |grep CORE
}

checkOSNAvailability() {
	# Use orderer's MSP for fetching system channel config block
	CORE_PEER_LOCALMSPID="OrdererMSP"
	CORE_PEER_TLS_ROOTCERT_FILE=$ORDERER_CA
	CORE_PEER_MSPCONFIGPATH=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/ordererOrganizations/guizhou.com/orderers/orderer0.guizhou.com/msp

	local rc=1
	local starttime=$(date +%s)

	# continue to poll
	# we either get a successful response, or reach TIMEOUT
	while test "$(($(date +%s)-starttime))" -lt "$TIMEOUT" -a $rc -ne 0
	do
		 sleep 3
		 echo "Attempting to fetch system channel '$ORDERER_SYSCHAN_ID' ...$(($(date +%s)-starttime)) secs"
		 if [ -z "$CORE_PEER_TLS_ENABLED" -o "$CORE_PEER_TLS_ENABLED" = "false" ]; then
			 peer channel fetch 0 -o orderer0.guizhou.com:7050 -c "$ORDERER_SYSCHAN_ID" >&log.txt
		 else
			 peer channel fetch 0 0_block.pb -o orderer0.guizhou.com:7050 -c "$ORDERER_SYSCHAN_ID" --tls --cafile $ORDERER_CA >&log.txt
		 fi
		 test $? -eq 0 && VALUE=$(cat log.txt | awk '/Received block/ {print $NF}')
		 test "$VALUE" = "0" && let rc=0
	done
	cat log.txt
	verifyResult $rc "Ordering Service is not available, Please try again ..."
	echo "===================== Ordering Service is up and running ===================== "
	echo
}

installTestCC () {
         PEER=$1
         ORG=$2
         setGlobals $PEER $ORG
         peer chaincode install -n $CHAINCODE_NAME -v 1.0 -p $CHAINCODE_PATH >&log.txt
         res=$?
         cat log.txt
         verifyResult $res "Chaincode installation on peer peer${PEER}.org${ORG} has Failed"
         echo "===================== Chaincode is installed on peer${PEER}.org${ORG} ===================== "
         echo
 }
instantiateTestCC () {
         PEER=$1
         ORG=$2
         setGlobals $PEER $ORG
         # while 'peer chaincode' command can get the orderer endpoint from the peer (if join was successful),
         # lets supply it directly as we know it using the "-o" option
         if [ -z "$CORE_PEER_TLS_ENABLED" -o "$CORE_PEER_TLS_ENABLED" = "false" ]; then
                peer chaincode instantiate -o orderer0.guizhou.com:7050 -C $CHANNEL_NAME -n $CHAINCODE_NAME -v 1.0 -c '{"Args":["Init","jack","0x123dgccbpomkwe"]}' -P "OR ('Org1MSP.peer','Org2MSP.peer')" >&log.txt
         else
                 peer chaincode instantiate -o orderer0.guizhou.com:7050 --tls --cafile $ORDERER_CA -C $CHANNEL_NAME -n $CHAINCODE_NAME -v 1.0 -c '{"Args":["Init","jack","0x123dgccbpomkwe"]}' -P "OR ('Org1MSP.peer','Org2MSP.peer')" >&log.txt
 
         fi
         res=$?
         cat log.txt
         verifyResult $res "RecordCC instantiation on peer${PEER}.org${ORG} on channel '$CHANNEL_NAME' failed"
         echo "===================== RecordCC is instantiated on peer${PEER}.org${ORG} on channel '$CHANNEL_NAME' ===================== "
         echo
 }

# Check for orderering service availablility
echo "Check orderering service availability..."
checkOSNAvailability

# Install chaincode on peer0.org1 and peer2.org2
echo "Installing chaincode on peer0.org1...  peer1.org1..."
 installTestCC 0 1
 installTestCC 1 1
#     installRecordCC 0 1
 #    installModelCC 0 1
  #   installQueryCC 0 1
# echo "Installing chaincode on peer1.org1..."
# installChaincode 1 1
#installChaincode 0 1
#installChaincode 1 2
#installChaincode 0 2

echo "Install chaincode on peer0.org2... peer1.org2... "
 installTestCC 0 2
 installTestCC 1 2
    # installModelCC 0 2
   #  installQueryCC 0 2
echo "Install chaincode on peer0.org3...  peer1.org3..."
installTestCC 0 3
installTestCC 1 3
   #  installRecordCC 0 3
    # installModelCC 0 3
    # installQueryCC 0 3

# Instantiate chaincode on peer0.org2
echo "Instantiating chaincode on peer0.org1..."
instantiateTestCC 0 1

echo
echo "===================== All GOOD, End-2-End execution completed ===================== "
echo

echo
echo " _____   _   _   ____            _____   ____    _____ "
echo "| ____| | \ | | |  _ \          | ____| |___ \  | ____|"
echo "|  _|   |  \| | | | | |  _____  |  _|     __) | |  _|  "
echo "| |___  | |\  | | |_| | |_____| | |___   / __/  | |___ "
echo "|_____| |_| \_| |____/          |_____| |_____| |_____|"
echo

exit 0



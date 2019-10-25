#!/bin/bash
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
ORDERER_CA3=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/ordererOrganizations/guizhou.com/orderers/orderer3.guizhou.com/msp/tlscacerts/tlsca.guizhou.com-cert.pem
ORDERER_CA4=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/ordererOrganizations/guizhou.com/orderers/orderer4.guizhou.com/msp/tlscacerts/tlsca.guizhou.com-cert.pem
ORDERER_CA5=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/ordererOrganizations/guizhou.com/orderers/orderer5.guizhou.com/msp/tlscacerts/tlsca.guizhou.com-cert.pem
PEER0_ORG1_CA=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/org1.guizhou.com/peers/peer0.org1.guizhou.com/tls/ca.crt
PEER0_ORG2_CA=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/org2.guizhou.com/peers/peer0.org2.guizhou.com/tls/ca.crt
PEER0_ORG3_CA=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/org3.guizhou.com/peers/peer0.org3.guizhou.com/tls/ca.crt
PEER0_ORG4_CA=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/org4.guizhou.com/peers/peer0.org4.guizhou.com/tls/ca.crt
PEER0_ORG5_CA=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/org5.guizhou.com/peers/peer0.org5.guizhou.com/tls/ca.crt
PEER0_ORG6_CA=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/org6.guizhou.com/peers/peer0.org6.guizhou.com/tls/ca.crt

ORDERER_SYSCHAN_ID=e2e-orderer-syschan
#ORDERER_SYSCHAN_ID=base_default
echo "Channel name : "$CHANNEL_NAME

verifyResult () {
	if [ $1 -ne 0 ] ; then
		echo "!!!!!!!!!!!!!!! "$2" !!!!!!!!!!!!!!!!"
                echo "================== ERROR !!! FAILED to execute End-2-End Scenario =================="
		echo
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
        elif [ $ORG -eq 4 ] ; then
                CORE_PEER_LOCALMSPID="Org4MSP"
                CORE_PEER_TLS_ROOTCERT_FILE=$PEER0_ORG4_CA
                CORE_PEER_MSPCONFIGPATH=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/org4.guizhou.com/users/Admin@org4.guizhou.com/msp
                if [ $PEER -eq 0 ]; then
                        CORE_PEER_ADDRESS=peer0.org4.guizhou.com:7051
                else
                        CORE_PEER_ADDRESS=peer1.org4.guizhou.com:8051
                fi
        elif [ $ORG -eq 5 ] ; then
                CORE_PEER_LOCALMSPID="Org5MSP"
                CORE_PEER_TLS_ROOTCERT_FILE=$PEER0_ORG5_CA
                CORE_PEER_MSPCONFIGPATH=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/org5.guizhou.com/users/Admin@org5.guizhou.com/msp
                if [ $PEER -eq 0 ]; then
                        CORE_PEER_ADDRESS=peer0.org5.guizhou.com:7051
                else
                        CORE_PEER_ADDRESS=peer1.org5.guizhou.com:8051
                fi
        elif [ $ORG -eq 6 ] ; then
                CORE_PEER_LOCALMSPID="Org6MSP"
                CORE_PEER_TLS_ROOTCERT_FILE=$PEER0_ORG6_CA
                CORE_PEER_MSPCONFIGPATH=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/org6.guizhou.com/users/Admin@org6.guizhou.com/msp
                if [ $PEER -eq 0 ]; then
                        CORE_PEER_ADDRESS=peer0.org6.guizhou.com:7051
                else
                        CORE_PEER_ADDRESS=peer1.org6.guizhou.com:8051
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

createChannel() {
	setGlobals 0 1
	if [ -z "$CORE_PEER_TLS_ENABLED" -o "$CORE_PEER_TLS_ENABLED" = "false" ]; then
		peer channel create -o orderer0.guizhou.com:7050 -c $CHANNEL_NAME -f ./channel-artifacts/channel.tx >&log.txt
	else
		peer channel create -o orderer0.guizhou.com:7050 -c $CHANNEL_NAME -f ./channel-artifacts/channel.tx --tls --cafile $ORDERER_CA >&log.txt
	fi
	res=$?
	cat log.txt
	verifyResult $res "Channel creation failed"
	echo "===================== Channel '$CHANNEL_NAME' created ===================== "
	echo
}

updateAnchorPeers() {
	PEER=$1
	ORG=$2
	setGlobals $PEER $ORG

	if [ -z "$CORE_PEER_TLS_ENABLED" -o "$CORE_PEER_TLS_ENABLED" = "false" ]; then
		peer channel update -o orderer0.guizhou.com:7050 -c $CHANNEL_NAME -f ./channel-artifacts/${CORE_PEER_LOCALMSPID}anchors.tx >&log.txt
	else
		peer channel update -o orderer0.guizhou.com:7050 -c $CHANNEL_NAME -f ./channel-artifacts/${CORE_PEER_LOCALMSPID}anchors.tx --tls --cafile $ORDERER_CA >&log.txt
	fi
	res=$?
	cat log.txt
	verifyResult $res "Anchor peer update failed"
	echo "===================== Anchor peers updated for org '$CORE_PEER_LOCALMSPID' on channel '$CHANNEL_NAME' ===================== "
	sleep 5
	echo
}

## Sometimes Join takes time hence RETRY atleast for 5 times
joinChannelWithRetry () {
	PEER=$1
	ORG=$2
	setGlobals $PEER $ORG

	peer channel join -b $CHANNEL_NAME.block  >&log.txt
	res=$?
	cat log.txt
	if [ $res -ne 0 -a $COUNTER -lt $MAX_RETRY ]; then
		COUNTER=` expr $COUNTER + 1`
		echo "peer${PEER}.org${ORG} failed to join the channel, Retry after 2 seconds"
		sleep 2
		joinChannelWithRetry $1 $2
	else
		COUNTER=1
	fi
	verifyResult $res "After $MAX_RETRY attempts, peer${PEER}.org${ORG} has failed to join channel '$CHANNEL_NAME' "
}

joinChannel () {
	for org in 1 2 3 4 5 6; do
	    for peer in 0 1; do
		    joinChannelWithRetry $peer $org
		    echo "===================== peer${peer}.org${org} joined channel '$CHANNEL_NAME' ===================== "
		    sleep 2
		    echo
        done
	done
	
}

installChaincode () {
	PEER=$1
	ORG=$2
	setGlobals $PEER $ORG
	peer chaincode install -n mycc -v 1.0 -p github.com/hyperledger/fabric/examples/chaincode/go/example02/cmd >&log.txt
	res=$?
	cat log.txt
	verifyResult $res "Chaincode installation on peer peer${PEER}.org${ORG} has Failed"
	echo "===================== Chaincode is installed on peer${PEER}.org${ORG} ===================== "
	echo
}

installRecordCC () {
	PEER=$1
	ORG=$2
	setGlobals $PEER $ORG
	peer chaincode install -n recordCC -v 1.0 -p github.com/hyperledger/fabric/examples/chaincode/go/recordCC/cmd >&log.txt
	res=$?
	cat log.txt
	verifyResult $res "Chaincode installation on peer peer${PEER}.org${ORG} has Failed"
	echo "===================== Chaincode is installed on peer${PEER}.org${ORG} ===================== "
	echo
}

installModelCC () {
	PEER=$1
	ORG=$2
	setGlobals $PEER $ORG
	peer chaincode install -n modelCC -v 1.0 -p github.com/hyperledger/fabric/examples/chaincode/go/modelCC/cmd >&log.txt
	res=$?
	cat log.txt
	verifyResult $res "Chaincode installation on peer peer${PEER}.org${ORG} has Failed"
	echo "===================== Chaincode is installed on peer${PEER}.org${ORG} ===================== "
	echo
}

installQueryCC () {
	PEER=$1
	ORG=$2
	setGlobals $PEER $ORG
	peer chaincode install -n queryCC -v 1.0 -p github.com/hyperledger/fabric/examples/chaincode/go/queryCC/cmd >&log.txt
	res=$?
	cat log.txt
	verifyResult $res "Chaincode installation on peer peer${PEER}.org${ORG} has Failed"
	echo "===================== Chaincode is installed on peer${PEER}.org${ORG} ===================== "
	echo
}

instantiateChaincode () {
	PEER=$1
	ORG=$2
	setGlobals $PEER $ORG
	# while 'peer chaincode' command can get the orderer endpoint from the peer (if join was successful),
	# lets supply it directly as we know it using the "-o" option
	if [ -z "$CORE_PEER_TLS_ENABLED" -o "$CORE_PEER_TLS_ENABLED" = "false" ]; then
		peer chaincode instantiate -o orderer0.guizhou.com:7050 -C $CHANNEL_NAME -n mycc -v 1.0 -c '{"Args":["init","a","100","b","200"]}' -P "AND ('Org1MSP.peer','Org2MSP.peer')" >&log.txt
	else
		peer chaincode instantiate -o orderer0.guizhou.com:7050 --tls --cafile $ORDERER_CA -C $CHANNEL_NAME -n mycc -v 1.0 -c '{"Args":["init","a","100","b","200"]}' -P "AND ('Org1MSP.peer','Org2MSP.peer')" >&log.txt
	fi
	res=$?
	cat log.txt
	verifyResult $res "Chaincode instantiation on peer${PEER}.org${ORG} on channel '$CHANNEL_NAME' failed"
	echo "===================== Chaincode is instantiated on peer${PEER}.org${ORG} on channel '$CHANNEL_NAME' ===================== "
	echo
}

instantiateRecordCC () {
	PEER=$1
	ORG=$2
	setGlobals $PEER $ORG
	# while 'peer chaincode' command can get the orderer endpoint from the peer (if join was successful),
	# lets supply it directly as we know it using the "-o" option
	if [ -z "$CORE_PEER_TLS_ENABLED" -o "$CORE_PEER_TLS_ENABLED" = "false" ]; then
		peer chaincode instantiate -o orderer0.guizhou.com:7050 -C $CHANNEL_NAME -n recordCC -v 1.0 -c '{"Args":[]}' -P "OR ('Org1MSP.peer','Org2MSP.peer','Org3MSP.member')" >&log.txt
	else
		peer chaincode instantiate -o orderer0.guizhou.com:7050 --tls --cafile $ORDERER_CA -C $CHANNEL_NAME -n recordCC -v 1.0 -c '{"Args":[]}' -P "OR ('Org1MSP.peer','Org2MSP.peer','Org3MSP.member')" >&log.txt

	fi
	res=$?
	cat log.txt
	verifyResult $res "RecordCC instantiation on peer${PEER}.org${ORG} on channel '$CHANNEL_NAME' failed"
	echo "===================== RecordCC is instantiated on peer${PEER}.org${ORG} on channel '$CHANNEL_NAME' ===================== "
	echo
}

instantiateModelCC () {
	PEER=$1
	ORG=$2
	setGlobals $PEER $ORG
	# while 'peer chaincode' command can get the orderer endpoint from the peer (if join was successful),
	# lets supply it directly as we know it using the "-o" option
	if [ -z "$CORE_PEER_TLS_ENABLED" -o "$CORE_PEER_TLS_ENABLED" = "false" ]; then
		peer chaincode instantiate -o orderer0.guizhou.com:7050 -C $CHANNEL_NAME -n modelCC -v 1.0 -c '{"Args":[]}' -P "OR ('Org1MSP.peer','Org2MSP.peer','Org3MSP.member')" >&log.txt
	else
		peer chaincode instantiate -o orderer0.guizhou.com:7050 --tls --cafile $ORDERER_CA -C $CHANNEL_NAME -n modelCC -v 1.0 -c '{"Args":[]}' -P "OR ('Org1MSP.peer','Org2MSP.peer','Org3MSP.member')" >&log.txt
	fi
	res=$?
	cat log.txt
	verifyResult $res "ModelCC instantiation on peer${PEER}.org${ORG} on channel '$CHANNEL_NAME' failed"
	echo "===================== ModelCC is instantiated on peer${PEER}.org${ORG} on channel '$CHANNEL_NAME' ===================== "
	echo
}

instantiateQueryCC () {
	PEER=$1
	ORG=$2
	setGlobals $PEER $ORG
	# while 'peer chaincode' command can get the orderer endpoint from the peer (if join was successful),
	# lets supply it directly as we know it using the "-o" option
	if [ -z "$CORE_PEER_TLS_ENABLED" -o "$CORE_PEER_TLS_ENABLED" = "false" ]; then
		peer chaincode instantiate -o orderer0.guizhou.com:7050 -C $CHANNEL_NAME -n queryCC -v 1.0 -c '{"Args":[]}' -P "OR ('Org1MSP.peer','Org2MSP.peer','Org3MSP.member')" >&log.txt
	else
		peer chaincode instantiate -o orderer0.guizhou.com:7050 --tls --cafile $ORDERER_CA -C $CHANNEL_NAME -n queryCC -v 1.0 -c '{"Args":[]}' -P "OR ('Org1MSP.peer','Org2MSP.peer','Org3MSP.member')" >&log.txt
	fi
	res=$?
	cat log.txt
	verifyResult $res "QueryCC instantiation on peer${PEER}.org${ORG} on channel '$CHANNEL_NAME' failed"
	echo "===================== QueryCC is instantiated on peer${PEER}.org${ORG} on channel '$CHANNEL_NAME' ===================== "
	echo
}

chaincodeQuery () {
	PEER=$1
	ORG=$2
	setGlobals $PEER $ORG
	EXPECTED_RESULT=$3
	echo "===================== Querying on peer${PEER}.org${ORG} on channel '$CHANNEL_NAME'... ===================== "
	local rc=1
	local starttime=$(date +%s)

	# continue to poll
	# we either get a successful response, or reach TIMEOUT
	while test "$(($(date +%s)-starttime))" -lt "$TIMEOUT" -a $rc -ne 0
	do
        	sleep 3
        	echo "Attempting to Query peer${PEER}.org${ORG} ...$(($(date +%s)-starttime)) secs"
        	peer chaincode query -C $CHANNEL_NAME -n tcc -c '{"Args":["get","jack"]}' >&log.txt
        	test $? -eq 0 && VALUE=$(cat log.txt | egrep '^[0-9]+$')
        	test "$VALUE" = "$EXPECTED_RESULT" && let rc=0
	done
	echo
	cat log.txt
	if test $rc -eq 0 ; then
		echo "===================== Query successful on peer${PEER}.org${ORG} on channel '$CHANNEL_NAME' ===================== "
    	else
		echo "!!!!!!!!!!!!!!! Query result on peer${PEER}.org${ORG} is INVALID !!!!!!!!!!!!!!!!"
        	echo "================== ERROR !!! FAILED to execute End-2-End Scenario =================="
		echo
		exit 1
    	fi
}

# parsePeerConnectionParameters $@
# Helper function that takes the parameters from a chaincode operation
# (e.g. invoke, query, instantiate) and checks for an even number of
# peers and associated org, then sets $PEER_CONN_PARMS and $PEERS
parsePeerConnectionParameters() {
	# check for uneven number of peer and org parameters
	if [ $(( $# % 2 )) -ne 0 ]; then
        	exit 1
	fi

	PEER_CONN_PARMS=""
	PEERS=""
	while [ "$#" -gt 0 ]; do
		PEER="peer$1.org$2"
		PEERS="$PEERS $PEER"
		PEER_CONN_PARMS="$PEER_CONN_PARMS --peerAddresses $PEER.guizhou.com:7051"
		if [ -z "$CORE_PEER_TLS_ENABLED" -o "$CORE_PEER_TLS_ENABLED" = "true" ]; then
        		TLSINFO=$(eval echo "--tlsRootCertFiles \$PEER$1_ORG$2_CA")
        		PEER_CONN_PARMS="$PEER_CONN_PARMS $TLSINFO"
        	fi
		# shift by two to get the next pair of peer/org parameters
        	shift; shift
	done
	# remove leading space for output
	PEERS="$(echo -e "$PEERS" | sed -e 's/^[[:space:]]*//')"

}
installTestCC () {
         PEER=$1
         ORG=$2
         setGlobals $PEER $ORG
         peer chaincode install -n dataCccc -v 1.0 -p github.com/hyperledger/fabric/examples/chaincode/go/testCC/cmd >&log.txt
         res=$?
         cat log.txt
         verifyResult $res "TestCC Chaincode installation on peer peer${PEER}.org${ORG} has Failed"
         echo "===================== TestCC Chaincode is installed on peer${PEER}.org${ORG} ===================== "
         echo
 }
instantiateTestCC () {
         PEER=$1
         ORG=$2
         setGlobals $PEER $ORG
         # while 'peer chaincode' command can get the orderer endpoint from the peer (if join was successful),
         # lets supply it directly as we know it using the "-o" option
         if [ -z "$CORE_PEER_TLS_ENABLED" -o "$CORE_PEER_TLS_ENABLED" = "false" ]; then
                peer chaincode instantiate -o orderer0.guizhou.com:7050 -C $CHANNEL_NAME -n dataCccc -v 1.0 -c '{"Args":["Init","jack","0x123dgccbpomkwe"]}' -P "OR ('Org1MSP.peer','Org2MSP.peer','Org3MSP.peer','Org4MSP.peer','Org5MSP.peer','Org6MSP.peer')" >&log.txt
         else
                 peer chaincode instantiate -o orderer0.guizhou.com:7050 --tls --cafile $ORDERER_CA -C $CHANNEL_NAME -n dataCccc -v 1.0 -c '{"Args":["Init","jack","0x123dgccbpomkwe"]}' -P "OR ('Org1MSP.peer','Org2MSP.peer','Org3MSP.peer','Org4MSP.peer','Org5MSP.peer','Org6MSP.peer')" >&log.txt
 
         fi
         res=$?
         cat log.txt
         verifyResult $res "TestCC instantiation on peer${PEER}.org${ORG} on channel '$CHANNEL_NAME' failed"
         echo "===================== TestCC is instantiated on peer${PEER}.org${ORG} on channel '$CHANNEL_NAME' ===================== "
         echo
 }
installSuyuanCC () {
	PEER=$1
	ORG=$2
	setGlobals $PEER $ORG
	peer chaincode install -n traceCc -v 1.0 -p github.com/hyperledger/fabric/examples/chaincode/go/suyuanCC/cmd >&log.txt
	res=$?
	cat log.txt
	verifyResult $res "SuyuanCC Chaincode installation on peer peer${PEER}.org${ORG} has Failed"
	echo "===================== SuyuanCC Chaincode is installed on peer${PEER}.org${ORG} ===================== "
	echo
}
instantiateSuyuanCC () {
	PEER=$1
	ORG=$2
	setGlobals $PEER $ORG
	# while 'peer chaincode' command can get the orderer endpoint from the peer (if join was successful),
	# lets supply it directly as we know it using the "-o" option
	if [ -z "$CORE_PEER_TLS_ENABLED" -o "$CORE_PEER_TLS_ENABLED" = "false" ]; then
		peer chaincode instantiate -o orderer0.guizhou.com:7050 -C $CHANNEL_NAME -n traceCc -v 1.0 -c '{"Args":[]}' -P "OR ('Org1MSP.peer','Org2MSP.peer')" >&log.txt
	else
		peer chaincode instantiate -o orderer0.guizhou.com:7050 --tls --cafile $ORDERER_CA -C $CHANNEL_NAME -n traceCc -v 1.0 -c '{"Args":[]}' -P "OR ('Org1MSP.peer','Org2MSP.peer')" >&log.txt

	fi
	res=$?
	cat log.txt
	verifyResult $res "SuyuanCC instantiation on peer${PEER}.org${ORG} on channel '$CHANNEL_NAME' failed"
	echo "===================== SuyuanCC is instantiated on peer${PEER}.org${ORG} on channel '$CHANNEL_NAME' ===================== "
	echo
}
# chaincodeInvoke <peer> <org> ...
# Accepts as many peer/org pairs as desired and requests endorsement from each
chaincodeInvoke () {
	parsePeerConnectionParameters $@
	res=$?
	verifyResult $res "Invoke transaction failed on channel '$CHANNEL_NAME' due to uneven number of peer and org parameters "

	# while 'peer chaincode' command can get the orderer endpoint from the
	# peer (if join was successful), let's supply it directly as we know
	# it using the "-o" option
	if [ -z "$CORE_PEER_TLS_ENABLED" -o "$CORE_PEER_TLS_ENABLED" = "false" ]; then
		peer chaincode invoke -o orderer0.guizhou.com:7050 -C $CHANNEL_NAME -n mycc $PEER_CONN_PARMS -c '{"Args":["invoke","a","b","10"]}' >&log.txt
	else
        peer chaincode invoke -o orderer0.guizhou.com:7050  --tls --cafile $ORDERER_CA -C $CHANNEL_NAME -n mycc $PEER_CONN_PARMS -c '{"Args":["invoke","a","b","10"]}' >&log.txt
	fi
	res=$?
	cat log.txt
	verifyResult $res "Invoke execution on PEER$PEER failed "
	echo "===================== Invoke transaction successful on $PEERS on channel '$CHANNEL_NAME' ===================== "
	echo
}
traceccInvoke () {
	parsePeerConnectionParameters $@
	res=$?
	verifyResult $res "Invoke transaction failed on channel '$CHANNEL_NAME' due to uneven number of peer and org parameters "

	# while 'peer chaincode' command can get the orderer endpoint from the
	# peer (if join was successful), let's supply it directly as we know
	# it using the "-o" option
	if [ -z "$CORE_PEER_TLS_ENABLED" -o "$CORE_PEER_TLS_ENABLED" = "false" ]; then
		peer chaincode invoke -o orderer0.guizhou.com:7050 -C $CHANNEL_NAME -n tracecc $PEER_CONN_PARMS -c '{"Args":["loan","333333","BCD","1000"]}' >&log.txt
	else
        peer chaincode invoke -o orderer0.guizhou.com:7050  --tls --cafile $ORDERER_CA -C $CHANNEL_NAME -n tracecc $PEER_CONN_PARMS -c '{"Args":["loan","333333","BCD","1000"]}' >&log.txt
	fi
	res=$?
	cat log.txt
	verifyResult $res "Invoke execution on PEER$PEER failed "
	echo "===================== Invoke transaction successful on $PEERS on channel '$CHANNEL_NAME' ===================== "
	echo
}
testInvoke () {
         parsePeerConnectionParameters $@
         res=$?
         verifyResult $res "Invoke transaction failed on channel '$CHANNEL_NAME' due to uneven number of peer and org parameters "
 
         # while 'peer chaincode' command can get the orderer endpoint from the
         # peer (if join was successful), let's supply it directly as we know
         # it using the "-o" option
         if [ -z "$CORE_PEER_TLS_ENABLED" -o "$CORE_PEER_TLS_ENABLED" = "false" ]; then
         peer chaincode invoke -o orderer0.guizhou.com:7050 -C $CHANNEL_NAME -n datac $PEER_CONN_PARMS -c '{"Args":["ContentUpload","{AssetContentId:"105",ContentInfomation:{{id:1,name:a,permission:0,vaild:0,},{id:2,name:b,permission:0,vaild:0,},{id:3,name:c,permission:0,vaild:0,},}}"]}' >&log.txt
         else
         peer chaincode invoke -o orderer0.guizhou.com:7050  --tls --cafile $ORDERER_CA -C $CHANNEL_NAME -n datac $PEER_CONN_PARMS -c '{"Args":["ContentUpload","{contentid:"105",contentinfo:{{id:1,name:a,permission:0,vaild:0,},{id:2,name:b,permission:0,vaild:0,},{id:3,name:c,permission:0,vaild:0,},}}"]}' >&log.txt
         fi
         res=$?
         cat log.txt
         verifyResult $res "Invoke execution on PEER$PEER failed "
         echo "===================== Invoke transaction successful on $PEERS on channel '$CHANNEL_NAME' ===================== "
         echo
 }
# Check for orderering service availablility
echo "Check orderering service availability..."
checkOSNAvailability

# Create channel
echo "Creating channel..."
#createChannel

# Join all the peers to the channel
echo "Having all peers join the channel..."
#joinChannel

# Set the anchor peers for each org in the channel
echo "Updating anchor peers for org1..."
#updateAnchorPeers 0 1
echo "Updating anchor peers for org2..."
#updateAnchorPeers 0 2
echo "Updating anchor peers for org3..."
#updateAnchorPeers 0 3
echo "Updating anchor peers for org4..."
#updataAnchorPeers 0 4
echo "Updating anchor peers for org5..."
#updateAnchorPeers 0 5
echo "Updating anchor peers for org6..."
#updateAnchorPeers 0 6

# Install chaincode on peer0.org1 and peer2.org2
echo "Installing chaincode on peer0.org1...  peer1.org1..."
   installTestCC 0 1
   installTestCC 1 1
#   installSuyuanCC 0 1
#   installSuyuanCC 1 1
# installQueryCC 0 1
# installChaincode 1 1
# installChaincode 0 1
# installChaincode 1 2
# installChaincode 0 2

echo "Install chaincode on peer0.org2... peer1.org2... "
    installTestCC 0 2
    installTestCC 1 2
#    installSuyuanCC 0 2
#    installSuyuanCC 1 2
echo "Install chaincode on peer0.org3...  peer1.org3..."
    installTestCC 0 3
    installTestCC 1 3
#    installSuyuanCC 0 3
#    installSuyuanCC 1 3

echo "Install chaincode on peer0.org4...  peer1.org4..."
    installTestCC 0 4
    installTestCC 1 4
#    installSuyuanCC 0 4
#    installSuyuanCC 1 4

echo "Install chaincode on peer0.org5...  peer1.org5..."
    installTestCC 0 5
    installTestCC 1 5
#    installSuyuanCC 0 5
#    installSuyuanCC 1 5

echo "Install chaincode on peer0.org6...  peer1.org6..."
    installTestCC 0 6
    installTestCC 1 6
#    installSuyuanCC 0 6
#    installSuyuanCC 1 6

echo "Instantiating chaincode on peer0.org1..."
# instantiateChaincode 0 1 
    instantiateTestCC 0 1
#    instantiateSuyuanCC 0 1
# instantiateModelCC 0 1
# instantiateQueryCC 0 1

# # Query on chaincode on peer0.org1
echo "Querying chaincode on peer0.org1..."
#chaincodeQuery 0 1 0x123dgccbpomkwe
echo "Querying chaincode on peer1.org1..."
#chaincodeQuery 1 1 100
echo "Querying chaincode on peer0.org2..."
#chaincodeQuery 0 2 100
echo "Querying chaincode on peer2.org2..."
#chaincodeQuery 1 2 100
echo "Querying chaincode on peer0.org3..."
#chaincodeQuery 0 3 100
echo "Querying chaincode on peer3.org3..."
#chaincodeQuery 1 3 100
# # Invoke on chaincode on peer0.org1 and peer0.org2
# echo "Sending invoke transaction on peer0.org1 and peer0.org2..."
#chaincodeInvoke 0 1 0 2
#testInvoke 

# # Query on chaincode on peer1.org2, check if the result is 90
# echo "Querying chaincode on peer1.org2..."
#chaincodeQuery 1 2 90

#Query on chaincode on peer1.org3 with idemix MSP type, check if the result is 90
#echo "Querying chaincode on peer1.org3..."
#chaincodeQuery 1 3 90

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



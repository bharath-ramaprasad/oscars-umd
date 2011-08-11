#!/bin/sh

# Script to stop OSCARS services.
# Call with list of servers to be stopped.
# ALL will start all the servers.Individual server args are:
#  authN authZ api coord topoBridge rm stubPSS lookup wbui
#  stubPCE bwPCE connPCE dijPCE vlanPCE nullAGG stubPSS
# Uses the pid files in $OSCARS_HOME/run to find processes to stop


printUsage() {
   echo "\nusage stopServers <server>"
   echo "<server> is either ALL or one or more of:"
   echo "\t authN authZ api coord topoBridge rm stubPSS lookup wbui"
   echo "\t stubPCE bwPCE connPCE dijPCE vlanPCE nullAGG stubPSS"
   exit 1
}

if [ $# -lt 1 ]; then
    printUsage
fi
if  [ -z $OSCARS_HOME ]; then
    echo "Please set the environment var OSCARS_HOME to the OSCARS deployment directory"
    exit -1
 fi
 DEFAULT_PID_DIR="${OSCARS_HOME-.}/run"
 
stopauthN() {
PID_FILE=$DEFAULT_PID_DIR/authN.pid
if [ -f $PID_FILE ]
then
   PID=`cat $PID_FILE`
   echo killing authN
   `kill -9 $PID`
   rm $PID_FILE
else
    echo "AuthN is not running"
fi
}

stopauthZ() {
PID_FILE=$DEFAULT_PID_DIR/authZ.pid
if [ -f $PID_FILE ]
then
   PID=`cat $PID_FILE`
   echo killing authZ
   `kill -9 $PID`
   rm $PID_FILE
else
    echo "AuthZ is not running"
fi
}

stopCoord () {
PID_FILE=$DEFAULT_PID_DIR/coord.pid
if [ -f $PID_FILE ]
then
   PID=`cat $PID_FILE`
    echo killing coordinator
    `kill -9 $PID`
    rm $PID_FILE
else
    echo "Coordinator is not running"
fi
}

stopRM () {
PID_FILE=$DEFAULT_PID_DIR/rm.pid
if [ -f $PID_FILE ]
then
   PID=`cat $PID_FILE`
   echo killing resourceManager
   `kill -9 $PID`
    rm $PID_FILE
else
    echo "Resource Manager is not running"
fi
}

stopTopoBridge() {
PID_FILE=$DEFAULT_PID_DIR/topoBridge.pid
if [ -f $PID_FILE ]
then
   PID=`cat $PID_FILE`
   echo killing TopoBridge
   `kill -9 $PID`
    rm $PID_FILE
else
    echo "TopoBridge is not running"
fi
}
stopStubPCE(){
PID_FILE=$DEFAULT_PID_DIR/stubPCE.pid
if [ -f $PID_FILE ]
then
   PID=`cat $PID_FILE`
   echo killing stubPCE
   `kill -9 $PID`
    rm $PID_FILE
else
    echo "StubPCE is not running"
fi
}

stopConnPCE() {
 PID_FILE=$DEFAULT_PID_DIR/connPCE.pid
if [ -f $PID_FILE ]
then
   PID=`cat $PID_FILE`
   echo killing connectivity PCE
   `kill -9 $PID`
    rm $PID_FILE
else
    echo "ConnPCE is not running"
fi
}

stopBWPCE() {
 PID_FILE=$DEFAULT_PID_DIR/bwPCE.pid
if [ -f $PID_FILE ]
then
   PID=`cat $PID_FILE`
   echo killing bandwidth PCE
   `kill -9 $PID`
    rm $PID_FILE
else
    echo "BWPCE is not running"
fi
}

stopDijPCE () {
PID_FILE=$DEFAULT_PID_DIR/dijPCE.pid
if [ -f $PID_FILE ]
then
   PID=`cat $PID_FILE`
   echo killing dijkstarPCE
   `kill -9 $PID`
    rm $PID_FILE
else
    echo "DijkstraPCE is not running"
fi
}

stopVlanPCE () {
PID_FILE=$DEFAULT_PID_DIR/vlanPCE.pid
if [ -f $PID_FILE ]
then
   PID=`cat $PID_FILE`
   echo killing vlanPCE
   `kill -9 $PID`
    rm $PID_FILE
else
    echo "VlanPCE is not running"
fi
}

stopstubPSS () {
PID_FILE=$DEFAULT_PID_DIR/stubPSS.pid
if [ -f $PID_FILE ]
then
   PID=`cat $PID_FILE`
   echo killing stubPSS
   `kill -9 $PID`
    rm $PID_FILE
else
    echo "StubPSS is not running"
fi
}

stopnullPCE () {
PID_FILE=$DEFAULT_PID_DIR/nullpce.pid
if [ -f $PID_FILE ]
then
   PID=`cat $PID_FILE`
   echo killing nullpce  [$PID]
   `kill -9 $PID`
    rm $PID_FILE
fi
}

stopnullAGG () {
PID_FILE=$DEFAULT_PID_DIR/nullagg.pid
if [ -f $PID_FILE ]
then
   PID=`cat $PID_FILE`
   echo killing nullagg [$PID]
   `kill -9 $PID`
    rm $PID_FILE
else
    echo "nullAGG is not running"
fi
}

stopOSCARSService() {
PID_FILE=$DEFAULT_PID_DIR/api.pid
if [ -f $PID_FILE ]
then
   PID=`cat $PID_FILE`
   echo killing OSCARSService
   `kill -9 $PID`
    rm $PID_FILE
else
    echo "OSCARSService api is not running"
fi
}

stopLookup() {
PID_FILE=$DEFAULT_PID_DIR/lookup.pid
if [ -f $PID_FILE ]
then
   PID=`cat $PID_FILE`
   echo killing LookupService
   `kill -9 $PID`
    rm $PID_FILE
else
    echo "Lookup is not running"
fi
}

stopWBUI() {
PID_FILE=$DEFAULT_PID_DIR/wbui.pid
if [ -f $PID_FILE ]
then
   PID=`cat $PID_FILE`
   echo killing WBUI
   `kill -9 $PID`
    rm $PID_FILE
else
    echo "WBUI is not running"
fi
}

while [ ! -z $1 ]
  do 
  case $1 in
  ALL)
    stopauthN
    stopauthZ
    stopOSCARSService
    stopCoord
    stopRM
    stopTopoBridge
    stopStubPCE
    stopConnPCE
    stopBWPCE
    stopDijPCE
    stopVlanPCE
    stopstubPSS
#    stopnullPCE
    stopnullAGG
    stopLookup
    stopWBUI;;
  authN)    stopauthN;;
  authZ)    stopauthZ;;
  api)      stopOSCARSService;;
  coord)    stopCoord;;
  rm)       stopRM;;
  topoBridge) stopTopoBridge;;
  stubPCE)  stopStubPCE;;
  connPCE)  stopConnPCE;;
  bwPCE)    stopBWPCE;;
  dijPCE)   stopDijPCE;;
  vlanPCE)  stopVlanPCE;;
  nullPCE)  stopnullPCE;;
  nullAGG)  stopnullAGG;;
  stubPSS)  stopstubPSS;;
  lookup)   stopLookup;;
  wbui)     stopWBUI;;
  *)        echo server $1 not recognized;;
  esac
  shift
done

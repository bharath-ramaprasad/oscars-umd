#!/bin/sh 
# Script to start OSCARS services.
# Call with a context and a list of servers to start.
# ALL will start all the servers.Individual server args are:
#  authN authZ api coord topoBridge rm stubPSS lookup wbui
# stubPCE bwPCE connPCE dijPCE vlanPCE nullAGG stubPSS
# saves server pids in $OSCARS_HOME/run for stopServers to use
# server output is directed to files in the current directory

DEFAULT_PID_DIR="${OSCARS_HOME-.}/run"
if [ ! -d $DEFAULT_PID_DIR ]
then
   mkdir $DEFAULT_PID_DIR
fi

if  [ -z $OSCARS_DIST ]; then
    echo "Please set the environment var OSCARS_DIST to the OSCARS source directory"
    exit -1
 fi
 
printUsage() {
   echo "\nusage startServers <context> <server >"
   echo "<context> is one of: PRODUCTION|pro UNITTEST|test DEVELOPMENT|dev SDK|sdk"
   echo "<server> is either ALL or one or more of:"
   echo "\t authN authZ api coord topoBridge rm stubPSS lookup wbui"
   echo "\t stubPCE bwPCE connPCE dijPCE vlanPCE nullAGG stubPSS"
   exit 1
}
startauthN() {
   Config=$(sh $OSCARS_DIST/bin/parseManifest.sh AuthNService $CONTEXT authN | sed "s/'//g")
   Service=$(echo $Config | awk -F/ '$1~//{print $2}')
   Conf=$(echo $Config | awk -F/ '$1~//{print $3}')
   Yaml=$(echo $Config | awk -F/ '$1~//{print $4}' | sed "s/'//g")
   if [ "$Conf" == "conf" ]; then
       port=$(awk -F: '$1~/publishTo/{print $4}' $OSCARS_HOME/$Service/$Conf/$Yaml)
   elif [ "$Conf" == "config" ]; then
       port=$(awk -F: '$1~/publishTo/{print $4}' $OSCARS_DIST/$Service/$Conf/$Yaml)
   fi
   port=$(echo $port | sed "s/[^0-9]//g")
   porttest=`netstat -na | grep tcp | grep LISTEN | grep $port`
   if [ ! -z "$porttest" ]; then
       echo authN already running
   else
       echo starting AuthN on port $port
      (cd $OSCARS_DIST/authN; bin/startServer.sh $CONTEXT > $currDir/authN.out 2>&1  & )
   fi
}

startauthZ(){
   Config=$(sh $OSCARS_DIST/bin/parseManifest.sh AuthZService $CONTEXT authZ)
   Service=$(echo $Config | awk -F/ '$1~//{print $2}')
   Conf=$(echo $Config | awk -F/ '$1~//{print $3}')
   Yaml=$(echo $Config | awk -F/ '$1~//{print $4}' | sed "s/'//g")
   if [ "$Conf" == "conf" ]; then
        port=$(awk -F: '$1~/publishTo/{print $4}' $OSCARS_HOME/$Service/$Conf/$Yaml)
   elif [ "$Conf" == "config" ]; then
        port=$(awk -F: '$1~/publishTo/{print $4}' $OSCARS_DIST/$Service/$Conf/$Yaml)
   fi
   port=$(echo $port | sed "s/[^0-9]//g")
   porttest=`netstat -na | grep tcp | grep LISTEN | grep $port`
   if [ ! -z "$porttest" ]; then
       echo authZ already running
   else
       echo starting AuthZ on port $port
       (cd $OSCARS_DIST/authZ; bin/startServer.sh $CONTEXT > $currDir/authZ.out 2>&1 &)
   fi
}

startOSCARSService() {
    Config=$(sh $OSCARS_DIST/bin/parseManifest.sh OSCARSService $CONTEXT api) 
    Service=$(echo $Config | awk -F/ '$1~//{print $2}')
    Conf=$(echo $Config | awk -F/ '$1~//{print $3}')
    Yaml=$(echo $Config | awk -F/ '$1~//{print $4}' | sed "s/'//g")
    if [ "$Conf" == "conf" ]; then
        port=$(awk -F: '$1~/publishTo/{print $4}' $OSCARS_HOME/$Service/$Conf/$Yaml)
    elif [ "$Conf" == "config" ]; then
        port=$(awk -F: '$1~/publishTo/{print $4}' $OSCARS_DIST/$Service/$Conf/$Yaml)
    fi
    port=$(echo $port | sed "s/[^0-9]//g")
    porttest=`netstat -na | grep tcp | grep LISTEN | grep $port`
    if [ ! -z "$porttest" ]; then
        echo OSCARSService already running
    else
        echo starting OSCARSService on port $port
        (cd $OSCARS_DIST/api; bin/startServer.sh $CONTEXT > $currDir/api.out 2>&1 &)
    fi
}

startCoord() {
    Config=$(sh $OSCARS_DIST/bin/parseManifest.sh CoordService $CONTEXT coordinator)
    Service=$(echo $Config | awk -F/ '$1~//{print $2}')
    Conf=$(echo $Config | awk -F/ '$1~//{print $3}')
    Yaml=$(echo $Config | awk -F/ '$1~//{print $4}' | sed "s/'//g")
    if [ "$Conf" == "conf" ]; then
        port=$(awk -F: '$1~/publishTo/{print $4}' $OSCARS_HOME/$Service/$Conf/$Yaml)
    elif [ "$Conf" == "config" ]; then
        port=$(awk -F: '$1~/publishTo/{print $4}' $OSCARS_DIST/$Service/$Conf/$Yaml)
    fi
    port=$(echo $port | sed "s/[^0-9]//g")
    porttest=`netstat -na | grep tcp | grep LISTEN | grep $port`
    if [ ! -z "$porttest" ]; then
        echo coordinator  already running
    else
        echo starting coordinator on port $port
        (cd $OSCARS_DIST/coordinator; bin/startServer.sh $CONTEXT > $currDir/coord.out 2>&1 &)
    fi
}

startRM(){
    Config=$(sh $OSCARS_DIST/bin/parseManifest.sh ResourceManagerService $CONTEXT resourceManager)
    Service=$(echo $Config | awk -F/ '$1~//{print $2}')
    Conf=$(echo $Config | awk -F/ '$1~//{print $3}')
    Yaml=$(echo $Config | awk -F/ '$1~//{print $4}' | sed "s/'//g")
    if [ "$Conf" == "conf" ]; then
        port=$(awk -F: '$1~/publishTo/{print $4}' $OSCARS_HOME/$Service/$Conf/$Yaml)
    elif [ "$Conf" == "config" ]; then
        port=$(awk -F: '$1~/publishTo/{print $4}' $OSCARS_DIST/$Service/$Conf/$Yaml)
    fi
    port=$(echo $port | sed "s/[^0-9]//g")
    porttest=`netstat -na | grep tcp | grep LISTEN | grep $port`
    if [ ! -z "$porttest" ]; then
        echo resourceManager already running
    else
        echo starting resource Manager on port $port
       (cd $OSCARS_DIST/resourceManager; bin/startServer.sh $CONTEXT > $currDir/rm.out 2>&1 &)
    fi
}

startTopoBridge() {
    Config=$(sh $OSCARS_DIST/bin/parseManifest.sh TopoBridgeService $CONTEXT topoBridge)
    Service=$(echo $Config | awk -F/ '$1~//{print $2}')
    Conf=$(echo $Config | awk -F/ '$1~//{print $3}')
    Yaml=$(echo $Config | awk -F/ '$1~//{print $4}' | sed "s/'//g")
    if [ "$Conf" == "conf" ]; then
        port=$(awk -F: '$1~/publishTo/{print $4}' $OSCARS_HOME/$Service/$Conf/$Yaml)
    elif [ "$Conf" == "config" ]; then
        port=$(awk -F: '$1~/publishTo/{print $4}' $OSCARS_DIST/$Service/$Conf/$Yaml)
    fi
    port=$(echo $port | sed "s/[^0-9]//g")
    porttest=`netstat -na | grep tcp | grep LISTEN | grep $port`
    if [ ! -z "$porttest" ]; then
        echo TopoBridge  already running
    else
        echo starting topoBridge Server on port $port
       (cd $OSCARS_DIST/topoBridge; bin/startServer.sh $CONTEXT >  $currDir/topoBridge.out 2>&1 &)
    fi
}

startStubPCE(){
    Config=$(sh $OSCARS_DIST/bin/parseManifest.sh StubPCE $CONTEXT stubPCE)
    Service=$(echo $Config | awk -F/ '$1~//{print $2}')
    Conf=$(echo $Config | awk -F/ '$1~//{print $3}')
    Yaml=$(echo $Config | awk -F/ '$1~//{print $4}' | sed "s/'//g")
    if [ "$Conf" == "conf" ]; then
        port=$(awk -F: '$1~/publishTo/{print $4}' $OSCARS_HOME/$Service/$Conf/$Yaml)
    elif [ "$Conf" == "config" ]; then
        port=$(awk -F: '$1~/publishTo/{print $4}' $OSCARS_DIST/$Service/$Conf/$Yaml)
    fi
    port=$(echo $port | sed "s/[^0-9]//g")
    porttest=`netstat -na | grep tcp | grep LISTEN | grep $port`
    if [ ! -z "$porttest" ]; then
        echo stubPCE  already running
    else
        echo starting stubPCE Server on port $port
       (cd $OSCARS_DIST/stubPCE; bin/startServer.sh $CONTEXT > $currDir/stubPCE.out 2>&1 &)
    fi
}

startConnPCE() {
    Config=$(sh $OSCARS_DIST/bin/parseManifest.sh ConnectivityPCE $CONTEXT connectivityPCE)
    Service=$(echo $Config | awk -F/ '$1~//{print $2}')
    Conf=$(echo $Config | awk -F/ '$1~//{print $3}')
    Yaml=$(echo $Config | awk -F/ '$1~//{print $4}' | sed "s/'//g")
    if [ "$Conf" == "conf" ]; then
        port=$(awk -F: '$1~/publishTo/{print $4}' $OSCARS_HOME/$Service/$Conf/$Yaml)
    elif [ "$Conf" == "config" ]; then
        port=$(awk -F: '$1~/publishTo/{print $4}' $OSCARS_DIST/$Service/$Conf/$Yaml)
    fi
    port=$(echo $port | sed "s/[^0-9]//g")
    porttest=`netstat -na | grep tcp | grep LISTEN | grep $port`
    if [ ! -z "$porttest" ]; then
       echo connectivityPCE already running
    else
       echo starting connectivity PCE on port $port
       (cd $OSCARS_DIST/connectivityPCE; bin/startServer.sh $CONTEXT > $currDir/connPCE.out 2>&1 &)
    fi
}

startBandwidthPCE() {
    Config=$(sh $OSCARS_DIST/bin/parseManifest.sh BandwidthPCE $CONTEXT bandwidthPCE)
    Service=$(echo $Config | awk -F/ '$1~//{print $2}')
    Conf=$(echo $Config | awk -F/ '$1~//{print $3}')
    Yaml=$(echo $Config | awk -F/ '$1~//{print $4}' | sed "s/'//g")
    if [ "$Conf" == "conf" ]; then
        port=$(awk -F: '$1~/publishTo/{print $4}' $OSCARS_HOME/$Service/$Conf/$Yaml)
    elif [ "$Conf" == "config" ]; then
        port=$(awk -F: '$1~/publishTo/{print $4}' $OSCARS_DIST/$Service/$Conf/$Yaml)
    fi
    port=$(echo $port | sed "s/[^0-9]//g")
    porttest=`netstat -na | grep tcp | grep LISTEN | grep $port`
    if [ ! -z "$porttest" ]; then
        echo bandwidthPCE already running
    else
        echo starting bandwidthPCE on port $port
        (cd $OSCARS_DIST/bandwidthPCE; bin/startServer.sh $CONTEXT > $currDir/bwPCE.out 2>&1 & )
    fi
}

startDijPCE () {
    Config=$(sh $OSCARS_DIST/bin/parseManifest.sh DijkstraPCE $CONTEXT dijkstraPCE)
    Service=$(echo $Config | awk -F/ '$1~//{print $2}')
    Conf=$(echo $Config | awk -F/ '$1~//{print $3}')
    Yaml=$(echo $Config | awk -F/ '$1~//{print $4}' | sed "s/'//g")
    if [ "$Conf" == "conf" ]; then
        port=$(awk -F: '$1~/publishTo/{print $4}' $OSCARS_HOME/$Service/$Conf/$Yaml)
    elif [ "$Conf" == "config" ]; then
        port=$(awk -F: '$1~/publishTo/{print $4}' $OSCARS_DIST/$Service/$Conf/$Yaml)
    fi
    port=$(echo $port | sed "s/[^0-9]//g")
    porttest=`netstat -na | grep tcp | grep LISTEN | grep $port`
    if [ ! -z "$porttest" ]; then
        echo dijkstraPCE already running
    else
        echo starting dijkstraPCE on port $port
        (cd $OSCARS_DIST/dijkstraPCE; bin/startServer.sh $CONTEXT > $currDir/dijPCE.out 2>&1 &)
    fi
}

startVlanPCE () {
    Config=$(sh $OSCARS_DIST/bin/parseManifest.sh VlanPCE $CONTEXT vlanPCE)
    Service=$(echo $Config | awk -F/ '$1~//{print $2}')
    Conf=$(echo $Config | awk -F/ '$1~//{print $3}')
    Yaml=$(echo $Config | awk -F/ '$1~//{print $4}' | sed "s/'//g")
    if [ "$Conf" == "conf" ]; then
        port=$(awk -F: '$1~/publishTo/{print $4}' $OSCARS_HOME/$Service/$Conf/$Yaml)
    elif [ "$Conf" == "config" ]; then
        port=$(awk -F: '$1~/publishTo/{print $4}' $OSCARS_DIST/$Service/$Conf/$Yaml)
    fi  
    port=$(echo $port | sed "s/[^0-9]//g")
    porttest=`netstat -na | grep tcp | grep LISTEN | grep $port`
    if [ ! -z "$porttest" ]; then
        echo vlanPCE already running
    else
        echo starting VlanPCE on port $port
        (cd $OSCARS_DIST/vlanPCE; bin/startServer.sh $CONTEXT >  $currDir/vlanPCE.out 2>&1 &)
    fi
}

startnullPCE () {
   Config=$(sh $OSCARS_DIST/bin/parseManifest.sh nullPCE $CONTEXT pce)
   Service=$(echo $Config | awk -F/ '$1~//{print $2}')
   Conf=$(echo $Config | awk -F/ '$1~//{print $3}')
   Yaml=$(echo $Config | awk -F/ '$1~//{print $4}' | sed "s/'//g")
   if [ "$Conf" == "conf" ]; then
        port=$(awk -F: '$1~/publishTo/{print $4}' $OSCARS_HOME/$Service/$Conf/$Yaml)
   elif [ "$Conf" == "config" ]; then
        port=$(awk -F: '$1~/publishTo/{print $4}' $OSCARS_DIST/$Service/$Conf/$Yaml)
   fi
   port=$(echo $port | sed "s/[^0-9]//g")
   porttest=`netstat -na | grep tcp | grep LISTEN | grep $port`
   if [ ! -z "$porttest" ]; then
        echo nullPCE already running
    else
       echo starting nullPCE on port $port
       (cd $OSCARS_DIST/pce; bin/startNullPCE.sh $CONTEXT > $currDir/nullpce.out 2>&1 & )
   fi
}

startnullAGG () {
   Config=$(sh $OSCARS_DIST/bin/parseManifest.sh NullAggregator $CONTEXT pce)
   Service=$(echo $Config | awk -F/ '$1~//{print $2}')
   Conf=$(echo $Config | awk -F/ '$1~//{print $3}')
   Yaml=$(echo $Config | awk -F/ '$1~//{print $4}' | sed "s/'//g")
   if [ "$Conf" == "conf" ]; then
        port=$(awk -F: '$1~/publishTo/{print $4}' $OSCARS_HOME/$Service/$Conf/$Yaml)
   elif [ "$Conf" == "config" ]; then
        port=$(awk -F: '$1~/publishTo/{print $4}' $OSCARS_DIST/$Service/$Conf/$Yaml)
   fi
   port=$(echo $port | sed "s/[^0-9]//g")
   porttest=`netstat -na | grep tcp | grep LISTEN | grep $port`
   if [ ! -z "$porttest" ]; then
        echo nullAGG  already running
   else
        echo starting nullAGG on port $port
        (cd $OSCARS_DIST/pce; bin/startNullAgg.sh $CONTEXT > $currDir/nullagg.out 2>&1 &)
   fi
}

startStubPSS(){
    Config=$(sh $OSCARS_DIST/bin/parseManifest.sh PSSService $CONTEXT stubPSS)
    Service=$(echo $Config | awk -F/ '$1~//{print $2}')
    Conf=$(echo $Config | awk -F/ '$1~//{print $3}')
    Yaml=$(echo $Config | awk -F/ '$1~//{print $4}' | sed "s/'//g")
    if [ "$Conf" == "conf" ]; then
        port=$(awk -F: '$1~/publishTo/{print $4}' $OSCARS_HOME/$Service/$Conf/$Yaml)
    elif [ "$Conf" == "config" ]; then
        port=$(awk -F: '$1~/publishTo/{print $4}' $OSCARS_DIST/$Service/$Conf/$Yaml)
    fi
    port=$(echo $port | sed "s/[^0-9]//g")
    porttest=`netstat -na | grep tcp | grep LISTEN | grep $port`
    if [ ! -z "$porttest" ]
    then
        echo stubPSS  already running
    else
        echo starting stubPSS Server on port $port
       (cd $OSCARS_DIST/stubPSS; bin/startServer.sh $CONTEXT > $currDir/stubPSS.out 2>&1 &)
    fi
}

startLookup(){
    Config=$(sh $OSCARS_DIST/bin/parseManifest.sh LookupService $CONTEXT lookup)
    Service=$(echo $Config | awk -F/ '$1~//{print $2}')
    Conf=$(echo $Config | awk -F/ '$1~//{print $3}')
    Yaml=$(echo $Config | awk -F/ '$1~//{print $4}' | sed "s/'//g")
    if [ "$Conf" == "conf" ]; then
        port=$(awk -F: '$1~/publishTo/{print $4}' $OSCARS_HOME/$Service/$Conf/$Yaml)
    elif [ "$Conf" == "config" ]; then
        port=$(awk -F: '$1~/publishTo/{print $4}' $OSCARS_DIST/$Service/$Conf/$Yaml)
    fi
    port=$(echo $port | sed "s/[^0-9]//g")
    porttest=`netstat -na | grep tcp | grep LISTEN | grep $port`
    if [ ! -z "$porttest" ]; then
       echo Lookup  already running
    else
       echo starting Lookup Server on port $port
       (cd $OSCARS_DIST/lookup; bin/startServer.sh $CONTEXT > $currDir/lookup.out 2>&1 &)
    fi
}

startWBUI(){
    Config=$(sh $OSCARS_DIST/bin/parseManifest.sh WBUIService $CONTEXT wbui jetty.xml)
    Service=$(echo $Config | awk -F/ '$1~//{print $2}')
    Conf=$(echo $Config | awk -F/ '$1~//{print $3}')
    Yaml=$(echo $Config | awk -F/ '$1~//{print $4}' | sed "s/'//g")
    if [ "$Conf" == "conf" ]; then
        port=$(awk -F\" '$4~/jetty.port/{print $6}' $OSCARS_HOME/$Service/$Conf/$Yaml)
    elif [ "$Conf" == "config" ]; then
        port=$(awk -F\" '$4~/jetty.port/{print $6}' $OSCARS_DIST/$Service/$Conf/$Yaml)
    fi
    port=$(echo $port | sed "s/[^0-9]//g")
    porttest=`netstat -na | grep tcp | grep LISTEN | grep $port`
    if [ ! -z "$porttest" ]; then
        echo WBUI  already running
    else
        echo starting WBUI Server on port $port
       (cd $OSCARS_DIST/wbui; bin/startServer.sh $CONTEXT > $currDir/wbui.out 2>&1 &)
    fi
}

# execution starts here
if [ $# -lt 2 ]; then
    printUsage
fi

currDir=$(pwd)
CONTEXT=$1
case $1 in
    d|D|dev|DEV) CONTEXT="DEVELOPMENT";;
    p|P|pro|PRO) CONTEXT="PRODUCTION";;
    t|T|test|TEST) CONTEXT="UNITTEST";;
    s|S|sdk) CONTEXT="SDK";;
esac
   
if [ "$CONTEXT" ==  "PRODUCTION" ] || [ "$CONTEXT" == "UNITTEST" ] || [ "$CONTEXT" == "DEVELOPMENT" ] || [ "$CONTEXT" == "SDK" ]; then
    echo "Start services in CONTEXT $CONTEXT"
else
    echo "CONTEXT  $CONTEXT is not recognized"
    printUsage
fi
shift
while [ ! -z $1 ]
    do 
    case $1 in
    ALL)
      startLookup
      startTopoBridge
      startRM
      startauthN
      startauthZ
      startCoord
#     startStubPCE
#     startnullPCE
      startnullAGG
      startDijPCE
      startConnPCE
      startBandwidthPCE
      startVlanPCE
      startStubPSS
      startWBUI
      startOSCARSService;;  
    authN)    startauthN;;
    authZ)    startauthZ;;
    api)      startOSCARSService;;
    coord)    startCoord;;
    topoBridge) startTopoBridge;;
    rm)       startRM;;
    stubPCE)  startStubPCE;;
    bwPCE)    startBandwidthPCE;;
    connPCE)  startConnPCE;;
    dijPCE)   startDijPCE;;
    vlanPCE)  startVlanPCE;;
    nullPCE)  startnullPCE;;
    nullAGG)  startnullAGG;;
    stubPSS)  startStubPSS;;
    lookup)   startLookup;;
    wbui)     startWBUI;;
    *)        echo $1 not a recognized server
  esac
  shift
done


FILE_PATH=UNSET
PATTERN=UNSET
declare -i MAX=10
PROJECT=UNSET
MODULE=UNSET
UUID=UNSET
LEVEL=INFO
ULA=UNSET

check_java_version() {
  test "$(echo "$@" | tr " " "\n" | sort -rV | head -n 1)" != "$1"
}

argsError() {
  echo "Usage: startSearcher
                        [ -p | --path ]  log file path
                        [ --pattern ]         log file pattern
                        [ --project ]         project
                        [ -m | --module ]     module
                        [ --uuid ]            uuid
                        [ --max ]             maximum number of monitored log files,default 10
                        [ --level ]           search log level [DEBUG,INFO,WARN,ERROR]
                        [ -u | --ula ]        ula
  exit 2"
}

if [ -x "$JAVA_HOME" ]; then

  JAVA_VERSION=$(java -version 2>&1 | sed '1!d' | sed -e 's/"//g' | awk '{print substr(""$3"",0,3)}')

  if check_java_version "$JAVA_VERSION" "1.8"; then
    echo "need jdk 1.8+"
    exit 1
  fi

else
  echo "need java env and make sure you are permitted"
  exit 1
fi

for arg in "$@"; do
  case $arg in
  -p | --path)
    FILE_PATH="$2"
    shift
    ;;
  --pattern)
    PATTERN="$2"
    shift
    ;;
  --project)
    PROJECT="$2"
    shift
    ;;
  --uuid)
    UUID="$2"
    shift
    ;;
  -m | --module)
    MODULE="$2"
    shift
    ;;
  --level)
    LEVEL="$2"
    shift
    ;;
  -u | --ula)
    ULA="$2"
    shift
    ;;
  --max)
    MAX="$2"
    shift
    shift
    ;;
  *)
    shift
    ;;
  esac
done

## check args
#if [ "$FILE_PATH" == "UNSET" ] || [ "$PATTERN" == "UNSET" ] || [ "$MODULE" == "UNSET" ] || [ "$UUID" == "UNSET" ] || [ "$ULA" == "UNSET" ]; then
#  argsError
#fi
#
#if [ "$LEVEL" != "DEBUG" ] || [ "$LEVEL" != "INFO" ] || [ "$LEVEL" != "WARN" ] || [ "$LEVEL" != "ERROR" ]; then
#  argsError
#fi

## todo curl ULA check right
ULA_URL=$ULA"/api/v1/log"

args="-Dultlog.searcher.path=$FILE_PATH -Dultlog.searcher.pattern=$PATTERN -Dultlog.searcher.max=$MAX -Dultlog.searcher.project=$PROJECT -Dultlog.searcher.module=$MODULE -Dultlog.searcher.uuid=$UUID -Dultlog.searcher.log.sendType=ULA -Dultlog.searcher.log.LEVEL=$LEVEL -Dultlog.ula.url=$ULA_URL"

nohup java -jar "$args" searcher.jar >searcherlog.log &

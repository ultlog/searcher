PATH=UNSET
PATTERN=UNSET
MAX=10
PROJECT=UNSET
MODULE=UNSET
LEVEL=INFO
ULA=UNSET


argsError()
{
  echo "Usage: startSearcher
                        [ -p | -P | --path ] logfilepath
                        [ --pattern ] logfilepattern
                        [ --project ] project
                        [ --module ] module
                        [ --max ] max
                        [ --level ] level[DEBUG,INFO,WARN,ERROR]
                        [ --ula ] ula
  exit 2"
}

if [ -n "$JAVA_HOME" ]; then
  JAVA="$JAVA_HOME/bin/java"
  echo "JAVA_HOME=""$JAVA"
else
  echo "need java env"
  exit 1
fi

for arg in "$@"
do
    case $arg in
        -p|--path| -P)
        PATH="$2"
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
        --module)
        MODULE="$2"
        shift
        ;;
        --level)
        LEVEL="$2"
        shift
        ;;
        --ula)
        ULA="$2"
        shift
        ;;
        -m|--max)
        MAX="$2"
        shift
        shift
        ;;
        *)
        shift
        ;;
    esac
done

## todo check args
argsError



## todo curl ULA check right

args="-Dultlog.searcher.path=$PATH -Dultlog.searcher.pattern=$PATTERN -Dultlog.searcher.MAX=$MAX -Dultlog.searcher.PROJECT=$PROJECT -Dultlog.searcher.MODULE=$MODULE -Dultlog.searcher.log.sendType=ULA -Dultlog.searcher.log.LEVEL=$LEVEL -Dultlog.ULA.url=$ULA"

#java -jar $args  searcher.jar
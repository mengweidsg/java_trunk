function make(){
    appName=$1
    target=$2
    mkdir -p $BASE/target/rpm/RPMS
    ant -DappName=$appName -DversionFormat=yyyyMMdd -Dbasedir=$target/target/release -f $target/bundle/rpm/framework/build.xml
#    cp -R $target/target/release/target/rpm/RPMS/* $BASE/target/rpm/RPMS
#    rm -rf $BASE/target
}

cd `dirname $0`
BASE=`pwd`

make "satellite" "$BASE/satellite-deploy" 

if [ -z "$1" ]
then
	mvn -T 8 -Dmaven.test.skip=true install
	exit 0
fi

mvn -T 8 -Dmaven.test.skip=true -pl $1 install

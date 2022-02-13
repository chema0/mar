if [ -z "$1" ]
then
	echo "No module specified"
	exit 1
fi

mvn -T 8 -Dmaven.test.skip=true -pl $1 install

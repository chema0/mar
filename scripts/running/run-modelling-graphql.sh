if [ "$1" = "default" ]
then
	echo "Running analyser with type=ecore and repo=repo-genmymodel-ecore"
	cd "$REPO_MAR/mar-modelling-graphql"
	mvn spring-boot:run -Dspring-boot.run.arguments="$REPO_MAR/configuration/dist/config.json '--type=ecore' '--repository=repo-genmymodel-ecore'"
	exit 0
elif [ "$#" -ne 4 ]
then
        echo "Usage: $0 -t type -r repo"
        exit 1
fi

while getopts :t:r: opt
do
	case "$opt" in
		t) # type 
		   type=$OPTARG ;;
		r) # repository
		   repo=$OPTARG ;;
	       \?) # invalid option
		   echo "Error: invalid option"
		   exit;;
	esac
done

echo "Running analyser with type=$type and repo=$repo"
cd "$REPO_MAR/mar-modelling-graphql"
mvn spring-boot:run -Dspring-boot.run.arguments="$REPO_MAR/configuration/dist/config.json '--type=$type' '--repository=$repo'"

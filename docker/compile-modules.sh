# Compiles mar-common module, we need it for mongo services
cd $REPO_MAR && ./scripts/compiling/compile_module.sh mar-common

# Compiles executable API JAR with dependencies
cd mar-api-graphql && mvn clean install spring-boot:repackage
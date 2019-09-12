rm -r ./bin/*
javac -d ./bin/ $(find ./src/* | grep .java)
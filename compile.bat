cd src
dir /s /B *.java > sources.txt
javac @sources.txt -d ../bin/
cd ..
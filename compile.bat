rmdir /q /s bin
cd src
dir /s /B *.java > sources.txt
javac @sources.txt
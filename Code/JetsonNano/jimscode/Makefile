
CC = gcc
CFLAGS  = -g -Wall -I/usr/include/opencv4/
LDFLAGS += $(shell pkg-config --libs --static opencv4)

default: finder

finder:  main.o config.o utils.o stopWatch.o
	$(CC) -o finder main.o config.o stopWatch.o utils.o $(LDFLAGS) -lstdc++ -lstdc++fs -ljsoncpp

file:  fileAndDriectoryTest.o 
	$(CC) -o file fileAndDriectoryTest.o $(LDFLAGS) -lstdc++ -lstdc++fs 

fileAndDriectoryTest.o:  fileAndDriectoryTest.cpp 
	$(CC) $(CFLAGS) -c -std=c++17 fileAndDriectoryTest.cpp

config.o:  config.cpp 
	$(CC) $(CFLAGS) -c -std=c++17 config.cpp

main.o:  main.cpp 
	$(CC) $(CFLAGS) -c -std=c++17 main.cpp

utils.o:  utils.cpp 
	$(CC) $(CFLAGS) -c -std=c++17 utils.cpp

stopWatch.o:  stopWatch.cpp 
	$(CC) $(CFLAGS) -c -std=c++17 stopWatch.cpp

clean: 
	$(RM) count *.o *~

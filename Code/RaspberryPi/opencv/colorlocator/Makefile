CXX ?= g++

CXXFLAGS += -c -Wall $(shell pkg-config --cflags opencv)
LDFLAGS += $(shell pkg-config --libs --static opencv)

all: colorlocator

colorlocator: colorlocator.o; $(CXX) $< -o $@ $(LDFLAGS) -ljsoncpp

%.o: %.cpp; $(CXX) $< -o $@ $(CXXFLAGS)

clean: ; rm -f colorlocator.o colorlocator


#all:
#	g++ colorlocator.cpp -I /usr/local/include/opencv3/ -L /usr/local/lib/ -lopencv_core -lopencv_photo -lopencv_ml -lopencv_imgcodecs -lopencv_highgui -lopencv_videoio -lstdc++  -o colorlocator



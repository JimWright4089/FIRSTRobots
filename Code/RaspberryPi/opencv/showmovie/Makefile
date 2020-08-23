CXX ?= g++

CXXFLAGS += -c -Wall $(shell pkg-config --cflags opencv)
LDFLAGS += $(shell pkg-config --libs --static opencv)

all: showmovie

showmovie: showmovie.o; $(CXX) $< -o $@ $(LDFLAGS)

%.o: %.cpp; $(CXX) $< -o $@ $(CXXFLAGS)

clean: ; rm -f colorlocator.o showmovie


#all:
#	g++ showmovie.cpp -I /usr/local/include/opencv3/ -L /usr/local/lib/ -lopencv_core -lopencv_photo -lopencv_ml -lopencv_imgcodecs -lopencv_highgui -lopencv_videoio -lstdc++  -o colorlocator



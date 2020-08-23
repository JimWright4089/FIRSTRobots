CXX ?= g++

CXXFLAGS += -c -Wall $(shell pkg-config --cflags opencv)
LDFLAGS += $(shell pkg-config --libs --static opencv) -ljsoncpp

all: bumperfinder

bumperfinder: bumperfinder.o; $(CXX) $< -o $@ $(LDFLAGS)

%.o: %.cpp; $(CXX) $< -o $@ $(CXXFLAGS)

clean: ; rm -f bumperfinder.o showmovie


#all:
#	g++ bumperfinder.cpp -I /usr/local/include/opencv3/ -L /usr/local/lib/ -lopencv_core -lopencv_photo -lopencv_ml -lopencv_imgcodecs -lopencv_highgui -lopencv_videoio -lstdc++  -o colorlocator



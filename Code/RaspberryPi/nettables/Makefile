CXX ?= gcc

CXXFLAGS += -c -std=c++17 -Wall
LDFLAGS += -L /usr/local/frc/lib -lntcore -lwpiutil -std=c++17 -pthread

all: client sin LEDRing

client: client.o; $(CXX) $< -o $@ $(LDFLAGS)
sin: sin.o; $(CXX) $< -o $@ $(LDFLAGS)
LEDRing: LEDRing.o; $(CXX) $< -o $@ $(LDFLAGS)

%.o: %.cpp; $(CXX) -I/usr/local/frc/include/ $< -o $@ $(CXXFLAGS) 

clean: ; rm -f client.o client sin.o sin LEDRing.o LEDRing


HOME_TREE := ../rplidar_sdk/sdk

CXX ?= gcc

CXXFLAGS += -c -std=c++17 -Wall
LDFLAGS += -L /home/jim/robot/allwpilib/build/lib -lntcore -lwpiutil -std=c++17 -pthread \
 -L /home/jim/share/rplidar_sdk/sdk/output/Linux/Release -lrplidar_sdk

all: runLidar

runLidar: runLidar.o; $(CXX) $< -o $@ $(LDFLAGS)

%.o: %.cpp; $(CXX) -I$(HOME_TREE)/sdk/include -I$(HOME_TREE)/sdk/src \
-I/home/jim/robot/allwpilib/wpiutil/src/main/native/include \
-I/home/jim/robot/allwpilib/ntcore/src/main/native/include $< -o $@ $(CXXFLAGS) 

clean: ; rm -f runLidar.o runLidar

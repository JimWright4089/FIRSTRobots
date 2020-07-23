#!/bin/sh
sudo dhclient -v
/home/jim/robot/bin/LEDRing &
sudo /home/jim/robot/bin/runLidar &


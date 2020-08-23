#Import libraries
import cv2
import os

#Counter Variable
count = 0

#img = cv2.imread('/home/pi/robot/Media/Pictures/Robot03.jpg')
#cv2.imshow('image',img)

#cv2.namedWindow('Frames', cv2.WINDOW_AUTOSIZE)
cv2.namedWindow('Frames')

cap = cv2.VideoCapture('/home/pi/robot/Media/Video/RobotVideo01.mp4')
#cap = cv2.VideoCapture(0)

while (cap.isOpened()):
   # Capture frame-by-frame
   ret, frame = cap.read()
   if ret == True:
      print('Read %d frame: ' % count, ret)
      cv2.imshow('Frames',frame)
      count += 1
   else:
      break

   key = cv2.waitKey(3)
   if key == 27:#if ESC is pressed, exit loop
        cv2.destroyAllWindows()
        break

# When everything done, release the capture
cap.release()
print(count)
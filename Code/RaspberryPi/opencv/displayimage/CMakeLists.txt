cmake_minimum_required(VERSION 2.8)

project( displayimage )

find_package( OpenCV REQUIRED )

include_directories( ${OpenCV_INCLUDE_DIRS} )

add_executable( displayimage displayimage.cpp )
target_link_libraries( displayimage ${OpenCV_LIBS} )


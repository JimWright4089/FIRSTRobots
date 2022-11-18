cmake_minimum_required(VERSION 2.8)

project( basicviewer )

find_package( OpenCV REQUIRED )

include_directories( ${OpenCV_INCLUDE_DIRS} )

add_executable( basicviewer basicviewer.cpp )
target_link_libraries( basicviewer ${OpenCV_LIBS} )


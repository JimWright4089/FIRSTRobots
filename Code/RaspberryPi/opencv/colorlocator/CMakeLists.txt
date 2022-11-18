cmake_minimum_required(VERSION 2.8)

project( colorlocator )

find_package( OpenCV REQUIRED )

include_directories( ${OpenCV_INCLUDE_DIRS} )

add_executable( colorlocator colorlocator.cpp )
target_link_libraries( colorlocator ${OpenCV_LIBS} )


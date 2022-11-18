cmake_minimum_required(VERSION 2.8)

project( apriltagdemo )

find_package( OpenCV REQUIRED )
find_package( apriltag REQUIRED )

find_package(PkgConfig)
pkg_search_module(apriltag REQUIRED apriltag)
set(apriltag_INCLUDE_DIRS "${apriltag_INCLUDE_DIRS}/apriltag")
link_directories(${apriltag_LIBDIR})

include_directories( ${OpenCV_INCLUDE_DIRS} )
include_directories( ${apriltag_INCLUDE_DIRS} )

add_executable( apriltagdemo opencv_demo.cpp )
target_link_libraries( apriltagdemo ${OpenCV_LIBS} )
target_link_libraries( apriltagdemo ${apriltag_LIBRARIES})

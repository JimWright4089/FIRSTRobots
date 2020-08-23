#include <iostream>
#include <fstream>
#include <jsoncpp/json/json.h>

using namespace std;

int main()
{
  ifstream ifs("colors.json");
  Json::Reader reader;
  Json::Value data;

  reader.parse(ifs, data);

  const Json::Value colors = data["colors"];  
	for ( int index = 0; index < colors.size(); ++index )
  {
//    cout << colors[index] << "\n";
    cout << colors[index]["color"] << "\n";
    cout << colors[index]["code"]["v"][0] << "\n";
    cout << colors[index]["code"]["v"][1] << "\n";
    cout << colors[index]["code"]["rgb"] << "\n";

/*
    print(p['color'])
    print(p['code']['v'][0])
    print(p['code']['v'][1])
    print(p['code']['rgb'])
*/
  }	
}

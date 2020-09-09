#include <experimental/filesystem>
#include <iostream>
#include <string>
using namespace std;
namespace fs = std::experimental::filesystem;

int main ()
{
  string path = "/media/jim/SAVE THE DA";
  
  cout << fs::exists(path) << "\n";
 
  return 0;
}



int mainx ()
{
  int i;
  cout << "Please enter an integer value: ";
  cin >> i;
  cout << "The value you entered is " << i;
  cout << " and its double is " << i*2 << ".\n";
  return 0;
}

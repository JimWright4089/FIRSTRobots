#include <stddef.h>
#include <string_view>
extern "C" {
const unsigned char* sysid_GetResource_mechanism_frcUserProgram(size_t* len) {
  *len = 506888;
  return contents;
}
}  // extern "C"
namespace sysid{
std::string_view GetResource_mechanism_frcUserProgram() {
  return {reinterpret_cast<const char*>(contents), 506888};
}
}  // namespace sysid
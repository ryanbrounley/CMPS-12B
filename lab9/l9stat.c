//Ryan Brounley
//rbrounle

#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <unistd.h>
#include <time.h>
#include <errno.h>
#include <string.h>

int ERROR_STATUS = 0;

void getinformation(char* filename){
  struct stat fileinfo;
  char link_buffer[1024];
  char timebuffer[1024];
  int readsymlink = readlink(filename, link_buffer, sizeof(link_buffer));
  time_t currenttime = time(NULL);
  int lstatstatus = lstat(filename, &fileinfo);
  int daystoseconds = (180 * 86400);
  
  struct tm *timestruct = localtime(&fileinfo.st_mtime);
  if(currenttime - fileinfo.st_mtime > daystoseconds ||
     fileinfo.st_mtime - currenttime > daystoseconds){
     strftime(timebuffer, sizeof(timebuffer), "%b %e  %Y", timestruct);}
  else{
     strftime(timebuffer, sizeof(timebuffer), "%b %e %R", timestruct);}

  if(lstatstatus != 0){ 
    ERROR_STATUS = 1;
    strerror(errno);
    return;}

  if(readsymlink == -1){
    printf("%06o  %8ld %s %s\n", fileinfo.st_mode, fileinfo.st_size,
    timebuffer, filename);
  }else{
    printf("%06o  %8ld %s %s -> %s\n", fileinfo.st_mode, fileinfo.st_size,
    timebuffer, filename, link_buffer);
  } 
}

int main(int argc, char** argv){
  if(argc > 1){ 
     for (int i = 1; i < argc; ++i){  getinformation(argv[i]);}}
  else{  getinformation(".");}    
  return ERROR_STATUS;
}

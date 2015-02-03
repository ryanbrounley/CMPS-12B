// $Id: nsort.c,v 1.1 2011-04-29 19:46:42-07 - - $
// Ryan Brounley -- rbrounle

#include <errno.h>
#include <libgen.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>
#include <stdbool.h>
#include <unistd.h>

#include "inssort.h"

char* string[10] = {NULL};
char  buffer[1024];

void print_strings(int size){
   for(int i = 0; i < size; ++i){
      printf("%20.15s\n", string[i]);
      free(string[i]);}
}

int strcomparison(const void* this, const void* that){
   int compared = strcmp(*((const char**)this), *((const char**)(that)));
   return compared;
}

int main (int argc, char**argv) {
   (void) argc;
   (void) argv;
   int scancount = 0;
   while(fgets(buffer, 1024, stdin)!= NULL){
      strcat(buffer, "\0");
      string[scancount] = strdup(buffer);
      scancount++;
   }
   inssort(&string[0], scancount, sizeof(char*), strcomparison);
   print_strings(scancount);
   return EXIT_SUCCESS;
}

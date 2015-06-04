//Ryan Brounley -- rbrounle

#include <stdlib.h>
#include <assert.h>
#include <stdio.h>

extern char **environ;

void main (){
   for(int argi = 0; environ[argi] != NULL; argi++){
       printf("%s\n", environ[argi]);
   }
   return EXIT_SUCCESS; 
}

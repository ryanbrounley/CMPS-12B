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

double number[1000] = {'\0'};

void print_sorts(int size){
   for(int i = 1; i < size; ++i)
      printf("%20.15g\n", number[i]);
}

int comparison(const void* this, const void* that){
   const double *tempthis = (const double *)this;
   const double *tempthat = (const double *)that;
   int compared = *tempthis - *tempthat;
   return compared;
}

int main (void) {
   int arraylength = 0;
   int scancount = 0;
   while(true){
      if(number[1000] != '\0') break;
      if(scancount == EOF) break; 
      scancount = scanf("%lf", &number[arraylength]);
      arraylength++;
   }
   inssort(&number[0], arraylength, sizeof(double*), comparison);
   print_sorts(arraylength);
   return EXIT_SUCCESS;
}

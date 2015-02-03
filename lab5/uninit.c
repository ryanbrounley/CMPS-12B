// $Id: uninit.c,v 1.1 2014-05-06 21:26:45-07 - - $

#include <stdio.h>
#include <stdlib.h>

int main (int argc, char **argv) {
   int *pointer;
   printf ("pointer=%p\n", pointer);
   printf ("*pointer=%d\n", *pointer);
   int foo;
   printf ("foo=%d\n", foo);
}

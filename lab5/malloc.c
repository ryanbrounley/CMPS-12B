// $Id: malloc.c,v 1.1 2014-05-06 21:26:45-07 - - $

#include <stdio.h>
#include <stdlib.h>

typedef struct node node;
struct node {
   int value;
   node *link;
};

int main (int argc, char **argv) {
   node *ptr = malloc (sizeof (struct node));
   ptr = malloc (sizeof (node));
   ptr->value = 6;
   ptr->link = NULL;
   printf ("%p-> {%d, %p}\n", ptr, ptr->value, ptr->link);
   free (ptr);
   return EXIT_SUCCESS;
}

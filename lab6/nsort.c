// $Id: nsort.c,v 1.1 2011-04-29 19:46:42-07 - - $
// Ryan Brounley -- rbrounle

#define _GNU_SOURCE
#include <errno.h>
#include <libgen.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>
#include <stdbool.h>
#include <unistd.h>

typedef struct node node;
struct node{
  double item;
  node *link;
};

node *head = NULL;
int EXIT_STATUS = 0;
int opt = 0;
bool debug = false;

void insort(double number){ 
   node *prev = NULL;
   node *curr = head;

   while(curr != NULL){
       if (curr->item > number) break;
           prev = curr;
           curr = curr->link;
   }

   node *temp = malloc(sizeof(struct node));
   temp->item = number;
   temp->link = curr;
   if(prev == NULL) head = temp;
           else     prev->link = temp;
   
}

void print_sorts(void){
   node *curr = head;
   node *temp = NULL;
   while(curr != NULL){
   if (!debug){
      printf("%24.15g\n",curr->item);}
   else{
       printf("%p-> {%lg, %p]\n", curr, curr->item, curr->link);}
   temp = curr;
   curr = curr -> link;
   free(temp);
   }
}

int main (int argc, char **argv) {
   char err_buffer [256];
   char err_buf_fmt[16];
   sprintf (err_buf_fmt, "%%%lds", sizeof err_buffer - 1);
   
   while((opt = getopt(argc, argv, "d"))!= -1){
    if((opt = 'd')){ debug = true;} 
    else             debug = false;  
    }
   
   for (;;) {
   double number;
   int scancount = scanf("%lg", &number);

   if(scancount == EOF){ break;
   }else if (scancount == 1) {
      insort(number);
   }else {
     scancount = scanf(err_buf_fmt, err_buffer);
     assert (scancount == 1);
     printf("%s: bad number \"\"\n",err_buffer);
     };
   };
   print_sorts();
  return EXIT_SUCCESS;
}

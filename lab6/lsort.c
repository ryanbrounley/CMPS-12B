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
  char *string;
  node *link;
};

node *head = NULL;
int EXIT_STATUS = 0;
int opt = 0;
bool debug = false;

void insort(char *word){ 
   node *prev = NULL;
   node *curr = head;

   while(curr != NULL){
       if (strcmp (head->string, word) > 0) break;
           prev = curr;
           curr = curr->link;
   }

   node *temp = malloc(sizeof(struct node));
   temp->string = strdup(word);
   temp->link = curr;
   if(prev == NULL) head = temp;
           else     prev->link = temp;
}

void print_sorts(void){
   node *curr = head;
   node *temp = NULL;
   while(curr != NULL){
   if (!debug){
      printf("%24.15s\n",curr->string);}
   else{
       printf("%p-> {%s, %p]\n", curr, curr->string, curr->link);}
   temp = curr;
   curr = curr -> link;
   free(temp);
   }
}

int main (int argc, char **argv){ 
   while((opt = getopt(argc, argv, "d"))!= -1){
    if((opt = 'd')){ debug = true;} 
    else             debug = false;  
    }

   char buffer [82];
   for (;;) {
   char *word = fgets (buffer, sizeof buffer, stdin);

   if(word == NULL){ break;}
   else { insort(buffer);}
   char *tmp = strchr(buffer, '\n');
   if (tmp != NULL) {
      *tmp = '\0';
   }else {
     printf("Invalid Entry!");
     EXIT_STATUS = 1;
     };
   };
   print_sorts();
  return EXIT_STATUS;
}

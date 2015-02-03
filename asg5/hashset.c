// $Id: hashset.c,v 1.1 2014-06-06 22:10:43-07 - - $

#include <assert.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <limits.h>
#include <stdbool.h>

#include "debug.h"
#include "hashset.h"
#include "strhash.h"

#define HASH_NEW_SIZE 15

typedef struct hashnode hashnode;
struct hashnode {
   char *word;
   hashnode *link;
};

struct hashset {
   size_t size;
   size_t load;
   hashnode **chains;
};

hashset *new_hashset (void) {
   hashset *this = malloc (sizeof (struct hashset));
   assert (this != NULL);
   this->size = HASH_NEW_SIZE;
   this->load = 0;
   size_t sizeof_chains = this->size * sizeof (hashnode *);
   this->chains = malloc (sizeof_chains);
   assert (this->chains != NULL);
   memset (this->chains, 0, sizeof_chains);
   DEBUGF ('h', "%p -> struct hashset {size = %zd, chains=%p}\n",
                this, this->size, this->chains);
   return this;
}

void arraydoubling(hashset *this){
  int doublelength = (2 * this->load) + 1;
  char **newarray = malloc(sizeof(char*) * doublelength);
  for (int i = 0; i < doublelength; ++i){
     newarray[i] = NULL;}
  for(size_t counter = 0; counter < this->size; ++counter){
     size_t index = strhash(this->chains[counter]) % this->size;
     while(this->chains[counter] != NULL){
        index = strhash(newarray[counter]) % this->size;
     }
   newarray[index] = this->chains[counter];
   }
   char **tmp = this->chains;
   this->size = doublelength;
   this->chains = newarray;
   free(tmp);
}

void free_hashset (hashset *this) {
   DEBUGF ('h', "free (%p)\n", this);
   for(unsigned int i = 0; i < this->size; i++) free(this->chains[i]);
}

void put_hashset (hashset *this, const char *item) {
   if((this->load * 4) > this->size) arraydoubling(this);
   size_t index = strhash (item) % this->size;
   while(this->chains[index] != NULL){
      int comparison = strcmp(this->chains[index], item);
      if (comparison == 0) return;
      index = (index + 1) % this->size;
   }
   this->chains[index] = strdup(item);
   this->load++;
}

void debug_hash(hashset *this){
   int clusters[1024] = {0};
   int count = 0;
   int pos = 0;
   printf("%7zd words in hashset\n", this->load);
   printf("%7zd length of hash array\n", this->size);
   while(pos < this->size){
     if(this->chains[pos] != NULL){
     count++;
     }
     else{
     ++clusters[count];
     count = 0;
     }
   pos++;
   }
   for(unsigned int i =0; i<sizeof(clusters)/sizeof(int);i++){
      if(clusters[i]!=0 && i!=0)
        printf("%7d clusters of size %7d\n", clusters[i],i);
}
}

void superdebugger(hashset *this){
   for(unsigned int i = 0; i < this->size; i++){
      //size_t index = (strhash(this->chains[i]) % this->size);
      if(this->chains[i] != NULL){
        printf("chains[%10d] = %12zu \"%s\"\n", i,
        strhash(this->chains[i]) % this->size, this->chains[i]);
}
}
}

bool has_hashset (hashset *this, const char *item) {
   if(item == NULL) return false;
   size_t index = ((strhash(item) % this->size));
   hashnode *tmp = this->chains[index];
   while(tmp != NULL){
      int num = strcmp(tmp->word, item);
      if(num == 0) return true;
      tmp = tmp->link;
   }
   return false;
}


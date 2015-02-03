// $Id: queue.c,v 1.15 2014-05-22 22:29:25-07 - - $

#include <assert.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "queue.h"

#define STUBPRINTF(...) fprintf (stderr, __VA_ARGS__);

typedef struct queue_node queue_node;
struct queue_node {
   queue_item_t item;
   queue_node *link;
};

struct queue {
   queue_node *front;
   queue_node *rear;
};

queue *new_queue (void) {
   queue *newqueue = malloc(sizeof(struct queue));
   assert(newqueue!=NULL);
   newqueue->front = NULL;
   newqueue->rear = NULL;
   return newqueue;
}

void free_queue (queue *this) {
   assert (isempty_queue (this));
   free (this);
}

void insert_queue (queue *this, queue_item_t item) {
   queue_node *insertnode = malloc(sizeof(queue_node));
   assert(insertnode!=NULL);
   insertnode->item = item;
   insertnode->link = NULL;
   if (this->front == NULL){ this->front = insertnode;
                            this->rear = insertnode;}
   else{ this->rear->link = insertnode;
         this->rear = insertnode;}
}

queue_item_t remove_queue (queue *this) {
   assert (! isempty_queue (this));
   queue_item_t removalitem = this->front->item;
   queue_node *removal = this->front;
   this->front = this->front->link;
   free(removal);
   return removalitem;
}

bool isempty_queue (queue *this) {
   return this->front == NULL;
}


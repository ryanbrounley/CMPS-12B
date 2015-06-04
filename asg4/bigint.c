// $Id: bigint.c,v 1.13 2014-01-24 18:33:47-08 - - $
// Ryan Brounley -- rbrounle

#include <assert.h>
#include <ctype.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "bigint.h"
#include "debug.h"

#define MIN_CAPACITY 16

struct bigint {
   size_t capacity;
   size_t size;
   bool negative;
   char *digits;
};

static void trim_zeros (bigint *this) {
   while (this->size > 0) {
      size_t digitpos = this->size - 1;
      if (this->digits[digitpos] != 0) break;
      --this->size;
   }
}

bigint *new_bigint (size_t capacity) {
   bigint *this = malloc (sizeof (bigint));
   assert (this != NULL);
   this->capacity = capacity;
   this->size = 0;
   this->negative = false;
   this->digits = calloc (this->capacity, sizeof (char));
   assert (this->digits != NULL);
   DEBUGS ('b', show_bigint (this));
   return this;
}

static size_t bigger (size_t this, size_t that){
   if(this > that) return this;
   else            return that;
}

static bool bigger_int(bigint *this, bigint *that){
   size_t length = bigger (this->size, that->size);
   bool thisbigger = false;
   for(size_t i = 0; i < length; ++i){
       size_t index = length - 1 - i;
       if (this->digits[index] > that->digits[index]) thisbigger = true; 
}
return thisbigger;
}

bigint *new_string_bigint (char *string) {
   assert (string != NULL);
   size_t length = strlen (string);
   bigint *this = new_bigint (length > MIN_CAPACITY
                            ? length : MIN_CAPACITY);
   char *strdigit = &string[length - 1];
   if (*string == '_') {
      this->negative = true;
      ++string;
   }
   char *thisdigit = this->digits;
   while (strdigit >= string) {
      assert (isdigit (*strdigit));
      *thisdigit++ = *strdigit-- - '0';
   }
   this->size = thisdigit - this->digits;
   trim_zeros (this);
   DEBUGS ('b', show_bigint (this));
   return this;
}

static bigint *do_add (bigint *this, bigint *that) {
   size_t largest;
   if(bigger_int(this,that) == true){  largest = this->size + 1;}
   else {                              largest = that->size + 1;}
   int carry = 0;
   int digit = 0;
   bigint *result = new_bigint(this->capacity + 1);
   result->size = largest;
   for(size_t index = 0; index < largest; ++index){
       digit = this->digits[index] + that->digits[index] + carry;
       result->digits[index] = digit % 10;
       carry = digit / 10;
   }
   trim_zeros(result);
   return result;
}

static bigint *do_sub (bigint *this, bigint *that) {
   size_t intsize = this->size;
   bigint *subint = new_bigint(this->capacity + 1);
   subint->size = intsize;
   int borrow = 0;
   int digit = 0;
   for (size_t index = 0; index < intsize; ++index){
       digit = this->digits[index] - that->digits[index] - borrow + 10;
       subint->digits[index] = digit % 10;
       borrow = 1 - digit / 10;
   }
   trim_zeros(subint);
   return subint;
}

void free_bigint (bigint *this) {
   free (this->digits);
   free (this);
}

void print_bigint (bigint *this, FILE *file) {
   if(this->negative == true) printf("-");
   for (char *byte = &this->digits[this->size - 1]; byte >= this->digits; --byte) {
       fprintf (file, "%d", *byte);
   }
}

bigint *add_bigint (bigint *this, bigint *that) {
   bigint *addint = NULL;
   if((this->negative == true && that->negative == true) ||
         (this->negative == false && that->negative == false)) addint = do_add(this, that);
   else if ((this->negative == true && that->negative == false) ||
         (this->negative == false && that->negative == true)){
        if (bigger_int(this,that) == true) addint = do_sub(this, that);
        else                               addint = do_sub(that, this);}
   return addint;
}

bigint *sub_bigint (bigint *this, bigint *that) {
   bigint *subint;
   if((this->negative == true && that->negative == true) ||
      (this->negative == false && that->negative == false)){
       if (bigger_int(this,that)) subint = do_sub(this, that);
       else{                      subint = do_sub(that, this); subint->negative = true;}}
   else{
       if(bigger_int(this, that)) subint = do_add(this, that);
       else                       subint = do_add(that, this);
       if(this->negative == true) subint->negative = true; 
   }
   return subint;
}

bigint *mul_bigint (bigint *this, bigint *that) {
   size_t digitsize = this->size + that->size;
   bigint *multint = new_bigint(digitsize);
   char *thisdig = this->digits;
   char *thatdig = that->digits;
   bigint *tempint = new_bigint(digitsize);
   char *tmpdig = tempint->digits;
   char multiply = 0;
   char remainder = 0;
   for(size_t i = 0; i < this->size; i++){
      for(size_t j = 0; j < that->size; j++){
         multiply = thisdig[i] * thatdig[j] + remainder;
         tmpdig[j+i] = multiply % 10;
         remainder = multiply / 10;
   }
   tempint->digits[that->size + i] = remainder;
   remainder = 0;
   tempint->size = that->size + i + 1; 
   bigint *tmpf = multint;
   multint = do_add (multint, tempint);
   free_bigint (tmpf);
   memset(tempint, 0, digitsize*sizeof(char));
   }
   if ((this->negative == true && that->negative == false) ||
         (that->negative == true && this->negative == false)) multint->negative = true;
   else multint->negative = false;
   free_bigint(tempint);
   trim_zeros(multint);
   return multint;
}

void show_bigint (bigint *this) {
   fprintf (stderr, "bigint@%p->{%lu,%lu,%c,%p->", this,
            this->capacity, this->size, this->negative ? '-' : '+',
            this->digits);
   for (char *byte = &this->digits[this->size - 1];
        byte >= this->digits; --byte) {
      fprintf (stderr, "%d", *byte);
   }
   fprintf (stderr, "}\n");
}


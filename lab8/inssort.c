// Ryan Brounley -- rbrounle
//
#include <stdlib.h>
#include <stdio.h>
#include <string.h>

void inssort(void* base, size_t nelem, size_t size, int (*compar)
    (const void*, const void*)){
    for(int sorted = 0; sorted < (int)nelem; ++sorted){
        int slot = sorted;
        char* copy = malloc(sizeof(size));
        memcpy(copy,base+slot*size, size);	
        for(; slot>0; --slot){
           int cmp = compar(copy, (base + (slot-1)*size));
           if (cmp > 0) break;
           memcpy(base + slot*size, base + (slot-1)*size, size);
        }
        memcpy(base + slot*size, copy, size);
    }
}
        

// $Id: queue.java,v 1.1 2014-05-13 00:22:49-07 - - $
// Ryan Brounley -- rbrounle

import java.util.Iterator;
import java.util.NoSuchElementException;

class queue <item_t> implements Iterable <item_t> {

   private class node {
      item_t item;
      node link;
   }
   private node head = null;
   private node tail = null;

   public boolean isempty () {
      boolean empty = false;
      if (head == null){
         empty = true;}
      return empty;
   }

   public void insert (item_t newitem) {
      node n = new node();
      n.item = newitem;
        if(isempty()){
          head  = n;
          tail  = n;}
      tail.link = n;
      tail = tail.link;
   }

   public Iterator <item_t> iterator () {
      return new itor ();
   }
     
   class itor implements Iterator <item_t> {
      node next = head;
      public boolean hasNext () {
         return next != null;
      }

      public item_t next () {
         if (! hasNext ()) throw new NoSuchElementException ();
         item_t result = next.item;
         next = next.link;
         return result;
      }

      public void remove () {
         if (isempty()){
            throw new NoSuchElementException ();}
         if(head != null) head.link = head;
         if(isempty()) tail = null;
         }
      }
}


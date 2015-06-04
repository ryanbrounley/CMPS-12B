// $Id: dllist.java,v 1.1 2014-04-29 01:34:27-07 - - $

import java.util.NoSuchElementException;
import java.util.*;

class dllist {

   public enum position {FIRST, PREVIOUS, FOLLOWING, LAST};

   private class node {
      String item;
      node prev;
      node next;
   }

   private node first = null;
   private node current = null;
   private node last = null;
   private int currentposition = 0;

   public void setposition (position pos) {
     switch(pos) {
         case FIRST: current = first; break;
         case PREVIOUS: current = current.prev; break;
         case FOLLOWING: current = current.next; break;
         case LAST: current = last; break;
         default: break;     
  } }

   public boolean isempty () {
     boolean empty = false;
     if(first == null){
        empty = true;}
     return empty;
   }

   public String getitem () {
      if(isempty()){
        throw new NoSuchElementException();
        }
      else{
         return current.item;}
   }

   public int getposition () {
      if(isempty()){
         throw new NoSuchElementException();}
      else{   
         return currentposition;}}

   public void delete () {
      if(isempty()){
         throw new NoSuchElementException();}
      else{
         node temp = current.prev;
         temp.next = current.next;
         if(temp != null){
           current = current.next;
           current.prev = temp;}
         else{
         }
        }
   }

   public void insert (String item, position pos) {
      node temp = new node();
      temp.item = item;
        if(isempty()){
          first = temp;
          last = temp;}
        else if(pos == position.PREVIOUS){
          current.prev = temp;         
        }
        else if(pos == position.FOLLOWING){
          current.next = temp;
        }
        else if (pos == position.LAST){
          last = temp;
        }
        current = temp;
        currentposition++;
}
}


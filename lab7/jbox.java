// $Id: jbox.java,v 1.6 2014-02-13 18:33:45-08 - - $

// Java equivalent of the cbox example.
// For brevity we put all of the classes on one file.

import static java.lang.System.*;

interface jbox {
   public int get();
   public void put (int value);
}

class jbox_code implements jbox {
   private int value;

   jbox_code() {
      this (0);
   }

   jbox_code (int value_) {
      value = value_;
   }

   public int get() {
      return value;
   }

   public void put (int value_) {
      value = value_;
   }

}

class jbox_main {
   public static void main (String[] args) {
      jbox box = new jbox_code();
      out.printf ("box = %s%n", box);
      box.put (1024);
      out.printf ("box value is %d\n", box.get());
   }
}


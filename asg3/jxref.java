// $Id: jxref.java,v 1.70 2014-05-13 00:57:29-07 - - $
// Ryan Brounley -- rbrounle

import java.io.*;
import java.util.Scanner;
import static java.lang.System.*;

class jxref{
   static final String STDIN_NAME = "-";
   static boolean caseChange = false;
   static boolean treeDump = false;

   static class printer implements visitor <String, queue <Integer>> {
      public void visit (String key, queue <Integer> value) {
         out.printf ("%s", key);
         for (int linenr: value) out.printf (" %d", linenr);
         out.printf ("%n");
      }
   }

   static void xref_file (String filename, Scanner scan) {
      treemap <String, queue <Integer>> map =
            new treemap <String, queue <Integer>> ();
      for (int linenr = 1; scan.hasNextLine (); ++linenr) {
         for (String word: scan.nextLine().split ("\\W+")) {
            if (word.matches ("^\\d*$")) continue;
            if (caseChange == true){
                word = word.toLowerCase();
            }
            queue <Integer> tqueue;
            tqueue = map.get(word);
            if (tqueue == null){
                 queue <Integer> newqueue = new queue<Integer>();
                 newqueue.insert(linenr);
                 map.put(word, newqueue);
           }else {
                 tqueue.insert(linenr);
          }
        }
      }
      if (treeDump == true){
          map.debug_dump();
      }else {
      visitor <String, queue <Integer>> print_fn = new printer ();
      map.do_visit (print_fn);
   }
}
   public static void main (String[] args) {
      if (args.length == 0) {
         xref_file (STDIN_NAME, new Scanner (in));
      }else {
         for (int argi = 0; argi < args.length; ++argi) {
            String filename = args[argi];
            if (filename.startsWith(STDIN_NAME) == false){
            for( int i = 0; i < 65; ++i) out.printf("%s",":");
            out.printf("%n%s%n", filename);
            for(int i = 0 ; i < 65; ++i) out.printf("%s", ":");
            out.printf("%n");}
            if (filename.startsWith(STDIN_NAME)) {
               char[] optionsarray = filename.toCharArray();
               if(optionsarray[1] == 'd' || optionsarray[1] == 'f'){
                    if(optionsarray.length >= 3 && (optionsarray[2] =='f' ||
                    optionsarray[2] == 'd')){
                    treeDump = true;
                    caseChange = true;
                    }
                    else if (optionsarray.length >= 3 && (optionsarray[2] != 'f' ||
                    optionsarray[2] != 'd')){
                    out.printf("Invalid Entry!");}
                    else if (optionsarray.length == 2){
                         if(optionsarray[1] == 'd'){
                             treeDump = true;}
                         else if (optionsarray [1] == 'f'){
                             caseChange = true;}}
               }else{
                    out.printf("Invalid Entry!\n");
                    xref_file(STDIN_NAME, new Scanner (in));}
            }else {
               try {
                  Scanner scan = new Scanner (new File (filename));
                  xref_file (filename, scan);
                  scan.close ();
               }catch (IOException error) {
                  auxlib.warn (error.getMessage ());
               }
            }
         }
      }
      auxlib.exit ();
   }
}


// $Id: jgrep.java,v 1.2 2014-04-02 21:30:12-07 - - $
// Ryan Brounley - rbrounle@ucsc.edu

//
// This program is a stub showing how to create a pattern from a
// regular expression.  It does not handle options or files, and
// has some other bugs which you must discover and fix.
//

import java.io.*;
import java.util.Scanner;
import java.util.regex.*;
import static java.lang.System.*;

class jgrep {

   static void scanfile (Scanner input, String filename,
   Pattern pattern, options opts) {
      int linenum = 0;
      while (input.hasNextLine()) {
         linenum++;
         String line = input.nextLine();
         boolean matches =  pattern.matcher (line).find();
         //if (opts.reverse_match == true){
           // matches = !matches;}
         if (matches == true && opts.reverse_match == false) { 
            if (opts.number_lines  && opts.filename_only == false){
               out.printf("%s: %s\n", linenum, line);
            } 
            if(opts.filename_only == false &&
            opts.number_lines == false){
               out.printf("%s\n",line);
            }
            if(opts.filename_only && opts.number_lines){
               out.printf("%s\n", filename);
               break;
            }
            if (opts.filename_only){
               out.printf("%s \n", filename);
               break;
               }
           }

          if (matches == false && opts.reverse_match == true) { 
              if (opts.number_lines && opts.filename_only == false){
                out.printf("%s: %s\n", linenum, line);
            } 
            if(opts.filename_only == false &&
            opts.number_lines == false){
               out.printf("%s\n",line);
            }
            if(opts.filename_only && opts.number_lines){
               out.printf("%s\n", filename);
               break;
            }
            if (opts.filename_only){
               out.printf("%s \n", filename);
               break;
               }
           matches = !matches;
           }
         }
      }
   public static void main (String[] args) {
      options opts = new options (args);
      try {
          //Pattern pattern = Pattern.compile (opts.regex);
          Pattern pattern = null;
          if(opts.insensitive){
             pattern = Pattern.compile (opts.regex,
            Pattern.CASE_INSENSITIVE);
            }
          else{
             pattern = pattern.compile (opts.regex);
          }
          if (opts.filenames.length == 0) {
            scanfile (new Scanner (in), "<stdin>", pattern, opts);
         }else {
            for (int argi = 1; argi < opts.filenames.length; ++argi) {
               try {
                  String filename = opts.filenames[argi];
                  Scanner input = new Scanner (new File (filename));
                  scanfile (input, filename, pattern, opts);
                  input.close();
               }catch (IOException error) {
                  messages.warnsyntax(error.getMessage());
               }
            }
         }
      }catch (PatternSyntaxException error) {
         messages.die(error.getMessage());
      }
      exit (messages.exit_status);
   }
}


// $Id: options.java,v 1.2 2014-04-02 21:30:12-07 - - $
//
// Ryan Brounley - rbrounle@ucsc.edu

import static java.lang.System.*;
import java.io.*;
import java.lang.Throwable;
import java.lang.Error;

class options {
  static boolean insensitive = false;
  static boolean filename_only = false;
  static boolean number_lines = false;
  static boolean reverse_match = false;
  String regex;
  String[] filenames;
  
  options(String[] args) {
        if (args.length == 0){
          err.printf("Usage: %s [-ilnv] regex [filename...]%n",
           messages.program_name);
          exit(messages.EXIT_FAILURE);
         }
         regex = args[1];
         char[] optsarray = args[0].toCharArray();
         if (args[0].startsWith("-")){
            for (int i = 1; i < optsarray.length; i++){
                 if (args == null) break;
                 switch (optsarray[i]){
                   case 'i': insensitive = true;
                   case 'l': filename_only = true;
                   case 'n': number_lines = true;
                   case 'v': reverse_match = true;
                   case ' ' : break; 
                   default: break;
                   }
        filenames = new String[args.length - 1];
        for (int argi = 1; argi < args.length; ++argi){
           filenames[argi - 1] = args[argi];
        }
       
       } 
    }
       else{
          regex = args[0];
          filenames = new String[args.length];
          for (int argi = 0; argi < args.length; ++argi){
               filenames[argi] = args[argi];
          }} 
}
}



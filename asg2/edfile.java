// $Id: edfile.java,v 1.198 2014-04-29 08:54:07-07 - - $

import java.util.Scanner;
import static java.lang.System.*;
import java.util.NoSuchElementException;
import java.io.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;

class edfile{

   public static void main (String[] args) {
      boolean want_echo = false;
      dllist lines = new dllist ();
      //lines.position pos = new dllist.position.LAST();
      String line = null;
      Scanner stdin = new Scanner (in);
      scanfile(line,lines,args); 
      for (;;) {
         out.printf ("%s: ", auxlib.program_name());
         if (! stdin.hasNextLine()) break;
         String inputline = stdin.nextLine();
         if (want_echo) out.printf ("%s   %s\n", lines.getposition(),
         inputline); want_echo = false;
         if (inputline.matches ("^\\s*$")) continue;
         char command = inputline.charAt(0);
         switch (command) {
            case '-': want_echo = true; break;
            case '#': break;
            case '$': curr_to_last(lines); break;
            case '*': print_all_lines(lines); break;
            case '.': print_current_line(lines); break;
            case '0': curr_to_first(lines); break;
            case '<': curr_to_prev(lines); break;
            case '>': curr_to_follow(lines); break;
            case 'a': insert_after(lines,inputline); break;
            case 'd': lines.delete(); break;
            case 'i': insert_before(lines,inputline); break;
            case 'r': readfile(lines,inputline); break;
            case 'w': writefile (lines,inputline); break;
            default : out.printf("Invalid Command.");  break;
         }
      }
   }

static void curr_to_last(dllist lines){
   dllist.position pos = dllist.position.LAST;
   lines.setposition(pos);
   out.printf("        %s   %s\n", lines.getposition(),
   lines.getitem());
}

static void print_all_lines(dllist lines){
   dllist.position pos = dllist.position.LAST;
   lines.setposition(pos);
   for(int i = 0; i < lines.getposition() ; i++) {
      out.printf("     %s   %s\n", i, lines.getitem());}
}

static void print_current_line(dllist lines){
   out.printf("        %s   %s\n", lines.getposition(),
   lines.getitem());
}

static void curr_to_first (dllist lines){
   dllist.position pos = dllist.position.FIRST;
   lines.setposition(pos);
   out.printf("        %s   %s\n", lines.getposition(),
   lines.getitem());
}

static void curr_to_prev (dllist lines){
   dllist.position pos = dllist.position.PREVIOUS;
   lines.setposition(pos);
   out.printf("        %s   %s\n", lines.getposition(),
   lines.getitem());
}

static void curr_to_follow (dllist lines){
   dllist.position pos = dllist.position.FOLLOWING;
   lines.setposition(pos);
   out.printf("        %s   %s\n", lines.getposition(),
   lines.getitem());
}

static void insert_before (dllist lines,String inputline){
   lines.insert(inputline, dllist.position.PREVIOUS);
}

static void insert_after (dllist lines,String inputline){
   lines.insert(inputline, dllist.position.FOLLOWING);
}

static void readfile(dllist lines,String inputline){
            int linenum = 0;
            File inFile = new File(inputline);
            try{
            curr_to_last(lines);
            StringBuilder sb = new StringBuilder();
            FileInputStream file = new FileInputStream(inFile);
            BufferedReader f = new BufferedReader 
            (new InputStreamReader(file));
            try{
               while((inputline = f.readLine()) != null){
               sb.append(inputline);
               lines.insert(inputline, dllist.position.FOLLOWING); 
               linenum++;}
            out.printf("%s lines read.\n", linenum);
            }catch (IOException error){
               auxlib.warn(error.getMessage());}
            }catch (FileNotFoundException error){
               auxlib.warn(error.getMessage());}
}

static void writefile(dllist lines, String inputline){
           try{
              File file = new File(inputline);
              if (!file.exists()) {
                 file.createNewFile();
              }
              FileWriter write = new FileWriter
              (file.getAbsoluteFile());
              BufferedWriter writer = new BufferedWriter(write);
              for(int i = 0; ; i++){
              writer.write(lines.getitem());
              writer.close();}
             }catch (IOException error){
                 auxlib.warn(error.getMessage());}
}

public static void scanfile(String line, dllist lines,  String[] args){
            if(args.length != 0){
            int linenum = 0;
            File inFile = new File(args[0]);
            try{
            StringBuilder sb = new StringBuilder();
            FileInputStream file = new FileInputStream(inFile);
            BufferedReader f = new BufferedReader(new
            InputStreamReader(file));
            try{
               while((line = f.readLine()) != null){
               sb.append(line);
               lines.insert(line, dllist.position.LAST); 
               linenum++;}
            out.printf("%s lines read.\n", linenum);
            }catch (IOException error){
               auxlib.warn(error.getMessage());}
            }catch (FileNotFoundException error){
               auxlib.warn(error.getMessage());}
            }
}


}


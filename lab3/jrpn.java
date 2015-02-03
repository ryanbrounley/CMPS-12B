// $Id: jrpn.java,v 1.22 2013-10-11 19:19:01-07 - - $

import java.util.Scanner;
import static java.lang.System.*;

class jrpn {
   static int exit_status = 0;
   static final int EMPTY = -1;
   static final int SIZE = 16;
   static class stack_t {
      int top = EMPTY;
      double[] numbers = new double[SIZE];
   }

   static void error (String format, Object... args) {
      out.flush();
      err.printf (format, args);
      err.flush();
      exit_status = 1;
   }

   static void bad_operator (String oper) {
      error ("\"%s\": invalid operator%n", oper);
   }

   static void push (stack_t stack, double number) {
      if (stack.top >= SIZE - 1) {
         out.printf ("%s: stack overflow%n", number);
      }else {
         stack.numbers[++stack.top] = number;
      }
   }

   static void do_binop (stack_t stack, char oper) {
      if (stack.top < 1) {
         out.printf ("'%s': stack underflow", oper);
      }else {
         double right = stack.numbers[stack.top--];
         double left = stack.numbers[stack.top--];
         switch (oper) {
            case '+': push (stack, left + right); break;
            case '-': push (stack, left - right); break;
            case '*': push (stack, left * right); break;
            case '/': push (stack, left / right); break;
         }
      }
   }


   static void do_print (stack_t stack) {
      if (stack.top == EMPTY) {
         out.printf ("stack is empty%n");
      }else {
         for (int pos = 0; pos <= stack.top; ++pos) {
            out.printf ("%s%n", stack.numbers[pos]);
         }
      }
   }

   static void do_clear (stack_t stack) {
      stack.top = EMPTY;
   }

   static void do_operator (stack_t stack, String oper) {
      switch (oper.charAt(0)) {
         case '+': do_binop (stack, '+'); break;
         case '-': do_binop (stack, '-'); break;
         case '*': do_binop (stack, '*'); break;
         case '/': do_binop (stack, '/'); break;
         case ';': do_print (stack);      break;
         case '@': do_clear (stack);      break;
         default : bad_operator (oper);   break;
      }
   }

   static String argv_0() {
      String jarname = getProperty ("java.class.path");
      if (jarname.equals (".")) jarname = "jrpn";
      return jarname.substring (jarname.lastIndexOf ("/") + 1);
   }


   public static void main (String[] args) {
      if (args.length != 0) {
         err.printf ("Usage: %s%n", argv_0());
         exit (1);
      }
      Scanner stdin = new Scanner (in);
      stack_t stack = new stack_t();
      while (stdin.hasNext()) {
         String token = stdin.next();
         if (token.startsWith("#")) {
            stdin.nextLine();
            continue;
         }
         try {
            double number = Double.parseDouble (token);
            push (stack, number);
         }catch (NumberFormatException error) {
            if (token.length() != 1) {
               bad_operator (token);
            }else {
               do_operator (stack, token);
            }
         }
      }
      exit (exit_status);
   }
}

// $Id: matching.java,v 1.2 2014-03-24 20:51:41-07 - - $

//
// Synopsis: matching [-i] pattern
// Matches CASE_INSENSITIVE if -i is specified, otherwise not.
// Only reads from stdin.
// BUGS: Doesn't do proper error checking.
//

import java.util.Scanner;
import java.util.regex.Pattern;
import static java.lang.System.*;
import static java.util.regex.Pattern.*;

class matching {
   static void usage() {
      err.printf ("Usage: matching [-i] pattern%n");
      exit (1);
   }
   public static void main (String[] args) {
      out.printf ("matching");
      for (String arg: args) out.printf (" %s", arg);
      out.printf ("%n");
      if (args.length < 1) usage();
      int insensitive = 0;
      int pattpos = 0;
      if (args[0].startsWith ("-")) {
         if (args.length != 2) usage();
         if (! args[0].equals ("-i")) usage();
         insensitive = CASE_INSENSITIVE + UNICODE_CASE;
         pattpos = 1;
      }
      Pattern pattern = Pattern.compile (args[pattpos], insensitive);
      Scanner stdin = new Scanner (System.in);
      while (stdin.hasNextLine()) {
         String line = stdin.nextLine();
         if (pattern.matcher(line).find()) out.println (line);
      }
   }
}

//TEST// ( echo line of lower case data \
//TEST// ; echo LINE OF UPPER CASE DATA \
//TEST// ) >test.data
//TEST// ./matching -i '[a-z]' <test.data >output.insens
//TEST// ./matching '[a-z]' <test.data >output.sens
//TEST// catnv test.data output.insens output.sens >more.output
//TEST// rm test.data output.insens output.sens
//TEST// mkpspdf Listing.ps matching.java more.output


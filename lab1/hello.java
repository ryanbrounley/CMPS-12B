// $Id: hello.java,v 1.1 2014-04-03 13:20:32-07 - - $
//
// Name: Ryan Brounley
// Username: rbrounle
// 
// NAME
//    hello - a Java version of the classical "Hello World" program.
//
// DESCRIPTION
//    Introduces itself and its environment.
//

import java.util.ArrayList;
import java.util.List;
import static java.lang.System.*;
class hello {
   static String[] properties = {
      "java.class.path",
      "sun.arch.data.model",
      "sun.cpu.endian",
      "os.arch",
      "os.name",
      "os.version",
      "java.runtime.name",
      "java.runtime.version",
      "java.vm.name",
      "java.vm.version",
      "java.home",
      "java.version",
   };
   public static void main (String[] args) {
      out.printf ("Hello, Java World!%n");
      for (String property: properties) {
         String property_value = getProperty (property);
         out.printf ("%s = %s%n", property, property_value);
      }
      long free_memory = Runtime.getRuntime().freeMemory();
      out.printf ("free memory = %d bytes = %.4f megabytes%n",
                  free_memory, free_memory / (double)(1<<20));
      long now = currentTimeMillis();
      out.printf ("current time = %.3f sec = %tc%n",
                  now / 1e3, now);
   }
}

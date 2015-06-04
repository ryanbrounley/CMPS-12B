// $Id: treemap.java,v 1.46 2014-05-13 00:31:56-07 - - $
// Ryan Brounley -- rbrounle

import static java.lang.System.*;
import java.util.*;
import java.util.Scanner;

class treemap <key_t extends Comparable <key_t>, value_t> {

   private class node {
      key_t key;
      value_t value;
      node left;
      node right;
   }
   private node root = new node();

   private void debug_dump_rec (node tree, int depth) {
          if (tree == null){ return;}
          if (tree.left != null)   debug_dump_rec(tree.left, depth++);
              for (int i = 0; i < depth; i++){ 
                  out.printf("  "); }
                  String queuestring = tree.value.toString(); 
              out.printf("%d %s %s\n", depth, tree.key, queuestring);
          if(tree.right != null) debug_dump_rec(tree.right, depth++); 
   }      

   private void do_visit_rec (visitor <key_t, value_t> visit_fn,
                              node tree) {
          if (tree == null){ return;}
          if (tree != null){
              do_visit_rec(visit_fn, tree.left);
              visit_fn.visit(tree.key, tree.value);
              do_visit_rec(visit_fn, tree.right);
   }
   }

   public value_t get (key_t key) {
        if(root.key == null) return null;
        node curr = root;
        while(curr != null){
           int tmp = curr.key.compareTo(key);
           if (tmp == 0) return curr.value;
           if (tmp < 0) curr = curr.right;
              else      curr = curr.left;
      }
      return null;   
      }

   public value_t put (key_t key, value_t value){
         node curr = root;
         node prev = null;
         node child = new node();
         if (root.key == null){
              root.key = key;
              root.value = value;
              return root.value;}
         while(curr != null){
           int cmp = key.compareTo(curr.key);
           if (cmp == 0){
              value_t temp = curr.value;
              curr.value = value;
              return temp;}   
           else if (cmp < 0){
              prev = curr;
              curr = curr.left;
              }
           else if (cmp > 0){
              prev = curr;
              curr = curr.right;}
      }
      curr = prev;
      int cmp = key.compareTo(curr.key);
      child.key = key;
      child.value = value;
      if (cmp < 0){
          curr.left = child;
          return child.value;
      }
      else {
          curr.right = child;
          return child.value;}

}

   public void debug_dump () {
      debug_dump_rec (root, 0);
   }

   public void do_visit (visitor <key_t, value_t> visit_fn) {
      do_visit_rec (visit_fn, root);
   }
}

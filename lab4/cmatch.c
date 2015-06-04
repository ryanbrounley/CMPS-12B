// Ryan Brounley -- rbrounle@ucsc.edu

#define _GNU_SOURCE
#include <errno.h>
#include <libgen.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <unistd.h>

#define STDIN_NAME "-"
char *program_name = NULL;
const char *needle = NULL;
const char *haystack = NULL;
int exit_status = EXIT_SUCCESS;

typedef struct options {
   bool ignore_case;
   bool filenames_only;
   bool number_lines;
} options;

void scan_options (int argc, char **argv, options *opts) {
   opts->ignore_case = false;
   opts->filenames_only = false;
   opts->number_lines = false;
   opterr = false;
   for (;;) {
      int opt = getopt (argc, argv, "iln");
      if (opt == EOF) break;
      switch (opt) {
         case 'i':
            opts->ignore_case = true;
            break;
         case 'l':
            opts->filenames_only = true;
            break;
         case 'n':
            opts->number_lines = true;
            break;
         default:
            exit_status = EXIT_FAILURE;
            fflush (NULL);
            fprintf (stderr, "%s: -%c: invalid option\n",
                             program_name, optopt);
            break;
      }
   }
}

const char *strbool (bool value) {
   return value ? "true" : "false";
}

void catbyline (FILE *input, char *filename, options opts,
const char *needle) {
   char buffer [1024];
   for (int linenr = 1;; ++linenr) {
      char *buf = fgets (buffer, sizeof buffer, input);
      haystack = buf;
      if (buf == NULL) break;
      char *ret = NULL;
      if (opts.ignore_case){ 
          ret = strcasestr(haystack, needle);}
      else{
          ret = strstr(haystack, needle);}
      if (ret != NULL){
         if(opts.filenames_only && opts.number_lines){
            printf("%s\n", filename); break; }
         else if(opts.filenames_only){
            printf("%s\n", filename);break;}
         else if(opts.number_lines){
            printf("%d: %s", linenr, buf);}
         else if(opts.ignore_case){
            printf("%s", buf);}
   }
}
}
int main (int argc, char **argv) {
   options opts;
   program_name = basename (argv[0]);
   scan_options (argc, argv, &opts); 
   needle = argv[optind];
   if (argc == 1) {
      catbyline (stdin, STDIN_NAME, opts, needle);
   }else {
      for (int argi = (optind + 1); argi < argc; ++argi) {
         char *filename = argv[argi];
         if (strcmp (filename, STDIN_NAME) == 0) {
            catbyline (stdin, STDIN_NAME, opts, needle);
         }else {
            FILE *input = fopen (filename, "r");
            if (input != NULL) {
               catbyline (input, filename, opts, needle);
               fclose (input);
            }else {
               exit_status = EXIT_FAILURE;
               fflush (NULL);
               fprintf (stderr, "%s: %s: %s\n", program_name,
                        filename, strerror (errno));
               fflush (NULL);
            }
         }
      }
   }
   return exit_status;
}


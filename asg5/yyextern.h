// $Id: yyextern.h,v 1.1 2014-06-06 22:10:43-07 - - $

#ifndef __YYEXTERN_H__
#define __YYEXTERN_H__

//
// DESCRIPTION
//    Definitions of external names used by flex-generated code.
//

#include <stdio.h>

extern FILE *yyin;        // File currently being read

extern char *yytext;      // Pointer to the string that was found

extern int yy_flex_debug; // yylex's verbose tracing flag

extern int yylex (void);  // Read next word from opened file yyin

extern int yylineno;      // Line number within the current file

extern int yylex_destroy (void);
                          // Cleans up flex's buffers when done
                          // Avoids valgrind memory leak report.

#endif


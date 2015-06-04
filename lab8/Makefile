# $Id: Makefile,v 1.8 2014-05-14 18:07:06-07 - - $

MKFILE    = Makefile
DEPSFILE  = ${MKFILE}.deps
NOINCLUDE = ci clean spotless
NEEDINCL  = ${filter ${NOINCLUDE}, ${MAKECMDGOALS}}
GMAKE     = gmake --no-print-directory

GCC       = gcc -g -O0 -Wall -Wextra -std=gnu99 
MKDEPS    = gcc -MM

CSOURCE   = numsort.c linesort.c inssort.c
CHEADER   = inssort.h
OBJECTS   = ${CSOURCE:.c=.o}
EXECBIN   = numsort linesort
SOURCES   = ${CHEADER} ${CSOURCE} ${MKFILE}
LISTING   = Listing.ps
SUBMIT    = submit cmps012b-wm.w14 lab8

all : numsort linesort

numsort: numsort.o inssort.o
	${GCC} numsort.o inssort.o -o numsort

linesort: linesort.o inssort.o
	${GCC} linesort.o inssort.o -o linesort

%.o : %.c
	${GCC}$< -c $<

ci : ${SOURCES}
	cid + ${SOURCES}
	checksource ${SOURCES}

lis : ${SOURCES} ${DEPSFILE}
	mkpspdf ${LISTING} ${SOURCES} ${DEPSFILE}

clean :
	- rm ${OBJECTS} ${DEPSFILE} core ${EXECBIN}.errs

spotless : clean
	- rm ${EXECBIN} ${LISTING} ${LISTING:.ps=.pdf}

deps : ${CSOURCE} ${CHEADER}
	@ echo "# ${DEPSFILE} created `date`" >${DEPSFILE}
	${MKDEPS} ${CSOURCE} >>${DEPSFILE}

${DEPSFILE} :
	@ touch ${DEPSFILE}
	${GMAKE} deps

again :
	${GMAKE} spotless deps ci all lis

submit: ${SOURCES}
	- checksource ${SOURCES}
	${SUBMIT} ${SOURCES}

ifeq "${NEEDINCL}" ""
include ${DEPSFILE}
endif


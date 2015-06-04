# $Id: Makefile,v 1.13 2014-05-02 19:14:19-07 - - $

GCC      = gcc -g -O0 -Wall -Wextra -std=gnu99
CSOURCES = nsort.c lsort.c
SOURCES  = ${CSOURCES} Makefile
EXECBINS = nsort   lsort
LISTING  = Listing.ps
SUBMIT   = submit cmps012b-wm.s14 lab6

all : ${EXECBINS}

% : %.c
	${GCC} $< -o $@

ci :
	cid + ${SOURCES}
	- checksource $<

clean :
	- rm -vf core

spotless : clean
	- rm -vf ${EXECBINS}

lis : ${CSOURCES} Makefile
	mkpspdf ${LISTING} ${SOURCES}

submit : ${SOURCES}
	${SUBMIT} ${SOURCES}

again : ${SOURCES}
	gmake --no-print-directory spotless ci all lis


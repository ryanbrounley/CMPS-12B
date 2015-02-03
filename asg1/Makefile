# $Id: Makefile,v 1.1 2014-03-24 18:45:16-07 - - $

JAVASRC    = jgrep.java messages.java options.java
ALLSOURCE  = ${JAVASRC} Makefile README
MAINCLASS  = jgrep
CLASSES    = ${JAVASRC:.java=.class}
JARCLASSES = ${CLASSES}
JARFILE    = jgrep
LISTING    = Listing.ps
SUBMITDIR  = cmps012b-wm.s14 asg1

all : ${JARFILE}
	- checksource ${ALLSOURCE}

${JARFILE} : ${CLASSES}
	echo Main-class: ${MAINCLASS} >Manifest
	jar cvfm ${JARFILE} Manifest ${JARCLASSES}
	- rm Manifest
	chmod +x ${JARFILE}

%.class : %.java
	javac $<

clean :
	- rm ${JARCLASSES}

spotless : clean
	- rm ${JARFILE}

ci : ${ALLSOURCE}
	cid + ${ALLSOURCE}
	- checksource ${ALLSOURCE}

lis : ${ALLSOURCE}
	mkpspdf ${LISTING} ${ALLSOURCE}

submit : ${ALLSOURCE}
	submit ${SUBMITDIR} ${ALLSOURCE}

again : ${ALLSOURCE}
	make spotless ci all lis


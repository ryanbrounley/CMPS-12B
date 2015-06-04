#!/usr/bin/perl
# $Id: l9stat.perl,v 1.1 2014-05-19 16:10:07-07 - - $
use POSIX qw(strftime);
$0 =~ s|.*/||;
$days180 = 180 * 24 * 3600;
for $file (@ARGV ? @ARGV : ".") {
   ($_, $_, $mode, $_, $_, $_, $_, $size, $_, $mtime, $_, $_, $_)
         = lstat $file;
   print STDERR "$0: $file: $!\n" and next unless defined $size;
   $fmt = $mtime < $^T - $days180 || $^T + $days180 < $mtime
        ? "%b %e  %Y" : "%b %e %R";
   printf "%06o %9d %s %s%s\n",
          $mode, $size, (strftime $fmt, localtime $mtime), $file,
          defined ($link = readlink $file) ? " -> $link" : "";
}

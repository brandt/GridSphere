#!/usr/bin/env perl

# $Id$

####################################################
#
# This is a perl script to install GridSphere
#
# To build invoke: build.pl <options>
# See build.pl help for more information
#
#################################################### 

#set $debug to 1 to turn on trace info
$debug=0;

if( $ENV{"JAVA_HOME"} eq "" ) {
  print "JAVA_HOME is not set.  Exiting.\n"; exit;
} else {
  $JAVA_HOME = $ENV{JAVA_HOME};
}

if( $ENV{"ANT_HOME"} ne "" ) {
  $anthome = $ENV{ANT_HOME};
}

if( $ENV{"TOMCAT_HOME"} eq "" ) {
  print "TOMCAT_HOME is not set.  Exiting.\n"; exit;
} else {
  $TOMCAT_HOME = $ENV{TOMCAT_HOME};
  if ( $anthome eq "" ) {
    $anthome = $TOMCAT_HOME;
  }
}

if( $#ARGV > 1 || $#ARGV < 0 ) {
  &usage();
  exit;
}

# $s = CLASSPATH is delimited by ":" on Unix and ";" on Windows
# $p = path type is "/" on Unix and "\" on Windows
$s=":";
$p="/";
if( ($^O eq "MSWin32") || ($^O eq "dos") ) {
  $s=";"; 
  $p="\\";
} 

$jarpattern=$anthome . "$p" . lib . "$p" . "*.jar";
@jarfiles =glob($jarpattern);
foreach $jar (@jarfiles ) {
    $CP.="$s$jar";
}

$jarpattern=$TOMCAT_HOME . "$p" . lib . "$p" . "*.jar";
@jarfiles =glob($jarpattern);
foreach $jar (@jarfiles ) {
    $CP.="$s$jar";
}

# The jars in Tomcat 4.0 are also in common/lib
$jarpattern=$TOMCAT_HOME . "$p" . common . "$p" . lib . "$p" . "*.jar";
@jarfiles =glob($jarpattern);
foreach $jar (@jarfiles ) {
    $CP.="$s$jar";
}

# The jars in Tomcat 3.3 are also in lib/{common, container}
$jarpattern=$TOMCAT_HOME . "$p" . lib . "$p" . container . "$p" . "*.jar";
@jarfiles =glob($jarpattern);
foreach $jar (@jarfiles ) {
    $CP.="$s$jar";
}

$jarpattern=$TOMCAT_HOME . "$p" . lib . "$p" . common . "$p" . "*.jar";
@jarfiles =glob($jarpattern);
foreach $jar (@jarfiles ) {
    $CP.="$s$jar";
}

if( $ENV{"CLASSPATH"} ne "" ) {
  $CP = $ENV{"CLASSPATH"} . "$s" . $CP;
}

$JAVA = $JAVA_HOME . "$p" . "bin" . "$p" . "java";
print $JAVA;
print "\n\nThe classpath is:\n";
print $CP;
print "\n\n";


my @ARGS;
push @ARGS, "-classpath", "$CP";
push @ARGS, "org.apache.tools.ant.Main";
push @ARGS, "-Dant.home=$anthome";
push @ARGS, "-Dtomcat.home=$TOMCAT_HOME";
push @ARGS, "-Djavadir=$JAVA_HOME";
push @ARGS, "-verbose" if ($debug);
push @ARGS, "$ARGV[0]";
system $JAVA, @ARGS;
print "\n $JAVA @ARGS\n\n" if ($debug);

sub usage {
print "\n";
print "Usage: build.pl \n";
print "\t[ help ]\t\t# Print this message and exit\n";
print "\t[ update ]\t\t# Update project from CVS\n";
print "\t[ all ]\t\t\t# Compile GridSphere performing the following:\n";
print "\t\t[ clean ]\t# Delete existing jar file\n";
print "\t\t[ compile ]\t# Compile src java files\n";
print "\t\t[ docs ]\t# Make javadoc output of classes\n";
print "\t\t[ dist ]\t# Make gridsphere.jar library and\n";
print "\t\t\t\t# copy to $TOMCAT_HOME/lib\n";
print "\n";
print "\t[ <target> ]\t\t# Build target specified in build.xml\n";
print "\n";
}

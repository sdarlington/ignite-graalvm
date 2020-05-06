# Apache Ignite JavaScript compute jobs with GraalVM

This is a simple demo that distributes a simple JavaScript block of
code to an Apache Ignite compute grid using the GraalVM Java Virtual
Machine.

JavaScript works "out of the box" but any GraalVM supported
language -- such as Python -- could be used with a little more effort.

## How to run

It's really designed to run inside an IDE currently. Run the
`Server` class and then `App`. The former needs to be run using
the GraalVM, the app can be run with any supported JVM (I'm using
OpenJDK 8).
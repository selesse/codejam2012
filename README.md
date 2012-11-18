McGill Code Jam 2012
====================

Code Jam 2012 repository. It's the same problem as last year (and the year
before). Look out!

Assumptions:

The behaviour of Managers assumes that sell transactions can take place before
buy transaction (i.e. the first transaction of the day can be a sell).

We assume that we only need to send the last 100 transactions to Silanis for
reporting.

Configurations:

To set the number of price points sent to the client modify the value of
dataRange in CodeJam2012/src/com/rathesh/codejam2012/server/DataDump.java. The
default this value is 100.

To set whether the entire range of price points is sent to the client upon
closing time set FULL_DATA_ON_FINISHED to true in
CodeJam2012/src/com/rathesh/codejam2012/server/DataDump.java.

To set the number of price points stored on server side modify the value of
WINDOW_SIZE in CodeJam2012/src/com/rathesh/codejam2012/server/MSETServlet.java.
The default is 34200 which is everything.


McGill Code Jam 2012
====================

Team: Rathesh.com
Names: Alex Bourgeois, Rathesh Sabapathipillai, Alex Selesse

Steps to launch application:

 1 Open Eclipse and run the CodeJam2012 project (Right-click -> Run As -> 
   Web Application). You must also run the RelayServer project (Right-click -> 
   Run As -> Java Application).


 2 Open a terminal (Ctrl + Alt + T). 

 3 Navigate to the Desktop ("cd Desktop") and run the script 
   start_team rathesh.sh.  ("./start_team_rathesh").

 4 Make sure a browser opens (if it does not open automatically manually open a
   browser and go to the following url: 
   http://127.0.0.1:8888/CodeJam2012.html?gwt.codesvr=127.0.0.1:9997).

 5 A page should appear with the manager schedule. Click on the Go button at the
   bottom of the page to start price feed.

 6 Once the Go button is pressed you can press the Reports button after the
   trading session to send the transaction report to Silanis.

Notes:

The reason why we have created the RelayServer is because a simple jQuery
request to Silanis gave us a cross-origin resource sharing (CORS) issue.

We couldn't use curl on our server side due to app restriction settings on
Google App Engine (we messed up packaging, hence the whole Eclipse)...

We couldn't send the request to Silanas and get the response purely through
JavaScript, so we tried to figure out another way to do it.

A proxy server (RelayServer) was thus implemented to accept the jQuery request
from our application and send the request to Silanis using the curl system
command. We were able to actually get the data from the client, get the
response from Silanis, but failed at giving the ceremony ID to the client.

You can see this if you just run the server and it printlns the ceremony id,
but there's a JSON error.


Assumptions:

The behaviour of managers assumes that sell transactions can take place before
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

Appendix:

To obtain the json containing price points (including strategies prices) you can
navigate to the following url (make sure the price feed is active i.e. Go was
pressed):
    http://127.0.0.1:8888/codejam2012/mset?data

Similarly the json containing all the report information (transactions) can be
obtained by navigating to the following url:
    http://127.0.01:8888/codejam2012/mset?report

JUnit tests were created for testing the different moving average algorithms and
manager transactions. To run these JUnit tests go to eclipse. Under the project
CodeJam2012 in test and run the desired JUnit test case (Right-click -> Run As
-> JUnit Test).

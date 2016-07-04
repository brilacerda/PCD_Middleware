# PCD_Middleware

Read Session: The basics of JMS 
http://www.embedded.com/design/connectivity/4006689/Building-a-effective-real-time-distributed-publish-subscribe-framework-Part-1

Check  http://activemq.apache.org/how-should-i-implement-request-response-with-jms.html

SYNCHRONY:

Message can be delivered asynchronously by registering a MessageListener; the onMessage() method will be called when a message arrives. Alternatively, messages can also be received synchronously by calling receive*() methods, the desired timeout (zero, finite, infinite) can be chosen by the user.

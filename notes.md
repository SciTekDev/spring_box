notes.md

This folder is going to be a sandbox project for a java 21 spring boot application group using mvn 3.9 

i want to setup 3 different api's in 3 different java applications, that register to consul as a test example



1) a java application simulating a devices status api, for getting and tracking the status of remote equipment, running on embedded devices, linux, or windows servers (device-status)

2) a java appliaction  simulating a data ware house and storage api, for tracking device id's types, and other data sets as needed. (data-store)

3) a java api for contoling devices and sending different requests to them.  (device-control)


all applications will connect to an elk stack with log4j for application logs.


the goals is to start with basic apps that log their startup, execution, and exception handling


for device conttrols we are thinking about basic on off, LED control, as well as some ROS2 style movement controls.



there should be a 4th project setup with JUNIT tests as an integration testing project 
(device-tests)
this way we can call the apis
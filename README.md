# Optimization
The Room Scheduling Problem:

Creating a schedule that assigns courses to classrooms based on some criteria. The goal of the algorithm is to find the best possible schedule as quickly as possible using a variety of search techniques.

More specifically, we have a set of N rooms, a set of M courses that need to be scheduled, and a set of L buildings. 

Each building has an associated location, given by (x,y) coordinates. 

Each room has the following properties:
A building
A maximum capacity

Each course has the following properties: 
An enrollment number
A value for being scheduled 
A list of values for each of 10 available time slots
A preferred building

There are 10 possible time slots, and each room can have only one class scheduled in each time slot. In addition, courses can only be scheduled in rooms where the capacity is greater than the enrollment. 

For each course, there is a list of values for each time slot.  A value of 0 corresponds to infeasible (i.e., the course cannot be held at this time), while any other positive value is a bonus given for scheduling the course in that particular time slot. 

Courses also have preferred buildings.  Courses scheduled in another building receive a penalty based on the distance between where the course is actually scheduled and the preferred building. 


A solution is a mapping from rooms and time slots to courses.  That is, each room can be assigned to hold one course in each available time slot. Courses are identified by their indices from 0 to N-1. 

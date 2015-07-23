# TimHortons
University of Ontario Institute of Technology / DHBW Mannheim
Project Industry / Big Data - Summer School 2015

Link to the presentation held on July 24, 2015: [click here] (https://docs.google.com/presentation/d/1ptnTii_kZqtrl_G6JCvVv2jaxookzrxZ5K-czQudQxE/edit#slide=id.p)

This Industry 4.0 Summer School Project simulates a production line which is supposed to teach us how to handle big data of the line.
The goals were to aggregate that data, find hidden pattern and visualize the results.

##The State Machine

The State Machine controls the way of the product through the machine. When an event is fired, the state machine will identify, to which this event belongs. Additionally when ERP data is fired or a Spectral Analysis is read, the relevant information is identified and allocated to the specific product. After a successfull Identify-Process all events in a Product Production Process are added to an object of a Product, so that a Product with all its various events can be stored in the database and later be used for analysis.

The relevant classes for the State Machine are
- Product: stores all information of a product in the database
- Identifier: analyzes messages from Camel, allocates them to the relevant product and processes the product accordingly. It is a Singleton, because it contains THE list of all products, that should event types (empty or full) totals in 15 different states. The product takes no decisions based on the input, but just time controls, when events got the next state. These states are displayed in the state machine below:

![](https://github.com/ChristopherStumm/TimHortons/blob/master/Presentation%20materials/State_Machine.png "State Machine")

The algorithm to check, to which product an event was fired, is quite simple. The Event Item is analyzed and resembles a state, which is transferred into a state integer. The product remembers its own state integer. If the product moves, the event will be in one station further than the current product station. So, when an event is fired, all product stations are checked, if they are 1 below the event station. If they are, the station of these is incremented by one. Events of the heat or speed of the Milling or the Drilling Station do not move the product, so they are checked, if they are exactly the station of the product and are not incremented. The station of the event is analyzed with the item name and the value it provides.

The identifier implements the Observer pattern. It is the subjects and informs the GUI when a Product is added or removed.

##Overall Architecture
![](https://raw.githubusercontent.com/ChristopherStumm/TimHortons/master/Presentation%20materials/Architecture.png "Architectural Overview")

The idea of the architecture is to handle new production lines easily. Therefore the graphic shows multiple production lines with separate applications which collect and aggregate the data of each line individually and send it to a back-end server. Each production line provides an operative view which graphically shows the current status of each product and can show important warning messages for the operative worker (more information: User Interface - Operative View).

In order to run good analysis on historic data to find pattern or create strategic reports a second view is implemented which is created for the management (more information: User Interface - Executive View)

##User Interface - Operative View
Within the meaning of business informatics we decided to implement an user interface that will be used in particular by employees with direct to the production line. With the help of that user interface the employee will be able to see all current items that are currently part of the production process. The list of these products will be updated dynamically (added / removed). The right part of the windows shows a scheme of the production line. By selecting a product an employee will see the current location of the product through a red flashing station. All other stations will remain black. By changing the production station the following station will light up. By changing the product in the list the 'factory' will adjust dynamically. The following figure shows an example of this view. 

![](https://github.com/ChristopherStumm/TimHortons/blob/master/Presentation%20materials/Fabric_UI.png)

We implemented this by using the observer pattern. With the help of this pattern the UI will be attached as an observer to the identifier class. In case of any changes a notify method in this class will be called. This results in updating the list model of the JList on the left side of the window. The factory shapes (stations) are updated in this way as well.  


## Saving the data ##
Saving the data takes place after the product has been fully produced and went through the spectral analysis. An Object containing all the data coming from the ERPData queue as well as the data from the different events (light barriers, drilling / milling stations). This Java object is then marshalled to a JSON object.

The JSON-Object is then inserted into a remotely available MongoDB installation via the native MongoDB driver for Java into a collection for the orders. 

The NodeJS backend serves as the data provider for the executive view. It serves the data for the most recent orders inserted into the database to be shown in the view.

There are background jobs running on the remote machine aggregating the data and calculating average values that are then placed in a different document in a different collection. This facilitates the possibility to provide a faster request of those aggregated values without having to recalculate all the averages with every request. This makes it possible to provide an application for the manager enabling him to have a quick look at all the data without having to drill into the details

# TimHortons
UOIT / DHBW Project Summer School 2015

Link for the presentation held in DHBW Mannheim: [click here] (https://docs.google.com/presentation/d/1ptnTii_kZqtrl_G6JCvVv2jaxookzrxZ5K-czQudQxE/edit#slide=id.p)

This Industry 4.0 Summer School Project simulates a production line which is supposed to teach us how to handle big data of the line.
The goals were to aggregate that data, find hidden pattern and visualize the results.

##The State Machine

The State Machine controls the way of the product through the machine. When an event is fired, the state machine will identify, to which this event belongs. Additionally when ERP data is fired or a Spectral Analysis is read, the relevant information is identified and allocated to the specific product. After a successfull Identify-Process all events in a Product Production Process are added to an object of a Product, so that a Product with all its various events can be stored in the database and later be used for analysis.

The relevant classes for the State Machine are
- Product: stores all information of a product in the database
- Identifier: analyzes messages from Camel, allocates them to the relevant product and processes the product accordingly. It is a Singleton, because it contains THE list of all products, that should be consistent throughout the Application. It is the factory for products.

The machine has got five light barriers, a milling module and a drilling module. Consequently the product has seven positions in which it can be. All these positions can either be empty or full, for which they will send messages. After ERP order of the Product has been received, the product is not yet at the first light barrier, so it is in a state before the machine procedure. One state for this plus 7 possible positions with two different event types (empty or full) totals in 15 different states. The product takes no decisions based on the input, but just time controls, when events got the next state. These states are displayed in the state machine below:

![](https://github.com/ChristopherStumm/TimHortons/blob/master/Presentation%20materials/State_Machine.png "State Machine")

The algorithm to check, to which product an event was fired, is quite simple. The Event Item is analyzed and resembles a state, which is transferred into a state integer. The product remembers its own state integer. If the product moves, the event will be in one station further than the current product station. So, when an event is fired, all product stations are checked, if they are 1 below the event station. If they are, the station of these is incremented by one. Events of the heat or speed of the Milling or the Drilling Station do not move the product, so they are checked, if they are exactly the station of the product and are not incremented. The station of the event is analyzed with the item name and the value it provides.

The identifier implements the Observer pattern. It is the subjects and informs the GUI when a Product is added or removed.

##Overall Architecture
![](https://raw.githubusercontent.com/ChristopherStumm/TimHortons/master/Presentation%20materials/Architecture.png "Architectural Overview")

The idea of the architecture is to handle new production lines easily. Therefore the graphic shows multiple production lines with separate applications which collect and aggregate the data of each line individually and send it to a back-end server. Each production line provides an operative view which graphically shows the current status of each product and can show important warning messages for the operative worker (more information: User Interface - Operative View).

In order to run good analysis on historic data to find pattern or create strategic reports a second view is implemented which is created for the management (more information: User Interface - Executive View)

##User Interface - Operative View
Within the meaning of business informatics we decided to implement an user interface that will be used in particular by employees with direct to the production line. With the help of that user interface the employee will be able to see all current items that are currently part of the production process. The list of these products will be updated dynamically (added / removed). The right part of the windows shows a scheme of the production line. By selecting a product an employee will see the current location of the product through a red flashing station. All other stations will remain black. By changing the production station the following station will light up. By changing the product in the list the 'fabric' will adjust dynamically. The following figure shows an example of this view. We implemented this by using the observer pattern. With the help of this pattern the UI will be attached as an observer to the identifier class. In case of any changes a notify method in this class will be called. 
![Operative UI](http://abload.de/img/fabric_ui9yot4.png)

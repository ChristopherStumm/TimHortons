# TimHortons
UOIT / DHBW Project Summer School 2015

Link for the presentation held in DHBW Mannheim: [click here] (https://docs.google.com/presentation/d/1ptnTii_kZqtrl_G6JCvVv2jaxookzrxZ5K-czQudQxE/edit#slide=id.p)

This Industry 4.0 Summer School Project simulates a production line which is supposed to teach us how to handle big data of the line.
The goals were to aggregate that data, find hidden pattern and visualize the results.

##Overall Architecture
![](https://raw.githubusercontent.com/ChristopherStumm/TimHortons/master/Presentation%20materials/Architecture.png "Architectural Overview")

The idea of the architecture is to handle new production lines easily. Therefore the graphic shows multiple production lines with separate applications which collect and aggregate the data of each line individually and send it to a back-end server. Each production line provides an operative view which graphically shows the current status of each product and can show important warning messages for the operative worker (more information: User Interface - Operative View).

In order to run good analysis on historic data to find pattern or create strategic reports a second view is implemented which is created for the management (more information: User Interface - Executive View)

##User Interface - Operative View
Within the meaning of business informatics we decided to implement an user interface that will be used in particular by employees with direct to the production line. With the help of that user interface the employee will be able to see all current items that are currently part of the production process. The list of these products will be updated dynamically (added / removed). The right part of the windows shows a scheme of the production line. By selecting a product an employee will see the current location of the product through a red flashing station. All other stations will remain black. By changing the production station the following station will light up. By changing the product in the list the 'fabric' will adjust dynamically. The following figure shows an example of this view. We implemented this by using the observer pattern. 
![](http://abload.de/img/fabric_ui9yot4.png "Operative View UI")


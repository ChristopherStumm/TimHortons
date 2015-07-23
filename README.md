# TimHortons
UOIT / DHBW Project Summer School 2015

##User Interface - Operative View
Within the meaning of business informatics we decided to implement an user interface that will be used in particular by employees with direct to the production line. With the help of that user interface the employee will be able to see all current items that are currently part of the production process. The list of these products will be updated dynamically (added / removed). The right part of the windows shows a scheme of the production line. By selecting a product an employee will see the current location of the product through a red flashing station. All other stations will remain black. By changing the production station the following station will light up. By changing the product in the list the 'fabric' will adjust dynamically. The following figure shows an example of this view. We implemented this by using the observer pattern. 
<img align="left" width="700" hspace="7" src="http://abload.de/img/fabric_ui9yot4.png">

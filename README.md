# TimHortons
UOIT / DHBW Project Summer School 2015

The State Machine

The State Machine controls the way of the product through the machine. When an event is fired, the state machine will identify, to which this event belongs. Additionally when ERP data is fired or a Spectral Analysis is read, the relevant information is identified and allocated to the specific product. After a successfull Identify-Process all events in a Product Production Process are added to an object of a Product, so that a Product with all its various events can be stored in the database and later be used for analysis.

The relevant classes for the State Machine are
- Product: stores all information of a product in the database
- Identifier: analyzes messages from Camel, allocates them to the relevant product and processes the product accordingly. It is a Singleton, because it contains THE list of all products, that should be consistent throughout the Application. It is the factory for products.

The machine has got five light barriers, a milling module and a drilling module. Consequently the product has seven positions in which it can be. All these positions can either be empty or full, for which they will send messages. After ERP order of the Product has been received, the product is not yet at the first light barrier, so it is in a state before the machine procedure. One state for this plus 7 possible positions with two different event types (empty or full) totals in 15 different states. The product takes no decisions based on the input, but just time controls, when events got the next state. These states are displayed in the state machine below:

The algorithm to check, to which product an event was fired, is quite simple. The Event Item is analyzed and resembles a state, which is transferred into a state integer. The product remembers its own state integer. If the product moves, the event will be in one station further than the current product station. So, when an event is fired, all product stations are checked, if they are 1 below the event station. If they are, the station of these is incremented by one. Events of the heat or speed of the Milling or the Drilling Station do not move the product, so they are checked, if they are exactly the station of the product and are not incremented. The station of the event is analyzed with the item name and the value it provides.

The identifier implements the Observer pattern. It is the subjects and informs the GUI when a Product is added or removed.
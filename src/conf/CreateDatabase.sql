--To run this script you need to actually create and then connect to the derby database.
--I will figure out how to create this database on application startup eventually, but for now this
--will have to do. 

--To create and connect to the DB: 

--Navigate to the services tab in netbeans.
--Right click on "Java DB" abd click "Create Database"
--Database name : "ProjectDB"
--Username : "Group7"
--Click "ok"
--Right click on "jdbc:derby://localhost:1527/ProjectDB [group7 on GROUP7] database driver and click connect.
--Right click on Tables folder and click "Execute command" and copy the contents of this script in there.

CREATE TABLE ClientType ( 
    TypeID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 0, INCREMENT BY 1),
    TypeName VARCHAR(15) NOT NULL,
    PRIMARY KEY (TypeID)
);

--User is a keyword, going with client instead to keep singular noun.
CREATE TABLE Client ( 
    ClientID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 0, INCREMENT BY 1),
    Username VARCHAR(15) NOT NULL UNIQUE,
    Password VARCHAR(15) NOT NULL,
    FirstName VARCHAR(15) NOT NULL,
    Surname VARCHAR(15) NOT NULL,
    TypeID INTEGER NOT NULL,
    Message VARCHAR(500) NOT NULL,
    Street1 VARCHAR(30) NOT NULL,
    Street2 VARCHAR(30) NOT NULL,
    City VARCHAR(30) NOT NULL,
    County VARCHAR(30) NOT NULL,
    Country VARCHAR(30) NOT NULL,
    PRIMARY KEY (ClientID)
);

CREATE TABLE Product (
    ProductID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 0, INCREMENT BY 1),  
    ProductName VARCHAR(15) NOT NULL,
    Description VARCHAR(500),
    Cost double NOT NULL,
    PRIMARY KEY (ProductID)
);

--Order is a keyword, this is the main order table that keeps track of orders and which customer made them.
CREATE TABLE ClientProductOrder (
    OrderID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 0, INCREMENT BY 1),  
    ClientID INTEGER NOT NULL,
    PRIMARY KEY (OrderID)
);

--This table keeps track of the products in an order.
CREATE TABLE ProductOrderDetails (
    OrderID INTEGER NOT NULL,  
    ProductID INTEGER NOT NULL,
    Quantity INTEGER NOT NULL,
    PRIMARY KEY (OrderID, ProductID)
);

--Keep track of stock for the shop.
CREATE TABLE Inventory ( 
    ProductID INTEGER NOT NULL,
    Quantity INTEGER NOT NULL,
    PRIMARY KEY (ProductID)
);

--Keeps track of a users current cart. Entries should be deleted when a user checks out their cart.
CREATE TABLE Cart ( 
    CartID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 0, INCREMENT BY 1),  
    ClientID INTEGER NOT NULL,
    PRIMARY KEY (CartID)
);

CREATE TABLE CartItem ( 
    CartID INTEGER,  
    ProductID INTEGER NOT NULL,
    Quantity INTEGER NOT NULL,
    PRIMARY KEY (CartID, ProductID)
);


--Add foreign keys now.

Alter Table GROUP7.Client
Add FOREIGN KEY (TypeID) 
References GROUP7.ClientType (TypeID) ON DELETE CASCADE; 

Alter Table GROUP7.Cart
Add FOREIGN KEY (ClientID) 
References GROUP7.Client (ClientID) ON DELETE CASCADE;  

Alter Table GROUP7.CartItem
Add FOREIGN KEY (CartID) 
References GROUP7.Cart (CartID) ON DELETE CASCADE;  

Alter Table GROUP7.CartItem
Add FOREIGN KEY (ProductID) 
References GROUP7.Product (ProductID) ON DELETE CASCADE; 

Alter Table GROUP7.ClientProductOrder
Add FOREIGN KEY (ClientID) 
References GROUP7.Client (ClientID) ON DELETE CASCADE;  

Alter Table GROUP7.ProductOrderDetails
Add FOREIGN KEY (OrderID) 
References GROUP7.ClientProductOrder (OrderID) ON DELETE CASCADE;  

Alter Table GROUP7.ProductOrderDetails
Add FOREIGN KEY (ProductID) 
References GROUP7.Product (ProductID) ON DELETE CASCADE;  

Alter Table GROUP7.Inventory
Add FOREIGN KEY (ProductID) 
References GROUP7.Product (ProductID) ON DELETE CASCADE;  

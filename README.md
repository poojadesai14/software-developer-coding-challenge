

### Prerequisites
* Java 8
* Gradle - gradle-4.8.1
* SpringBoot - Version: 2.1.11.RELEASE
* MySql - Version: 3.x
* Lombok - Version: 1.18.10
### DataBase Schema

![alt text](https://raw.githubusercontent.com/poojadesai14/software-developer-coding-challenge/master/schema.png)

### Steps to run the project
#### 1. Create the following tables under car_bidding_platform schema:
1. ```
    CREATE TABLE car_bidding_platform.user_details (
	user_id INT NOT NULL,
	user_name VARCHAR(50),
	user_address VARCHAR(70),
	user_contact_number VARCHAR(12),
	user_email_id VARCHAR(25),
	CONSTRAINT `PRIMARY` PRIMARY KEY (user_id)) ;
   ```
2. ```
    CREATE TABLE car_bidding_platform.car_details (
	car_id INT NOT NULL,
	car_name VARCHAR(50),
	car_price DECIMAL(10,0),
	status VARCHAR(20),
	mileage DECIMAL(10,0),
	location VARCHAR(50),
	CONSTRAINT `PRIMARY` PRIMARY KEY (car_id)) ;
   ```

3. ```
    CREATE TABLE car_bidding_platform.car_bidding_details (
	car_bidding_id INT NOT NULL,
	bidding_amount DECIMAL(19,4),
	last_updated_time TIMESTAMP,
	bidder_id INT NOT NULL,
	auction_car_id INT NOT NULL,
	CONSTRAINT `PRIMARY` PRIMARY KEY (car_bidding_id),
	CONSTRAINT car_bidding_details_ibfk_1 FOREIGN KEY (bidder_id) REFERENCES car_bidding_platform.user_details(user_id) ON DELETE CASCADE ON UPDATE RESTRICT, 
    CONSTRAINT car_bidding_details_ibfk_2 FOREIGN KEY (auction_car_id) REFERENCES car_bidding_platform.car_details(car_id) ON DELETE CASCADE ON UPDATE RESTRICT) ; 
    CREATE INDEX auction_car_id ON car_bidding_platform.car_bidding_details (auction_car_id) ;
    CREATE INDEX bidder_id ON car_bidding_platform.car_bidding_details (bidder_id) ;
   ```
### 2. Compile the project and run the main class file to start the application
       compile command for project: ./gradlew clean build 
       
### 3. Execute the following API via POSTMAN
#### 1. Business Requirement: Record a user's bid on a car
     http://localhost:8080/api/v1/auction/details
     POST Request Body:
     ```
     {
	"userId":1,
	"carId":1,
	"biddingAmount":4001.00
     }
     ```
     Result: 201 Created
     
#### 2. Business Requirement: Get the current winning bid for a car

     GET Request: http://localhost:8080/api/v1/auction/max_bid/car?cardId=1
     Result: 4001.00
     
#### 3. Business Requirement: Get a car's bidding history
     GET Request: http://localhost:8080/api/v1/auction/history?cardId=1
     Result:
     [
     {
        "userId": "1",
        "userName": "Pooja",
        "userEmail": "poojandes@gmail.com",
        "userNumber": "3473564756",
        "carId": "1",
        "carName": "AltoK10",
        "biddingAmount": 4000
    },
    {
        "userId": "2",
        "userName": "Kunal",
        "userEmail": "kunal@gmail.com",
        "userNumber": "734637634",
        "carId": "1",
        "carName": "AltoK10",
        "biddingAmount": 4001
    }
    ]



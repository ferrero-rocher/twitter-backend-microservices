# Twitter-backend-microservices
backend clone of Twitter with microservices architecture in spring boot

## Links
[Detailed explanation of components and services in this spring-boot-based-twitter-implementation](https://shaunlewis.hashnode.dev/exploring-microservices-and-key-components-in-a-spring-boot-based-twitter-implementation)

[Postman Collection](https://api.postman.com/collections/22670645-66748c08-ea0e-4122-b131-9b8c9ee314e7?access_key=PMAT-01H5QH1P0A77R3QTN6AQ663AZ8)
## **Architecture**
![image](https://github.com/ferrero-rocher/twitter-backend-microservices/assets/60911166/fad794d6-d882-41eb-b5c5-bb5f397c7b9e)

To access the endpoint you'll have to create a new bearer token and pass it as a header. The token has an expiry of 15 mins and needs to be regenerated post that. Since this project is following microservices architecture each services are independent. The Notification service and the Tweet service are connected to each other via RabbitMQ and for in terms of catching we are using Redis cache to cache the tweets that are popular or are from famous celebrities. 

## **How to run the project**
1. Run the following command :- **docker compose** up in the root directory
2. Run mvn clean verify -DskipTests by going inside each folder to build the applications.
3. After that run mvn spring-boot:run by going inside each folder to start the applications.

## Endpoints 
One can directly call the endpoints of the microservices or can call the endpoints exposed via API Gateway which will inturn call the respective microservices
### User Service

BASEPATH:- http://localhost:8081

| Method | Endpoint | Description |
|:---: | :---: | :---:|
| POST | /api/auth/login | Generates a bearer token for the registered user |
| POST | /api/auth/register | Registers a new user |
| GET | /api/user/{userId} | Retrieves details of the given userId |
| GET | /api/user?username={userName} |  Retrieves details of the given userId |
| POST | /api/auth/logout |  Invalidates the users token |

### Follow Service

BASEPATH:- http://localhost:8082

| Method | Endpoint | Description |
|:---: | :---: | :---:|
| GET | /api/followservice/user/{userId}/followers | Retreives a list of users who follow the  given userId |
| GET | /api/followservice/user/{userId}/following | Retreives a list of users followed by given userId |
| POST | /api/followservice/user/{userId}/follow | Follows the given userId |
| POST | /api/followservice/user/{userId}/unfollow |  Unfollows the given userId |

### Tweet Service

BASEPATH:- http://localhost:8083

| Method | Endpoint | Description |
|:---: | :---: | :---:|
| POST | /api/tweetservice/tweet | Post a new tweet |
| GET | /api/tweetservice/tweet/{tweetId} | Get a tweet by tweetId |
| GET | /api/tweetservice/user/{userID}/tweet | Gets a list of tweet posted by userID |
| DELETE | /api/tweetservice/tweet/{tweetId} | Softdeletes a Tweet |
| POST | /api/tweetservice/tweet/{tweetId}/like |  Likes a tweet |
| POST | /api/tweetservice/tweet/{tweetId}/unlike |  Unlikes a tweet |
| POST | /api/tweetservice/tweet/{tweetId}/retweet |  Retweet a tweet |
| GET | /api/tweetservice/tweet/populartweets |  Tetries a list of all popular tweets |


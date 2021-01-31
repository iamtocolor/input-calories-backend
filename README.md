#### Calorie Input APIs
With reference to following requirements this project exposes multiple APIs to create User and add food for them.

##

#### Description
* This Backend project exposes the multiple CRUD APIs for User and to add to food for the User. 
* All the APIs have RBAC(Role Based Access Control) to restrict the usage.
* All the passwords get encrypted before saving to database.
* Along with CRUD, there is Pagination support for APIs return List of Elements.
* For Search, there is an API that support search query involving Multiple level bracket.

##

#### Usage guide
* There is a special API for creating ADMIN user that is protected through a secret.
* Once the ADMIN is created, the ADMIN create multiple users
* Using the Assign role API, the ADMIN can assign multiple roles to a User.
* By calling the login API, one can get their token which they can pass while making API calls.

##

#### Tech Stack
* Java
* Spring Boot
* H2
* Guava libraries for Caching
* [Postman API Collection](https://www.postman.com/collections/300b0dd5d48c9fbef72d)
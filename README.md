#### Calorie Input APIs
With reference to following requirements this project exposes multiple APIs to create User and add food for them.
* API Users must be able to create an account and log in.
* All API calls must be authenticated.
* Implement at least three roles with different permission levels: a regular user would only be able to CRUD on their owned records, a user manager would be able to CRUD only users, and an admin would be able to CRUD all records and users.
* Each entry has a date, time, text, and number of calories.
* If the number of calories is not provided, the API should connect to a Calories API provider (for example https://www.nutritionix.com) and try to get the number of calories for the entered meal.
* User setting – Expected number of calories per day.
* Each entry should have an extra boolean field set to true if the total for that day is less than expected number of calories per day, otherwise should be false.
* The API must be able to return data in the JSON format.
* The API should provide filter capabilities for all endpoints that return a list of elements, as well should be able to support pagination.
* The API filtering should allow using parenthesis for defining operations precedence and use any combination of the available fields. The supported operations should at least include or, and, eq (equals), ne (not equals), gt (greater than), lt (lower than).
  Example -> (date eq '2016-05-01') AND ((number_of_calories gt 20) OR (number_of_calories lt 10)).
* REST API. Make it possible to perform all user and admin actions via the API, including authentication.
* In any case, you should be able to explain how a REST API works and demonstrate that by creating functional tests that use the REST Layer directly. Please be prepared to use REST clients like Postman, cURL, etc. for this purpose.
* Write unit and e2e tests.

##

#### Description
* This Backend project exposes the multiple CRUD APIs for User and to add to food for the User. 
* All the APIs have RBAC(Role Based Access Control) to restrict the usage.
* All the passwords get encrypted before saving to database.
* Along with CRUD, there is Pagination support for APIs return List of Elements.
* For Search, there is an API that support search query involving Multiple level bracket.
* The calories for food which fetched from Nutritionix when its not present in the request.
* There is a Cache which sits before calling to Nutrionix API, thus making the add food API fast in some cases.

##

#### Usage guide
* There is a special API for creating ADMIN user that is protected through a secret.
* Once the ADMIN is created, the ADMIN create multiple users
* Using the Assign role API, the ADMIN can assign multiple roles to a User.
* By calling the login API, one can get their token which they can pass while making API calls.
* The search API have the following structure
```json
{
    "operand1" : {
        "operand1" : "DATE",
        "operator" : "EQ",
        "operand2" : "2020-01-01",
        "type" : "simple"
    },

    "operator" : "AND",

    "operand2" : {
        "operand1" : {
            "operand1" : "NUMBER_OF_CALORIES",
            "operator" : "GT",
            "operand2" : "30",
            "type" : "simple"
        },

        "operator" : "OR",
        
        "operand2" : {
            "operand1" : "NUMBER_OF_CALORIES",
            "operator" : "LT",
            "operand2" : "20",
            "type" : "simple"
        },

        "type" : "complex"
    },
    "type" : "complex"
}
```

##

#### Tech Stack
* Java
* Spring Boot
* H2
* Guava libraries for Caching
* [Postman API Collection](https://www.postman.com/collections/300b0dd5d48c9fbef72d)
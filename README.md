#Recipes
The program that stores all recipes in one place. Multi-user web service with Spring Boot that allows storing, retrieving, updating, and deleting recipes.
Endpoint ```POST /api/register``` receives a JSON object with two fields: ```email (string)```, and ```password (string)```. If a user with a specified email does not exist, the program saves (registers) the user in a database and responds with ```200 (Ok)```. If a user is already in the database, respond with the ```400 (Bad Request)``` status code. Both fields are required and must be valid: email should contain ```@``` and ```.``` symbols, password should contain at least ```8 characters``` and shouldn't be blank. If the fields do not meet these restrictions, the service should respond with ```400 (Bad Request)```.
Only an author of a recipe can delete or update a recipe. If a user is not the author of a recipe, but they try to carry out the actions mentioned above, the service should respond with the ```403 (Forbidden)``` status code.

Endpoints:

```POST /api/register``` request without authentication
```
{
   "email": "Cook_Programmer@somewhere.com",
   "password": "RecipeInBinary"
}
```
Status code: ```200 (Ok)```

```POST /api/recipe/new``` request with authentication
```
{
   "name": "",
   "category": "",
   "description": "",
   "ingredients": ["",""],
   "directions": ["",""]
}
```
Response:
```
{
   "id": 1
}
```
```PUT /api/recipe/{id}``` request only an author of a recipe
```
{
   "name": "Fresh Mint Tea",
   "category": "beverage",
   "description": "Light, aromatic and refreshing beverage, ...",
   "ingredients": ["",""],
   "directions": ["",""]
}
```
Response:

Status code: ```204 (No Content)```

```GET /api/recipe/{id}``` request with authentication

Response:
```
{
   "name": "",
   "category": "",
   "date": "2020-01-02T12:11:25.034734",
   "description": "",
   "ingredients": ["",""],
   "directions": ["",""]
}
```
```DELETE /api/recipe/{id}``` request only an author of a recipe

```GET /api/recipe/search?category=?&name=?``` request with authentication

Response:
```
{
   "name": "Iced Tea Without Sugar",
   "category": "beverage",
   "date": "2019-07-06T17:12:32.546987",
   ....
},
{
   "name": "vegan avocado ice cream",
   "category": "DESSERT",
   "date": "2020-01-06T13:10:53.011342",
   ....
},
{
   "name": "Fresh Mint Tea",
   "category": "beverage",
   "date": "2021-09-06T14:11:51.006787",
   ....
},
{
   "name": "Vegan Chocolate Ice Cream",
   "category": "dessert",
   "date": "2021-04-06T14:10:54.009345",
   ....
},
{
   "name": "warming ginger tea",
   "category": "beverage",
   "date": "2020-08-06T14:11:42.456321",
   ....
}
```

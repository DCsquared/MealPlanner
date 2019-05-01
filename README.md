# Meal Planner
Meal Planner is a database application with the following features:
1) Manage a collection of recipes
2) Keep track of current items in the fridge
3) Manage a weekly plan
4) Generate the shopping list according to the weekly menu and what is currently in the fridge
5) Allow users to search for recipes based on the ingredient it uses or the cateogry it is in

## Setup
1) If you would like to create the required tables in your own databse, modify the ConnectionDB.java
	so it has the correct information to connect to your database
2) Run MealPlannerSetup.sql in SQL Plus after connecting to the database

## Installation
1) Download the ZIP MealPlanner from the Github
2) Unzip it into a folder
3) Install ojdbc7.jar (included in the respoitory

## Usage
There are five tabs that correspond to pages

### Home: displays the current week's meal plan
* Click a day to display the recipes for each meal of that day
* Click a recipe to dispay the ingredients required for that recipe

### Fridge: Fridge displays the current food in the fridge	
* Food not in fridge displays the food in the database that is not currently in the fridge
* Select a food item in either of these lists to display its Nutrition Facts
* Click Add Food while a food item that is not in the fridge is selected to add it to the fridge
* Click Remove Food while a food item that is in the fridge is selected to remove it from the fridge
### Shopping List: Displays the food needed for the current week that is not in the fridge
* Select a food to display its nutrition information
### Meal Planning: Allows the user to edit meal plans
* Select a day to display the recipes for each day
* Select a meal for a day, then select a recipe, then click Add Recipe to add a recipe to that meal
* Select a meal for a day, then select a recipe, then click Remove Recipe to remove a recipe to that meal
* To search by category, press the Category button then select the category you would like to search by
* To search by ingredient, press the Ingredient button then select the ingredient you would like to search by
### Cook Book: Allows the user to search for recipes by category or ingredient, add recipes, or modify existing recipes
* Select a recipe to view its ingredients
* To search by category, press the Category button then select the category you would like to search by
*  To search by ingredient, press the Ingredient button then select the ingredient you would like to search by
*  To modify a recipe, select an ingredient in Current Ingredients and click Remove Ingredient 
   * OR select an ingredient in Available Ingredients and click Add Ingredient
* To add a recipe, type a name for it, select a category, and type instructions, then click Add Recipe
* To add ingredients to this recipe, modify it as described previously

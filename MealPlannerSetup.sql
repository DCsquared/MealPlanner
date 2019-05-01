drop table Food cascade constraints;
drop table Recipes cascade constraints;
drop table DailyPlan cascade constraints;
drop table Meal cascade constraints;
drop table RecipeIngredient cascade constraints;
drop table Week cascade constraints;
drop table WeekPlan cascade constraints;

-- come up with 3-5 per food group
create table Food(
name varchar2(20) primary key,
calories integer, 
sugar integer,
protein integer, 
sodium integer, 
fat integer,
foodGroup varchar2(40),
inStock number(1)
);

-- come up with 3-5 per category
create table Recipes(
id number(3) primary key,
name varchar2(20) unique,
category varchar2(30),  -- main dish, side dish
instructions varchar2(100)
);

-- 1 per weekday
create table DailyPlan(
id number(3) primary key,
weekday varchar2(10)
);

-- does your best!
create table Meal(
recipeID number(3),
dailyPlanID number(3),
typeOfMeal varchar2(20),
id number(3) primary key,
constraint recipeFK foreign key (recipeID) references Recipes(id) on delete cascade,
constraint planFK foreign key (dailyPlanID) references DailyPlan(id) on delete cascade
);

create table RecipeIngredient(
recipeID number(3),
foodName varchar2(20),
primary key (recipeID, foodName),
constraint recipeFK2 foreign key (recipeID) references Recipes(id) on delete cascade,
constraint foodNameFK foreign key (foodName) references Food(name) on delete cascade
);

create table Week(
id number (3) primary key,
currentWeek number(1), -- 0 or 1
name varchar2(20)
);

create table WeekPlan(
weekID number(3),
mealID number(3),
primary key (weekID, mealID),
constraint weekFK foreign key (weekID) references Week(id) on delete cascade,
constraint mealFK foreign key (mealID) references Meal(id) on delete cascade
);

insert into Food values ('Carrot', 123, 123, 123, 123, 123, 'Veggie', 1);
insert into Food values ('Tomato', 321, 321, 321, 321, 312, 'Veggie or fruit', 1);
insert into Food values ('Ice Cream', 1230, 1230, 1230, 1230, 1230, 'Comfort', 0);
insert into Food values ('Milk', 123, 123, 123, 123, 123, 'Dairy', 1);
insert into Recipes values (1, 'Cereal', 'Main Dish', 'You put the milk on the cereal and drink it all up');
insert into Recipes values (2, 'Eggs', 'Side Dish', 'You murder some baby chickens');
insert into Recipes values (3, 'Pancakes', 'Main Dish', 'As long as you dont go to Waffle House, youre fine');
insert into Recipes values (4, 'Ham Sandwich', 'Main Dish', 'You put ham between 2 slices of bread');
insert into Recipes values (5, 'Vegan Ham Sandwich', 'Fake Dish', 'You put nothing between 2 slices of bread');
insert into Recipes values (6, 'Turkey Sandwich', 'Main Dish', 'You put turkey between 2 slices of bread');
insert into Recipes values (7, 'Taters', 'Side Dish', 'Boil em, mash em, stick em in a stew');
insert into Recipes values (8, 'Rabbit', 'Main Dish', ':(');
insert into Recipes values (9, 'Human', 'Hunted Dish', 'The worlds most dangerous game');
insert into Recipes values (10, 'Ice Cream', 'Dessert', 'You take a trip to Kroger or go to an ice cream parlor');
insert into Recipes values (11, 'Cake', 'Dessert', 'Go to a bakery, and Happy Birthday!');
insert into Recipes values (12, 'Cupcakes', 'Dessert', 'Idk man, try Kroger?');
insert into DailyPlan values (1, 'Sunday');
insert into DailyPlan values (2, 'Monday');
insert into DailyPlan values (3, 'Tuesday');
insert into DailyPlan values (4, 'Wednesday');
insert into DailyPlan values (5, 'Thursday');
insert into DailyPlan values (6, 'Friday');
insert into DailyPlan values (7, 'Saturday');
insert into Meal values (1, 1, 'Breakfast', 1);
insert into Meal values (1, 2, 'Breakfast', 2);
insert into Meal values (1, 3, 'Breakfast', 3);
insert into Meal values (4, 1, 'Lunch', 4);
insert into Meal values (6, 2, 'Lunch', 5);
insert into Meal values (8, 3, 'Dinner', 6);
insert into Meal values (10, 1, 'Dinner', 7);
insert into Meal values (3, 1, 'Breakfast', 8);
insert into Meal values (5, 1, 'Lunch', 9);
insert into Meal values (8, 1, 'Dinner', 10);
insert into Meal values (3, 2, 'Breakfast', 11);
insert into Meal values (4, 2, 'Lunch', 12);
insert into Meal values (7, 2, 'Dinner', 13);
insert into Meal values (2, 3, 'Breakfast', 14);
insert into Meal values (6, 3, 'Lunch', 15);
insert into Meal values (9, 3, 'Dinner', 16);
insert into Meal values (1, 4, 'Breakfast', 17);
insert into Meal values (5, 4, 'Lunch', 18);
insert into Meal values (2, 4, 'Dinner', 19);
insert into Meal values (2, 5, 'Breakfast', 20);
insert into Meal values (10, 5, 'Lunch', 21);
insert into Meal values (11, 5, 'Dinner', 22);
insert into RecipeIngredient values (1, 'Milk');
insert into RecipeIngredient values (10, 'Ice Cream');
insert into RecipeIngredient values (2, 'Tomato');
insert into RecipeIngredient values (8, 'Ice Cream');
insert into RecipeIngredient values (7, 'Tomato');
insert into RecipeIngredient values (6, 'Tomato');
insert into RecipeIngredient values (5, 'Ice Cream');
insert into RecipeIngredient values (4, 'Carrot');
insert into RecipeIngredient values (3, 'Milk');
insert into RecipeIngredient values (9, 'Ice Cream');
insert into RecipeIngredient values (11, 'Carrot');
insert into RecipeIngredient values (12, 'Milk');
insert into Week values (1, 0, 'April 7');
insert into Week values (2, 0, 'April 14');
insert into Week values (3, 1, 'April 21');
insert into Week values (4, 0, 'April 18');
insert into WeekPlan values (1, 1);
insert into WeekPlan values (2, 1);
insert into WeekPlan values (3, 8);
insert into WeekPlan values (3, 9);
insert into WeekPlan values (3, 10);
insert into WeekPlan values (3, 11);
insert into WeekPlan values (3, 12);
insert into WeekPlan values (1, 4);
insert into WeekPlan values (2, 5);
insert into WeekPlan values (3, 13);
insert into WeekPlan values (3, 14);
insert into WeekPlan values (3, 15);
insert into WeekPlan values (3, 16);
insert into WeekPlan values (3, 17);
insert into WeekPlan values (3, 18);
insert into WeekPlan values (3, 19);
insert into WeekPlan values (3, 20);
insert into WeekPlan values (3, 21);
insert into WeekPlan values (3, 22);

select * from Recipes;
select * from Meal;
select * from Food;
select * from Week;
select * from WeekPlan;




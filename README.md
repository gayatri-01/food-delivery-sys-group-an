What is this repo about?
Its an assignment for SEZG504 API-Based Products to design, develop, and deploy an API product for a Food Delivery System that allows customers to browse restaurants and menus, place orders, track deliveries, and manage accounts. Restaurants can manage their menus and orders, while delivery personnel can accept and track deliveries. Administrators can manage the platform’s users and orders.

How to build this repo?
Go inside a module and run mvn clean install

How to run an application?
This is a multi module, multi application repo.
Each module is an application.
So go inside a module and run mvn spring-boot:run.
This will run the app (name corresponding to folder name so, authService for authService)
on port 8081 with context app name without service so /auth for authService
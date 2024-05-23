How to run this app:

1. method (Required software: Docker)
	1. Use docker: docker-compose up
	2. App is available on localhost:3000

2. method (Required software: MySQL database server Ver 8.0.31+ on port 3306, Spring boot with Java and Maven and Node Package Manager)

	1. Navigate to database directory cd ./database
	2. Insert database with: mysql -u "your_username" -p < webshop.sql
	3. Insert database data: mysql -u "your username" -p < webshop_data.sql
	
	4. Navigate to backend app directory with: cd ./backend/webshop-app/: 
	5. Run run.bat if your mysql database password and username is root OR enter command mvn spring-boot:run -Dspring-boot.run.arguments="--PORT=9000 --MYSQL_DATASOURCE_PASSWORD="your password" --MYSQL_DATASOURCE_USERNAME="your username" --TOKEN_SECRET=244226452948404D635166546A576E5A7234753778214125432A462D4A614E64 --MYSQL_DATASOURCE_URL=jdbc:mysql://localhost:3306/webshop?useUnicode=yes&characterEncoding=UTF-8&serverTimezone=CET"
	
	6. Navigate to frontend app directory with: cd ./frontend/webshop-app/: 
	7. Install packages with npm: npm install
	8. Start frontend web server with: ng serve
	9. App is available on localhost:4200

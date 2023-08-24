# Loan Service Application 

This Loan Request Service is used to store and retrieve one or more loan request for ING Customer.

When customer will request for loan, first it will be checked whether it is existing customer then only loan details will be added and if customer does not exist So both the customer and the loan will be created.

## About the Service
Loan request service Using Spring Boot and H2 Database.

## Get information about system health 
http://localhost:8082/actuator/health

## To view Swagger 2 API docs
Run the server and browse to http://localhost:8082/swagger-ui/index.html

## H2 Database
http://localhost:8082/h2-console/login.jsp

## Save Customer Loan 
http://localhost:8082/loan/createLoan
{
"customerId": 101,
"customerFullName": "Test",
"loanAmount": 12000.50
}

## Get Loan By CustomerId
http://localhost:8082/loan/101


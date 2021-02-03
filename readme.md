# [Homework](https://github.com/sizovs/awesome-homework-for-java-developers)

## Spring boot application
### Endpoints
1. Apply for loan
	* `POST` localhost:8080/loan
	* e.g. ```curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X POST --data   '{"loanAmount": 100, "name":"Frodo", "surname":"Baggins", "personalId": "5432231", "term": 1643659623882}' "http://localhost:8080/loan"```
1. See all approved loans
	* `GET` localhost:8080/loans
	* e.g. ```curl localhost:8080/loans```
3. See borrower loans
	* `GET` localhost:8080/loan/{userUUID}
	* e.g. ```curl localhost:8080/loans/afbea226-71fa-40f0-becc-51c171fe2545 ```


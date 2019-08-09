# Sample Implementation of money transfer between accounts
How to start the bank-account application
---

1. Start application with `java -jar build/bank-account-1.0-SNAPSHOT.jar server build/config.yml`
2. To check that your application is running enter url `http://localhost:8080`

Health Check
---

To see your applications health enter url `http://localhost:8081/healthcheck`

# Testing

1. When the app boots up, it creates some seed data:
- First account with number : `acc_001` and account balance: 1000
- Second account with number: `acc_002` and account balance: 0
- Both accounts are tied to the currency with iso_code: `USD`


2. To test transfer, make a `POST` request with:

- API endpoint: `http://localhost:8080/api/v1/account/transfer`
- Body:
```
{
	"amount": 10,
	"from": "acc_001",
	"to": "acc_002",
	"iso_currency_code": "USD"
}
```

# Running unit tests
1. Run command:  `mvn test`

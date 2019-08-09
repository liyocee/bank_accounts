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
- First account with number : `acc_001` and account balance: 1000, and `currency_code`: USD
- Second account with number: `acc_002` and account balance: 0, and `currency_code`: USD
- Third account with number: `acc_003` and account balance: 50, and `currency_code`: KES

- Both accounts are tied to the currency with iso_code: `USD`
- Other currency that's setup, and seeded to the database: `KES` 

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

- This transfer API endpoint enforces the following constraints:
  - a. The supplied accounts: `from` and `to` MUST be already setup in the system
  - b. The supplied iso currency code: `iso_currency_code` MUST be already setup in the system
  - c. The `from` account MUST have sufficient balance to support the amount being transferred
  - d. All the parameters : `amount`, `from`, `to`, `iso_currency_code` MUST be provided
  - f. The source account: `from` must have the same currency as the supplied currency: `iso_currency_code`
  
- If ANY of the above constraints is violated, the transfer will fail
- If the source account: `from`  and target account: `to` are in different currency(i.e associated to different currency), the amount being transferred is first converted
to the currency of the target account, before updating the balance for the target account


# Running unit tests
1. Run command:  `mvn test`

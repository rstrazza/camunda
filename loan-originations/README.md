# loan originations

Endpoints

```
GET http://localhost:8080/ping
```

```
POST http://localhost:8080/loan/origination/application

{
  "name": "John Doe",
  "address": {
    "street": "1 Main St",
    "city": "Richmond",
    "state": "VA",
    "zipCode": "22000"
  },
  "income": 1000000
}
```
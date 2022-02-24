# currency-converter

This is a simple currency converter aimed to complete the task described below. In addition, I've added some CRUD methods, redesigned the database, and hooked up the services to an actual sqlite database with a DAO. 

## Programming Exercise | SPEC

Write a simple currency converter in any programming language. The list of allowed currencies and exchange rates are store in a single database table with the following fields: CurrencyID (int), CurrencyCode (3 letter string), ExchangeRate (double, stored as the rate from US dollars to the given currency). 

Example Database rows: {1, "USD", 1.0}, {2, "PHP", 43.1232}. 


For simplicity write everything in one class. ***(I did not do this. It turned out very messy, so I hope my packaging will be forgiven)***. Write separate private functions as needed, and include the following public function, which returns the converted currency amount: 

`public decimal ConvertCurrency(string currencyFrom, string currencyTo, decimal amount);`

The following are items you might consider while programming: 
* Data Validation
* Database availability
* Comments
* Variable Names
* Reusability

### Extra Credit
Extend the functionality of your current class. 

Here are some ideas. Extra points for coming up with your own. 
* Write a method that gets the exchange rate for a specific currency. 
* Write CRUD methods for keeping your exchange rates up to date in the database. 
* Redesign the table in the database to account for the fact that the exchange rates are constantly fluctuating. 
* Add an option for using a public API to get the exchange rate instead of the database. 

# tech-mojo-transactions-avg

### Prerequisites
* Java 8
* Maven

### Getting started

##### Compile the code and build
```bash 
$ mvn package
```

##### Run the code 
> uses the default path
```bash
$ java -cp target/transactions_avg_time-1.0-SNAPSHOT.jar Launcher [optional filepath]
```

##### Pass custom input transaction logs and custom file to print output example

```bash
$ java -cp target/transactions_avg_time-1.0-SNAPSHOT.jar Launcher data/transaction_logs_custom.txt data/my_transaction_output.txt
```

##### TODO
- Change output filepath to dir path
- Add tests covering more scenarios
- Replace file reading boiler plate code with a less verbose one.

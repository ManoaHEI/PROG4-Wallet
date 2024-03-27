CREATE TYPE transaction_type AS ENUM ('CREDIT', 'DEBIT');

CREATE TABLE transaction (
     id SERIAL PRIMARY KEY,
     label VARCHAR(50) NOT NULL ,
     transaction_date TIMESTAMP,
     amount DECIMAL,
     transaction_type transaction_type,
     account_id INT REFERENCES account(id),
     balance DECIMAL
);

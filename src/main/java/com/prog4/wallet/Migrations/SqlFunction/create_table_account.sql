CREATE TABLE account (
    id SERIAL primary key,
    name VARCHAR(20) NOT NULL ,
    firstname VARCHAR(50) NOT NULL ,
    birthday DATE CHECK (birthday <= CURRENT_DATE - INTERVAL '18 years'),
    salary DECIMAL NOT NULL ,
    account_number VARCHAR(10) NOT NULL ,
    always_enable_transaction BOOLEAN,
    balance DECIMAL
);
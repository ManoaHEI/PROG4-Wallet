CREATE TABLE credit (
    id SERIAL PRIMARY KEY,
    description VARCHAR,
    amount DECIMAL NOT NULL,
    date_begin DATE NOT NULL,
    date_end DATE NOT NULL,
    loan_interest DECIMAL,
    account_id INTEGER REFERENCES account(id)
);

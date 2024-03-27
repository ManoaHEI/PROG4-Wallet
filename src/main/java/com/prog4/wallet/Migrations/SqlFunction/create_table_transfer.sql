CREATE TYPE transfer_type_enum AS ENUM ('Internal Transfer', 'External Transfer');
CREATE TYPE transfer_status_enum AS ENUM ('Pending', 'Completed', 'Cancelled');

CREATE TABLE transfer (
      id SERIAL PRIMARY KEY,
      transfer_amount DECIMAL,
      transfer_date TIMESTAMP,
      transfer_reason VARCHAR,
      transfer_type transfer_type_enum,
      transfer_status transfer_status_enum,
      source_id INTEGER REFERENCES account(id)
);

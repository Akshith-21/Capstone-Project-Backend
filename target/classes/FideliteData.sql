DROP TABLE c_Trade;
DROP TABLE c_Portfolio;
DROP TABLE c_Price;
DROP TABLE c_Client_Identification;
DROP TABLE c_Person;



CREATE TABLE c_person (
    client_id VARCHAR(50) PRIMARY KEY,
    email VARCHAR(255),
    date_of_birth VARCHAR(10),
    country VARCHAR(255),
    postal_code VARCHAR(10)
);

CREATE TABLE c_client_identification (
    client_id VARCHAR(50),
    type VARCHAR(255),
    value VARCHAR(255),
    FOREIGN KEY (client_id) REFERENCES c_person(client_id)
);


INSERT INTO c_person (client_id, email, date_of_birth, country, postal_code)
VALUES 
    ('Client1', 'client1@example.com', '1990-01-15', 'USA', '12345');

INSERT INTO c_person (client_id, email, date_of_birth, country, postal_code)
VALUES 
    ('Client2', 'client2@example.com', '1985-03-20', 'Canada', 'A1B2C3');

INSERT INTO c_person (client_id, email, date_of_birth, country, postal_code)
VALUES 
    ('Client3', 'client3@example.com', '1992-07-08', 'UK', 'SW1A 1AA');

INSERT INTO c_person (client_id, email, date_of_birth, country, postal_code)
VALUES 
    ('Client4', 'client4@example.com', '1988-05-11', 'Australia', '2000');

INSERT INTO c_person (client_id, email, date_of_birth, country, postal_code)
VALUES 
    ('Client5', 'client5@example.com', '1995-09-30', 'Germany', '12345');

INSERT INTO c_person (client_id, email, date_of_birth, country, postal_code)
VALUES 
    ('Client6', 'client6@example.com', '1980-12-25', 'France', '75001');

INSERT INTO c_client_identification (client_id, type, value) VALUES ('Client1', 'Passport', 'P123456');
INSERT INTO c_client_identification (client_id, type, value) VALUES ('Client2', 'Driver License', 'DL7890');
INSERT INTO c_client_identification (client_id, type, value) VALUES ('Client3', 'Social Security Number', 'SSN4567');
INSERT INTO c_client_identification (client_id, type, value) VALUES ('Client4', 'Passport', 'P987654');
INSERT INTO c_client_identification (client_id, type, value) VALUES ('Client5', 'Driver License', 'DL1234');
INSERT INTO c_client_identification (client_id, type, value) VALUES ('Client6', 'Social Security Number', 'SSN7890');

CREATE TABLE c_Price (
    instrument_id VARCHAR2(50) PRIMARY KEY,
    external_id VARCHAR2(50),
    external_type VARCHAR2(50),
    description VARCHAR2(255),
    max_quantity NUMBER,
    min_quantity NUMBER,
    category_id VARCHAR2(50),
    bid_price NUMBER,
    ask_price NUMBER,
    timestamp TIMESTAMP
);


INSERT INTO c_Price (instrument_id, external_id, external_type, description, max_quantity, min_quantity, category_id, bid_price, ask_price, timestamp)
VALUES ('AAPL', 'AAPL001', 'Type1', 'Apple Inc.', 1000, 10, 'Tech', 150.50, 151.00, TO_TIMESTAMP('2023-09-19 10:30:00', 'YYYY-MM-DD HH24:MI:SS'));


INSERT INTO c_Price (instrument_id, external_id, external_type, description, max_quantity, min_quantity, category_id, bid_price, ask_price, timestamp)
VALUES ('GOOGL', 'GOOGL001', 'Type2', 'Alphabet Inc.', 800, 20, 'Tech', 2765.25, 2766.50, TO_TIMESTAMP('2023-09-19 11:45:00', 'YYYY-MM-DD HH24:MI:SS'));


INSERT INTO c_Price (instrument_id, external_id, external_type, description, max_quantity, min_quantity, category_id, bid_price, ask_price, timestamp)
VALUES ('TSLA', 'TSLA001', 'Type1', 'Tesla, Inc.', 500, 5, 'Auto', 725.75, 726.25, TO_TIMESTAMP('2023-09-19 13:15:00', 'YYYY-MM-DD HH24:MI:SS'));


INSERT INTO c_Price (instrument_id, external_id, external_type, description, max_quantity, min_quantity, category_id, bid_price, ask_price, timestamp)
VALUES ('AMZN', 'AMZN001', 'Type2', 'Amazon.com, Inc.', 1200, 30, 'Retail', 3420.00, 3421.50, TO_TIMESTAMP('2023-09-19 14:30:00', 'YYYY-MM-DD HH24:MI:SS'));


CREATE TABLE c_Trade (
    trade_id VARCHAR2(50) PRIMARY KEY,
    client_id VARCHAR2(50),
    instrument_id VARCHAR2(50),
    direction VARCHAR2(10),
    quantity NUMBER,
    execution_price NUMBER,
    FOREIGN KEY(client_id) REFERENCES c_Person(client_id),
    FOREIGN KEY(instrument_id) REFERENCES c_Price(instrument_id)
);



INSERT INTO c_Trade (trade_id, client_id, instrument_id, direction, quantity, execution_price)
VALUES (1, 'Client1', 'AAPL', 'BUY', 100, 50.25);



INSERT INTO c_Trade (trade_id, client_id, instrument_id, direction, quantity, execution_price)
VALUES (2, 'Client2', 'GOOGL', 'SELL', 75, 45.75);



INSERT INTO c_Trade (trade_id, client_id, instrument_id, direction, quantity, execution_price)
VALUES (3, 'Client3', 'TSLA', 'BUY', 50, 55.50);



INSERT INTO c_Trade (trade_id, client_id, instrument_id, direction, quantity, execution_price)
VALUES (4, 'Client4', 'AMZN', 'SELL', 125, 40.00);


CREATE TABLE c_Portfolio (
    client_id VARCHAR2(50),
    instrument_id VARCHAR2(50),
    current_holdings NUMBER,
    PRIMARY KEY (client_id, instrument_id),
    FOREIGN KEY(client_id) REFERENCES c_Person(client_id),
    FOREIGN KEY(instrument_id) REFERENCES c_Price(instrument_id)
);


-- Inserting mock data into the Portfolio table
INSERT INTO c_Portfolio (client_id, instrument_id, current_holdings)
VALUES ('Client1', 'AAPL', 250);



INSERT INTO c_Portfolio (client_id, instrument_id, current_holdings)
VALUES ('Client2', 'GOOGL', 150);



INSERT INTO c_Portfolio (client_id, instrument_id, current_holdings)
VALUES ('Client3', 'TSLA', 100);



INSERT INTO c_Portfolio (client_id, instrument_id, current_holdings)
VALUES ('Client4', 'AMZN', 300);


COMMIT;
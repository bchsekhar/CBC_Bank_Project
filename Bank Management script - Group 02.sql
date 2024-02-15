-- BEGIN
--    BEGIN
--       EXECUTE IMMEDIATE 'DROP TABLE CBC_CUSTOMER_TABLE';
--    EXCEPTION
--       WHEN OTHERS THEN
--          NULL;
--    END;

--    EXECUTE IMMEDIATE '
--       CREATE TABLE cbc_customer_table(
--       account_number number(10) NOT NULL,
--       customer_name VARCHAR2(45) NOT NULL,
--       contact_number number(12) NOT NULL UNIQUE,
--       username VARCHAR2(45) NOT NULL UNIQUE,
--       password VARCHAR2(45) NOT NULL,
--       balance number(15) NOT NULL,
--       PRIMARY KEY (account_number))';

--    INSERT INTO CBC_CUSTOMER_TABLE VALUES (10001, 'Bilal', 0712345678, 'bilal', 'bilal', 500000.25);
--    INSERT INTO CBC_CUSTOMER_TABLE VALUES (10002, 'Pradeep', 0787654321, 'pradeep', 'pradeep', 605010.85);
--    INSERT INTO CBC_CUSTOMER_TABLE VALUES (10003, 'Chamindu', 07545678965, 'chamindu', 'chamindu', 793000.75);
--    INSERT INTO CBC_CUSTOMER_TABLE VALUES (10004, 'Janani', 0787656743, 'janani', 'janani', 396000.50);
--    INSERT INTO CBC_CUSTOMER_TABLE VALUES (10005, 'Pragathy', 0708764379, 'pragathy', 'pragathy', 478000.20);
-- END;
-- /
-- SELECT * FROM CBC_CUSTOMER_TABLE
-- SELECT * FROM TRANSACTIONS



-- CREATE TABLE transactions (
--     transaction_id NUMBER ,
--     account_number NUMBER NOT NULL,
--     transaction_type VARCHAR2(30) NOT NULL,
--     amount NUMBER(15, 2) NOT NULL,
--     transaction_date TIMESTAMP NOT NULL,
--     CONSTRAINT transaction_pk PRIMARY KEY (transaction_id),
--     CONSTRAINT account_fk FOREIGN KEY (account_number) REFERENCES cbc_customer_table(account_number)
-- );

-- CREATE SEQUENCE transaction_seq START WITH 1 INCREMENT BY 1;

-- CREATE OR REPLACE TRIGGER transaction_trigger
-- BEFORE INSERT ON transactions
-- FOR EACH ROW
-- BEGIN
--     SELECT transaction_seq.NEXTVAL INTO :new.transaction_id FROM DUAL;
-- END;
-- /
-- drop TABLE TRANSACTIONs
-- drop table transaction_seq

SELECT * FROM CBC_CUSTOMER_TABLE
SELECT * FROM TRANSACTIONS

-- Drop existing tables and sequence (if needed)
DROP TABLE CBC_CUSTOMER_TABLE;
DROP TABLE TRANSACTIONS;
DROP SEQUENCE customer_seq

-- Create sequence
CREATE SEQUENCE customer_seq
    START WITH 1
    INCREMENT BY 1
    NOMAXVALUE
    NOCYCLE;

-- Create customer table without specifying DEFAULT for account_number
CREATE TABLE CBC_CUSTOMER_TABLE(
   account_number NUMBER,
   customer_name VARCHAR2(45) NOT NULL,
   contact_number NUMBER(12) NOT NULL UNIQUE,
   username VARCHAR2(45) NOT NULL UNIQUE,
   password VARCHAR2(45) NOT NULL,
   balance NUMBER(15) NOT NULL,
   PRIMARY KEY (account_number)
);

-- Create trigger to populate account_number using the sequence
CREATE OR REPLACE TRIGGER customer_seq_trigger
BEFORE INSERT ON CBC_CUSTOMER_TABLE
FOR EACH ROW
BEGIN
    SELECT customer_seq.NEXTVAL INTO :new.account_number FROM DUAL;
END;
/

-- Insert data into the customer table using the sequence
INSERT INTO CBC_CUSTOMER_TABLE (customer_name, contact_number, username, password, balance) 
VALUES ('Bilal', 712345678, 'bilal', 'bilal', 500000.25);
INSERT INTO CBC_CUSTOMER_TABLE (customer_name, contact_number, username, password, balance) 
VALUES ('Pradeep', 787654321, 'pradeep', 'pradeep', 605010.85);
INSERT INTO CBC_CUSTOMER_TABLE (customer_name, contact_number, username, password, balance) 
VALUES('Chamindu', 7545678965, 'chamindu', 'chamindu', 793000.75);
INSERT INTO CBC_CUSTOMER_TABLE (customer_name, contact_number, username, password, balance) 
VALUES('Janani', 787656743, 'janani', 'janani', 396000.50);
INSERT INTO CBC_CUSTOMER_TABLE (customer_name, contact_number, username, password, balance) 
VALUES('Pragathy', 708764379, 'pragathy', 'pragathy', 478000.20);

-- Create transactions table
CREATE TABLE TRANSACTIONS (
   transaction_id NUMBER,
   account_number NUMBER NOT NULL,
   transaction_type VARCHAR2(30) NOT NULL,
   amount NUMBER(15, 2) NOT NULL,
   transaction_date TIMESTAMP NOT NULL,
   CONSTRAINT transaction_pk PRIMARY KEY (transaction_id),
   CONSTRAINT account_fk FOREIGN KEY (account_number) REFERENCES CBC_CUSTOMER_TABLE(account_number)
);

-- Create sequence for transactions table
CREATE SEQUENCE TRANSACTION_SEQ START WITH 1 INCREMENT BY 1;

-- Create trigger for transactions table
CREATE OR REPLACE TRIGGER TRANSACTION_TRIGGER
BEFORE INSERT ON TRANSACTIONS
FOR EACH ROW
BEGIN
   SELECT TRANSACTION_SEQ.NEXTVAL INTO :new.transaction_id FROM DUAL;
END;
/

CREATE TABLE EXTENSIONVALUE (EXTN_VAL_ID BIGINT NOT NULL, VALUE VARCHAR(255), VER_NO BIGINT, EXTN_ID BIGINT, PRIMARY KEY (EXTN_VAL_ID));
CREATE TABLE USERS (USER_ID BIGINT NOT NULL, ACC_NON_EXPIRED TINYINT(1) default 0, ACC_NON_LOCKED TINYINT(1) default 0, VALID_FROM DATE, PASSWORD_NON_EXPIRED TINYINT(1) default 0, ACTIVE TINYINT(1) default 0, PASSWORD VARCHAR(100) NOT NULL, SALT VARCHAR(255), USER_NAME VARCHAR(50) NOT NULL UNIQUE, VER_NO BIGINT, FIRST_NAME VARCHAR(255) NOT NULL, LAST_NAME VARCHAR(255), MIDDLE_NAME VARCHAR(255), PRIMARY KEY (USER_ID));
CREATE TABLE SUPPORTWEAVEEXTENSIBLE (ID BIGINT NOT NULL, PRIMARY KEY (ID));
CREATE TABLE EXTENSIONS (EXTENSION_ID BIGINT NOT NULL, EXTENSION_NAME VARCHAR(255), CLUSTER_NAME VARCHAR(1000) NOT NULL, ENTITY_NAME VARCHAR(255), EXTENSION_TYPE VARCHAR(255), TENANT VARCHAR(255), VER_NO BIGINT, PRIMARY KEY (EXTENSION_ID));
CREATE TABLE CONFIGURATION_ITEMS (CONFIG_ITEM_ID BIGINT NOT NULL, CONFIG_ITEM_KEY VARCHAR(255), CONFIG_TYPE INTEGER, DEFAULT_VALUE VARCHAR(255), IS_KEY_VALUE_PAIR TINYINT(1) default 0, CONFIG_ITEM_TYPE INTEGER, VER_NO BIGINT, PRIMARY KEY (CONFIG_ITEM_ID));
CREATE INDEX INDEX_CONFIGURATION_ITEMS_CONFIG_ITEM_KEY ON CONFIGURATION_ITEMS (CONFIG_ITEM_KEY);
CREATE TABLE SESSION_REGISTRY (ITEM_ID BIGINT NOT NULL, EXPIRED TINYINT(1) default 0, LAST_REQUEST_TIME DATETIME, SESSION_ID VARCHAR(255) UNIQUE, VER_NO BIGINT, PRINCIPAL BIGINT, PRIMARY KEY (ITEM_ID));
CREATE TABLE SUPPORTWEAVE (ID BIGINT NOT NULL, AUDIT_TIMESTAMP DATETIME, AUDIT_USER VARCHAR(255), VER_NO BIGINT, PRIMARY KEY (ID));
CREATE TABLE WM_PARAMS (PARAM_ID BIGINT NOT NULL, AMOUNT_DECIMALS BIGINT, RATE_DECIMALS BIGINT, PERCENT_DECIMALS BIGINT, PRICE_DECIMALS BIGINT, UNIT_DECIMALS BIGINT, VER_NO BIGINT, PRIMARY KEY (PARAM_ID));
CREATE TABLE CONFIGURATIONS (CONFIG_ID BIGINT NOT NULL, CONFIG_VALUE VARCHAR(255) NOT NULL, VER_NO BIGINT, CONFIG_ITEM_ID BIGINT, USER_ID BIGINT, PRIMARY KEY (CONFIG_ID));
CREATE TABLE AUTHORITIES (AUTHORITY_ID BIGINT NOT NULL, AUTHORITY VARCHAR(50) UNIQUE, VER_NO BIGINT, PRIMARY KEY (AUTHORITY_ID));
CREATE TABLE EXPENSES (EXPENSE_ID BIGINT NOT NULL, ACTIVE TINYINT(1) default 0, DESCRIPTION VARCHAR(255), EXPEXNSE_DATE DATE NOT NULL, EXPENSE INTEGER NOT NULL, VER_NO BIGINT, TYPE_ID BIGINT, USER_EXP_FK BIGINT, PRIMARY KEY (EXPENSE_ID));
CREATE TABLE FORECAST_EXPENSE (FORECAST_EXPENSE_ID BIGINT NOT NULL, FORECAST_DATE DATE, DESCRIPTION VARCHAR(255), FORECAST_AMOUNT DECIMAL(38), FORECAST_TYPE VARCHAR(255), PERIOD VARCHAR(255), IS_PERIODIC TINYINT(1) default 0, TITLE VARCHAR(255), VER_NO BIGINT, USER_EXP_FK BIGINT, PRIMARY KEY (FORECAST_EXPENSE_ID));
CREATE TABLE USER_EXPENSES (USER_EXP_ID BIGINT NOT NULL, VER_NO BIGINT, USER_FK BIGINT UNIQUE, PRIMARY KEY (USER_EXP_ID));
CREATE TABLE EXPENSE_TYPES (TYPE_ID BIGINT NOT NULL, DESCRIPTION VARCHAR(2500), SHOW_IN_DB TINYINT(1) default 0, EXP_TYPE VARCHAR(255) NOT NULL, VER_NO BIGINT, USER_EXP_FK BIGINT, PRIMARY KEY (TYPE_ID));
CREATE TABLE INSTRUMENTS (INSTRUMENT_ID BIGINT NOT NULL, INSTRUMENT_TYPE VARCHAR(31), INSTRUMENT_CODE VARCHAR(255), INSTRUMENT_GROUP VARCHAR(255), ISIN VARCHAR(255), INSTRUMENT_NAME VARCHAR(255) NOT NULL, INSTRUMENT_SYMBOL VARCHAR(255) NOT NULL, VER_NO BIGINT, PRIMARY KEY (INSTRUMENT_ID));
CREATE TABLE EQUITIES (INSTRUMENT_ID BIGINT NOT NULL, STOCK_EXCHANGE VARCHAR(255), MTM_CANDIDATE TINYINT(1) default 0, NSE_CODE VARCHAR(255), PRIMARY KEY (INSTRUMENT_ID));
CREATE TABLE PORTFOLIO (PORTFOLIO_ID BIGINT NOT NULL, PORTFOLIO_NAME VARCHAR(255) NOT NULL, VER_NO BIGINT, WEALTH_CONTEXT_ID BIGINT NOT NULL, PRIMARY KEY (PORTFOLIO_ID));
CREATE TABLE HOLDINGS (HOLDING_ID BIGINT NOT NULL, AMOUNT DECIMAL(38) NOT NULL, BROKERAGE DECIMAL(38), PRICE DECIMAL(38) NOT NULL, PROFIT DECIMAL(38), QUANTITY DECIMAL(38) NOT NULL, TOTAL_AMOUNT DECIMAL(38) NOT NULL, VER_NO BIGINT, BROKER_ID BIGINT, INSTRUMENT_ID BIGINT NOT NULL, PORTFOLIO_ID BIGINT, WEALTH_CONTEXT_ID BIGINT NOT NULL, PRIMARY KEY (HOLDING_ID));
CREATE TABLE BROKERAGE_STRUCTURES (EXPENSE_ID BIGINT NOT NULL, BROKERAGE DECIMAL(38), INSTITUTION VARCHAR(255), MIN_BROKERAGE DECIMAL(38), OTHER_CHARGES DECIMAL(38), SERVICE_TAX DECIMAL(38), TRANSACTION_TAX DECIMAL(38), VER_NO BIGINT, PRIMARY KEY (EXPENSE_ID));
CREATE TABLE TRANSACTIONS (TRAN_ID BIGINT NOT NULL, AMOUNT DECIMAL(38) NOT NULL, BROKERAGE DECIMAL(38), EXCHANGE VARCHAR(255) NOT NULL, OTHER_CHRGS DECIMAL(38), PRICE DECIMAL(38) NOT NULL, QUANTITY DECIMAL(38) NOT NULL, TOTAL_AMOUNT DECIMAL(38) NOT NULL, TRADE_DATE DATE NOT NULL, TRADED TINYINT(1) default 0 NOT NULL, TRANSACTION_TYPE VARCHAR(255) NOT NULL, VER_NO BIGINT, BROKER_ID BIGINT, INSTRUMENT_ID BIGINT NOT NULL, PORTFOLIO_ID BIGINT, PRIMARY KEY (TRAN_ID));
CREATE TABLE STOCK_PREFERENCE (FAV_STOCK_ID BIGINT NOT NULL, BUY_PRICE DECIMAL(38), DESCRIPTION VARCHAR(255), LNG_TARGET_PRICE DECIMAL(38), TARGET_PRICE DECIMAL(38), VER_NO BIGINT, USER_ID BIGINT NOT NULL, STOCK_ID BIGINT, PRIMARY KEY (FAV_STOCK_ID));
CREATE TABLE MTMS (MTM_ID BIGINT NOT NULL, CLOSE_PRICE DECIMAL(38), DAYS_HIGH DECIMAL(38), DAYS_LOW DECIMAL(38), LAST_TRADED_PRICE DECIMAL(38), OPEN_PRICE DECIMAL(38), PREVIOUS_CLOSE DECIMAL(38), SERIES VARCHAR(255), SYMBOL VARCHAR(255), TOTAL_TRADED_QTY DECIMAL(38), TOTAL_TRADED_VALUE DECIMAL(38), TRADE_DATE DATE, VER_NO BIGINT, INSTRUMENT_ID BIGINT, PRIMARY KEY (MTM_ID));
CREATE TABLE USER_WEALTH_CONTEXT (WEALTH_CONTEXT_ID BIGINT NOT NULL, VER_NO BIGINT, USER_FK BIGINT UNIQUE, PRIMARY KEY (WEALTH_CONTEXT_ID));
CREATE TABLE USER_AUTHORITIES (USER_ID BIGINT NOT NULL, AUTHORITY_ID BIGINT NOT NULL, PRIMARY KEY (USER_ID, AUTHORITY_ID));
CREATE TABLE SUPPORTWEAVEEXTENSIBLE_EXTENSIONVALUE (SupportWeaveExtensible_ID BIGINT NOT NULL, extensions_EXTN_VAL_ID BIGINT NOT NULL, PRIMARY KEY (SupportWeaveExtensible_ID, extensions_EXTN_VAL_ID));
CREATE TABLE FAV_STOCKS (USER_ID BIGINT NOT NULL, STOCK_ID BIGINT NOT NULL, PRIMARY KEY (USER_ID, STOCK_ID));
ALTER TABLE CONFIGURATION_ITEMS ADD CONSTRAINT UNQ_CONFIGURATION_ITEMS_0 UNIQUE (CONFIG_ITEM_KEY);
ALTER TABLE CONFIGURATIONS ADD CONSTRAINT UNQ_CONFIGURATIONS_0 UNIQUE (USER_ID, CONFIG_ITEM_ID);
ALTER TABLE EXPENSE_TYPES ADD CONSTRAINT UNQ_EXPENSE_TYPES_0 UNIQUE (USER_EXP_FK, EXP_TYPE);
ALTER TABLE INSTRUMENTS ADD CONSTRAINT UNQ_INSTRUMENTS_0 UNIQUE (INSTRUMENT_SYMBOL);
ALTER TABLE HOLDINGS ADD CONSTRAINT UNQ_HOLDINGS_0 UNIQUE (INSTRUMENT_ID, WEALTH_CONTEXT_ID, PORTFOLIO_ID);
ALTER TABLE BROKERAGE_STRUCTURES ADD CONSTRAINT UNQ_BROKERAGE_STRUCTURES_0 UNIQUE (INSTITUTION);
ALTER TABLE MTMS ADD CONSTRAINT UNQ_MTMS_0 UNIQUE (SYMBOL, SERIES, TRADE_DATE);
ALTER TABLE EXTENSIONVALUE ADD CONSTRAINT FK_EXTENSIONVALUE_EXTN_ID FOREIGN KEY (EXTN_ID) REFERENCES EXTENSIONS (EXTENSION_ID);
ALTER TABLE SESSION_REGISTRY ADD CONSTRAINT FK_SESSION_REGISTRY_PRINCIPAL FOREIGN KEY (PRINCIPAL) REFERENCES USERS (USER_ID);
ALTER TABLE CONFIGURATIONS ADD CONSTRAINT FK_CONFIGURATIONS_USER_ID FOREIGN KEY (USER_ID) REFERENCES USERS (USER_ID);
ALTER TABLE CONFIGURATIONS ADD CONSTRAINT FK_CONFIGURATIONS_CONFIG_ITEM_ID FOREIGN KEY (CONFIG_ITEM_ID) REFERENCES CONFIGURATION_ITEMS (CONFIG_ITEM_ID);
ALTER TABLE EXPENSES ADD CONSTRAINT FK_EXPENSES_TYPE_ID FOREIGN KEY (TYPE_ID) REFERENCES EXPENSE_TYPES (TYPE_ID);
ALTER TABLE EXPENSES ADD CONSTRAINT FK_EXPENSES_USER_EXP_FK FOREIGN KEY (USER_EXP_FK) REFERENCES USER_EXPENSES (USER_EXP_ID);
ALTER TABLE FORECAST_EXPENSE ADD CONSTRAINT FK_FORECAST_EXPENSE_USER_EXP_FK FOREIGN KEY (USER_EXP_FK) REFERENCES USER_EXPENSES (USER_EXP_ID);
ALTER TABLE USER_EXPENSES ADD CONSTRAINT FK_USER_EXPENSES_USER_FK FOREIGN KEY (USER_FK) REFERENCES USERS (USER_ID);
ALTER TABLE EXPENSE_TYPES ADD CONSTRAINT FK_EXPENSE_TYPES_USER_EXP_FK FOREIGN KEY (USER_EXP_FK) REFERENCES USER_EXPENSES (USER_EXP_ID);
ALTER TABLE EQUITIES ADD CONSTRAINT FK_EQUITIES_INSTRUMENT_ID FOREIGN KEY (INSTRUMENT_ID) REFERENCES INSTRUMENTS (INSTRUMENT_ID);
ALTER TABLE PORTFOLIO ADD CONSTRAINT FK_PORTFOLIO_WEALTH_CONTEXT_ID FOREIGN KEY (WEALTH_CONTEXT_ID) REFERENCES USER_WEALTH_CONTEXT (WEALTH_CONTEXT_ID);
ALTER TABLE HOLDINGS ADD CONSTRAINT FK_HOLDINGS_BROKER_ID FOREIGN KEY (BROKER_ID) REFERENCES BROKERAGE_STRUCTURES (EXPENSE_ID);
ALTER TABLE HOLDINGS ADD CONSTRAINT FK_HOLDINGS_PORTFOLIO_ID FOREIGN KEY (PORTFOLIO_ID) REFERENCES PORTFOLIO (PORTFOLIO_ID);
ALTER TABLE HOLDINGS ADD CONSTRAINT FK_HOLDINGS_INSTRUMENT_ID FOREIGN KEY (INSTRUMENT_ID) REFERENCES INSTRUMENTS (INSTRUMENT_ID);
ALTER TABLE HOLDINGS ADD CONSTRAINT FK_HOLDINGS_WEALTH_CONTEXT_ID FOREIGN KEY (WEALTH_CONTEXT_ID) REFERENCES USER_WEALTH_CONTEXT (WEALTH_CONTEXT_ID);
ALTER TABLE TRANSACTIONS ADD CONSTRAINT FK_TRANSACTIONS_PORTFOLIO_ID FOREIGN KEY (PORTFOLIO_ID) REFERENCES PORTFOLIO (PORTFOLIO_ID);
ALTER TABLE TRANSACTIONS ADD CONSTRAINT FK_TRANSACTIONS_INSTRUMENT_ID FOREIGN KEY (INSTRUMENT_ID) REFERENCES INSTRUMENTS (INSTRUMENT_ID);
ALTER TABLE TRANSACTIONS ADD CONSTRAINT FK_TRANSACTIONS_BROKER_ID FOREIGN KEY (BROKER_ID) REFERENCES BROKERAGE_STRUCTURES (EXPENSE_ID);
ALTER TABLE STOCK_PREFERENCE ADD CONSTRAINT FK_STOCK_PREFERENCE_USER_ID FOREIGN KEY (USER_ID) REFERENCES USERS (USER_ID);
ALTER TABLE STOCK_PREFERENCE ADD CONSTRAINT FK_STOCK_PREFERENCE_STOCK_ID FOREIGN KEY (STOCK_ID) REFERENCES INSTRUMENTS (INSTRUMENT_ID);
ALTER TABLE MTMS ADD CONSTRAINT FK_MTMS_INSTRUMENT_ID FOREIGN KEY (INSTRUMENT_ID) REFERENCES INSTRUMENTS (INSTRUMENT_ID);
ALTER TABLE USER_WEALTH_CONTEXT ADD CONSTRAINT FK_USER_WEALTH_CONTEXT_USER_FK FOREIGN KEY (USER_FK) REFERENCES USERS (USER_ID);
ALTER TABLE USER_AUTHORITIES ADD CONSTRAINT FK_USER_AUTHORITIES_AUTHORITY_ID FOREIGN KEY (AUTHORITY_ID) REFERENCES AUTHORITIES (AUTHORITY_ID);
ALTER TABLE USER_AUTHORITIES ADD CONSTRAINT FK_USER_AUTHORITIES_USER_ID FOREIGN KEY (USER_ID) REFERENCES USERS (USER_ID);
ALTER TABLE SUPPORTWEAVEEXTENSIBLE_EXTENSIONVALUE ADD CONSTRAINT SPPRTWEAVEEXTENSIBLEEXTENSIONVALUExtnsonsEXTNVALID FOREIGN KEY (extensions_EXTN_VAL_ID) REFERENCES EXTENSIONVALUE (EXTN_VAL_ID);
ALTER TABLE SUPPORTWEAVEEXTENSIBLE_EXTENSIONVALUE ADD CONSTRAINT SPPRTWVEXTENSIBLEEXTENSIONVALUESpprtWvExtensibleID FOREIGN KEY (SupportWeaveExtensible_ID) REFERENCES SUPPORTWEAVEEXTENSIBLE (ID);
ALTER TABLE FAV_STOCKS ADD CONSTRAINT FK_FAV_STOCKS_USER_ID FOREIGN KEY (USER_ID) REFERENCES USER_WEALTH_CONTEXT (WEALTH_CONTEXT_ID);
ALTER TABLE FAV_STOCKS ADD CONSTRAINT FK_FAV_STOCKS_STOCK_ID FOREIGN KEY (STOCK_ID) REFERENCES INSTRUMENTS (INSTRUMENT_ID);
CREATE TABLE ID_GEN (ID_NAME VARCHAR(50) NOT NULL, ID_VAL DECIMAL(38), PRIMARY KEY (ID_NAME));
CREATE TABLE SEQUENCE (SEQ_NAME VARCHAR(50) NOT NULL, SEQ_COUNT DECIMAL(38), PRIMARY KEY (SEQ_NAME));
INSERT INTO ID_GEN(ID_NAME, ID_VAL) values ('EXTENSION_SEQ', 0);
INSERT INTO ID_GEN(ID_NAME, ID_VAL) values ('MTM_SEQ', 0);
INSERT INTO ID_GEN(ID_NAME, ID_VAL) values ('FORECAST_SEQ', 0);
INSERT INTO ID_GEN(ID_NAME, ID_VAL) values ('FAV_STOCK_SEQ', 0);
INSERT INTO SEQUENCE(SEQ_NAME, SEQ_COUNT) values ('SEQ_GEN', 0);
INSERT INTO ID_GEN(ID_NAME, ID_VAL) values ('USER_SEQ', 0);
INSERT INTO ID_GEN(ID_NAME, ID_VAL) values ('CONFIG_ITEM_SEQ', 0);
INSERT INTO ID_GEN(ID_NAME, ID_VAL) values ('WEALTH_CONTEXT_SEQ', 0);
INSERT INTO ID_GEN(ID_NAME, ID_VAL) values ('WM_PARAMS_SEQ', 0);
INSERT INTO ID_GEN(ID_NAME, ID_VAL) values ('INSTRUMENT_SEQ', 0);
INSERT INTO ID_GEN(ID_NAME, ID_VAL) values ('CONFIG_SEQ', 0);
INSERT INTO ID_GEN(ID_NAME, ID_VAL) values ('EXP_TYPE_SEQ', 0);
INSERT INTO ID_GEN(ID_NAME, ID_VAL) values ('EXTN_VAL_SEQ', 0);
INSERT INTO ID_GEN(ID_NAME, ID_VAL) values ('FOLIO_SEQ', 0);
INSERT INTO ID_GEN(ID_NAME, ID_VAL) values ('BROKERAGE_SEQ', 0);
INSERT INTO ID_GEN(ID_NAME, ID_VAL) values ('HOLDING_SEQ', 0);
INSERT INTO ID_GEN(ID_NAME, ID_VAL) values ('AUTH_SEQ', 0);
INSERT INTO ID_GEN(ID_NAME, ID_VAL) values ('TXN_SEQ', 0);
INSERT INTO ID_GEN(ID_NAME, ID_VAL) values ('SESSION_REGISTRY_SEQ', 0);
INSERT INTO ID_GEN(ID_NAME, ID_VAL) values ('EMP_SEQ', 0);
INSERT INTO ID_GEN(ID_NAME, ID_VAL) values ('USER_EXP_SEQ', 0);
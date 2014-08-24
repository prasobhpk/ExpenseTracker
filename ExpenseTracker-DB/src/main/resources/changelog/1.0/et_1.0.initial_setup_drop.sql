ALTER TABLE EXTENSIONVALUE DROP FOREIGN KEY FK_EXTENSIONVALUE_EXTN_ID;
ALTER TABLE CONFIGURATION_ITEMS DROP FOREIGN KEY UNQ_CONFIGURATION_ITEMS_0;
ALTER TABLE SESSION_REGISTRY DROP FOREIGN KEY FK_SESSION_REGISTRY_PRINCIPAL;
ALTER TABLE CONFIGURATIONS DROP FOREIGN KEY FK_CONFIGURATIONS_USER_ID;
ALTER TABLE CONFIGURATIONS DROP FOREIGN KEY FK_CONFIGURATIONS_CONFIG_ITEM_ID;
ALTER TABLE CONFIGURATIONS DROP FOREIGN KEY UNQ_CONFIGURATIONS_0;
ALTER TABLE EXPENSES DROP FOREIGN KEY FK_EXPENSES_TYPE_ID;
ALTER TABLE EXPENSES DROP FOREIGN KEY FK_EXPENSES_USER_EXP_FK;
ALTER TABLE FORECAST_EXPENSE DROP FOREIGN KEY FK_FORECAST_EXPENSE_USER_EXP_FK;
ALTER TABLE USER_EXPENSES DROP FOREIGN KEY FK_USER_EXPENSES_USER_FK;
ALTER TABLE EXPENSE_TYPES DROP FOREIGN KEY FK_EXPENSE_TYPES_USER_EXP_FK;
ALTER TABLE EXPENSE_TYPES DROP FOREIGN KEY UNQ_EXPENSE_TYPES_0;
ALTER TABLE INSTRUMENTS DROP FOREIGN KEY UNQ_INSTRUMENTS_0;
ALTER TABLE EQUITIES DROP FOREIGN KEY FK_EQUITIES_INSTRUMENT_ID;
ALTER TABLE PORTFOLIO DROP FOREIGN KEY FK_PORTFOLIO_WEALTH_CONTEXT_ID;
ALTER TABLE HOLDINGS DROP FOREIGN KEY FK_HOLDINGS_BROKER_ID;
ALTER TABLE HOLDINGS DROP FOREIGN KEY FK_HOLDINGS_PORTFOLIO_ID;
ALTER TABLE HOLDINGS DROP FOREIGN KEY FK_HOLDINGS_INSTRUMENT_ID;
ALTER TABLE HOLDINGS DROP FOREIGN KEY FK_HOLDINGS_WEALTH_CONTEXT_ID;
ALTER TABLE HOLDINGS DROP FOREIGN KEY UNQ_HOLDINGS_0;
ALTER TABLE BROKERAGE_STRUCTURES DROP FOREIGN KEY UNQ_BROKERAGE_STRUCTURES_0;
ALTER TABLE TRANSACTIONS DROP FOREIGN KEY FK_TRANSACTIONS_PORTFOLIO_ID;
ALTER TABLE TRANSACTIONS DROP FOREIGN KEY FK_TRANSACTIONS_INSTRUMENT_ID;
ALTER TABLE TRANSACTIONS DROP FOREIGN KEY FK_TRANSACTIONS_BROKER_ID;
ALTER TABLE STOCK_PREFERENCE DROP FOREIGN KEY FK_STOCK_PREFERENCE_USER_ID;
ALTER TABLE STOCK_PREFERENCE DROP FOREIGN KEY FK_STOCK_PREFERENCE_STOCK_ID;
ALTER TABLE MTMS DROP FOREIGN KEY FK_MTMS_INSTRUMENT_ID;
ALTER TABLE MTMS DROP FOREIGN KEY UNQ_MTMS_0;
ALTER TABLE USER_WEALTH_CONTEXT DROP FOREIGN KEY FK_USER_WEALTH_CONTEXT_USER_FK;
ALTER TABLE USER_AUTHORITIES DROP FOREIGN KEY FK_USER_AUTHORITIES_AUTHORITY_ID;
ALTER TABLE USER_AUTHORITIES DROP FOREIGN KEY FK_USER_AUTHORITIES_USER_ID;
ALTER TABLE SUPPORTWEAVEEXTENSIBLE_EXTENSIONVALUE DROP FOREIGN KEY SPPRTWEAVEEXTENSIBLEEXTENSIONVALUExtnsonsEXTNVALID;
ALTER TABLE SUPPORTWEAVEEXTENSIBLE_EXTENSIONVALUE DROP FOREIGN KEY SPPRTWVEXTENSIBLEEXTENSIONVALUESpprtWvExtensibleID;
ALTER TABLE FAV_STOCKS DROP FOREIGN KEY FK_FAV_STOCKS_USER_ID;
ALTER TABLE FAV_STOCKS DROP FOREIGN KEY FK_FAV_STOCKS_STOCK_ID;
DROP TABLE EXTENSIONVALUE;
DROP TABLE USERS;
DROP TABLE SUPPORTWEAVEEXTENSIBLE;
DROP TABLE EXTENSIONS;
DROP INDEX INDEX_CONFIGURATION_ITEMS_CONFIG_ITEM_KEY ON CONFIGURATION_ITEMS;
DROP TABLE CONFIGURATION_ITEMS;
DROP TABLE SESSION_REGISTRY;
DROP TABLE SUPPORTWEAVE;
DROP TABLE WM_PARAMS;
DROP TABLE CONFIGURATIONS;
DROP TABLE AUTHORITIES;
DROP TABLE EXPENSES;
DROP TABLE FORECAST_EXPENSE;
DROP TABLE USER_EXPENSES;
DROP TABLE EXPENSE_TYPES;
DROP TABLE INSTRUMENTS;
DROP TABLE EQUITIES;
DROP TABLE PORTFOLIO;
DROP TABLE HOLDINGS;
DROP TABLE BROKERAGE_STRUCTURES;
DROP TABLE TRANSACTIONS;
DROP TABLE STOCK_PREFERENCE;
DROP TABLE MTMS;
DROP TABLE USER_WEALTH_CONTEXT;
DROP TABLE USER_AUTHORITIES;
DROP TABLE SUPPORTWEAVEEXTENSIBLE_EXTENSIONVALUE;
DROP TABLE FAV_STOCKS;
DELETE FROM ID_GEN WHERE ID_NAME = 'EMP_SEQ';
DELETE FROM ID_GEN WHERE ID_NAME = 'WEALTH_CONTEXT_SEQ';
DELETE FROM ID_GEN WHERE ID_NAME = 'INSTRUMENT_SEQ';
DELETE FROM ID_GEN WHERE ID_NAME = 'HOLDING_SEQ';
DELETE FROM ID_GEN WHERE ID_NAME = 'USER_SEQ';
DELETE FROM ID_GEN WHERE ID_NAME = 'MTM_SEQ';
DELETE FROM ID_GEN WHERE ID_NAME = 'CONFIG_ITEM_SEQ';
DELETE FROM ID_GEN WHERE ID_NAME = 'EXTN_VAL_SEQ';
DELETE FROM ID_GEN WHERE ID_NAME = 'FOLIO_SEQ';
DELETE FROM ID_GEN WHERE ID_NAME = 'AUTH_SEQ';
DELETE FROM ID_GEN WHERE ID_NAME = 'FORECAST_SEQ';
DELETE FROM ID_GEN WHERE ID_NAME = 'EXP_TYPE_SEQ';
DELETE FROM SEQUENCE WHERE SEQ_NAME = 'SEQ_GEN';
DELETE FROM ID_GEN WHERE ID_NAME = 'CONFIG_SEQ';
DELETE FROM ID_GEN WHERE ID_NAME = 'TXN_SEQ';
DELETE FROM ID_GEN WHERE ID_NAME = 'WM_PARAMS_SEQ';
DELETE FROM ID_GEN WHERE ID_NAME = 'FAV_STOCK_SEQ';
DELETE FROM ID_GEN WHERE ID_NAME = 'EXTENSION_SEQ';
DELETE FROM ID_GEN WHERE ID_NAME = 'BROKERAGE_SEQ';
DELETE FROM ID_GEN WHERE ID_NAME = 'USER_EXP_SEQ';
DELETE FROM ID_GEN WHERE ID_NAME = 'SESSION_REGISTRY_SEQ';
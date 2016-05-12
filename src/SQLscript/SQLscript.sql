DROP TABLE MONTH_TRANSACTION_TBL;
DROP TABLE MONTH_TBL;
DROP SEQUENCE MONTH_ID_SEQ;
DROP SEQUENCE MONTH_TRANSACTION_ID_SEQ;

CREATE SEQUENCE MONTH_ID_SEQ  
  MINVALUE 0 
  MAXVALUE 99999999999999 
  INCREMENT BY 1 
  START WITH 0;

CREATE SEQUENCE MONTH_TRANSACTION_ID_SEQ  
  MINVALUE 0 
  MAXVALUE 99999999999999 
  INCREMENT BY 1 
  START WITH 0;


CREATE TABLE MONTH_TBL (
  ID NUMBER,
  NAME VARCHAR2(15),
  CONSTRAINT MONTH_PK PRIMARY KEY (ID)
  );

CREATE TABLE MONTH_TRANSACTION_TBL (
  ID NUMBER,
  MONTH_ID NUMBER,
  NAME VARCHAR(30),
  AMOUNT NUMBER,
  TYPE VARCHAR2(20),
  CONSTRAINT MONTH_TRANSACTION_PK PRIMARY KEY (ID),
  CONSTRAINT MONTH_TRANSACTION_FK FOREIGN KEY (MONTH_ID) REFERENCES MONTH_TBL
  );
  
INSERT INTO MONTH_TBL VALUES(MONTH_ID_SEQ.NEXTVAL, 'January 2016'); 
INSERT INTO MONTH_TBL VALUES(MONTH_ID_SEQ.NEXTVAL, 'February 2016'); 
INSERT INTO MONTH_TBL VALUES(MONTH_ID_SEQ.NEXTVAL, 'March 2016'); 
INSERT INTO MONTH_TBL VALUES(MONTH_ID_SEQ.NEXTVAL, 'April 2016'); 
INSERT INTO MONTH_TBL VALUES(MONTH_ID_SEQ.NEXTVAL, 'May 2016'); 
INSERT INTO MONTH_TBL VALUES(MONTH_ID_SEQ.NEXTVAL, 'June 2016'); 

INSERT INTO MONTH_TRANSACTION_TBL VALUES(CPHCD77.MONTH_TRANSACTION_ID_SEQ.NEXTVAL, 1, 'accommodation', 7000, 'expense'); 
INSERT INTO MONTH_TRANSACTION_TBL VALUES(CPHCD77.MONTH_TRANSACTION_ID_SEQ.NEXTVAL, 1, 'transportation', 2000, 'expense'); 
INSERT INTO MONTH_TRANSACTION_TBL VALUES(CPHCD77.MONTH_TRANSACTION_ID_SEQ.NEXTVAL, 1, 'food', 1200, 'expense'); 

INSERT INTO MONTH_TRANSACTION_TBL VALUES(CPHCD77.MONTH_TRANSACTION_ID_SEQ.NEXTVAL, 1, 'salary', 21738, 'income'); 
INSERT INTO MONTH_TRANSACTION_TBL VALUES(CPHCD77.MONTH_TRANSACTION_ID_SEQ.NEXTVAL, 2, 'salary', 28214, 'income');
INSERT INTO MONTH_TRANSACTION_TBL VALUES(CPHCD77.MONTH_TRANSACTION_ID_SEQ.NEXTVAL, 3, 'salary', 25108, 'income');
INSERT INTO MONTH_TRANSACTION_TBL VALUES(CPHCD77.MONTH_TRANSACTION_ID_SEQ.NEXTVAL, 4, 'salary', 20136, 'income');
INSERT INTO MONTH_TRANSACTION_TBL VALUES(CPHCD77.MONTH_TRANSACTION_ID_SEQ.NEXTVAL, 5, 'salary', 27444, 'income');
INSERT INTO MONTH_TRANSACTION_TBL VALUES(CPHCD77.MONTH_TRANSACTION_ID_SEQ.NEXTVAL, 6, 'salary', 22890, 'income');

COMMIT;
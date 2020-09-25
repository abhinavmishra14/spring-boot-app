DROP TABLE IF EXISTS exchange_rates;

CREATE TABLE exchange_rates (
    id bigint,
    currency_from varchar(20),
    currency_to varchar(20),
    conversion_multiple decimal,
    port int
);

insert into exchange_rates(id,currency_from,currency_to,conversion_multiple,port)
values(10001,'USD','INR',74,0);
insert into exchange_rates(id,currency_from,currency_to,conversion_multiple,port)
values(10002,'EUR','INR',86,0);
insert into exchange_rates(id,currency_from,currency_to,conversion_multiple,port)
values(10003,'CAD','INR',55,0);
insert into exchange_rates(id,currency_from,currency_to,conversion_multiple,port)
values(10003,'NZD','INR',48,0);
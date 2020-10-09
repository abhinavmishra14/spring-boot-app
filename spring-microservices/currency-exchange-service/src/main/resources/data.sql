DROP TABLE IF EXISTS exchange_rates;

CREATE TABLE exchange_rates (
    id bigint,
    currency_from varchar(20),
    currency_to varchar(20),
    conversion_multiple decimal,
    port int
);

insert into exchange_rates(id,currency_from,currency_to,conversion_multiple,port)
values(10001,'USD','INR',73.28,0);
insert into exchange_rates(id,currency_from,currency_to,conversion_multiple,port)
values(10001,'INR','USD',0.014,0);
insert into exchange_rates(id,currency_from,currency_to,conversion_multiple,port)
values(10002,'EUR','INR',86.05,0);
insert into exchange_rates(id,currency_from,currency_to,conversion_multiple,port)
values(10002,'INR','EUR',0.012,0);
insert into exchange_rates(id,currency_from,currency_to,conversion_multiple,port)
values(10003,'CAD','INR',55.14,0);
insert into exchange_rates(id,currency_from,currency_to,conversion_multiple,port)
values(10003,'INR','CAD',0.018,0);
insert into exchange_rates(id,currency_from,currency_to,conversion_multiple,port)
values(10003,'NZD','INR',48.70,0);
insert into exchange_rates(id,currency_from,currency_to,conversion_multiple,port)
values(10003,'INR','NZD',0.021,0);
insert into exchange_rates(id,currency_from,currency_to,conversion_multiple,port)
values(10003,'GBP','INR',94.41,0);
insert into exchange_rates(id,currency_from,currency_to,conversion_multiple,port)
values(10003,'INR','GBP',0.011,0);
insert into exchange_rates(id,currency_from,currency_to,conversion_multiple,port)
values(10003,'AUD','INR',52.61,0);
insert into exchange_rates(id,currency_from,currency_to,conversion_multiple,port)
values(10003,'INR','AUD',0.019,0);
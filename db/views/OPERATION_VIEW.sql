create or replace view OPERATION_VIEW
as
select
    o.id as operation_id,
    o.account_id,
    c.id as client_id,
    c.email as client_email,
    o.operation_date,
    o.transaction_sum,
    o.operation_kind,
    a.balance,
    a.account_name as account_name,
    a.currency as account_currency,
    o.currency_from,
    concat(c.last_name, ' ', c.first_name) as owner_full_name
from operations o
join account a on a.id = o.account_id
join client c on c.id = a.client_id
create or replace view ACCOUNT_VIEW
as
select
    row_number() over (order by client_id) as id,
    a.id as account_id,
    concat(c.first_name, ' ', c.last_name) as full_name,
    c.id as client_id,
    a.account_name,
    a.balance,
    a.currency,
    a.is_active,
    o.number_of_operations,
    o.latest_operation
from client c
left join account a on c.id = a.client_id
left join
(
    select
        account_id,
        max(operation_date) as latest_operation,
        count(*) as number_of_operations
    from operations
    group by account_id
) o on o.account_id = a.id

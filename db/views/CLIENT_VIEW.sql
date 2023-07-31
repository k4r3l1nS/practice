create or replace view CLIENT_VIEW
as
select
    c.id,
    c.last_name,
    c.first_name,
    c.registration_date,
    c.birth_date,
    c.is_active,
    c.email,
    coalesce(a.number_of_accounts, 0) as number_of_accounts
from client c
left join
(
    select client_id,
           coalesce(count(*), 0) as number_of_accounts
    from account
    where is_active = true
    group by client_id
) a on a.client_id = c.id

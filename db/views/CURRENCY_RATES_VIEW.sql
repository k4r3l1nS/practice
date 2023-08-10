create or replace view CURRENCY_RATES_VIEW
as
select
    cr.currency,
    cr.value,
    cr.char_code,
    lcru.value as last_update
from currency_rates cr, last_currency_rates_update lcru
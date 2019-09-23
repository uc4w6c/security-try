select
    email
    , birthday
    , enabled
from
    accounts
where
    email = /* email */'foo@foo.com'
    and birthday = /* birthday */'2019/1/1'
    and enabled = true;

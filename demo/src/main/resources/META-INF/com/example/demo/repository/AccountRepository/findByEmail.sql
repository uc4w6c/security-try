select
    email
    , password_digest
    , birthday
    , enabled
from
    accounts
where
    email = /* email */'taro'

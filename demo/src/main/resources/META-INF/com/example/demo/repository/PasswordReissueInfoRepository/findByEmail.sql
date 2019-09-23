select
    email
    , token
    , expiry_date
from
    password_reissue_info
where
    email = /* email */'foo@foo.com'

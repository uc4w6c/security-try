select
    email
    , token
    , expiry_date
from
    password_reissue_info
where
    token = /* token */'foo@foo.com'
    and expiry_date >= /* nowTime */'#2019/1/1 12:00:00#'

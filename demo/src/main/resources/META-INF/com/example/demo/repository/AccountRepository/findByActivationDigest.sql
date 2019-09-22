select
    email
    , enabled
from
    accounts
where
    activation_digest = /* activationDigest */'XXXXXXXXXXXX'
    and enabled = false;

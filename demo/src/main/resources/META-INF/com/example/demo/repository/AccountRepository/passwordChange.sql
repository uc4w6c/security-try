update
    accounts
set
    password_digest = /* passwordDigest */'password'
where
    email = /* email */'foo@foo.com'

select
    id
    , email
    , body
    , created_at
from
    posts
where
    deleted_at is null
    and body like /*# queryBody */

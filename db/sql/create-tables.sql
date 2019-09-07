---- create ----
create table testdb.accounts
(
 username        varchar(50) not null
 , password        varchar(250) not null
 , birthday        date not null
 , enabled         boolean not null default 0
 ,   primary key (username)
);


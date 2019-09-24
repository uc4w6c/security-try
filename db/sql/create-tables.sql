---- create ----
create table testdb.accounts
(
 email                  varchar(50) not null
 , password_digest      varchar(250) not null
 , birthday             date not null
 , activation_digest    varchar(250)
 , enabled         boolean not null default 0
 ,   primary key (email)
);

create table testdb.password_reissue_info
(
 email                  varchar(50) not null
 , token                varchar(250) not null
 , expiry_date          datetime not null
 ,   primary key (email)
 ,   foreign key fk_email (email) references accounts(email)
);

create table testdb.login_failure_manager
(
 email                  varchar(50) not null
 , failure_count        tinyint not null default 0
 , lock_count           tinyint not null default 0
 , last_fail_datetime   datetime
 ,   primary key (email)
 ,   foreign key fk_email (email) references accounts(email)
);


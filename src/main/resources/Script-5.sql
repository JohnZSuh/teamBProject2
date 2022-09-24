create table ers_reimbursements (
reimb_id uuid primary key default gen_random_uuid(),
amount NUMERIC(6,2) not NULL,
submitted TIMESTAMP not NULL,
resolved TIMESTAMP,
description VARCHAR not NULL,
author_id UUID not null,
resolver_id UUID,
status_id UUID not null,
type_id UUID not null,

foreign key (author_id) references ers_users (user_id),
foreign key (resolver_id) references ers_users (user_id),
foreign key (status_id) references ers_reimbursement_statuses (status_id),
foreign key (type_id) references ers_reimbursement_types (type_id)

);

create table ers_reimbursement_statuses (
status_id UUID primary key default gen_random_uuid(),
status VARCHAR unique
);

create table ers_reimbursement_types (
type_id UUID primary key default gen_random_uuid(),
type VARCHAR UNIQUE
);

create table ers_users (
user_id UUID primary key default gen_random_uuid(),
username VARCHAR not null unique,
email VARCHAR not null unique,
password VARCHAR not null,
given_name VARCHAR not null,
surname VARCHAR not null,
is_active BOOLEAN,
role_id UUID,

foreign key (role_id) references ers_user_roles (role_id)

);

create table ers_user_roles (
role_id uuid primary key default gen_random_uuid(),
role VARCHAR unique


);

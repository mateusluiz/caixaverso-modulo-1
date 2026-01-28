create table accounts (
  id uuid primary key,
  user_id uuid not null,
  name varchar(120) not null,
  type varchar(30) not null,
  initial_balance numeric(19,2) not null
);

create table categories (
  id uuid primary key,
  user_id uuid not null,
  name varchar(120) not null,
  type varchar(30) not null
);

create table transactions (
  id uuid primary key,
  user_id uuid not null,
  account_id uuid not null,
  category_id uuid not null,
  type varchar(30) not null,
  amount numeric(19,2) not null,
  date date not null,
  description varchar(255),

  constraint fk_tx_account foreign key (account_id) references accounts(id),
  constraint fk_tx_category foreign key (category_id) references categories(id)
);

create index idx_tx_user_date on transactions(user_id, date);
create index idx_tx_user_account_date on transactions(user_id, account_id, date);

-- Pessoa 1

insert into accounts (id, user_id, name, type, initial_balance)
values
('11111111-1111-1111-1111-111111111111', '00000000-0000-0000-0000-000000000001', 'Carteira', 'WALLET', 0.00);

insert into categories (id, user_id, name, type)
values
('22222222-2222-2222-2222-222222222222', '00000000-0000-0000-0000-000000000001', 'Alimentação', 'EXPENSE'),
('33333333-3333-3333-3333-333333333333', '00000000-0000-0000-0000-000000000001', 'Salário', 'INCOME');

-- Pessoa 2

insert into accounts (id, user_id, name, type, initial_balance)
values
('22222222-2222-2222-2222-222222222222', '00000000-0000-0000-0000-000000000002', 'Carteira', 'WALLET', 0.00);

insert into categories (id, user_id, name, type)
values
('44444444-4444-4444-4444-444444444444', '00000000-0000-0000-0000-000000000002', 'Lazer', 'EXPENSE'),
('55555555-5555-5555-5555-555555555555', '00000000-0000-0000-0000-000000000002', 'Pro Labore', 'INCOME');

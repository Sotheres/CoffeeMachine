-- Fill coffee machine on first start
INSERT INTO CoffeeMachine VALUES (1, 5) ON CONFLICT DO NOTHING;
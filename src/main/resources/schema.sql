CREATE TABLE IF NOT EXISTS CoffeeMachine (
    id                 SERIAL PRIMARY KEY,
    number_of_portions int NOT NULL
);

CREATE TABLE IF NOT EXISTS Coffee (
    id              SERIAL PRIMARY KEY,
    type            varchar(50) NOT NULL,
    time_of_brewing timestamp NOT NULL
);

CREATE TABLE cars
(
    id    SERIAL PRIMARY KEY,
    stamp VARCHAR  NOT NULL UNIQUE,
    model VARCHAR  NOT NULL UNIQUE,
    cost  money NOT NULL
);

CREATE TABLE humans
(
    id     SERIAL PRIMARY KEY,
    name   VARCHAR NOT NULL UNIQUE,
    age    INTEGER CHECK (age >= 18),
    rights BOOLEAN NOT NULL,
    car_id INTEGER NOT NULL REFERENCES cars (id)
);






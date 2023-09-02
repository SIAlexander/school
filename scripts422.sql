CREATE TABLE cars
(
    id    INTEGER PRIMARY KEY,
    stamp TEXT  NOT NULL UNIQUE,
    model TEXT  NOT NULL UNIQUE,
    cost  money NOT NULL
);

CREATE TABLE humans
(
    id     INTEGER PRIMARY KEY,
    name   TEXT,
    age    INTEGER CHECK (age >= 18),
    rights BOOLEAN NOT NULL,
    car_id INTEGER NOT NULL REFERENCES cars (id)
);






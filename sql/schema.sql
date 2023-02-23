CREATE TABLE employee (
    id BIGSERIAL NOT NULL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    gender VARCHAR(8) NOT NULL,
    date_of_birth VARCHAR(50) NOT NULL);

CREATE TABLE pay_route(
    id BIGSERIAL NOT NULL PRIMARY KEY,
    hour_rate INT NOT NULL,
    hours INT NOT NULL,
    ot_hours INT NOT NULL,
    reg_pay INT NOT NULL,
    ot_pay INT NOT NULL,
    gross_sal int not null,
    net_sal int not null,
    emp_id BIGINT REFERENCES employee (id) ON DELETE CASCADE);




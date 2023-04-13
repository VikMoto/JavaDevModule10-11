CREATE TABLE client (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(200) NOT NULL
);

CREATE TABLE planet (
  id VARCHAR(10) NOT NULL PRIMARY KEY,
  name VARCHAR(500) NOT NULL
);

CREATE TABLE ticket (
  id BIGSERIAL PRIMARY KEY,
  created_at TIMESTAMP,
  client_id BIGINT REFERENCES client(id),
  from_planet_id VARCHAR(10) REFERENCES planet(id),
  to_planet_id VARCHAR(10) REFERENCES planet(id)
);



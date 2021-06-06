CREATE TABLE IF NOT EXISTS rate (
    id               bigint GENERATED ALWAYS AS IDENTITY,
    valute_id        varchar(7) NOT NULL,
    num_code         smallint NOT NULL,
    char_code        char(3) NOT NULL,
    nominal          smallint NOT NULL,
    name             varchar(50) NOT NULL,
    value            numeric(7,4) NOT NULL,
    date             date NOT NULL,
    PRIMARY KEY(id)
);

CREATE INDEX IF NOT EXISTS idx_rate_date
ON rate(date DESC);

CREATE TABLE IF NOT EXISTS conversion (
    id               bigint GENERATED ALWAYS AS IDENTITY,
    source_code      char(3) NOT NULL,
    source_name      varchar(50) NOT NULL,
    target_code      char(3) NOT NULL,
    target_name      varchar(50) NOT NULL,
    source_amount    numeric(15,4) NOT NULL,
    target_amount    numeric(15,4) NOT NULL,
    rate_id          bigint,
    converted_at     timestamptz NOT NULL,
    PRIMARY KEY(id),
    CONSTRAINT fk_rate
        FOREIGN KEY(rate_id)
            REFERENCES rate(id)
);

CREATE INDEX IF NOT EXISTS idx_conversion_converted_at
ON conversion(converted_at DESC);


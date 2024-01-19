CREATE TABLE users (
                             user_id INT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) PRIMARY KEY,
                             username VARCHAR(255) NOT NULL
);

CREATE TABLE measurements (
                                    id INT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) PRIMARY KEY,
                                    user_id INT,
                                    date TIMESTAMP,
                                    type VARCHAR(255),
                                    value DECIMAL,
                                    month VARCHAR(255),
                                    year INT,
                                    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE VIEW v_last_measurements AS
SELECT
    m.id,
    m.user_id,
    m.date,
    m.type,
    m.value,
    m.month,
    m.year
FROM
    measurements m
WHERE
        (m.user_id, m.type, m.date) IN (
        SELECT
            user_id,
            type,
            MAX(date) AS max_date
        FROM
            measurements
        GROUP BY
            user_id,
            type
    );

CREATE INDEX idx_last_measurements
    ON v_last_measurements (user_id, type, date DESC);

INSERT INTO users (username) VALUES
                                       ('User1'),
                                       ('User2'),
                                       ('User3');


INSERT INTO measurements (user_id, date, type, value, month, year) VALUES
                                                                (1, '2023-01-01 12:00:00', 'GAS', 25.5, 'OCTOBER', 2023),
                                                                (1, '2023-01-02 14:30:00', 'COLD_WATER', 15.3, 'OCTOBER', 2023),
                                                                (2, '2023-01-01 13:45:00', 'GAS', 30.0, 'OCTOBER', 2023),
                                                                (2, '2023-01-02 15:30:00', 'HOT_WATER', 20.2, 'OCTOBER', 2023),
                                                                (3, '2023-01-01 11:15:00', 'GAS', 100.0, 'OCTOBER', 2023),
                                                                (3, '2023-01-02 16:00:00', 'COLD_WATER', 18.7, 'OCTOBER', 2023);
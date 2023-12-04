CREATE TABLE Poll(
    id bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    theme VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    user_id bigint REFERENCES users(id) ON DELETE CASCADE NOT NULL
);

CREATE TABLE Question(
    id bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    text VARCHAR(100) NOT NULL,
    poll_id bigint REFERENCES poll(id) ON DELETE CASCADE NOT NULL
);

CREATE TABLE Answer(
    id bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    text VARCHAR(100) NOT NULL,
    question_id bigint REFERENCES question(id) ON DELETE CASCADE NOT NULL
);

CREATE TABLE Vote(
    id bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    answer_id bigint REFERENCES answer(id) ON DELETE CASCADE NOT NULL,
    user_id bigint REFERENCES users(id) ON DELETE CASCADE NOT NULL,
    UNIQUE (answer_id, user_id)
);
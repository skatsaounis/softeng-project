CREATE TABLE user
(
    user_id        INTEGER     PRIMARY KEY AUTOINCREMENT,
    forwardee      INTEGER     DEFAULT '0',
    name           TEXT        NOT NULL UNIQUE,
    program        INTEGER     NOT NULL DEFAULT '1',
    p_registration TIMESTAMP   DEFAULT CURRENT_TIMESTAMP,
    online         INTEGER     DEFAULT '0',
    available      INTEGER     DEFAULT '1',
    FOREIGN KEY    (forwardee) REFERENCES user(id)
) ;

CREATE TABLE block
(
    block_id    INTEGER     PRIMARY KEY AUTOINCREMENT,
    blocker     INTEGER     NOT NULL,
    blockee     INTEGER     NOT NULL,
    FOREIGN KEY (blockee)   REFERENCES user(id),
    FOREIGN KEY (blocker)   REFERENCES user(id)
);

CREATE TABLE call
(
    call_id      INTEGER    PRIMARY KEY AUTOINCREMENT,
    caller       INTEGER    NOT NULL,
    callee       INTEGER    NOT NULL,
    callers_prog INTEGER    NOT NULL,
    start        TIMESTAMP  DEFAULT CURRENT_TIMESTAMP,
    end          TIMESTAMP  DEFAULT '0000-00-00 00:00:00',
    cost         REAL       NOT NULL,
    FOREIGN KEY  (caller)   REFERENCES user(id),
    FOREIGN KEY  (callee)   REFERENCES user(id)
) ;

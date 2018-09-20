CREATE TABLE IF NOT EXISTS user (
  `id`     int NOT NULL AUTO_INCREMENT,
  `username` varchar(40),
  `password` varchar(20),
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
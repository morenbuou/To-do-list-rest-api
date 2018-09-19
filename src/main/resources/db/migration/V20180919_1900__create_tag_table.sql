CREATE TABLE IF NOT EXISTS tag (
  `id`     int NOT NULL AUTO_INCREMENT,
  `label` varchar(40),
  `value` varchar(40),
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
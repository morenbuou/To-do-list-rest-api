CREATE TABLE IF NOT EXISTS todo (
  `id`     int NOT NULL AUTO_INCREMENT,
  `name` varchar(40),
  `status` varchar(20),
  `date`   date,
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
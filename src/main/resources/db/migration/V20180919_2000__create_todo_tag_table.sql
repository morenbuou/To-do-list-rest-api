create table if not exists todo_tag (
  `id`      int not null auto_increment,
  `todo_id` int not null,
  `tag_id`  int not null,
  primary key (id)
)
  engine = InnoDB
  default charset = utf8;
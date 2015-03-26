-- // add sys_knowledge_prove table
-- Migration SQL that makes the change goes here.
CREATE TABLE sys.sys_knowledge_prove (
  id SERIAL4 NOT NULL,
  user_id INT4 NOT NULL,
  knowledge_id INT4 NOT NULL,
  approved BOOL,
  score INT4,
  primary key (id),
  CONSTRAINT fk_approve_knowledge_id FOREIGN KEY (knowledge_id) REFERENCES sys.sys_knowledge(id),
  CONSTRAINT fk_approve_user_id FOREIGN KEY (user_id) REFERENCES sys.sys_user(id)
);
COMMENT ON TABLE sys.sys_knowledge_prove IS '评分信息';
COMMENT ON COLUMN sys.sys_knowledge_prove.approved IS '是否赞同';
COMMENT ON COLUMN sys.sys_knowledge_prove.score IS '分数';

-- //@UNDO
-- SQL to undo the change goes here.
DROP TABLE sys.sys_knowledge_prove;



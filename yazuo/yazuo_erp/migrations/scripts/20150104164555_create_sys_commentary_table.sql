-- // create sys_commentary table
-- Migration SQL that makes the change goes here.
CREATE TABLE sys.sys_commentary (
  id SERIAL4 NOT NULL,
  knowledge_id int4 NOT NULL,
  content text NOT NULL,
  user_id int4,
  PRIMARY KEY(id),
  CONSTRAINT fk_commentary_knowledge FOREIGN KEY (knowledge_id) REFERENCES sys.sys_knowledge (id),
  CONSTRAINT fk_commentary_user_id FOREIGN KEY (user_id) REFERENCES sys.sys_user(id)
);
COMMENT ON TABLE sys.sys_commentary IS '评论';
COMMENT ON COLUMN sys.sys_commentary.content IS '评论内容';

-- //@UNDO
-- SQL to undo the change goes here.
DROP TABLE sys.sys_commentary;


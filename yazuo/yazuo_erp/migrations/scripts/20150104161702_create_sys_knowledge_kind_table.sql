-- // create knowledge_kind table
-- Migration SQL that makes the change goes here.
CREATE TABLE sys.sys_knowledge_kind (
  id SERIAL,
  parent_id INT4,
  ancestors VARCHAR(255),
  title VARCHAR(255)
);
ALTER TABLE sys.sys_knowledge_kind ADD CONSTRAINT sys_knowledge_kind_pk PRIMARY KEY(id);
COMMENT ON TABLE sys.sys_knowledge_kind IS '知识库类型';
COMMENT ON COLUMN sys.sys_knowledge_kind.parent_id IS '上一级ID';
COMMENT ON COLUMN sys.sys_knowledge_kind.title IS '标题';
COMMENT ON COLUMN sys.sys_knowledge_kind.ancestors IS '包含上有线ID的所有ID';

-- //@UNDO
-- SQL to undo the change goes here.
DROP TABLE sys.sys_knowledge_kind;



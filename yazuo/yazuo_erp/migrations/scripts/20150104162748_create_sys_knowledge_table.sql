-- // create knowledge table
-- Migration SQL that makes the change goes here.
CREATE TABLE sys.sys_knowledge (
  id SERIAL4 NOT NULL,
  kind_id INT4 NOT NULL,
  title VARCHAR(255) NOT NULL,
  merchant_id INT4 NOT NULL,
  business_type_id INT4 NOT NULL,
  description TEXT,
  analysis TEXT,
  resolution TEXT,
  successful_case TEXT,
  talking_skills TEXT,
  viewed_times int4,
  attachment_id int4,
  updated_time TIMESTAMP NOT NULL,
  created_time TIMESTAMP NOT NULL,
  created_by INT4 NOT NULL,
  updated_by INT4 NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_knowledge_knowledge_kind_id FOREIGN KEY (kind_id) REFERENCES sys.sys_knowledge_kind (id),
  CONSTRAINT fk_knowledge_merchant_id FOREIGN KEY (kind_id) REFERENCES syn.syn_merchant(merchant_id),
  CONSTRAINT fk_knowledge_business_type_id FOREIGN KEY (kind_id) REFERENCES mkt.mkt_business_type(id),
  CONSTRAINT fk_knowledge_attachment_id FOREIGN KEY (attachment_id) REFERENCES sys.sys_attachment(id)
);

COMMENT ON table sys.sys_knowledge IS '知识库';
COMMENT ON COLUMN sys.sys_knowledge.title IS '标题';
COMMENT ON COLUMN sys.sys_knowledge.description IS '描述';
COMMENT ON COLUMN sys.sys_knowledge.analysis IS '分析';
COMMENT ON COLUMN sys.sys_knowledge.resolution IS '解决方案';
COMMENT ON COLUMN sys.sys_knowledge.successful_case IS '成功案例';
COMMENT ON COLUMN sys.sys_knowledge.talking_skills IS '话术';
COMMENT ON COLUMN sys.sys_knowledge.viewed_times IS '浏览次数';
COMMENT ON COLUMN sys.sys_knowledge.updated_time IS '更新时间';
COMMENT ON COLUMN sys.sys_knowledge.created_time IS '创建时间';
COMMENT ON COLUMN sys.sys_knowledge.created_by IS '作者ID';
COMMENT ON COLUMN sys.sys_knowledge.updated_by IS '最后更新人ID';

-- //@UNDO
-- SQL to undo the change goes here.
DROP TABLE sys.sys_knowledge;
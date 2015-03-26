-- // create sys_sms_template
-- Migration SQL that makes the change goes here.
create table sys.sys_sms_template(
   id serial4,
   title varchar(128) not null,
   content varchar(128) not null,
   created_time timestamp not null,
   tpl_type varchar(8) not null,
   role_types varchar(16)[],
   user_types varchar(16)[],
   group_ids int4[],
   user_ids int4[],
   position_ids int4[],
   primary key(id)
);
COMMENT ON TABLE sys.sys_sms_template IS '短信模板';
COMMENT ON COLUMN sys.sys_sms_template.title IS '模板名称';
COMMENT ON COLUMN sys.sys_sms_template.content IS '正文';
COMMENT ON COLUMN sys.sys_sms_template.tpl_type IS '模板类型';
COMMENT ON COLUMN sys.sys_sms_template.role_types IS '商户联系人';
COMMENT ON COLUMN sys.sys_sms_template.user_types IS '商户负责人';
COMMENT ON COLUMN sys.sys_sms_template.group_ids IS '部门';
COMMENT ON COLUMN sys.sys_sms_template.position_ids IS '职位';
COMMENT ON COLUMN sys.sys_sms_template.user_ids IS '员工';

-- //@UNDO
-- SQL to undo the change goes here.
drop table sys.sys_sms_template;

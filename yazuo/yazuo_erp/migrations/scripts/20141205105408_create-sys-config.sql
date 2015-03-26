-- // create-sys-config
-- Migration SQL that makes the change goes here.
CREATE TABLE sys.sys_config (
    id serial NOT NULL,
    config_name varchar(128) NOT NULL,
    config_key varchar(64) NOT NULL,
    config_value varchar(32) NOT NULL,
    description text ,
    update_by int4 NOT NULL,
    update_time timestamp(6) NOT NULL
);
ALTER TABLE sys.sys_config ADD CONSTRAINT sys_config_PK PRIMARY KEY (id);

COMMENT ON TABLE sys.sys_config IS '系统配置';
COMMENT ON COLUMN sys.sys_config.id IS '配置ID';
COMMENT ON COLUMN sys.sys_config.config_name IS '名称';
COMMENT ON COLUMN sys.sys_config.config_key IS '键';
COMMENT ON COLUMN sys.sys_config.config_value IS '值';
COMMENT ON COLUMN sys.sys_config.description IS '描述';
COMMENT ON COLUMN sys.sys_config.update_by IS '最后修改人';
COMMENT ON COLUMN sys.sys_config.update_time IS '最后修改时间';




-- //@UNDO
-- SQL to undo the change goes here.



drop table sys.sys_config;
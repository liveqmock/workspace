-- // new report params
-- Migration SQL that makes the change goes here.
CREATE TABLE sys.sys_report_params (
  key varchar(64) NOT NULL,
  merchant_id int4 NOT NULL,
  created_time timestamp NOT NULL,
  report_time timestamp NOT NULL,
  is_expired bool NOT NULL,
  short_link varchar(64),
  PRIMARY KEY (key)
);
COMMENT ON TABLE sys.sys_report_params IS '报表参数表';
COMMENT ON COLUMN sys.sys_report_params.created_time IS '创建时间';
COMMENT ON COLUMN sys.sys_report_params.report_time IS '汇报时间';
COMMENT ON COLUMN sys.sys_report_params.is_expired IS '是否过期';
COMMENT ON COLUMN sys.sys_report_params.short_link IS '短链接';

-- //@UNDO
-- SQL to undo the change goes here.
DROP TABLE sys.sys_report_params;


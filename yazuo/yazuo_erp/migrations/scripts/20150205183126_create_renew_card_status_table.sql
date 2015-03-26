-- // create renew_card_status table
-- Migration SQL that makes the change goes here.
CREATE TABLE bes.bes_renew_card_status (
  id                         SERIAL,
  status                     VARCHAR(8)     NOT NULL,
  card_type_id               INT4           NOT NULL,
  card_type                  VARCHAR(64)    NOT NULL,
  card_type_status           INT4           NOT NULL,
  brand_id                   INT4           NOT NULL,
  no_activated_num           INT4           NOT NULL,
  card_total_num             INT4           NOT NULL,
  card_total_num_for_3months INT4           NOT NULL,
  avg_card_num_for_3months   NUMERIC(10, 2) NOT NULL,
  avail_date_num             INT4,
  is_entity_card             VARCHAR(8)     NOT NULL,
  last_activate_date         TIMESTAMP,
  remark                     VARCHAR(1024),
  updated_time               TIMESTAMP      NOT NULL,
  operation_ids              INT4 []        NOT NULL,
  PRIMARY KEY (id)
);
COMMENT ON TABLE bes.bes_renew_card_status IS '续卡状态表';
COMMENT ON COLUMN bes.bes_renew_card_status.status IS '状态';
COMMENT ON COLUMN bes.bes_renew_card_status.card_type_id IS '卡类型ID';
COMMENT ON COLUMN bes.bes_renew_card_status.card_type IS '卡类型名称';
COMMENT ON COLUMN bes.bes_renew_card_status.card_type_status IS '卡类型状态卡 0：正常显示，1：处于隐藏状态';
COMMENT ON COLUMN bes.bes_renew_card_status.brand_id IS '商户ID';
COMMENT ON COLUMN bes.bes_renew_card_status.no_activated_num IS '未激活卡数量';
COMMENT ON COLUMN bes.bes_renew_card_status.card_total_num IS '开卡总量';
COMMENT ON COLUMN bes.bes_renew_card_status.card_total_num_for_3months IS '近3个月发卡总量';
COMMENT ON COLUMN bes.bes_renew_card_status.avg_card_num_for_3months IS '近3个月每日发卡平均数';
COMMENT ON COLUMN bes.bes_renew_card_status.avail_date_num IS '卡可用天数';
COMMENT ON COLUMN bes.bes_renew_card_status.is_entity_card IS '是否为非实体卡';
COMMENT ON COLUMN bes.bes_renew_card_status.last_activate_date IS '最后开卡日期';
COMMENT ON COLUMN bes.bes_renew_card_status.remark IS '备注';
COMMENT ON COLUMN bes.bes_renew_card_status.updated_time IS '最后更新时间';
COMMENT ON COLUMN bes.bes_renew_card_status.operation_ids IS '历史记录';

-- //@UNDO
-- SQL to undo the change goes here.
DROP TABLE bes.bes_renew_card_status;

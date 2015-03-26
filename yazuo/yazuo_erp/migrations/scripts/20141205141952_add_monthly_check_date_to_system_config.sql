-- // add monthly check date to system config
-- Migration SQL that makes the change goes here.
INSERT INTO sys.sys_config (config_name, config_key, config_value, description, update_by, update_time) VALUES ('月报发送考核日', 'bes_monthly_check_date', '15', '', 999999, now());

-- //@UNDO
-- SQL to undo the change goes here.
DELETE FROM sys.sys_config WHERE config_key = 'bes_monthly_check_date';



-- // add data dictionary for renew card
-- Migration SQL that makes the change goes here.
INSERT into sys.sys_dictionary(dictionary_type,dictionary_name,dictionary_key,dictionary_value,remark,insert_by,insert_time,update_by,update_time) VALUES ('00000077','操作日志类型','33','续卡管理','',1,'2014-5-27',1,'2014-5-27');
INSERT into sys.sys_dictionary(dictionary_type,dictionary_name,dictionary_key,dictionary_value,remark,insert_by,insert_time,update_by,update_time) VALUES ('00000124','续卡状态','1','商户拒绝','',1,'2015-2-13',1,'2015-2-13');
INSERT into sys.sys_dictionary(dictionary_type,dictionary_name,dictionary_key,dictionary_value,remark,insert_by,insert_time,update_by,update_time) VALUES ('00000124','续卡状态','2','同意制卡','',1,'2015-2-13',1,'2015-2-13');
INSERT into sys.sys_dictionary(dictionary_type,dictionary_name,dictionary_key,dictionary_value,remark,insert_by,insert_time,update_by,update_time) VALUES ('00000124','续卡状态','3','考虑中','',1,'2015-2-13',1,'2015-2-13');
INSERT into sys.sys_dictionary(dictionary_type,dictionary_name,dictionary_key,dictionary_value,remark,insert_by,insert_time,update_by,update_time) VALUES ('00000124','续卡状态','4','继续跟进','',1,'2015-2-13',1,'2015-2-13');
INSERT into sys.sys_dictionary(dictionary_type,dictionary_name,dictionary_key,dictionary_value,remark,insert_by,insert_time,update_by,update_time) VALUES ('00000124','续卡状态','5','正常','',1,'2015-2-13',1,'2015-2-13');
INSERT into sys.sys_dictionary(dictionary_type,dictionary_name,dictionary_key,dictionary_value,remark,insert_by,insert_time,update_by,update_time) VALUES ('00000125','续卡卡分类','1','实体卡','',1,'2015-2-13',1,'2015-2-13');
INSERT into sys.sys_dictionary(dictionary_type,dictionary_name,dictionary_key,dictionary_value,remark,insert_by,insert_time,update_by,update_time) VALUES ('00000125','续卡卡分类','2','非实体卡','',1,'2015-2-13',1,'2015-2-13');

-- //@UNDO
-- SQL to undo the change goes here.
delete from sys.sys_dictionary where dictionary_type='00000077' AND dictionary_key='33';
delete from sys.sys_dictionary where dictionary_type='00000124' AND dictionary_key='1';
delete from sys.sys_dictionary where dictionary_type='00000124' AND dictionary_key='2';
delete from sys.sys_dictionary where dictionary_type='00000124' AND dictionary_key='3';
delete from sys.sys_dictionary where dictionary_type='00000124' AND dictionary_key='4';
delete from sys.sys_dictionary where dictionary_type='00000124' AND dictionary_key='5';
delete from sys.sys_dictionary where dictionary_type='00000125' AND dictionary_key='1';
delete from sys.sys_dictionary where dictionary_type='00000125' AND dictionary_key='2';



-- // add sys_dictionary
-- Migration SQL that makes the change goes here.
INSERT into sys.sys_dictionary(dictionary_type,dictionary_name,dictionary_key,dictionary_value,remark,insert_by,insert_time,update_by,update_time) VALUES ('00000122','模板类型','1','月报短信模板','',1,'2015-1-29',1,'2015-1-29');
INSERT into sys.sys_dictionary(dictionary_type,dictionary_name,dictionary_key,dictionary_value,remark,insert_by,insert_time,update_by,update_time) VALUES ('00000123','商务负责人类型','1','销售负责人','',1,'2015-1-29',1,'2015-1-29');
INSERT into sys.sys_dictionary(dictionary_type,dictionary_name,dictionary_key,dictionary_value,remark,insert_by,insert_time,update_by,update_time) VALUES ('00000123','商务负责人类型','2','前端顾问','',1,'2015-1-29',1,'2015-1-29');


-- //@UNDO
-- SQL to undo the change goes here.

delete from sys.sys_dictionary where dictionary_type='00000122' and dictionary_key='1';
delete from sys.sys_dictionary where dictionary_type='00000123' and dictionary_key='1';
delete from sys.sys_dictionary where dictionary_type='00000123' and dictionary_key='2';

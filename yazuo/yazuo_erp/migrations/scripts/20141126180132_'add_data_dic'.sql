-- // 'add_data_dic'
-- Migration SQL that makes the change goes here.

INSERT into sys.sys_dictionary(dictionary_type,dictionary_name,dictionary_key,dictionary_value,remark,insert_by,insert_time,update_by,update_time) VALUES ('00000078','填单类型','4','回访需求单','',1,'2014-5-27',1,'2014-5-27');

insert into sys.sys_question (id,document_type,question_type,title,tip,is_enable,remark,insert_by,insert_time,update_by,update_time) VALUES (21,'4','1','雅座顾问是否在（@replace）为您讲解过关于贵公司8月份的月报情况？','','1',NULL,1,now(),1,now());
insert into sys.sys_question (id,document_type,question_type,title,tip,is_enable,remark,insert_by,insert_time,update_by,update_time) VALUES (22,'4','1','请问此次月报讲解大概用了多长时间吗？','（商户若记不清时间，主动询问“有30分钟吗”，包括月报讲解、方案的沟通调整等）','1',NULL,1,now(),1,now());
insert into sys.sys_question (id,document_type,question_type,title,tip,is_enable,remark,insert_by,insert_time,update_by,update_time) VALUES (23,'4','1','请问您对顾问提出的建议，是否接受？','','1',NULL,1,now(),1,now());
insert into sys.sys_question (id,document_type,question_type,title,tip,is_enable,remark,insert_by,insert_time,update_by,update_time) VALUES (24,'4','1','麻烦您给负责贵公司的雅座顾问打个综合评分，满分为100，请问您打多少分?','','1',NULL,1,now(),1,now());
insert into sys.sys_question (id,document_type,question_type,title,tip,is_enable,remark,insert_by,insert_time,update_by,update_time) VALUES (25,'4','1','请问您还有其他的意见或建议吗？','','1',NULL,1,now(),1,now());

INSERT INTO sys.sys_question_option (id, question_id, option_content, is_open_textarea, tip, remark, insert_by, insert_time, update_by, update_time) VALUES (46, 21, '是', '0', '', NULL, 1, now(), 1, now());
INSERT INTO sys.sys_question_option (id, question_id, option_content, is_open_textarea, tip, remark, insert_by, insert_time, update_by, update_time) VALUES (47, 21, '否', '0', '', NULL, 1, now(), 1, now());
INSERT INTO sys.sys_question_option (id, question_id, option_content, is_open_textarea, tip, remark, insert_by, insert_time, update_by, update_time) VALUES (48, 23, '是', '0', '', NULL, 1, now(), 1, now());
INSERT INTO sys.sys_question_option (id, question_id, option_content, is_open_textarea, tip, remark, insert_by, insert_time, update_by, update_time) VALUES (49, 23, '否', '0', '', NULL, 1, now(), 1, now());
INSERT INTO sys.sys_question_option (id, question_id, option_content, is_open_textarea, tip, remark, insert_by, insert_time, update_by, update_time) VALUES (50, 22, '10分钟', '0', '', NULL, 1, now(), 1, now());
INSERT INTO sys.sys_question_option (id, question_id, option_content, is_open_textarea, tip, remark, insert_by, insert_time, update_by, update_time) VALUES (51, 22, '20分钟', '0', '', NULL, 1, now(), 1, now());
INSERT INTO sys.sys_question_option (id, question_id, option_content, is_open_textarea, tip, remark, insert_by, insert_time, update_by, update_time) VALUES (52, 22, '30分钟', '0', '', NULL, 1, now(), 1, now());
INSERT INTO sys.sys_question_option (id, question_id, option_content, is_open_textarea, tip, remark, insert_by, insert_time, update_by, update_time) VALUES (53, 22, '40分钟', '0', '', NULL, 1, now(), 1, now());
INSERT INTO sys.sys_question_option (id, question_id, option_content, is_open_textarea, tip, remark, insert_by, insert_time, update_by, update_time) VALUES (54, 22, '50分钟', '0', '', NULL, 1, now(), 1, now());
INSERT INTO sys.sys_question_option (id, question_id, option_content, is_open_textarea, tip, remark, insert_by, insert_time, update_by, update_time) VALUES (55, 22, '60分钟', '0', '', NULL, 1, now(), 1, now());
INSERT INTO sys.sys_question_option (id, question_id, option_content, is_open_textarea, tip, remark, insert_by, insert_time, update_by, update_time) VALUES (56, 24, '非常满意（80-100分）', '0', '', NULL, 1, now(), 1, now());
INSERT INTO sys.sys_question_option (id, question_id, option_content, is_open_textarea, tip, remark, insert_by, insert_time, update_by, update_time) VALUES (57, 24, '满意（60-80分）', '0', '', NULL, 1, now(), 1, now());
INSERT INTO sys.sys_question_option (id, question_id, option_content, is_open_textarea, tip, remark, insert_by, insert_time, update_by, update_time) VALUES (58, 24, '不满意（0-60分）', '0', '', NULL, 1, now(), 1, now());
INSERT INTO sys.sys_question_option (id, question_id, option_content, is_open_textarea, tip, remark, insert_by, insert_time, update_by, update_time) VALUES (59, 25, '无', '0', '', NULL, 1, now(), 1, now());
INSERT INTO sys.sys_question_option (id, question_id, option_content, is_open_textarea, tip, remark, insert_by, insert_time, update_by, update_time) VALUES (60, 25, '有', '1', '反馈意见', NULL, 1, now(), 1, now());


-- //@UNDO
-- SQL to undo the change goes here.


delete from sys.sys_dictionary where dictionary_type = '00000078' and dictionary_key = '4';
delete from sys.sys_question where id>=21 and id<=25;
delete from sys.sys_question_option  where id>=46 and id<=60;
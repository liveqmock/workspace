-- // add resource to sys_resource
-- Migration SQL that makes the change goes here.
INSERT INTO sys.sys_resource (id, parent_id, tree_code, parent_tree_code, resource_type, resource_name, resource_url, sort, is_visible, remark, insert_by, insert_time, update_by, update_time) VALUES (188, 26, '002010', '002', '1', '短信模板', '', 10, '1', 'sms_template_188', 1, now(), 1, now());
INSERT INTO sys.sys_resource (id, parent_id, tree_code, parent_tree_code, resource_type, resource_name, resource_url, sort, is_visible, remark, insert_by, insert_time, update_by, update_time) VALUES (189, 188, '002010001', '002010', '2', '添加', '', 1, '1', 'add_sms_template_189', 1, now(), 1, now());
INSERT INTO sys.sys_resource (id, parent_id, tree_code, parent_tree_code, resource_type, resource_name, resource_url, sort, is_visible, remark, insert_by, insert_time, update_by, update_time) VALUES (190, 188, '002010002', '002010', '2', '修改', '', 2, '1', 'update_sms_template_190', 1, now(), 1, now());
INSERT INTO sys.sys_resource (id, parent_id, tree_code, parent_tree_code, resource_type, resource_name, resource_url, sort, is_visible, remark, insert_by, insert_time, update_by, update_time) VALUES (191, 188, '002010003', '002010', '2', '删除', '', 3, '1', 'delete_sms_template_191', 1, now(), 1, now());
-- //@UNDO
-- SQL to undo the change goes here.
delete from sys.sys_resource where id=188;
delete from sys.sys_resource where id=189;
delete from sys.sys_resource where id=190;
delete from sys.sys_resource where id=191;


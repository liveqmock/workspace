-- // add resources of renew card
-- Migration SQL that makes the change goes here.
INSERT INTO sys.sys_resource (id, parent_id, tree_code, parent_tree_code, resource_type, resource_name, resource_url, sort, is_visible, remark, insert_by, insert_time, update_by, update_time) VALUES (192, 175, '006008001', '006008', '2', '修改状态', '', 1, '1', 'renew_card_status_192', 1, now(), 1, now());
INSERT INTO sys.sys_resource (id, parent_id, tree_code, parent_tree_code, resource_type, resource_name, resource_url, sort, is_visible, remark, insert_by, insert_time, update_by, update_time) VALUES (193, 175, '006008002', '006008', '2', '查看历史', '', 2, '1', 'renew_card_history_193', 1, now(), 1, now());



-- //@UNDO
-- SQL to undo the change goes here.
delete from sys.sys_resource where id = 192;
delete from sys.sys_resource where id = 193;


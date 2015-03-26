-- // add sys_resource
-- Migration SQL that makes the change goes here.
INSERT INTO sys.sys_resource (id, parent_id, tree_code, parent_tree_code, resource_type, resource_name, resource_url, sort, is_visible, remark, insert_by, insert_time, update_by, update_time) VALUES (180, 110, '010002', '010', '1', '知识分类', '', 2, '1', 'knowledge_kind_180', 1, now(), 1, now());
INSERT INTO sys.sys_resource (id, parent_id, tree_code, parent_tree_code, resource_type, resource_name, resource_url, sort, is_visible, remark, insert_by, insert_time, update_by, update_time) VALUES (181, 110, '010003', '010', '1', '知识库', '', 3, '1', 'knowledge_181', 1, now(), 1, now());
INSERT INTO sys.sys_resource (id, parent_id, tree_code, parent_tree_code, resource_type, resource_name, resource_url, sort, is_visible, remark, insert_by, insert_time, update_by, update_time) VALUES (182, 180, '010002001', '010002', '3', '添加', '', 1, '1', 'knowledge_add_182', 1, now(), 1, now());
INSERT INTO sys.sys_resource (id, parent_id, tree_code, parent_tree_code, resource_type, resource_name, resource_url, sort, is_visible, remark, insert_by, insert_time, update_by, update_time) VALUES (183, 180, '010002002', '010002', '3', '修改', '', 2, '1', 'knowledge_update_183', 1, now(), 1, now());
INSERT INTO sys.sys_resource (id, parent_id, tree_code, parent_tree_code, resource_type, resource_name, resource_url, sort, is_visible, remark, insert_by, insert_time, update_by, update_time) VALUES (184, 180, '010002003', '010002', '3', '删除', '', 3, '1', 'knowledge_delete_184', 1, now(), 1, now());
INSERT INTO sys.sys_resource (id, parent_id, tree_code, parent_tree_code, resource_type, resource_name, resource_url, sort, is_visible, remark, insert_by, insert_time, update_by, update_time) VALUES (185, 181, '010003001', '010003', '3', '添加', '', 1, '1', 'knowledge_kind_add_185', 1, now(), 1, now());
INSERT INTO sys.sys_resource (id, parent_id, tree_code, parent_tree_code, resource_type, resource_name, resource_url, sort, is_visible, remark, insert_by, insert_time, update_by, update_time) VALUES (186, 181, '010003002', '010003', '3', '修改', '', 2, '1', 'knowledge_kind_update_186', 1, now(), 1, now());
INSERT INTO sys.sys_resource (id, parent_id, tree_code, parent_tree_code, resource_type, resource_name, resource_url, sort, is_visible, remark, insert_by, insert_time, update_by, update_time) VALUES (187, 181, '010003003', '010003', '3', '删除', '', 3, '1', 'knowledge_kind_delete_187', 1, now(), 1, now());


-- //@UNDO
-- SQL to undo the change goes here.
delete from sys.sys_resource where id=180;
delete from sys.sys_resource where id=181;
delete from sys.sys_resource where id=182;
delete from sys.sys_resource where id=183;
delete from sys.sys_resource where id=184;
delete from sys.sys_resource where id=185;
delete from sys.sys_resource where id=186;
delete from sys.sys_resource where id=187;

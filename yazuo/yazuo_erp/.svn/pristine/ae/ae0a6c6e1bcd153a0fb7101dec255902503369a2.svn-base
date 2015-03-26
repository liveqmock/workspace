-- // add created_time to sys_commentary
-- Migration SQL that makes the change goes here.
alter table sys.sys_commentary add column created_time timestamp default now() not null;



-- //@UNDO
-- SQL to undo the change goes here.
alter table sys.sys_commentary drop column created_time;



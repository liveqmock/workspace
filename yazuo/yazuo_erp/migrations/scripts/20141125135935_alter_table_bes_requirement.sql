-- // alter table bes_requirement set column contact_id is nullable
-- Migration SQL that makes the change goes here.
alter table bes.bes_requirement alter column contact_id drop not null;


-- //@UNDO
-- SQL to undo the change goes here.
alter table bes.bes_requirement alter column contact_id set not null;


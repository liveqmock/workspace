-- // 'alter-bes-req-column'
-- Migration SQL that makes the change goes here.

alter table bes.bes_requirement add COLUMN type_of_key varchar(8) ;  
alter table bes.bes_requirement add COLUMN fes_plan_id int ;  


-- //@UNDO
-- SQL to undo the change goes here.



alter table bes.bes_requirement drop column type_of_key;
alter table bes.bes_requirement drop COLUMN fes_plan_id ;
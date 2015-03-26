-- // 'monthly-report'
-- Migration SQL that makes the change goes here.

CREATE TABLE bes.bes_requirement (
    id serial NOT NULL,
    merchant_id int4 NOT NULL,
    store_id int4 NOT NULL,
    contact_cat varchar(8) NOT NULL,
    contact_id int4 NOT NULL,
    source_cat varchar(8) NOT NULL,
    source_content varchar(8) NOT NULL,
    resource_remark varchar(64) NOT NULL,
    resource_extra_remark varchar(64) ,
    title varchar(128) NOT NULL,
    content text ,
    attachment_id int4 ,
    operation_log_ids int4[] ,
    handler_id int4 ,
    handled_time timestamp ,
    conments text ,
    status varchar(8) ,
    is_enable varchar(8) NOT NULL,
    remark varchar(512) ,
    insert_by int4 NOT NULL,
    insert_time timestamp(6) NOT NULL,
    update_by int4 NOT NULL,
    update_time timestamp(6) NOT NULL
);
ALTER TABLE bes.bes_requirement ADD CONSTRAINT bes_requirement_PK PRIMARY KEY (id);
CREATE TABLE bes.bes_former_user_merchant (
    id serial NOT NULL,
    merchant_id int4 NOT NULL,
    user_id int4 NOT NULL,
    begin_time timestamp NOT NULL,
    end_time timestamp ,
    is_enable varchar(8) NOT NULL,
    remark varchar(512) ,
    insert_by int4 NOT NULL,
    insert_time timestamp(6) NOT NULL,
    update_by int4 NOT NULL,
    update_time timestamp(6) NOT NULL
);
ALTER TABLE bes.bes_former_user_merchant ADD CONSTRAINT bes_former_user_merchant_PK PRIMARY KEY (id);
CREATE TABLE bes.bes_monthly_report (
    id serial NOT NULL,
    access_factor varchar(8) NOT NULL,
    operator_time timestamp NOT NULL,
    merchant_id int4 NOT NULL,
    status varchar(8) ,
    is_enable varchar(8) NOT NULL,
    remark varchar(512) ,
    insert_by int4 NOT NULL,
    insert_time timestamp(6) NOT NULL,
    update_by int4 NOT NULL,
    update_time timestamp(6) NOT NULL
);
ALTER TABLE bes.bes_monthly_report ADD CONSTRAINT bes_monthly_report_PK PRIMARY KEY (id);
CREATE TABLE bes.bes_talking_skills (
    id serial NOT NULL,
    resource_remark varchar(64) NOT NULL,
    resource_extra_remark varchar(64) NULL,
    title varchar(256) NOT NULL,
    content text ,
    is_enable varchar(8) NOT NULL,
    remark varchar(512) ,
    insert_by int4 NOT NULL,
    insert_time timestamp(6) NOT NULL,
    update_by int4 NOT NULL,
    update_time timestamp(6) NOT NULL
);
ALTER TABLE bes.bes_talking_skills ADD CONSTRAINT bes_talking_skills_PK PRIMARY KEY (id);
CREATE TABLE bes.bes_call_record (
    id serial NOT NULL,
    merchant_id int4 NOT NULL,
    requirement_id int4 NOT NULL,
    begin_time timestamp NOT NULL,
    end_time timestamp NOT NULL,
    document_id int4 ,
    status varchar(8) ,
    contact_id int4 ,
    handler_id int4 ,
    is_enable varchar(8) NOT NULL,
    remark varchar(512) ,
    insert_by int4 NOT NULL,
    insert_time timestamp(6) NOT NULL,
    update_by int4 NOT NULL,
    update_time timestamp(6) NOT NULL
);
ALTER TABLE bes.bes_call_record ADD CONSTRAINT bes_call_record_PK PRIMARY KEY (id);
-- COMMENT SQL
COMMENT ON TABLE bes.bes_requirement IS '后端需求';
COMMENT ON COLUMN bes.bes_requirement.id IS '后端需求ID';
COMMENT ON COLUMN bes.bes_requirement.merchant_id IS '商户ID';
COMMENT ON COLUMN bes.bes_requirement.store_id IS '门店/域ID';
COMMENT ON COLUMN bes.bes_requirement.contact_cat IS '联系人分类';
COMMENT ON COLUMN bes.bes_requirement.contact_id IS '联系人ID';
COMMENT ON COLUMN bes.bes_requirement.source_cat IS '来源分类';
COMMENT ON COLUMN bes.bes_requirement.source_content IS '来源';
COMMENT ON COLUMN bes.bes_requirement.resource_remark IS '需求类型';
COMMENT ON COLUMN bes.bes_requirement.resource_extra_remark IS '其他需求类型';
COMMENT ON COLUMN bes.bes_requirement.title IS '标题';
COMMENT ON COLUMN bes.bes_requirement.content IS '内容';
COMMENT ON COLUMN bes.bes_requirement.attachment_id IS '附件ID';
COMMENT ON COLUMN bes.bes_requirement.operation_log_ids IS '流水IDs';
COMMENT ON COLUMN bes.bes_requirement.handler_id IS '处理人ID';
COMMENT ON COLUMN bes.bes_requirement.handled_time IS '处理时间';
COMMENT ON COLUMN bes.bes_requirement.conments IS '放弃原因';
COMMENT ON COLUMN bes.bes_requirement.status IS '状态';
COMMENT ON COLUMN bes.bes_requirement.is_enable IS '是否有效';
COMMENT ON COLUMN bes.bes_requirement.remark IS '备注';
COMMENT ON COLUMN bes.bes_requirement.insert_by IS '创建人';
COMMENT ON COLUMN bes.bes_requirement.insert_time IS '创建时间';
COMMENT ON COLUMN bes.bes_requirement.update_by IS '最后修改人';
COMMENT ON COLUMN bes.bes_requirement.update_time IS '最后修改时间';
COMMENT ON TABLE bes.bes_former_user_merchant IS '老用户-商户关系表';
COMMENT ON COLUMN bes.bes_former_user_merchant.id IS '老用户商户关系表ID';
COMMENT ON COLUMN bes.bes_former_user_merchant.merchant_id IS '商户ID';
COMMENT ON COLUMN bes.bes_former_user_merchant.user_id IS '用户ID';
COMMENT ON COLUMN bes.bes_former_user_merchant.begin_time IS '服务开始时间';
COMMENT ON COLUMN bes.bes_former_user_merchant.end_time IS '服务结束时间';
COMMENT ON COLUMN bes.bes_former_user_merchant.is_enable IS '是否有效';
COMMENT ON COLUMN bes.bes_former_user_merchant.remark IS '备注';
COMMENT ON COLUMN bes.bes_former_user_merchant.insert_by IS '创建人';
COMMENT ON COLUMN bes.bes_former_user_merchant.insert_time IS '创建时间';
COMMENT ON COLUMN bes.bes_former_user_merchant.update_by IS '最后修改人';
COMMENT ON COLUMN bes.bes_former_user_merchant.update_time IS '最后修改时间';
COMMENT ON TABLE bes.bes_monthly_report IS '商户月报信息表';
COMMENT ON COLUMN bes.bes_monthly_report.id IS '商户月报信息表ID';
COMMENT ON COLUMN bes.bes_monthly_report.access_factor IS '评估要素';
COMMENT ON COLUMN bes.bes_monthly_report.operator_time IS '操作时间';
COMMENT ON COLUMN bes.bes_monthly_report.merchant_id IS '商户ID';
COMMENT ON COLUMN bes.bes_monthly_report.status IS '评估状态';
COMMENT ON COLUMN bes.bes_monthly_report.is_enable IS '是否有效';
COMMENT ON COLUMN bes.bes_monthly_report.remark IS '备注';
COMMENT ON COLUMN bes.bes_monthly_report.insert_by IS '创建人';
COMMENT ON COLUMN bes.bes_monthly_report.insert_time IS '创建时间';
COMMENT ON COLUMN bes.bes_monthly_report.update_by IS '最后修改人';
COMMENT ON COLUMN bes.bes_monthly_report.update_time IS '最后修改时间';
COMMENT ON TABLE bes.bes_talking_skills IS '话术表';
COMMENT ON COLUMN bes.bes_talking_skills.id IS '话术ID';
COMMENT ON COLUMN bes.bes_talking_skills.resource_remark IS '资源ID';
COMMENT ON COLUMN bes.bes_talking_skills.resource_extra_remark IS '其他资源ID';
COMMENT ON COLUMN bes.bes_talking_skills.title IS '标题';
COMMENT ON COLUMN bes.bes_talking_skills.content IS '内容';
COMMENT ON COLUMN bes.bes_talking_skills.is_enable IS '是否有效';
COMMENT ON COLUMN bes.bes_talking_skills.remark IS '备注';
COMMENT ON COLUMN bes.bes_talking_skills.insert_by IS '创建人';
COMMENT ON COLUMN bes.bes_talking_skills.insert_time IS '创建时间';
COMMENT ON COLUMN bes.bes_talking_skills.update_by IS '最后修改人';
COMMENT ON COLUMN bes.bes_talking_skills.update_time IS '最后修改时间';
COMMENT ON TABLE bes.bes_call_record IS '回访记录';
COMMENT ON COLUMN bes.bes_call_record.id IS '回访记录ID';
COMMENT ON COLUMN bes.bes_call_record.merchant_id IS '商户ID';
COMMENT ON COLUMN bes.bes_call_record.requirement_id IS '需求ID';
COMMENT ON COLUMN bes.bes_call_record.begin_time IS '通话开始时间';
COMMENT ON COLUMN bes.bes_call_record.end_time IS '通话结束时间';
COMMENT ON COLUMN bes.bes_call_record.document_id IS '回访表单ID';
COMMENT ON COLUMN bes.bes_call_record.status IS '回访状态';
COMMENT ON COLUMN bes.bes_call_record.contact_id IS '联系人ID';
COMMENT ON COLUMN bes.bes_call_record.handler_id IS '指派人ID';
COMMENT ON COLUMN bes.bes_call_record.is_enable IS '是否有效';
COMMENT ON COLUMN bes.bes_call_record.remark IS '备注';
COMMENT ON COLUMN bes.bes_call_record.insert_by IS '创建人';
COMMENT ON COLUMN bes.bes_call_record.insert_time IS '创建时间';
COMMENT ON COLUMN bes.bes_call_record.update_by IS '最后修改人';
COMMENT ON COLUMN bes.bes_call_record.update_time IS '最后修改时间';
-- ADD FOREIGN KEY SQL
-- ADD FOREIGN KEY SQL
ALTER TABLE bes.bes_requirement ADD CONSTRAINT bes_requirement_merchant_id_FK FOREIGN KEY (merchant_id) REFERENCES syn.syn_merchant;
ALTER TABLE bes.bes_requirement ADD CONSTRAINT bes_requirement_contact_id_FK FOREIGN KEY (contact_id) REFERENCES mkt.mkt_contact;
ALTER TABLE bes.bes_requirement ADD CONSTRAINT bes_requirement_attachment_id_FK FOREIGN KEY (attachment_id) REFERENCES sys.sys_attachment;
ALTER TABLE bes.bes_requirement ADD CONSTRAINT bes_requirement_handler_id_FK FOREIGN KEY (handler_id) REFERENCES sys.sys_user;
ALTER TABLE bes.bes_former_user_merchant ADD CONSTRAINT bes_former_user_merchant_merchant_id_FK FOREIGN KEY (merchant_id) REFERENCES syn.syn_merchant;
ALTER TABLE bes.bes_former_user_merchant ADD CONSTRAINT bes_former_user_merchant_user_id_FK FOREIGN KEY (user_id) REFERENCES sys.sys_user;
ALTER TABLE bes.bes_monthly_report ADD CONSTRAINT bes_monthly_report_merchant_id_FK FOREIGN KEY (merchant_id) REFERENCES syn.syn_merchant;
ALTER TABLE bes.bes_call_record ADD CONSTRAINT bes_call_record_merchant_id_FK FOREIGN KEY (merchant_id) REFERENCES syn.syn_merchant;
ALTER TABLE bes.bes_call_record ADD CONSTRAINT bes_call_record_requirement_id_FK FOREIGN KEY (requirement_id) REFERENCES bes.bes_requirement;
ALTER TABLE bes.bes_call_record ADD CONSTRAINT bes_call_record_document_id_FK FOREIGN KEY (document_id) REFERENCES sys.sys_document;

-- //@UNDO
-- SQL to undo the change goes here.


ALTER TABLE bes.bes_requirement drop constraint bes_requirement_merchant_id_FK;
ALTER TABLE bes.bes_requirement drop constraint bes_requirement_contact_id_FK;
ALTER TABLE bes.bes_requirement drop constraint bes_requirement_attachment_id_FK;
ALTER TABLE bes.bes_requirement drop constraint bes_requirement_handler_id_FK;
ALTER TABLE bes.bes_former_user_merchant drop constraint bes_former_user_merchant_merchant_id_FK;
ALTER TABLE bes.bes_former_user_merchant drop constraint bes_former_user_merchant_user_id_FK;
ALTER TABLE bes.bes_monthly_report drop constraint bes_monthly_report_merchant_id_FK;
ALTER TABLE bes.bes_call_record drop constraint bes_call_record_merchant_id_FK;
ALTER TABLE bes.bes_call_record drop constraint bes_call_record_requirement_id_FK;
ALTER TABLE bes.bes_call_record drop constraint bes_call_record_document_id_FK;

drop table bes.bes_requirement;
drop table bes.bes_former_user_merchant;
drop table bes.bes_monthly_report;
drop table bes.bes_talking_skills;
drop table bes.bes_call_record;


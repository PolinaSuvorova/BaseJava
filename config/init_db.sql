-- auto-generated definition
create table resume
(
    uuid      char(36) default nextval('resume_uuid_seq'::regclass) not null
        constraint resume_pk
            primary key,
    full_name text                                                  not null
);
comment
on column resume.uuid is 'ID';
comment
on column resume.full_name is 'ФИО';
alter table resume
    owner to postgres;

-- auto-generated definition
create table contact
(
    id          serial
        constraint contact_pk
            primary key
                deferrable,
    type        text     not null,
    value       text     not null,
    resume_uuid char(36) not null
        constraint "contact_resume_uuid_fk"
            references resume
            on update restrict on delete cascade
);

comment
on table contact is 'Контакты';

alter table contact
    owner to postgres;
create index contact_uuid_type_ind
    on contact (resume_uuid, type);

--
create table section
(
    id          serial
        constraint section_pk
            primary key,
    type        text     not null,
    text        text     not null,
    resume_uuid char(36) not null
        constraint section_resume_fk
            references resume
            on delete cascade
);

comment
on table section is 'Секции';

alter table section
    owner to postgres;

create unique index section_uuid_type_index
    on section (resume_uuid, type);

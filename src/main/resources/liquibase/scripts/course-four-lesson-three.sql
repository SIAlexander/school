-- liquibase formatted sql

-- changeset asmokvin:1
CREATE INDEX student_name_index ON student (name);

-- changeset asmokvin:2
CREATE INDEX faculty_cn_idx ON faculty (color, name);

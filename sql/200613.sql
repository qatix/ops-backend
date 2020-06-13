
drop table if exists audit_log;
create table audit_log(
    id bigint auto_increment primary key,
    doc_id bigint not null default 0 comment '文章ID',
        doc_title varchar(128) not null default '' comment '文章标题',
        result_type   ENUM("none","pass","reject") comment '审核结果',
    user_name varchar(32) not null default '' comment '操作人姓名',
    create_time datetime not null default '0000-00-00 00:00:00' comment '创建时间'
)default charset=utf8 comment='审核日志';
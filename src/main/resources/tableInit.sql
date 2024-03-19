DROP TABLE POST;
DROP TABLE users;
DROP TABLE social_accounts;
DROP TABLE link_icon;
DROP TABLE hashtags;
DROP TABLE interests;

create table if not exists POST
(
    post_id       int auto_increment
        primary key,
    user_id       int                                 not null,
    content       text                                null,
    like_count    int                                 null,
    comment_count int                                 null,
    type          varchar(10)                         null comment '무슨 종류의 post인지',
    created_at    timestamp default CURRENT_TIMESTAMP null
);

create table users
(
    user_id                  int auto_increment
        primary key,
    organization_information varchar(20)               null,
    user_name                varchar(20)               null,
    email                    varchar(320)              not null,
    password                 varchar(60)               null,
    self_introduction        text                      null,
    profile_img_url          text                      null,
    post_count               int      default '0'      not null,
    follower_count           int      default '0'      not null,
    following_count          int      default '0'      not null,
    type                     char(21) default 'ACTIVE' not null,
    login_type               char(6)                   not null comment 'GITHUB,NORMAL'
);

create table social_accounts
(
    link_id int  not null
        primary key,
    icon_id int  not null,
    user_id int  not null,
    link    text not null
);

create table link_icon
(
    icon_id int         not null
        primary key,
    icon_url    text        null,
    domain  varchar(20) null
);

create table hashtags
(
    hashtag_id int auto_increment
        primary key,
    name       varchar(15) not null
);

create table interests
(
    user_id int         not null,
    name    varchar(15) not null,
    hashtag_id int not null,
    primary key (user_id, name)
);
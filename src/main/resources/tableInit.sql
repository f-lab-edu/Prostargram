DROP TABLE IF EXISTS POST;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS link_icon;
DROP TABLE IF EXISTS hashtags;
DROP TABLE IF EXISTS interests;
DROP TABLE IF EXISTS badwords;
DROP TABLE IF EXISTS choice;
DROP TABLE IF EXISTS comment;
DROP TABLE IF EXISTS comment_like;
DROP TABLE IF EXISTS file_extension;
DROP TABLE IF EXISTS follows;
DROP TABLE IF EXISTS social_accounts;
DROP TABLE IF EXISTS organization;
DROP TABLE IF EXISTS hyper_link;
DROP TABLE IF EXISTS post_hashtag_mapping;
DROP TABLE IF EXISTS poll_metadata;
DROP TABLE IF EXISTS post_images;
DROP TABLE IF EXISTS post_like;
DROP TABLE IF EXISTS post_options;

create table badwords
(
    name varchar(100) not null
        primary key
);

create table choice
(
    post_id   int not null,
    option_id int not null,
    user_id   int not null,
    primary key (post_id, option_id, user_id)
);

create table comment
(
    comment_id     int auto_increment
        primary key,
    user_id        int             not null,
    post_id        int             not null,
    parent_id      int             null,
    content        varchar(300)    not null,
    like_count     int default '0' null,
    created_at     datetime        null,
    children_count int             null
);

create table comment_like
(
    user_id    int not null,
    comment_id int not null,
    primary key (user_id, comment_id)
);

create table file_extension
(
    extension varchar(20) not null,
    type      varchar(20) not null
);

create table follows
(
    from_user_id int not null,
    to_user_id   int not null,
    primary key (from_user_id, to_user_id)
);

create table hashtags
(
    hashtag_id int auto_increment
        primary key,
    name       varchar(15) not null
);

create table hyper_link
(
    link_id int  not null
        primary key,
    icon_id int  not null,
    user_id int  not null,
    link    text not null
);

create table interests
(
    user_id    int         not null,
    hashtag_id int         not null,
    name       varchar(15) not null,
    primary key (user_id, name)
);

create table social_accounts
(
    link_id int  not null,
    icon_id int  not null,
    user_id int  not null,
    link    text not null,
    primary key (user_id)
);

create table link_icon
(
    icon_id  int         not null
        primary key,
    icon_url text        null,
    domain   varchar(20) null
);

create table organization
(
    organization_id int         not null
        primary key,
    name            varchar(40) not null,
    domain          varchar(65) not null,
    type            char(7)     not null comment 'SCHOOL / COMPANY (ENUM으로 쓰는게 좋을듯?)'
);

create table poll_metadata
(
    post_id              int  not null,
    start_date           date null,
    end_date             date null,
    allow_multiple_votes int  not null
);

create table post
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

create table post_hashtag_mapping
(
    post_id    int not null,
    hashtag_id int not null,
    primary key (post_id, hashtag_id)
);

create table post_images
(
    image_id int auto_increment
        primary key,
    post_id  int  not null,
    img_url  text not null
);

create table post_like
(
    user_id int not null,
    post_id int not null,
    primary key (user_id, post_id)
);

create table post_options
(
    option_id  int             not null,
    post_id    int             not null,
    content    char(40)        not null,
    vote_count int default '0' not null,
    primary key (option_id, post_id)
);

create table users
(
    user_id                  int auto_increment
        primary key,
    organization_information varchar(200)                   null,
    public_scope             varchar(20)                        not null default 'PUBLIC',
    user_name                varchar(20)                    null,
    email                    varchar(320)                   not null,
    password                 varchar(60)                    null,
    self_introduction        text                           null,
    profile_img_url          text                           null,
    post_count               int      default '0'           not null,
    follower_count           int      default '0'           null,
    following_count          int      default '0'           null,
    type                     varchar(21) default 'NORMAL_USER' not null,
    login_type               char(6)                        not null comment 'GITHUB,NORMAL'
);
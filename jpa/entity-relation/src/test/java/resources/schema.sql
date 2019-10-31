create table post (
    id int not null,
    title varchar (255),
    primary key (id)
);

create table post_comment (
    id int not null,
    review varchar (255),
    post_id int,
    primary key (id),
    foreign key (post_id) references post (id)
);
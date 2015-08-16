TRUNCATE TABLE todo;

insert into todo (todo_id, todo_title, finished, created_at) values('test','title',false,current_date());

commit;
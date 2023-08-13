select * from student where age >= 10 and age <= 35;
select s.name from student as s where s.name is not null;
select * from student where lower(name) like '%с%';
select * from student where age < id;
select * from student ORDER BY age;

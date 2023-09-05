SELECT student.name, student.age, faculty.name, faculty.color
FROM student
         INNER JOIN faculty ON student.faculty_id = faculty.id;

SELECT student.name, student.age, avatar.file_path, avatar.file_size
FROM avatar
         LEFT JOIN student ON avatar.student_id = student.id;
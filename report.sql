-- show the classes of each student ----------------------------------------------------
SELECT  student.name, student.surname, class.name
FROM student
INNER JOIN class ON student.class_id = class.id;

-- show students of class 1b (id 2) ----------------------------------------------------
SELECT  student.name, student.surname
FROM student
WHERE class_id = 2;

--show students of class "Klasse 1b", I don't know the class_id ------------------------
SELECT  student.name, student.surname
FROM student
INNER JOIN class ON student.class_id = class.id
WHERE class.name = "Klasse 1b";

-- show which teacher can teach which class? ------------------------------------------------
SELECT  teacher.name, teacher.surname, class.name
FROM teaching
INNER JOIN teacher ON teaching.teacher_id = teacher.id
INNER JOIN class on teaching.class_id = class.id;

-- sql querry for the bonus - teacher.id is the id from the selected id from observable list
SELECT  class.name
FROM teaching
INNER JOIN teacher ON teaching.teacher_id = teacher.id
INNER JOIN class on teaching.class_id = class.id
WHERE teacher.id=1; 
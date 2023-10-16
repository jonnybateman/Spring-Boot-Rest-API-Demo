DROP SCHEMA IF EXISTS `instructor-student-tracking`;

CREATE SCHEMA `instructor-student-tracking`;

use `instructor-student-tracking`;

SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE `instructor` (
  `id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `instructor_detail_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_DETAIL_idx` (`instructor_detail_id`),
  CONSTRAINT `FK_INSTRUCTOR` FOREIGN KEY (`instructor_detail_id`) REFERENCES `instructor_detail` (`id`)
    ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

insert into `instructor-student-tracking`.instructor (first_name, last_name, email, instructor_detail_id)
values
('John', 'Smith', 'jogn@abcxyz.com', 1),
('Joe', 'Bloggs', 'joe@abcxyz.com', 2),
('Rachel', 'Bloomingdale', 'rachel@abcxyz.com', 3),
('Jennifer', 'Ohara', 'jennifer@abcxyz.com', 4),
('Harry', 'Carrick', 'harry@abcxyz.com', 5);

CREATE TABLE `instructor_detail` (
  `id` int NOT NULL AUTO_INCREMENT,
  `age` int DEFAULT NULL,
  `sex` varchar(1) DEFAULT NULL,
  `address` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

insert into `instructor-student-tracking`.instructor_detail (age, sex, address)
values
(52, 'M', '44 Preston Road, Broxburn, EH54 6XZ'),
(38, 'M', '10546 Sunnydale Drive, San Francisco, 790766'),
(29, 'F', 'Appt 4b, City Tower, London, WQ4 8AB'),
(41, 'F', 'Buzzard Cottage, Bathgate, EH48 6TG'),
(36, 'M', '18 Gilmour Street, Waybridge, WY22 1ST');

CREATE TABLE `course` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(128) DEFAULT NULL,
  `instructor_id` int DEFAULT NULL,
  
  PRIMARY KEY (`id`),
  
  UNIQUE KEY `TITLE_UNIQUE` (`title`),
  
  KEY `FK_INSTRUCTOR_idx` (`instructor_id`),
  
  CONSTRAINT `FK_INSTRUCTOR_COURSE` 
  FOREIGN KEY (`instructor_id`) 
  REFERENCES `instructor` (`id`) 
  
  ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

insert into `instructor-student-tracking`.course (title, instructor_id)
values
('Maths', 1),
('Physics', 1),
('Biology', 2),
('Chemistry', 3),
('Geology', 3),
('Bio-chemistry', 3),
('Geo-physics', 4),
('Geography', 4),
('Architecture', 5);

CREATE TABLE `review` (
  `id` int NOT NULL AUTO_INCREMENT,
  `comment` varchar(256) DEFAULT NULL,
  `course_id` int DEFAULT NULL,

  PRIMARY KEY (`id`),

  KEY `FK_COURSE_ID_idx` (`course_id`),

  CONSTRAINT `FK_COURSE` 
  FOREIGN KEY (`course_id`) 
  REFERENCES `course` (`id`) 

  ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

insert into `instructor-student-tracking`.review (comment, course_id)
values
('Excellent, maths made easy!', 1),
('Instructor made perfect sense.', 1),
('Course was tough to follow at times!', 3),
('Too much content in too little time.', 3),
('Instructor needed to prepare better presentations!', 3),
('Feel fully prepared to take the exam.', 4),
('Loved the course, instrucotr was very knowledgeable.', 5),
('Difficult subject made easy.', 6),
('I struggled with this but instructor helped immensely!', 6),
('Very instersting, loved it!', 8),
('Got bored very quickly, dropped it after a while.', 8),
('A lot of hard work but well worth it in the end.', 9);

CREATE TABLE `student` (
  `id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

insert into `instructor-student-tracking`.student (first_name, last_name, email)
values
('Brian', 'Brown', 'brian@abcxyz.com'),
('Brenda', 'Strong', 'brenda@abcxyz.com'),
('Helen', 'Grey', 'helen@abcxyz.com'),
('Rupert', 'Bare', 'rupert@abcxyz.com'),
('Richard', 'Wickey', 'richard@abcxyz.com'),
('Katie', 'Henshaw', 'katie@abcxyz.com'),
('leona', 'Potts', 'leona@abcxyz.com'),
('Bob', 'Murray', 'bob@abcxyz.com'),
('Zing', 'Zu', 'zing@abcxyz.com'),
('Percy', 'Haslewaite', 'percy@abcxyz.com'),
('Philipa', 'Richmond', 'philipa@abcxyz.com'),
('sue', 'Abbott', 'sue@abcxyz.com'),
('Lacey', 'Benjamin', 'lacey@abcxyz.com'),
('Keith', 'Aubrey', 'keith@abcxyz.com');

CREATE TABLE `course_student` (
  `course_id` int NOT NULL,
  `student_id` int NOT NULL,
  
  PRIMARY KEY (`course_id`,`student_id`),
  
  KEY `FK_STUDENT_idx` (`student_id`),
  
  CONSTRAINT `FK_COURSE_05` FOREIGN KEY (`course_id`) 
  REFERENCES `course` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION,
  
  CONSTRAINT `FK_STUDENT` FOREIGN KEY (`student_id`) 
  REFERENCES `student` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

insert into `instructor-student-tracking`.course_student
values
(1, 1),
(1, 6),
(1, 7),
(2, 14),
(3, 6),
(3, 8),
(3, 12),
(3, 2),
(4, 13),
(4, 5),
(5, 4),
(5, 3),
(5, 9),
(5, 10),
(6, 11),
(7, 4),
(7, 8),
(7, 1),
(8, 5),
(9, 9),
(9, 2);

CREATE TABLE `users` (
  `username` varchar(50) NOT NULL,
  `password` char(68) NOT NULL,
  `active` tinyint NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `roles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `role` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_USER_ROLE` FOREIGN KEY (`username`) 
  REFERENCES `users` (`username`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

INSERT INTO `users`
VALUES
('john','{bcrypt}$2a$10$qeS0HEh7urweMojsnwNAR.vcXJeXR1UcMRZ2WcGQl9YeuspUdgF.q',1),
('mary','{bcrypt}$2a$10$qeS0HEh7urweMojsnwNAR.vcXJeXR1UcMRZ2WcGQl9YeuspUdgF.q',1),
('susan','{bcrypt}$2a$10$qeS0HEh7urweMojsnwNAR.vcXJeXR1UcMRZ2WcGQl9YeuspUdgF.q',1);

INSERT INTO `roles`
VALUES
(1,'john','STUDENT'),
(2,'mary','STUDENT'),
(3,'mary','INSTRUCTOR'),
(4,'susan','ADMIN');

SET FOREIGN_KEY_CHECKS = 1;



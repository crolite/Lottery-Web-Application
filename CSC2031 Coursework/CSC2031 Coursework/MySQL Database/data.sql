CREATE TABLE userAccounts (
Firstname varchar(50) NOT NULL,
Lastname varchar(50) NOT NULL, 
Email varchar(50) NOT NULL,
Phone varchar(50) NOT NULL,
Username varchar(50) NOT NULL,
Pwd varchar(200) NOT NULL,
Salt varchar(50) NOT NULL,
Userrole varchar(10) NOT NULL,


PRIMARY KEY (Username)
);


DROP TABLE IF EXISTS Luckydraw;

CREATE TABLE Luckydraw (
Numbers varchar(20) NOT NULL,
PRIMARY KEY (Numbers)
);

INSERT INTO Luckydraw  VALUES
('1, 2, 3, 4, 5, 6');








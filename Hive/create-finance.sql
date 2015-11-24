CREATE TABLE MajorTrade(
  id INT NOT NULL    AUTO_INCREMENT PRIMARY KEY,
  item VARCHAR(256), 
  volume INT NOT NULL,
  value INT NOT NULL,
  date datetime NOT NULL)
;

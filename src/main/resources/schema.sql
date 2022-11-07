create table books
(
   id INT NOT NULL,
   author VARCHAR(255) NOT NULL,
   title VARCHAR(255) NOT NULL,
   publication_date DATE NOT NULL,
   genre VARCHAR(255) NOT NULL,
   PRIMARY KEY (id)
);

create table news_papers
(
    id INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    publication_date DATE NOT NULL,
    genre VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);




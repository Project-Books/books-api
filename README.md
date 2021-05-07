<p align="center">
	<img src="https://raw.githubusercontent.com/Project-Books/book-project/master/media/banner/book_project_newlogo_2x.png" alt="Logo"/>
</p>

<p align="center">
  <a href="https://dev.azure.com/project-books/Books%20API/_build/latest?definitionId=3&branchName=main">
    <img src="https://dev.azure.com/project-books/Books%20API/_apis/build/status/Project-Books.books-api?branchName=main" alt="Build Status" />
  </a>
	
  <a href="https://codecov.io/gh/Project-Books/books-api">
     <img src="https://codecov.io/gh/Project-Books/books-api/branch/main/graph/badge.svg?token=5OF8MVDZW7"/>
  </a>
	
  <a href="https://join.slack.com/t/teambookproject/shared_invite/zt-punc8os7-Iz9PTCAkYcO_0S~XwtO5_A">
    <img src="https://img.shields.io/badge/slack-teambookproject-4A154B?logo=slack" alt="Slack" />
  </a>
  
  <a href="https://sonarcloud.io/dashboard?id=project-books_Books-API">
    <img src="https://sonarcloud.io/api/project_badges/measure?project=project-books_Books-API&metric=alert_status" alt="Quality Gate Status" />
  </a>
</p>

GraphQL books API made using Spring Boot and [DGS](https://netflix.github.io/dgs/). This is a sibling project of the [Book Project](https://github.com/Project-Books/book-project).

## Setup

Prerequisites: 
- JDK 11 or higher
- Configure [Lombok](https://github.com/Project-Books/book-project/wiki/Troubleshooting#cannot-find-log-statements-or-the-entities-do-not-have-constructors-lombok-errors)
- MySQL 8.0.* or (better) Docker

Recommended IntelliJ plugin: [JS GraphQL](https://plugins.jetbrains.com/plugin/8097-js-graphql)

## Running the app

1. Import as a Gradle project into your favourite IDE
2. Start the MySQL Database or run the docker-compose file `docker-compose up -d` (you may need to add `sudo` to this command)
3. Set the active Spring profile to dev (see how to do this in [IntelliJ](https://github.com/Project-Books/books-api/wiki/Change-active-Spring-profile-in-IntelliJ))
4. Run `BooksApiApplication.java`
5. Go to `localhost:8080/graphiql`

Sample query:
```graphql
{
  findAllBooks {
    title
    authors {
      fullName
    }
    genre
    isbn13
    yearOfPublication
    format
  }
}
```

### Access database

To access the MySQL database when docker-compose is running:

1. Go to `http://localhost:8081`
2. Log in with the details below:
    - Username: `root`
    - Password: `rootpassword`

## Contributing

If you wish to contribute (thanks!), please first see the [contributing document](https://github.com/Project-Books/books-api/blob/main/CONTRIBUTING.md).

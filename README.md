<p align="center">
	<img src="https://raw.githubusercontent.com/Project-Books/book-project/master/media/banner/book_project_newlogo_2x.png" alt="Logo"/>
</p>

<p align="center">
  <a href="https://github.com/Project-Books/books-api/actions/workflows/build.yml">
    <img src="https://github.com/Project-Books/books-api/actions/workflows/build.yml/badge.svg" alt="Build Status" />
  </a>
	
  <a href="https://sonarcloud.io/dashboard?id=Project-Books_books-api">
    <img src="https://sonarcloud.io/api/project_badges/measure?project=Project-Books_books-api&metric=coverage" alt="Code coverage" />
  </a>
	
  <a href="https://join.slack.com/t/teambookproject/shared_invite/zt-punc8os7-Iz9PTCAkYcO_0S~XwtO5_A">
    <img src="https://img.shields.io/badge/slack-teambookproject-4A154B?logo=slack" alt="Slack" />
  </a>
</p>

GraphQL books API made using Spring Boot and [DGS](https://netflix.github.io/dgs/). This is a sibling project of the [Book Project](https://github.com/Project-Books/book-project).

## Setup

Prerequisites: 
- JDK 11 or higher
- Configure [Lombok](https://github.com/Project-Books/book-project/wiki/Troubleshooting#cannot-find-log-statements-or-the-entities-do-not-have-constructors-lombok-errors)
- PostgreSQL 12 or (better) Docker
  - For Linux users, install docker-compose 
  - For macOS and Windows users, install Docker Desktop

Recommended IntelliJ plugin: [JS GraphQL](https://plugins.jetbrains.com/plugin/8097-js-graphql)

## Running the app

1. Import as a Maven project into your favourite IDE
2. Start the MySQL Database or run the docker-compose file `docker-compose up -d` (you may need to add `sudo` to this command) 
   - If using macOS or Windows, you'll need to first ensure Docker Desktop is running 
3. Run `BooksApiApplication.java`
4. Go to `localhost:8080/graphiql`

Sample query:
```graphql
{
    findAllBooks {
        title
        isbn13
        yearOfPublication
        blurb
        publisher {
            name
        }
        isbn10
        authors {
            fullName
        }
        lang {
            name
        }
        genre {
            name
        }
        cover {
            small
            medium
            large
        }
        publishingFormat {
            formatName
        }
    }
}
```

### Access database

To access the PostgreSQL database when docker-compose/Docker desktop is running, use the follow credentials in your favourite client:

- Host: `localhost`
- Port: `5432`
- User: `dbuser`
- Password: `dbpassword`
- Database: books_api
- URL: `jdbc:postgresql://localhost:5432/books_api`

For example, in IntelliJ ultimate or DataGrip:

![image](https://user-images.githubusercontent.com/11173328/132951060-7018b96a-cd96-4d74-a3f4-69233517a751.png)

## Contributing

If you wish to contribute (thanks!), please first see the [contributing document](https://github.com/Project-Books/books-api/blob/main/CONTRIBUTING.md).

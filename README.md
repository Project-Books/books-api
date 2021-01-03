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
	
  <a href="https://join.slack.com/t/teambookproject/shared_invite/zt-jcijyenp-JiKFGBv62FIPoFnvOW6Ubg">
    <img src="https://img.shields.io/badge/chat%20on-slack-%233f0e40" alt="Slack" />
  </a>
  
  <a href="https://sonarcloud.io/dashboard?id=project-books_Books-API">
    <img src="https://sonarcloud.io/api/project_badges/measure?project=project-books_Books-API&metric=alert_status" alt="Quality Gate Status" />
  </a>
</p>

GraphQL books API made using Spring Boot. This is a sibling project of the [Book Project](https://github.com/Project-Books/book-project).

## Setup

Prerequisites: 
- JDK 11 or higher
- Configure [Lombok](https://github.com/Project-Books/book-project/wiki/Troubleshooting#cannot-find-log-statements-or-the-entities-do-not-have-constructors-lombok-errors)

## Running the app

1. Import as a Maven project into your favourite IDE
2. Run the app
3. Go to `localhost:8080/playground`

Sample query:
```graphql
{
  findAllBooks {
    title
    authors {
      firstName
      lastName
    }
    genre
    isbn13
    yearOfPublication
    publishedBy
    format
  }
}
```

### Voyager

To visualise the schema, go to `localhost:8080/voyager`. You will also need to comment out the `maxQueryDepth` line in
application.properties.

## Contributing

If you wish to contribute (thanks!), please first see the [contributing document](https://github.com/Project-Books/books-api/blob/main/CONTRIBUTING.md).

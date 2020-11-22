<p align="center">
	<img src="https://raw.githubusercontent.com/Project-Books/book-project/master/media/banner/book_project_newlogo_2x.png" alt="Logo"/>
</p>

<p align="center">
  <a href="https://travis-ci.com/github/Project-Books/book-project">
     <img src="https://travis-ci.com/Project-Books/books-api.svg?branch=main" alt="Build Status"/>
  </a>
  <a href="https://join.slack.com/t/teambookproject/shared_invite/zt-i7lept44-rgJ9yB0A2vedJTLyyfkjKQ">
    <img src="https://img.shields.io/badge/chat%20on-slack-%233f0e40" alt="Slack" />
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
```
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

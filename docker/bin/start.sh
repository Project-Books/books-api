#!/bin/bash 

# Abort on any error (including if wait-for-it fails).
set -e

##
## @fn start.sh
##
## Automatically configures and starts Booksapi under Java. Booksapi's
## booksapi.properties file will be automatically generated based on the
## linked database container (PostgreSQL).
## The Java process will ultimately replace the process of this
## script, running in the foreground until terminated.
##

BOOKSAPI_HOME_TEMPLATE="$BOOKSAPI_HOME"

BOOKSAPI_HOME="$HOME/.booksapi"
# BOOKSAPI_EXT="$BOOKSAPI_HOME/extensions"
# BOOKSAPI_LIB="$BOOKSAPI_HOME/lib"
BOOKSAPI_PROPERTIES="$BOOKSAPI_HOME/booksapi.properties"
BOOKSAPI_PROFILE="dev"
SPRINGBOOT_DIALECT="org.hibernate.dialect.PostgreSQLDialect"
SPRINGBOOT_DATASOURCE="jdbc:"
SPRINGBOOT_DB_DRIVER="org.postgresql.Driver"
##
## Sets the given property to the given value within booksapi.properties,
## creating booksapi.properties first if necessary.
## See https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html 
## for more info.
##
## @param NAME
##     The name of the property to set.
##
## @param VALUE
##     The value to set the property to.
##
set_property() {

    NAME="$1"
    VALUE="$2"

    # Ensure booksapi.properties exists
    if [ ! -e "$BOOKSAPI_PROPERTIES" ]; then
        mkdir -p "$BOOKSAPI_HOME"
        echo "# booksapi.properties - generated $(date)" > "$BOOKSAPI_PROPERTIES"
    fi

    # Set property
    echo "$NAME=$VALUE" >> "$BOOKSAPI_PROPERTIES"

}

##
## Sets the given property to the given value within booksapi.properties only
## if a value is provided, creating booksapi.properties first if necessary.
##
## @param NAME
##     The name of the property to set.
##
## @param VALUE
##     The value to set the property to, if any. If omitted or empty, the
##     property will not be set.
##
set_optional_property() {

    NAME="$1"
    VALUE="$2"

    # Set the property only if a value is provided
    if [ -n "$VALUE" ]; then
        set_property "$NAME" "$VALUE"
    fi

}


# Print error message regarding missing required variables for PostgreSQL authentication
postgres_missing_vars() {
    cat <<END
FATAL: Missing required environment variables
-------------------------------------------------------------------------------
If using a PostgreSQL database, you must provide each of the following
environment variables or their corresponding Docker secrets by appending _FILE
to the environment variable, and setting the value to the path of the
corresponding secret:
    POSTGRES_USER      The user to authenticate as when connecting to
                       PostgreSQL.
    POSTGRES_PASSWORD  The password to use when authenticating with PostgreSQL
                       as POSTGRES_USER.
    POSTGRES_DATABASE  The name of the PostgreSQL database to use for Booksapi
                       authentication.
END
    exit 1;
}

##
## Adds properties to booksapi.properties which select the PostgreSQL
## authentication provider, and configure it to connect to the linked
## PostgreSQL container. If a PostgreSQL database is explicitly specified using
## the POSTGRES_HOSTNAME and POSTGRES_PORT environment variables, that will be
## used instead of a linked container.
##
associate_postgresql() {

    # Use linked container if specified
    if [ -n "$POSTGRES_NAME" ]; then
        POSTGRES_HOSTNAME="postgres"
        POSTGRES_PORT="5432"
    fi

    # Use default port if none specified
    POSTGRES_PORT="${POSTGRES_PORT-5432}"

    # Verify required connection information is present
    if [ -z "$POSTGRES_HOSTNAME" ] || [ -z "$POSTGRES_PORT" ]; then
        cat <<END
FATAL: Missing POSTGRES_HOSTNAME or "postgres" link.
-------------------------------------------------------------------------------
If using a PostgreSQL database, you must either:
(a) Explicitly link that container with the link named "postgres".
(b) If not using a Docker container for PostgreSQL, explicitly specify the TCP
    connection to your database using the following environment variables:
    POSTGRES_HOSTNAME  The hostname or IP address of the PostgreSQL server,
                       this environment variable is *REQUIRED*.
    POSTGRES_PORT      The port on which the PostgreSQL server is listening for
                       TCP connections. This environment variable is option. If
                       omitted, the standard PostgreSQL port of 5432 will be
                       used.
END
        exit 1;
    fi

    # Verify that the required Docker secrets are present, else, default to their normal environment variables
    if [ -n "$POSTGRES_USER_FILE" ]; then
        set_property "spring.datasource.username" "$(cat "$POSTGRES_USER_FILE")"
    elif [ -n "$POSTGRES_USER" ]; then
        set_property "spring.datasource.username" "$POSTGRES_USER"
    else
        postgres_missing_vars
        exit 1;
    fi

    if [ -n "$POSTGRES_PASSWORD_FILE" ]; then
        set_property "spring.datasource.password" "$(cat "$POSTGRES_PASSWORD_FILE")"
    elif [ -n "$POSTGRES_PASSWORD" ]; then
        set_property "spring.datasource.password" "$POSTGRES_PASSWORD"
    else
        postgres_missing_vars
        exit 1;
    fi

    if [ -n "$POSTGRES_DATABASE_FILE" ]; then
        set_property "postgresql-database" "$(cat "$POSTGRES_DATABASE_FILE")"
    elif [ -n "$POSTGRES_DATABASE" ]; then
        set_property "#postgresql-database" "$POSTGRES_DATABASE"
    else
        postgres_missing_vars
        exit 1;
    fi

    if [ -n "$SPRINGBOOT_DIALECT" ]; then
       set_property "spring.jpa.properties.hibernate.dialect" "$SPRINGBOOT_DIALECT"
    else
       postgres_missing_vars
       exit 1
    fi

    if [ -n "$SPRINGBOOT_DATASOURCE" ]; then
        for ELEMENT in "postgresql" "://" "$POSTGRES_HOSTNAME" ":" "$POSTGRES_PORT" "/" "$POSTGRES_DATABASE"; do
        SPRINGBOOT_DATASOURCE+="${ELEMENT}"
        done
        set_property "spring.datasource.url" "$SPRINGBOOT_DATASOURCE"
    else
       postgres_missing_vars
       exit 1
    fi   

    if [ -n "$SPRINGBOOT_DB_DRIVER" ]; then
       set_property "spring.datasource.driver-class-name" "$SPRINGBOOT_DB_DRIVER"
    else
       postgres_missing_vars
       exit 1
    fi

    if [ $BOOKSAPI_PROFILE == "dev" ]; then
        echo "$(cat "$BOOKSAPI_PROPERTIES")"
    else
       echo "Container isn't ready for production. Please wait for newer release. https://github.com/Project-Books/books-api"
       exit 1
    fi
}

##
## Ascertain whether supplied var's resolve to a postgres db.
##
resolve_postgresql() {

    if [ -n "$POSTGRES_HOSTNAME" ]; then
      /opt/booksapi/bin/wait-for-it.sh --timeout=0 --strict "$POSTGRES_HOSTNAME:$POSTGRES_PORT" -- echo "Starting BooksApi"
    fi
}

##
## Starts Booksapi under Java, replacing the current process with the
## Java process. As the current process will `be replaced, this MUST be the
## last function run within the script.
##
echo "Starting the BooksApi"
start_booksapi() {

    exec java -jar -Dspring.profiles.active=$BOOKSAPI_PROFILE /opt/booksapi/app.jar --spring.config.name=jdbc --spring.config.location=file://"$BOOKSAPI_PROPERTIES"

}

#
# Start with a fresh BOOKSAPI_HOME
#

rm -Rf "$BOOKSAPI_HOME"


#
# Track which backends are to be used
#

INSTALLED_DB=""


# Use PostgreSQL if database specified
if [ -n "$POSTGRES_DATABASE" ] || [ -n "$POSTGRES_DATABASE_FILE" ]; then
    associate_postgresql
    INSTALLED_DB="$INSTALLED_DB postgres"
    resolve_postgresql
fi

#
# Validate that at least one backend is installed
#

if [ -z "$INSTALLED_DB" ] && [ -z "$BOOKSAPI_HOME_TEMPLATE" ]; then
    cat <<END
FATAL: No authentication configured
-------------------------------------------------------------------------------
The Booksapi Docker container needs at least one db present in
order to function, such as a PostgreSQL database.
Please specify at least the POSTGRES_DATABASE, 
or check Booksapi's Docker
documentation regarding configuring the DB.
END
    exit 1;
fi


#
# Finally start Booksapi (under JAVA)
#

start_booksapi
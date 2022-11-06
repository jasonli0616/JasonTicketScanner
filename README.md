# JasonTicketScanner

This is a general admission (multiple) ticket scanner application.

This is a quick thrown-together application built for one event,
and it may require some changes to deploy in a real world situation.

## Important

This project uses [org.xerial/sqlite-jdbc](https://search.maven.org/artifact/org.xerial/sqlite-jdbc/3.39.3.0/jar) as a dependency.\
This dependency does not use the [Java 9+ module system](https://www.oracle.com/corporate/features/understanding-java-9-modules.html), and therefore does not work with [Jlink](https://openjfx.io/openjfx-docs/).\
See relevant issues: [#790](https://github.com/xerial/sqlite-jdbc/issues/790), [#478](https://github.com/xerial/sqlite-jdbc/issues/478).

Therefore, we will use a workaround ([source](https://github.com/xerial/sqlite-jdbc/issues/478#issuecomment-622391698)).
* Download the `.jar` for the dependency (on [Github](https://github.com/xerial/sqlite-jdbc/releases/tag/3.39.3.0) or [Maven](https://search.maven.org/artifact/org.xerial/sqlite-jdbc/3.39.3.0/jar))
* Add the `.jar`to the base directory for this repo
* Run the following (in this directory):
```shell
javac --patch-module org.xerial.sqlitejdbc=./sqlite-jdbc-3.39.3.0.jar org.xerial.sqlitejdbc/module-info.java
```

```shell
jar uf ./sqlite-jdbc-3.39.3.0.jar -C org.xerial.sqlitejdbc module-info.class
```

* To build this application, run `mvn clean javafx:jlink`
* To run this application, run `mvn clean javafx:run`

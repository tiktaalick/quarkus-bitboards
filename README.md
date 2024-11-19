# quarkus-bitboards

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: <https://quarkus.io/>.

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at <http://localhost:8080/q/dev/>.

## Packaging and running the application

The application can be packaged using:

```shell script
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./mvnw package -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/quarkus-bitboards-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult <https://quarkus.io/guides/maven-tooling>.

## Sliding across the board and capturing material

Let's assume the board is empty, except for one rank. This rank has 4 occupied squares. In the third position to the right there is a so-called
slider. That slider wants to slide across the board and capture some material.

```
occupied o  = 11000101
slider   s  = 00000100
```

Here you can see it sliding to the left (`1111`):

```
occupied o  = 11000101
o-2s        = 10111101
o^(o-2s)    = 01111000
```

Here you can see it sliding to the right (`11`). The apostrophs stand voor binary in a reversed order.

```
occupied'   = 10100011
slider'     = 00100000
o'-2s'      = 01100011
o'^(o'-2s') = 11000000
11000000'   = 00000011
```

This can be simplified to:

```
occupied    = 11000101
o'-2s'      = 01100011
(o'-2s')'   = 11000110
o^(o'-2s')' = 00000011
```

And still you can see it sliding to the right (`11`).

Combining both directions results in a `1111` as well as a `11`:

```
(o^(o-2s)) ^ (o^(o'-2s'))' = 01111000
                             00000011
                             --------^
                             01111011
```

This formula can be simplified to:

```
   (o-2s)  ^    (o'-2s')'  = 10111101
                             11000110
                             --------^
                             01111011
```

A chessboard does not consist of one horizontal rank, but of 64 squares. To slide over in all allowed directions a horizontal, vertical or
diagonal mask is added to the formula:

```
(((o&m)-2s) ^ ((o&m)'-2s')')&m
```

## Related Guides

- REST ([guide](https://quarkus.io/guides/rest)): A Jakarta REST implementation utilizing build time processing and Vert.x. This extension is not
  compatible with the quarkus-resteasy extension, or any of the extensions that depend on it.
- REST Jackson ([guide](https://quarkus.io/guides/rest#json-serialisation)): Jackson serialization support for Quarkus REST. This extension is not
  compatible with the quarkus-resteasy extension, or any of the extensions that depend on it
- Hibernate ORM with Panache ([guide](https://quarkus.io/guides/hibernate-orm-panache)): Simplify your persistence code for Hibernate ORM via the
  active record or the repository pattern
- JDBC Driver - PostgreSQL ([guide](https://quarkus.io/guides/datasource)): Connect to the PostgreSQL database via JDBC

## Provided Code

### Hibernate ORM

Create your first JPA entity

[Related guide section...](https://quarkus.io/guides/hibernate-orm)

[Related Hibernate with Panache section...](https://quarkus.io/guides/hibernate-orm-panache)

### REST

Easily start your REST Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)

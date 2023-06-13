# 📋 Todo CLI App 🎛️

CRUD **command-line** app, built with **Spring Shell** and connected to a **Postgres DB**.

Users can create, read, update and delete _todo_ items, as well as query by todo's tags and completion status.

Uses Spring Shell's **Flow Components**, for interactive commands.

Compile to a native executable via **GraalVN**, using command `./mvnw package -Pnative`.

[(More info about compiling to native via GraalVN)](https://docs.spring.io/spring-shell/docs/3.1.0/docs/index.html#native)
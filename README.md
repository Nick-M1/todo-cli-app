# 📋 Todo CLI App 🎛️

CRUD **command-line** app, built with **Spring Shell** and connected to a **Postgres DB**.

Users can create, read, update and delete _todo_ items, as well as query by todo's tags and completion status.

Uses Spring Shell's **Flow Components**, for interactive commands.

Compile to a native executable via **GraalVN**, using command `./mvnw package -Pnative`.

[(More info about compiling to native via GraalVN)](https://docs.spring.io/spring-shell/docs/3.1.0/docs/index.html#native)



## Available Commands:
```
Auth Controller
       greeting: Greets user

Todo Controller
       todo delete: Deletes a todo, by id
       todo find by completion: Returns all todos that are completed (if --incomplete flag, returns todos that are incomplete
       todo find by tag: Returns a todo, by its tags
       todo create: Creates a new todo
       tags find: Returns all distinct todo tags
       todo find by id: Returns a todo, by id
       todo find all: Returns all todos
       todo update: Updates a todo, by its id
       todo completed: Marks a todo as complete
```

## Demo Video:
https://github.com/Nick-M1/todo-cli-app/assets/91367903/f2373ba8-1057-4047-b81c-acaa78262d36
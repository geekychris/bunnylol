<p align="center">
  <img src="assets/logo.png" alt="Bunnylol logo" width="180">
</p>

# Bunny - URL Redirection Framework

A Spring Boot application that provides a browser shortcut/redirection framework similar to bunnylol. This application allows you to configure custom commands in your browser's search to quickly navigate to websites.

## Overview

Bunny allows you to set up custom shorthand commands that redirect to specific websites. For example:
- Typing `yt cats` in your browser will redirect you to YouTube with a search for "cats"
- Typing `g spring boot` will redirect you to Google with a search for "spring boot"
- Typing `gh spring` will redirect you to GitHub with a search for "spring"

## Setup and Installation

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher

### Building the Application

1. Clone the repository:
   ```
   git clone https://github.com/yourusername/bunny.git
   cd bunny
   ```

2. Build the application using Maven:
   ```
   mvn clean package
   ```

3. Run the application:
   ```
   java -jar target/bunny-0.0.1-SNAPSHOT.jar
   ```
   
   Alternatively, you can run it directly with Maven:
   ```
   mvn spring-boot:run
   ```

4. The application will start on port 8080 by default (configurable in application.properties)

### Configuring Your Browser

To use Bunny as your browser's search engine:

#### Chrome
1. Go to Settings > Search engine > Manage search engines
2. Click "Add" to add a new search engine
3. Name: Bunny
4. Keyword: bunny
5. URL: http://localhost:8080/r?q=%s
6. Click "Add"

You can now use Bunny by typing "bunny" followed by a space in the address bar, then entering your command.

#### Firefox
1. Right-click on the address bar search field
2. Click "Add a Keyword for this Search..."
3. Name: Bunny
4. Keyword: bunny
5. URL: http://localhost:8080/r?q=%s
6. Click "Save"

## Usage

### Built-in Commands

The application comes with several built-in commands:

| Command | Description                      | Example               | Result                                           |
|---------|----------------------------------|------------------------|--------------------------------------------------|
| g       | Google search                    | g spring boot          | Redirects to Google search for "spring boot"     |
| yt      | YouTube search                   | yt java tutorial       | Redirects to YouTube search for "java tutorial"  |
| gh      | GitHub search                    | gh spring framework    | Redirects to GitHub search for "spring framework"|
| so      | Stack Overflow search            | so java exception      | Redirects to Stack Overflow search              |
| wiki    | Wikipedia search                 | wiki java              | Redirects to Wikipedia article                  |
| amzn    | Amazon search                    | amzn headphones        | Redirects to Amazon search                      |
| tw      | Twitter search                   | tw spring boot         | Redirects to Twitter search                     |
| li      | LinkedIn search                  | li java developer      | Redirects to LinkedIn search                    |
| help    | Show all available commands      | help                   | Shows the help page with all commands           |

### Viewing All Commands

To see a list of all available commands, simply navigate to:
```
http://localhost:8080/
```

Or use the command:
```
help
```

## Customization

You can customize the available commands by modifying the `RedirectService.java` file. Add new commands in the `initializeCommands()` method following the existing pattern.

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License

This project is licensed under the MIT License - see the LICENSE file for details.


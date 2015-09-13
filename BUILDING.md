# Building Rustidea

## Prerequisites

1. You need [Intellij IDEA Community or Ultimate](https://www.jetbrains.com/idea/) to be installed in your system
2. Set up plugin development environment, see [IntelliJ Platform SDK DevGuide / Setting Up Development Environment](http://www.jetbrains.org/intellij/sdk/docs/basics/getting_started/setting_up_environment.html)
3. Install and enable additional plugins:
    - **Grammar-Kit** (only for syntax highlighting of `flex` files)
    - **UI Designer**
4. Additionally you can enable following plugins:
    - **PsiViewer** (very usable when debugging parsers)

## Project configuration

1. Open Rustidea project in IntelliJ Idea
2. Open *Project Structure* window and in *Project* screen:
    - set **Project SDK** to recently configured IntelliJ Plugin SDK
    - set **Project compiler output** path to somewhere, for example: `[project directory]/out`
3. Copy file `build.properties.skeleton` as `build.properties` and adjust it to your environment.

## Building the plugin

1. Run `JFlex` run configuration (`generate.jflex` Ant task) to generate lexers
2. Make

## Running the plugin

1. Build the plugin
2. Run `Rustidea` run configuration

## Running tests

1. Build the plugin
2. Run `All in Rustidea` run configuration

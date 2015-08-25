# Building Rustidea

## Prerequisites

1. You need [Intellij IDEA Community or Ultimate](https://www.jetbrains.com/idea/) to be installed in your system.
1. Set up plugin development environment, see [IntelliJ Platform SDK DevGuide / Setting Up Development Environment](http://www.jetbrains.org/intellij/sdk/docs/basics/getting_started/setting_up_environment.html).
1. Install and enable additional plugins:
  - **PsiViewer*
  - **Grammar Kit**
  - **UI Designer**

## Building the plugin

1. Run following run configurations:
  - `JFlex`
  - `Grammar-Kit (for Unix platforms)` or `Grammar-Kit (for Windows platforms)`, depending on your OS.
    The requirement for different configurations is caused by handling of path separator in Grammar-Kit standalone executable.
1. Make

## Running the plugin

Just run `Rustidea` run configuration. It has all build steps listed as dependencies.

## Running tests

TODO

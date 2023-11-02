# Color Flood DOS Memories: Mid-90's Nostalgia

<!-- TOC -->
* [Description](#description)
  * [Gameplay](#gameplay)
* [Implementation](#implementation)
  * [Development](#development)
  * [Testing](#testing)
* [Installation](#installation)
  * [Java 21](#java-21)
  * [Apache Maven 3.9.5](#apache-maven-395)
  * [IntelliJ Idea](#intellij-idea)
* [Setup and Execution](#setup-and-execution)
  * [Opening the Project in IntelliJ](#opening-the-project-in-intellij)
  * [Downloading External Libraries](#downloading-external-libraries)
  * [Building the Project](#building-the-project)
  * [Running the Project](#running-the-project)
* [Future Work](#future-work)
<!-- TOC -->

## Description

This project is a very simple in-browser implementation of a version of the 
Color Flood game that I remember playing in the mid-90s. 

### Gameplay

The game is played on a grid of cells that are of a variety of colors. The 
Player starts with control of the lower left corner of the grid, while the 
opponent (Computer) starts with control of the upper right corner of the grid. 

To take their turn, the Player clicks on any of the links below the grid 
that correspond to the colors used on the grid. The cells controlled by the 
Player then switch to the color clicked on, as well as any cells of that 
color that were touching them. The Computer then responds by taking its turn 
in the same fashion. 

The object of the game is to control more area of the grid than the opponent.
The game ends when there are no more cells that are not controlled by the 
Player or Computer, or someone is winning by more cells than are left not 
controlled. Whoever controls more cells is the winner. At the bottom there 
is a 'Reset Game' link, allowing the game to be played again. 

## Implementation

### Development

This game was made using the Java environment I have become used to while 
working as a Full Stack Java Dev. It is a SpringBoot MVC application that uses 
the ThymeLeaf framework. However, for the sake of simplicity, the Database 
layer has been omitted from this project. Instead of using VFX libraries to 
draw the grid and other components, simple tables and hyperlinks are used. 

The part that was most interesting to me while writing this were the 
algorithms for determining which cells the Player absorbs during their turn, 
as well ass how the Computer determines which color to select. 

Once the Player clicks on a color, all  the cells they own are iterated 
over to find neighboring cells of the chosen color. If such cells are found, 
their neighboring cells are iterated over to see if they are of the same 
color as well. 

Similarly, the Computer iterates over the cells it owns, counting up how 
many cells of each color are available to absorb, and then selects the color 
with the most cells for its turn. 

### Testing

Due to time constraints, most of the testing was conducted manually by 
playing the game and using very simple console output log statements.

Unit testing was conducted on the functionality for absorbing cells by the 
Player and Computer, which were discussed above, since these areas needed 
very close attention.

## Installation

### Java 21

This project requires Java 21, which can be downloaded from [this page](
https://www.oracle.com/java/technologies/downloads/#java21). The page also contains a link to installation instructions. 
 
### Apache Maven 3.9.5

This project uses Maven 3.9.5, which can be found [here](
https://maven.apache.org/download.cgi). Also, there are very good [Installation Instructions](
https://maven.apache.org/install.html). 

### IntelliJ Idea

The IntelliJ Idea IDE can be downloaded from [this page](
https://www.jetbrains.com/idea/download/).
The 'Other Versions' link contains downloads for the Community Edition, 
which is free and should support everything needed to run this. This page 
also has a link for Installation Instructions. 

## Setup and Execution

### Opening the Project in IntelliJ

If you already downloaded this project locally, and IntelliJ is open 
inside a project, click 
File --> New --> Project from Existing Sources and open the directory in 
which the project is located. There is also a method of creating the new project by opening it via Version Control, if desired. If IntelliJ opens on the welcome page, Click 'Open' for locally downloaded project, or 'Get from VCS' to open the project by downloading it from GitHub.

### Downloading External Libraries

Once the project is open, it should be recognized as a maven project. It will be clear this is the case if the IDE starts downloading all the External Libraries needed.
If this does not occur, right-click on the pom.xml file in the root 
directory, and select 'Add as maven project.'

### Building the Project

Once the External Libraries are downloaded, there should be a tab on the right side labeled 'maven.' If it is not there, click View --> Tool Windows --> maven. Click on the maven tab to expand it. This is possibly overkill, but in order to make sure the project compiles correctly, under Lifecycle, I run the 'clean' option, followed by 'install.'

### Running the Project

Within IntelliJ, click on the 'Terminal' tab at the bottom, and enter './mvnw spring-boot:run' to run the project.
To open the webpage that contains the game, navigate to
http://localhost:8080 in a browser.
The project can be stopped by entering ctrl+c into the Terminal window. 

## Future Work

This game is nowhere near where I would like it to be. Here are a few things 
I would improve provided more time.

- __Visual Aesthetics:__ I would love to make this game more visually pleasing, 
  possibly even using 
VFX libraries. The way colors are selected could especially be improved, as 
  well as the messaging indicating the result of the game, and the 'Reset 
  Game' option.
  - It would also be handy to have some sort of a log to the side of the 
    grid that would note down what the Player and Computer did on their 
    turns, sort of like a chess move log, or the combat log of a CRPG.
- __Configurability:__ The DOS game I remember let me pick how big the 
  grid would 
  be, as well as how many colors were used. 
- __Gameplay Modes:__ Since a lot of the modern 
  implementations of Color Flood games are not against an opponent, a solo 
  play mode would be handy. In these circumstances the Player would try to 
  absorb the whole grid in as few turns as possible. 
- __Functionality:__ There is an issue that the game does not detect when 
  the Player or Computer can't take more turns, as there are no more cells 
  to absorb. Currently, if this happens to the Computer, the Player can 
  continue as normal (the Computer will stop picking new colors), but when 
  it happens to the Player, and the Computer still has cells it could absorb, 
  the Player needs to keep clicking on colors until the game ending 
  conditions are met. This needs to be remedied with a method of determining 
  whether the Player has more moves or not.
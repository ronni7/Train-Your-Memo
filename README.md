# Train-Your-Memo
Train Your Memo 
# 1 Technologies
# 2 Structure
# 3 Installation
# 4 Gameplay 
Train Your Memo is a PC desktop game

1.Technologies
Client Side:
- Java
- JavaFX
- CSS
Server(REST API)
- Spring-Boot
- Hibernate JPA
2. Structure
rest api folder contains REST API files, its excluded from client part, so it runs as a standalone application
other files are client application
Both application have been developed using Intellij IDEA Premium IDE
REST API deployed using Apache Tomcat Application Server, connected through IDE and run as embedded
In that part, MySQL database is used, also deployed locally with Apache
3. Installation
REST API is built using Maven, so the project can be cloned and created from pom.xml
Database can be created using bazatestowa.db from package Database
Client-side is not built with Maven it needs to be copy-pasted into JavaFx type project(hope it will work same as for me). Also, it uses GSON library from 'libs' package, so it needs to be added into project's structure class path
4. Gameplay
Client:
Gameplay(New Game):
4.1.1 General:
The game is about matching pairs, if a pair has been matched, it disappears from the board view. In other cases the cards are being hidden again.
Score is defined by time, viewed in a clock at the top of the board view. Shorter the time, better the score is :)
In game, there are 4 difficulty levels:
  -beginner (8 pairs)
  -average (16 pairs)
  -expert (24 pairs)
  -master (32 pairs)
4.1.2 Winning condition
Winning a game, is defined by matching all pairs from the board. If that condition is met, user will obtain a dialog, which informs him about winning.
That dialog also provides an option to submit user score into Highscore table (requires internet connection and running REST API server)
4.2 Highscore
The highscore table is presented as a list, in a highscore view. requires internet connection and running REST API.
4.3. Options:
Options view consists of:
- pack selection: which means images to load as a cards in game.
- difficulty level selection, which is also defines a board size 
- On-Off music 
- Changing theme (light/dark)
- Save/Cancel changes
- Back to menu
4.4 Credits 
Credits view contains information about author, and date since last development
4.5 Exit 
Making a click on exit button shows dialog asking player, if he wants to leave game.

Server side
REST API provides following functionality
- User registration(include access key generation, and hashing with BCrypt algorithm into database)
- Access key validation (Look #FirstRun)
- Loading highscore table, depending on user selected level
- Loading best user score, at selected level, which appears on the left side of view
- Adding new score to highscore table

Database:
Database has simple structure for development purposes
It consists of two tables User and Scores
User has the following structure:
- ID (int)
- name (varchar)
- login(varchar)
- nickname (varchar)
- validation_key(varchar)
- activated (Boolean)
Scores has the following structure:





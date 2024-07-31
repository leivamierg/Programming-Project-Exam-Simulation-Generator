# Programming-Project-Exam-Simulation-Simulator

Free Choice-Group project for the "Programming Project" course of the Free University of Bozen-Bolzano, 2nd semester 2023-2024.

## Members of the group and its GitHub identifiers

- Sebastiano Nardin (sebanardin)
- Gersson Leiva Mier (leivamierg / cool27027)
- Violetta Fazzi (Vaiolo)
- Massimiliano Mola (@MassimiIiano)

## Instructions to build and run the project

### Build

To build the project, run the following command in the root directory of the project:

```bash
mvn clean compile assembly:single
```

The documentation will be generated in the `target/site/apidocs` folder.

### Run

Execute the jar file created in the target folder

```bash
java -jar target/tester-1.0-jar-with-dependencies.jar
```

## Project's overall description

- Our project is a test simulation software, thought to be used by the users to prepare themselves for a multiple-choice format exam.
- The main feature of our program consists in generating an exam simulation of a chosen topic, representing a specific university course.
- In the simulation the user has a timer indicating the time left, and a Command Line User Interface to see the current question with its
  answers and select one of the given choices. In addition, the user can move around the questions by skipping some parts of the exam or going back to an already seen question.
  The simulation finishes automatically when the timer expires or when the user inserts a command to terminate the simulation.
- Once the simulation is finished the user sees its results and can decide to do another simulation or not.
- Other features of the project include the visualization of the available topics, past simulations data and stats.
- The program selects the questions to put in the simulation based on how many times an answer was present in previous simulations an if it was answered correctly or not, using a priority system for all the questions available.
- The program also makes sure that once the program is closed it saves the priority of the questions, stats and data, and loads them again whenever the program starts.

## User-guide for our project

N.B Do not use any quotes. Write directly the word needed.
Example: 
- linear algebra -s
- exit
- terminate


List of possible commands after opening the program:

- -t, --topics : to list all the topics
- 'topic' -s, --subtopics : to list all the subtopics
- 'topic' : to start the test
- --history : to show the history of simulation (it will be empty at the beginning)
- --stats : to show the general statistics (it will be empty at the beginning)
- 'exit' : to close the exam simulation program

List of possible commands during the simulation:

- A, B, C, D, ' ' : to answer the questions
- '+', '-' : to navigate through the questions
- 0-9 : to choose the question by its number
- 'terminate' : to terminate the simulation and get the results

## Implementation of the project

### Overall Structure: Model, Controller and App

If you want also a more in depth explanation of the project you can generate the javadoc by running the following command in the root directory of the project:

```bash
mvn javadoc:javadoc
```
The documentation will be generated in the `target/site/apidocs` folder.


#### Interfaces

In this directory we can find every interface of the controller classes, including all the public methods of each class implementing an interface together with a javadoc giving a brief explanation on what the method does, its parameters, return types and possible exceptions. Every interface has the same name as its implementing class + the Int sufix.\
There are no interfaces available for the records and the comparators.

#### Implementations

##### Base structural classes (@leivamierg)

We have only three different classes to keep the different questions organised and separated in different areas: Topic, Subtopic and Question.\
The highest hierarchy class would be Topic, parting from there, each Topic object contains many Subtopic objects, and each Subtopic object contains many Question objects. The lowest class on the hierarchy would be Question, not containing any structural class among its various data members.

###### Topic class

Represents a specific university course, for instance: Linear Algebra or Computer Systems Architecture.\
It contains a Set of subtopic representing the different sections of the course.

###### Subtopic class

Represents a university course Chapter/Lesson, for example: (Linear Algebra) Spectral Analysis, etc.\
It contains a set of questions of the current Subtopic, and it is linked to its appertaining Topic object.

###### Question class

Represents a single possible question in a simulation.
Contains the question statement and the possible answers. It is by far the most complex class of the structural classes as it provides many methods to shuffle the possible answers and assign them randomly a letter from A to D.


##### FileLoading, FileSaving and Serialization
We used Jackson to implement JSON serialization and deserialization
- When the program starts the topics (along with subtopics and questions) are loaded by using the static method loadBank of the FileLoader class
- When the program ends the topics are saved into the JSON files by using the static method saveBank of the FileLoaderClass -> when the user re-start the program the last state of the program is loaded
- The same process is valid for History and Stats, which use the HistoryStatsLoader class to be serialized and deserialized

###### PDFGenerator class

This class is designed to convert a JSON file into a well-formatted PDF document. It is based on the Jackson library for JSON processing and Apache PDFBox for the PDF creation. It creates printable simulations for the different topics. 

##### Stats and history classes (@leivamierg and @sebanardin)

These two classes give a sense of progress to the user saving data of previous simulations, they use serialization and deserialization as well to save and load their content.

###### Stats class

This class is meant to store the stats of every topic, subtopic and the general stats after every simulation.
In addition, it supports JSON serialization/deserialization. Moreover, it provides some methods to compare the stats of a certain topic or subtopic from simulation x to simulation y.
Furthermore, it allows the user also to see the stats of a certain topic/subtopic after x simulation.

###### History class

This class is quite simple, it only contains a list of TestRegister records, created specifically to store the previous simulation data. Other than supporting JSON serialization and deserialization it also has methods to see the full or a part of the history in the command line.\
Some data saved in the history are: number of questions, correct answers, wrong questions, used time / available time, etc.

###### User class

This class represents a user in the application who has a username, who can earn badges, mantain streaks and participate in daily challenges. The role of the class is to keep track of the information regarding a specific user.

###### Badge class

This class represents an individual badge that a user can earn during the daily challenges. It provides the model of a badge which can be earned, and it can be used with the 'User' class to represent the badges earned by the user.

##### Command Handling

The Exam Simulation Program is based on the interaction between the user and the program itself via command-line inputs. The command handling process ensures that the inputs are interpreted and the actions executed.
The process handling starts from the entry point of the program through the 'App' class, where the user is asked to insert one of the possible commands.
Each of the command calls a specific function from the 'ModelInt' interface, where the method 'testAllSubtopics' starts the simulation through the method 'start' from the Simulation class.
On the method 'testAllSubtopics', two types of input are required: the number of questions per subtopic and the answer for each question.
Connected to the 'Simulation' class, the user can input letters (A,B,C,D), numbers (0-9), and the characters '+' and '-'. Other characters are not accepted and if an invalid command is inserted during the simulation, the user will be asked the same question again.
There are three ways of seeing the questions: by pressing 'Enter' after answering, by pressing '+' or '-' to see the next or previous one, and by writing the number of the question.
If the user presses '-' at the first question or '+' at the last one, respectively the first question or the last one will be asked again, since the person cannot go backwards or forward.
During the simulation the user can write 'terminate' to finish the simulation and visualize the results, and then write 'exit' to leave the program.
The command 'exit' can also be written on the main "page" at the beginning.
In the more advanced version of the project, the commands 'history' and 'stats' can also be written and the general history and statistics can be checked.
*All the commands can be written in upper case, lower case or both.*

###### Simulation class
Represents a simulation of an exam. It provides several functionalities:
- Select the topic the simulation is about -> all the subtopics of the given topic are part of the simulation (the questions are grouped by subtopic)
- Select only some subtopics of a given topic (not implemented but can be tested by running the tests)
- Answer to a question 
- Change the question (either to the previous/next one or to a specific one)
- Terminate the simulation -> the result (general and for each subtopic) is printed and every parameter needed for the next simulations is updated

#####  Model class

Model is mainly a facade for interacting with simulation. In retrospective we should have designed it as a part of the controller.

##### Timer functionality

The timer functionality was implemented to give a duration to the tests' simulation. Each simulation is set to a duration of 30 minutes and the remaining time is displayed every time the user answers to a question.
This way, the remaining time is continuously updated. When the remaining time reaches zero or the user ends the test through the command 'terminate', both the test and the timer are stopped.

#### Comparators

Even though having its own directory, actually there is only one comparator:

##### QuestionPriorityComparator (@leivamierg)

It is a useful tool to sort a question list based on their "priorityLevel" ordering them from lower priority to higher priority.\
It is used on the Subtopic class, for the "pickQuestions" method.

###### DailyChallenge class
The 'DailyChallenge' class is where the logic for the daily challenge is handled. The daily challenge is based on five random questions about any topic, which are presented only once every day to the logged-in user. The latter must answer correctly to at least three of the five questions to win a badge, moreover, the user can accumulate a streak if he/she does the daily challenge more days in a row. The user's data are then saved after the application is closed in a separate json file with the user's name.

### The Controller

###### Controller class

The 'Controller' class handles the user's input and interacts principally with the 'ModelInt' interface.
It parses the commands and calls the relative methods from the 'ModelInt' interface by using regexes. It also relies on 'HistoryInt' and 'StatsInt' for the advanced version of the program.
In the updated version, further interactions with new classes were implemented ('PDFGenerator', 'DailyChallenge', 'User').
The 'elaborateArgs' method is where the list of arguments from the user is processed.
The overall class works as an intermediate between the user and the program, to delegate tasks to methods by processing commands.

### The App

###### App class

The 'App' class is the entry point for the program. It manages the program's lifecycle (initialization, command handling and user interaction).
It is mostly connected with the 'Controller' class, since it delegates command processing.
It also displays the possible commands for the user, allowing user-friendliness.

## Tests and Utils (@all)
Each class was tested separately by using JUnit tests.

## Human experience in this project
### Workload
- @sebanardin: Stats, Simulation, FileLoading, HistoryStatsLoader, App, Controller
- @leivamierg: Topic, Subtopic, Question, QuestionPriorityComparator, HistoryStatsLoader, History, TestRegisters
- @MassimiIiano: Controller, App, Model, Simulation, Question, HistoryStatsLoader
- @Vaiolo: Controller, App, Model, Simulation, ExamTimer, History, Stats, DailyChallenge, Badge, User, PDFGenerator

### Use of git

We used git to manage the project, each of us had a branch where we worked on our part of the project and then we merged it to the main branch. At the end we had issues with using different branches due to timerush.

### Challenges faced by each member
#### @Massimiliano

- Keeping the project organized and structured
- It was difficult to communicate about changes in each others branches so sometimes we had errors after merging to main
- POM file is not intuitive

#### @Violetta

- Communication within the group
- Issues with Libraries
- Issues with completely understanding the structure of the different classes
- Issues with working on both the project and the other courses

#### @Gersson

- Working constantly on the project while following the other courses and projects.
- Communicating with the other members of the group
- Solving tricky bugs implying java libraries, mainly because an incorrect type/class, reading and understanding the documentation could be tricky at times.

#### @Sebastiano
- Developing and testing simulation and stats classes
- Developing and testing JSON serialization/deserialization
- Developing a part of stats in App and Controller classes
- Communicate and collaborate was tricky but it gave me experience on how to collaborate within a team of developers
- The most difficult problem I faced was the Jackson serialization. In fact, Jackson's documentation is not so well described and this makes debugging a tough duty to carry out.
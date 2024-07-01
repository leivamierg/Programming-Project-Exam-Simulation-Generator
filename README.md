# Programming-Project-Exam-Simulation-Simulator

Free Choice-Group project for the "Programming Project" course of the Free University of Bozen-Bolzano, 2nd semester 2023-2024.

## Members of the group and its GitHub identifiers

- Sebatiano Nardin (sebanardin)
- Gersson Leiva Mier (leivamierg / cool27027)
- Violetta (Vaiolo)
- Masimiliano (@MassimiIiano)

## Instructions to build and run the project

### Build

To build the project, run the following command in the root directory of the project:

```bash
mvn clean compile assembly:single
```

If you want also the javadoc, run the following command:

```bash
mvn javadoc:javadoc
```
the documentation will be generated in the `target/site/apidocs` folder.

### Run

execute the jar file created in the target folder

```bash
java -jar target/tester-1.0-jar-with-dependencies.jar
```

## Project's overall description

- Our project is a test simulation software, thought to be used by the users to prepare themselves for a multiple-choice format exam.
- The main feature of our programm consists in generating an exam simulation of a chosen topic, representing a specific university course.
- In the simulation the user has a timer indicating the time left, and a Command Line User Interface to see the current question with its
  answers and select one of the given choices. In addition, the user can move around the questions by skiping some parts of the exam or going back to an already seen question.
  The simulation finishes automatically when the timer expires or when the user inserts a command to terminate the simulation.
- Once the simulation is finished the user sees its results and can decide to do another simulation or not.
- Other features of the project include the visualization of the available topics, past simulations data and stats.
- The program selects the questions to put in the simulation based on how many times an answer was present in previous simulations an if it was answered correctly or not, using a priority system for all the questions available.
- The program also makes sure that once the program is closed it saves the priority of the questions, stats and data, and loads them again whenever the program starts.

## User-guide for our project

Commands... TODO

## Implementation of the project

### Overall Structure: Model, Controller and App

### Model classes and interfaces

The whole model of the app is saved in src/controller, and its divided in three files: TO FIX

#### Interfaces

In this directory we can find every interface of the controller classes, including all the public methods of each class implementing an interface together with a javadoc giving a brief explanation on what the method does, its parameters, return types and possible exceptions. Every interface has the same name as its implementing class + the Int sufix.\
There are no interfaces available for the records and the comparators.

#### Implementations

more detailed explanation of the classes and their methods can be found in the javadoc of the classes.

run the following command to generate the javadoc:

```bash
mvn javadoc:javadoc
```

and navigate to `target/site/apidocs/index.html` to see the documentation.

##### Base structural classes (@leivamierg)

We have only three different classes to keep the different questions organised and separated in different areas: Topic, Subtopic and Question.\
The highest hierarchy class would be Topic, parting from there, each Topic object contains many Subtopic objects, and each Subtopic object contains many Question objects. The lowest class on the hierarchy would be Question, not containing any structural class among its various data members.

###### Topic class

Represents a specific university course, for instance: Linear Algebra or Computer Systems Architecture.\
It contains a Set of subtopic representing the different sections of the course.

###### Subtopic class

Represents an university course Chapter/Lesson, for example: (Linear Algebra) Spectral Analysis, etc.\
It contains a set of questions of the current Subtopic and its linked to its appartaining Topic object.

###### Question class

Represents a single possible question in a simulation.\
Contains the question statement and the possible answers. It is by far the most complex class of the structural classes as it provides many methods to shuffle the possible answers and assign them randomly a letter from A to D.

###### Simulation class
Represents a simulation of an exam. It provides several functionalities:
- select the topic the simulation is about -> all the subtopics of the given topic are part of the simulation (the questions are grouped by subtopic)
- select only some subtopics of a given topic (not implemented but can be tested by running the tests)
- answer to a question 
- change the question (either to the previous/next one or to a specific one)
- terminate the simulation -> the result (general and for each subtopic) is printed and every parameter needed for the next simulations is updated
##### FileLoading, FileSaving and Serialization
We used Jackson to implement JSON serialization and deserialization
- when the program starts the topics (along with subtopics and questions) are loaded by using the static method loadBank of the FileLoader class
- when the program ends the topics are saved into the JSON files by using the static method saveBank of the FileLoaderClass -> when the user re-start the program the last state of the program is loaded
- The same process is valid for History and Stats, which use the HistoryStatsLoader class to be serialized and deserialized
##### Stats and history (@leivamierg and @sebanardin)

These two classes give a sense of progress to the user saving data of previous simulations, they use serialization and deserialization as well to save and load their content.

###### Stats
This class is meant to store the stats of every topic, subtopic and the general stats after every simulation.
In addition, it supports JSON serialization/deserialization. Moreover, it provides some methods to compare the stats of a certain topic or subtopic from simulation x to simulation y.
Furthermore, it allows the user also to see the stats of a certain topic/subtopic after x simulation.

###### History

This class is quite simple, it only contains a list of TestRegister records, created specifically to store the previous simulation data. Other than supporting JSON serialization and deserialization it also has methods to see the full or a part of the history in the command line.\
Some of the data saved in the history are: number of questions, correct answers, wrong questions, used time / available time, etc.

##### Command Handling
TODO

##### The Simulation and the Model

Simulation class is the 'real backend' of the application and it is responsible for managing the exam simulation itself (keeps track of questins order, answers, time etc.). It has the

Model is mainly a facade for interacting with simulation. in retrospective we should have designed it as a part of the controller.
TO FIX
#### Comparators

Even though having its own directory, actually there is only one comparator:

##### QuestionPriorityComparator (@leivamierg)

It is a useful tool to sort a question list based on their "priorityLevel" ordering them from lower priority to higher priority.\
It is used on the Subtopic class, for the "pickQuestions" method.

### The Controller
TODO

### The App
TODO
## Tests and Utils (@all)
Each class was tested separately by using JUnit tests.

## Human experience in this project
- Sebastiano: communicate and collaborate was tricky but it gave me experience on how to collaborate within a team of developers

### Workload
- Sebastiano: 50 hours (only for writing and debugging code)

### Use of git
- Sebastiano: only in few moments I struggled with git, but it's absolutely essential for developing the project

### Challenges faced by each member
- Sebastiano: the most difficult problem I faced was the Jackson serialization. In fact, Jackson's documentation is not so well described and this makes debugging a tough duty to carry out.

#### @Massimiliano

- keeping the project organized and structured
- it was difficult to communicate about changes in each others branches so sometimes we had errors after merging to main

#### @Violetta

#### @Gersson

- Working constantly on the project while following the other courses and projects.
- Communicating with the other members of the group
- Solving tricky bugs implying java libraries, mainly because an incorrect type/class, reading and understanding the documentation could be tricky at times.

#### @Sebastiano
- Developing and testing simulation and stats classes
- Developing and testing JSON serialization/deserialization
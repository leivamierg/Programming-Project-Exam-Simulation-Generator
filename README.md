# Programming-Project-Exam-Simulation-Simulator

Free Choice-Group project for the "Programming Project" course of the Free University of Bozen-Bolzano, 2nd semester 2023-2024.

## Members of the group and its GitHub identifiers

- Sebatiano Nardin (sebanardin)
- Gersson Leiva Mier (leivamierg / cool27027)
- Violetta (Vaiolo)
- Masimiliano (Masimiliano)

## Instructions to build and run the project

### Build

```bash
mvn clean package
```

### Run

execute the jar file created in the target folder

```bash
mvn exec:java -Dexec.mainClass="it.unibz.app.App"
```

## Project's overall description

- Our project is a test simulation software, thought to be used by the users to prepare themselves for a multiple-choice format exam.
- The main feature of our programm consists in generating an exam simulation of a chosen topic, representing a specific university course.
- In the simulation the user has a timer indicating the time left, and a Command Line User Interface to see the current question with its
  answers and select one of the given choices. In addition, the user can move around the questions by skiping some parts of the exam or going back to an already seen question.
  The simulation finishes automatically when all the questions has been answered or when the user inserts a command to terminate the simulation.
- Once the simulation is finished the user sees its results and can decide to do another simulation or not.
- Other features of the project include the visualization of the available topics, past simulations data and stats.
- The programm selects the questions to put in the simulation based on how many times an answer was present in previous simulations an if it was answered correctly or not, using a priority system for all the questions available.
- The program also makes sure that once the program is closed it saves the priority of the questions, stats and data, and loads them again whenever the program starts.

## User-guide for our project

Commands...

## Implementation of the project

### Overal Structure: Model, Controller and App

### Model classes and interfaces

The whole model of the app is saved in src/controller, and its divided in three files:

#### Interfaces

In this directory we can find every interface of the controller classes, including all the public methods of each class implementing an interface together with a javadoc giving a brief explanation on what the method does, its parameters, return types and possible exceptions. Every interface has the same name as its implementing class + the Int sufix.\
There are no interfaces available for the records and the comparators.

#### Implementations

##### Base structural classes

We have only three different classes to keep the different questions organised and separated in different areas: Topic, Subtopic and Question.\
The highest hierarchy class would be Topic, parting from there, each Topic object contains many Subtopic objects, and each Subtopic object contains many Question objects. The lowest class on the hierarchy would be Question, not containing any structural class between its various data members.

###### Topic class

Represents a specific university course, for instance: Linear Algebra or Computer Systems Architecture.\
It contains a Set of subtopic representing the different sections of the course.

###### Subtopic class

Represents an university course Chapter/Lesson, for example: (Linear Algebra) Spectral Analysis, etc.\
It contains a set of questions of the current Subtopic and its linked to its appartaining Topic object.

###### Question class

Represents a single possible question in a simulation.\
Contains the question statement and the possible answers. It is by far the most complex class of the structural classes as it provides many methods to shuffle the possible answers and assign them randomly a letter from A to D.

##### FileLoading, FileSaving and Serialization

##### Stats and history

These two classes give a sense of progress to the user saving data of previous simulations, they use serialization and deserialization as well to save and load their content.

###### Stats

###### History

This class is quuite simple, it only contains a list of TestRegister records, created specifically to store the previous simulation data. Other than supporting JSON serialization and deserialization it also has methods to see the full or a part of the history in the command line./
Some of the data saved in the history are: number of questions, correct answers, wrong questions, used time / available time, etc.

##### Command Handling

##### The Simulation and the Model

#### Comparators

Even though having its own directory, actually there is only one comparator:

##### QuestionPriorityComparator

It is a useful tool to sort a question list based on their "priorityLevel" ordering them from lower priority to higher priority./
It is used on the Subtopic class, for the "pickQuestions" method.

### The Controller

### The App

## Human experience in this project

### Workload

### Use of git

### Challenges faced by each member

#### @MassimiIiano

- keeping the project organized and structured
- it was difficult to communicate about changes in each others branches so sometimes we had errors after merging to main

#### @Violetta

#### @Gersson

#### @Sebastiano

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
mvn clean compile assembly:single
```

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

In this directory we can find every interface of the controller classes, including all the public methods of each class implementing an interface together with a javadoc giving a brief explanation on what the method does, its parameters, return types and possible exceptions. Every interface has the same name as its implementing class + the Int sufix./
There are no interfaces available for the records and the comparators.

#### Implementations

##### Base structural classes

We have only three different classes to keep the different questions organised and separated in different areas: Topic, Subtopic and Question./
The highest hierarchy class would be Topic, parting from there, each Topic object contains many Subtopic objects, and each Subtopic object contains many Question objects. The lowest class on the hierarchy would be Question, not containing any structural class between its various data members.

###### Topic class

Represents a specific university course, for instance: Linear Algebra or Computer Systems Architecture./
It has the following data members:

- topicName: the name of the Topic
- subtopics: A set of the subtopics contained in the specific topic/
  And the following methods:
- [- private -] setters(x)
- {+ public +} getters()
- {+ public +} toString(): returns the Topic name, and the number of subtopics it contains
- {+ public +} addSubtopic(Subtopic subtopic): adds the given Subtopic object to the subtopics Set of the current Topic.
- {+ public +} equals(Object o)
- {+ public +} hashCode()

##### FileLoading, FileSaving and Serialization

##### Stats and history

##### Command Handling

##### The Simulation and the Model

Simulation class is the 'real backend' of the application and it is responsible for managing the exam simulation itself (keeps track of questins order, answers, time etc.). It has the 

Model is mainly a facade for interacting with simulation. in retrospective we should have designed it as a part of the controller.



#### Comparators

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

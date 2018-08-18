# Conference Scheduler
This application generates a conference schedule algorithmically using
constraints.

## Pre-requisites
To build and execute this, you need the following installed on your system:

  * [Java 8](https://www.java.com)
  * [Maven 3](https://maven.apache.org/download.cgi)
  
## Build and execution

### Build
Go to the home folder of this project and build the package.

```bash
cd conf-sked
mvn clean package
```

### Execute
After the package has been built, execute the jar.

```bash
java -jar target/conf-sked.jar
```

### Sample output
```console
Day 1
09:00 Create a Serverless Searchable Media Library KEYNOTE
09:30 Deep Learning for Edge to Cloud WORKSHOP
 -or- AWS DevOps Essentials: An Introductory Workshop on CI/CD Best Practices WORKSHOP
10:30 Machine Learning for the Enterprise SESSION
11:00 AWS DeepLens Projects: Tips & Tricks LIGHTNING
11:10 Deep Learning for Developers: An Introduction SESSION
11:40 Unlock the Full Potential of Your Media Assets SESSION
12:10 Create the Voices You Want with Amazon Polly LIGHTNING
12:20 Amazon SageMaker and TensorFlow: Tips & Tricks LIGHTNING
12:30 LUNCH
13:30 Build an ETL Pipeline to Analyze Customer Data WORKSHOP
14:30 Make Money with Alexa Skills SESSION
15:00 TEA
15:15 Monitor Your AWS Usage with Amazon ES WORKSHOP
16:15 Technology Trends: Data Lakes and Analytics SESSION
16:45 Scale up a Web Application LIGHTNING
17:00 Create a Custom Celebrity List for Your Media Assets CLOSING

Day 2
09:00 Improve Accessibility Using Machine Learning KEYNOTE
09:30 What's New in Amazon Athena SESSION
10:00 Log Analytics with Amazon ES, Amazon ECS, and Amazon EKS WORKSHOP
11:00 AWS Reference Architectures SESSION
11:30 Serverless Architectural Patterns and Best Practices SESSION
12:00 Chaos Engineering and Scalability at Audible.com SESSION
12:30 LUNCH
13:30 Real-Time Analytics with AWS SESSION
14:00 Amazon SageMaker and Chainer: Tips & Tricks LIGHTNING
14:10 Three Core Pillars of Conversational UX for Alexa SESSION
15:00 TEA
15:15 Serverless Data Prep with AWS Glue WORKSHOP
17:00 Effective Cost Optimization for Business CLOSING
```

> Note: The sample talks are from AWS re:Invent

### Customize
You can modify the property file `scheduler.properties` to change the
different conference parameters or use your own property file from a different
location by adding it in the command line argument.

```bash
java -jar target/conf-sked.jar /path/to/mycustom.properties
```

## Specifications

### Talk types
There are six types of talks.

| Talk type        | Duration | Data type |
| ---------------- | -------- | ----------|
| Workshop         | 60 mins  | WORKSHOP  |
| Chalk talk       | 60 mins  | CHALK     |
| Session talk     | 30 mins  | SESSION   |
| Lightning talk   | 10 mins  | LIGHTNING |
| Keynote          | 30 mins  | KEYNOTE   |
| Closing          | 30 mins  | CLOSING   |

### Breaks
There are two break types.

| Type  | Duration |
| ------| -------- |
| LUNCH | 60 mins  |
| TEA   | 15 mins  |

> In the code, breaks are still defined in the the `TalkType` enum.

### Constraints
Here are the specific constraints the scheduler will follow when generating
the code:

  * Conference runs for two days (29-30 Jun 2018).
  * Each day starts with a 30-min keynote speech at 09:00.
  * Each day ends with a 30-min closing speech at 17:00.
  * Day starts at 09:00 and ends at 17:30.
  * There's a 60-min lunch break at 12:30 to 13:30 daily.
  * There's a 15-min tea break at 15:00 to 15:15 daily.
  * All talks must be scheduled.
  * Talks in parallel is allowed to accommodate all talks.

> The constraint parameters can be modified by changing the property file
`scheduler.properties`

## Design

### Analysis
  * Talks at 09:00 to 09:30 and 17:00 to 17:30 are fixed for `KEYNOTE` and
  `CLOSING`, respectively.
  * Breaks at 12:30 to 13:30 and 15:00 to 15:15 are fixed for `LUNCH` and
  `TEA`, respectively.
  * Since the constraints are mostly predefined timings, we will split a day
  into multiple blocks where each block applies the `MustStartWithConstraint`
  implementation.
    * The MustStartWithConstraint means that a block must start with a specific
    talk type only.
    * The rest of the talks scheduled on that block can be of any type except
    `KEYNOTE` and `CLOSING`.
  * In this case, we will split a day into four blocks with the following
  conditions: 
    * Block `B1` starts at `09:00` and ends at `12:30`
    and must start with `KEYNOTE`.
    * Block `B2` starts at `12:30` and ends at `15:00`
    and must start with `LUNCH`.
    * Block `B3` starts at `15:00` and ends at `17:00`
    and must start with `TEA`.
    * Block `B4` starts at `17:00` and ends at `17:30`
    and must start with `CLOSING`.  
    
### Data model
  * A `Conference` contains one or more days.
  * A `Day` contains one or more blocks.
  * A `Block` contains one or more slots.
  * A `Slot` contains one talk with start and end times.
  * A `Talk` contains the talk details and maps to the JSON input file.
   
### Scheduler
The `Scheduler` is the main processor that schedules the conference given a pool
of talks. All the blocks from each day in the conference are iterated and filled
with slots until all the talks in the pool is exhausted. If all the blocks are
filled and the pool still has talks not scheduled, these talks will be scheduled
in parallel with other slots. The parallel talks will be of the same type to
sync the timings.
  
### Constraint
The schedule is applied against a `Constraint` defined for each block. Specific
rules or requirements are implemented using constraints. An example constraint
`MustStartWithConstraint` is included here.

### Extensibility
New constraint implementation can be added to apply different rules when
processing the scheduling.

> Implement the interface `Constraint` to add a new constraint.

### Limitations
The current algorithm used in this project uses the `MustStartWithConstraint`
only. The following are some of its limitations:
  * The talks are searched from the pool in the same order as they are found in
  the input file.
  * The scheduling is not optimal and some free time slots are not used fully.

### For improvements
  * Schedule talks based on content using their `tags`.
  * To maximize the time slots of each block:
    * If a block is large, prioritize the long running talks in it.
    * If a block is small, prioritize the short running talks in it.
    
## Testing
Unit tests are included to check if the requirements are met.
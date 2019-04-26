# Mars-Rover

Built in [Scala](https://www.scala-lang.org/), uses the [Scala Build Tool (sbt)](https://www.scala-sbt.org/) to run.

Simple grid navigation, using a text file. The input format is
1. Grid size (`5 5`)
2. Rover start position and direction (`1 2 N`)
3. Rover movment instructions (`LMLMLMLMM`)
4. Next rover start position and direction (`3 3 E`)
5. Next rover movement (`MMRMMRMRRM`)
6. Third rover... and so on

Comes with an `input.txt` file that can be used to specify these instructions. Eg
```
5 5
1 2 N
LMLMLMLMM
3 3 E
MMRMMRMRRM
```

## Logic
The input format is checked before the commands are executed; if there's something wrong with the format (ie the instructions in the `input.txt` file), then the process will terminate.
Aside from that, the program follows the following logic:
* Rovers execute their commands sequentially; the first rover in the list will go first.
* Rovers will stop moving if they bump into each other.
* Rovers will stop moving if their commands tell them to go outside the grid.
* Final positions will be outputted once all the rovers have finished moving.
* Grid can be between 5 - 100 on an axis

Check out the tests for more examples of this

## Running
Go to the root directory and type `sbt run`

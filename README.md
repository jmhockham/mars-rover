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
(I've asked about creating a docker file for this, but was told it's not needed, so have left it out)

## Technical Notes

We assume that all the rovers are going to "land" at the same time, in which case any given rover will need to avoid displacing its peers when navigating. The least troublesome method of implementing this is to have the rovers stop moving if they're going to bump into something (at this point it's just another  rover, but the logic could easily be extended to include "obsticales" or something similar).

---

The command inputs come from a text file, which needs to be modified if further instructions are given; it can also be overriden on the command line to specify something else.

---

Even though it wasn't asked for, I've put some simple sanity checking in for the inputs, as this is just good practice. Regardless of what we intend, sometimes different formats will end up coming through, so it doesn't hurt to do some defensive programming around this.

---

Because this is Scala I've tail recurred through the rovers/instructions. However Scala seems to want to pass streams back when using `group` or several other convenience functions, which break the pattern matching. Consequently I've done the following:
Instead of 
```Scala
sequence match {
  case Nil =>
    //our end clause
    ...
  case head::tail =>
    //do something with the head, then recur
    recur(tail)
}
```
I've done
```Scala
sequence match {
  case Nil =>
    //our end clause
    ...
  case seq =>
    //do something with the head, then recur
    val head = seq.head
    ...
    recur(seq.tail)
}
```

---

I've set the grid limits to be minimum of 5 or a maximum of 100, but in reality they could be any numbers - the rovers would move to any coordinates, provided they weren't outside the min/max for the grid. The limits can be modified in the input format regex (could have been incorporated as an arg when running the program, but this seemed outside the scope of the test), and in the rover service `canMove` function.

---

I've tried to make the tests as comprehensive as I can, whilst still being within the bounds of reason. There are certain assumptions that I've made (eg the input file being always being a text file), and as a consequence haven't tested for those things. Ideally testing should always be as holistic and concise as possible, which is what I was aiming for here.

---

I've used services to keep track of things outside a models scope - so the rovers don't contain information about where other rovers are, that's something the service above them handles. I've used case classes to encapsulate data that I'm passing around, with the exception of the max grid x/y axis, as I didn't think two ints that don't change they warranted their own model.


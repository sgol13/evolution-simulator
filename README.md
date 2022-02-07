# Evolution Simulator

I have created this project during my OOP course at AGH UST. It is written in Java and uses JavaFX for GUI. The program simulates a group of animals which live on a square map. The animals walk (consuming energy), eat grass (getting more energy), reproduce and die (if they run out of energy). Each animal has its own genotype that affects its way of moving. If two animals reproduce, children get mixed genotypes of their parents. 

The program allows you to run a simulation with custom parameters, choosig map size, initial number of animals, the amount of energy that is consumed by moving and so on. You can start two simulations with different parameters parallely and observe the results.

<a href="https://youtu.be/ABD29XPm-eA">
         <img alt="Evolution Simulator video" src="https://i.postimg.cc/yN4T37h7/play-screen.png" width=600>

## Compilation

First, clone the repository:
```
git clone https://github.com/sgol13/evolution-simulator.git
cd evolution-simulator
```

Then you can compile and run the program using Gradle:
```
./gradlew run
```

## License
This project is under MIT [license](LICENSE).

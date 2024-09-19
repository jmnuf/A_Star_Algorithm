# A* Algorithm Implementation

## Description
This is a small library that just simply uses the A* algorithm to find the path between 2 points in either a 2D space or a 3D space. It's completely free to use and it this was made as a small project to possibly use in later times but also for some practice.
 
 ## Use
 Depending on the world's dimensions you'll need to use an AStar class, though the AStar class alone can be used with just passing in a Grid instance into constructor but for simplicity it's best to use one of the designated AStar classes
<ul>
  <li>AStar2D</li>
  <li>AStar3D</li>
</ul>

### AStar2D Example
Slight example on how to use the AStar2D
```java 
// Instances a new Grid2D under the hood, creating a 2D "world" with the default dimensions of 20x20
AStar2D aStar = new AStar2D();
// Alternatively I could pass the width and height of the "world" in the constructor
// AStar2D aStar = new AStar2D(20, 20);

// Declare the start point and end point with the **index** values of each dimension
int[] start = new int[] { 0, 0 };
int[] end = new int[] { 19, 19 };
aStar.prepare(start, end);

// I'm instantly calculating the result, but for just going step by step you'd do aStar.calculateStep()
// When doing the full calculation the method returns either the best path to the goal or the best try
// to reach if it's impossible to reach the goal
Node[] best = aStar.calculateAll();

if (aStar.isSolvable()) { // Returns true if the path truly reaches the goal
  // Here you can do what you need to do with the path
  System.out.println("Solution found and it takes " + best.length + "steps to go from the start to the goal.");
} else {
  // Here the path to the goal was not found so one could do what they need if the goal is unreachable
  System.out.println("Unable to find a solution");
}
```

### AStar3D Example
Slight example on how to use the AStar3D (practically like AStar2D)
```java 
// Instances a new Grid3D under the hood, creating a 3D "world" with the default dimensions of 20x20x20
AStar3D aStar = new AStar3D();
// Alternatively I could pass the width, height and depth of the "world" in the constructor
// AStar3D aStar = new AStar3D(20, 20, 20);

// Declare the start point and end point with the **index** values of each dimension
int[] start = new int[] { 0, 2, 0 };
int[] end = new int[] { 19, 2, 19 };
aStar.prepare(start, end);

// I'm instantly calculating the result, but for just going step by step you'd do aStar.calculateStep()
Node[] best = aStar.calculateAll();

if (aStar.isSolvable()) { // Returns true if the path truly reaches the goal
  // Here you can do what you need to do with the path
  System.out.println("Solution found and it takes " + best.length + "steps to go from the start to the goal.");
} else {
  // Here the path to the goal was not found so one could do what they need if the goal is unreachable
  System.out.println("Unable to find a solution");
}
```

If you actually saw both code examples, the classes work practically the same. What changes is how it's initiallized but after that it's actually used in the same manner. There's also the option of not using diagonals if for some reason you don't want them.. Why wouldn't you, they are amazing!
Though still in case you don't need them then you'd use either the <code>Grid2DNoDiagonal</code> or <code>Grid3DNoDiagonal</code> depending on your dimensional plane but it's too much of a bother to instance yourself the class so one can just do `AStar2D.createNoDiagonals(width, height)` or `AStar3D.createNoDiagonals(width, height, depth)` if you're using 3D

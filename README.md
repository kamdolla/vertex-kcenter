# Vertex K Center with Min Radius Problem

Solves the vertex k-center problem (with a slight variation).

The codebase consists of the following:
- **VertexKCenter.java** the solution file
- **graph.txt** the serialized input graph
- **solution.txt** solution as hash set
- **Main.txt** example run file

## Usage

Runninng **Main.java** with the given codebase will print out solution to the vertex k center problem

Although, running **Main.java** does NOT check the solution–– validating is done via another method.

## Contents

Codebase consists of solution file, and input graph.

### VertexKCenter.java

As described above, this class finds a solution to the vertex k-center problem (with a slight variation).

Finds vertex set (describing neighbors of vertex within kValue radius) for each vertex, makes vertex set list sorted by vertex set size from max to min, removes largest vertex set and all elements from vertex set list, resorts vertex set list, and repeats removal and resorting process until vertex set list is empty. When a vertex set is said to be removed, we also add that desired vertex set onto the list of desired intersections.

The class is well documented–– so to learn more about each method, read there.

### graph.txt

An input graph to be read from reader. Describes graph as a hash mapping of vertices -> (neigbors -> weights).

Input graphs are expected to be very large, so performing operations must be kept runtime and memory efficent.

## Additional

Other, less important files in the codebase.

### Main.java

An example run file. Will print the solution the solution file computes-- though no checking is done.

Validating solutions are done in another class yet to be added.

### solution.txt

Outputted solution for input graph. Just put here for safe keeping.

## License

[MIT](https://choosealicense.com/licenses/mit/)
#Here’s a clean and well-structured `README.md` file for your **Perfect Hashing** project:

---

# Perfect Hashing

## Introduction

This project implements a **Perfect Hashing** data structure, designed to ensure **O(1)** time complexity for all lookups. Based on concepts from **Universal Hashing**, two hashing methods are implemented and analyzed:

* A **Quadratic-Space Perfect Hash Table** (`O(N²)`)
* A **Linear-Space Two-Level Perfect Hash Table** (`O(N)`)

The system supports dictionary operations via a **command-line interface**, and its correctness and performance are validated with **JUnit tests**.

---

## Features

* Two perfect hashing implementations:

  * **Quadratic Space Hashing** (`O(N²)`)
  * **Linear Space Two-Level Hashing** (`O(N)`)
* English dictionary operations:

  * Insert
  * Delete
  * Search
  * Batch Insert (from file)
  * Batch Delete (from file)
* Command-line interface to interact with the dictionary
* Java unit tests to validate correctness and performance

---

## Universal Hashing

We use a **universal hash family** where:

> For all `x ≠ y`, `P[h(x) = h(y)] ≤ 1/M`

### Matrix Method

* Represent the hash function as a random binary matrix
* Compute `h(x) = hx` mod 2
* Ensures uniform distribution of keys into hash table slots

---

## Method 1: `O(N²)`-Space Perfect Hashing

* Uses a universal hash function `h` to hash `N` elements into a table of size `N²`
* If a collision occurs, a new `h` is randomly chosen
* Expected number of rebuilds is constant (usually ≤ 2)

---

## Method 2: `O(N)`-Space Two-Level Perfect Hashing

* First-level hash distributes keys into `N` buckets
* Second-level hash is applied per bucket with a quadratic table size
* Guarantees zero collisions at second level by rehashing as needed

---

## Application: English Dictionary

Implemented as a dictionary with the following functionality:

* `Initialize`: create dictionary using selected perfect hashing method
* `Insert`: insert a single word
* `Delete`: delete a word
* `Search`: check if a word exists
* `Batch Insert`: load and insert words from file
* `Batch Delete`: remove words from file

---

## Command Line Interface

After specifying the hash type, the following commands are supported:

* **Insert `<word>`**
  Insert a word and confirm success or duplication.

* **Delete `<word>`**
  Delete a word and confirm success or error.

* **Search `<word>`**
  Check if the word exists and report accordingly.

* **Batch Insert `<file-path>`**
  Insert words from file and report added vs. duplicates.

* **Batch Delete `<file-path>`**
  Delete words from file and report deleted vs. non-existing.

---

## Java Unit Testing

Includes 15–20 **JUnit tests** for:

* Basic functionality of both hashing implementations
* Validation of space usage
* Rebuild attempts count for both methods
* Performance and correctness comparisons:

  * Time
  * Space
  * Rebuilds

---

# co-int-output

[![Build Status](https://travis-ci.com/pwall567/co-int-output.svg?branch=main)](https://app.travis-ci.com/github/pwall567/co-int-output)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Kotlin](https://img.shields.io/static/v1?label=Kotlin&message=v1.8.22&color=7f52ff&logo=kotlin&logoColor=7f52ff)](https://github.com/JetBrains/kotlin/releases/tag/v1.8.22)
[![Maven Central](https://img.shields.io/maven-central/v/net.pwall.util/co-int-output?label=Maven%20Central)](https://search.maven.org/search?q=g:%22net.pwall.util%22%20AND%20a:%22co-int-output%22)

Non-blocking integer output functions

## Background

This is a Kotlin coroutine-aware version of the [`int-output`](https://github.com/pwall567/int-output) integer output
function library.
All of the `outputX` functions in that library are replicated here as `coOutputX`, taking a suspend function as a
parameter, and as an extension function on a [`CoOutput`](#cooutput).
This brings the efficiencies (in particular, the avoidance of object creation) of the original library to non-blocking
output.

## `CoOutput`

The library includes a `typealias` for `CoOutput`, along with a number of extension functions for that alias.
A `CoOutput` instance can therefore be viewed both as a function and as an object with member functions.

The simplest extension functions (the ones named `output` defined alongside `CoOutput`) output a single character, a
`CharSequence` (usually a `String`) or a section of a `CharSequence` in a non-blocking manner.

## `CoOutputFlushable`

The `CoOutputFlushable` abstract class extends `CoOutput` and defines an additional function `flush()`.
This allows implementing classes to flush the output at appropriate points.

Functions may be written to accept a `CoOutput` as a parameter, but if a `CoOutputFlushable` is passed to the function,
it may be flushed (for example, on completion of a significant block of output) as follows:
```kotlin
        if (output is CoOutputFlushable)
            output.flush()
```

## Integer Functions

There are functions to output decimal or hexadecimal, and each one comes in two forms &ndash; a function taking a lambda
as its last parameter, and an extension function on `CoOutput`.

### Decimal

| Function taking lambda        | Parameter | Output                                                          |
|-------------------------------|-----------|-----------------------------------------------------------------|
| `coOutputInt`                 | `int`     | left-trimmed                                                    |
| `coOutputPositiveInt`         | `int`     | left-trimmed (value must be positive)                           |
| `coOutputUnsignedInt`         | `int`     | left-trimmed (value is treated as unsigned)                     |
| `coOutputLong`                | `long`    | left-trimmed                                                    |
| `coOutputPositiveLong`        | `long`    | left-trimmed (value must be positive)                           |
| `coOutputUnsignedLong`        | `long`    | left-trimmed (value is treated as unsigned)                     |
| `coOutput2Digits`             | `int`     | 2 digits left filled with zeros                                 |
| `coOutput3Digits`             | `int`     | 3 digits left filled with zeros                                 |
| `coOutputIntGrouped`          | `int`     | left-trimmed, output in 3-digit groups                          |
| `coOutputPositiveIntGrouped`  | `int`     | left-trimmed, output in 3-digit groups (value must be positive) |
| `coOutputLongGrouped`         | `long`    | left-trimmed, output in 3-digit groups                          |
| `coOutputPositiveLongGrouped` | `long`    | left-trimmed, output in 3-digit groups (value must be positive) |

(the "grouped" forms output digits in blocks of three, separated by a nominated separator character)

| Extension function                   | Parameter | Output                                                          |
|--------------------------------------|-----------|-----------------------------------------------------------------|
| `CoOutput.outputInt`                 | `int`     | left-trimmed                                                    |
| `CoOutput.outputPositiveInt`         | `int`     | left-trimmed (value must be positive)                           |
| `CoOutput.outputUnsignedInt`         | `int`     | left-trimmed (value is treated as unsigned)                     |
| `CoOutput.outputLong`                | `long`    | left-trimmed                                                    |
| `CoOutput.outputPositiveLong`        | `long`    | left-trimmed (value must be positive)                           |
| `CoOutput.outputUnsignedLong`        | `long`    | left-trimmed (value is treated as unsigned)                     |
| `CoOutput.output2Digits`             | `int`     | 2 digits left filled with zeros                                 |
| `CoOutput.output3Digits`             | `int`     | 3 digits left filled with zeros                                 |
| `CoOutput.outputIntGrouped`          | `int`     | left-trimmed, output in 3-digit groups                          |
| `CoOutput.outputPositiveIntGrouped`  | `int`     | left-trimmed, output in 3-digit groups (value must be positive) |
| `CoOutput.outputLongGrouped`         | `long`    | left-trimmed, output in 3-digit groups                          |
| `CoOutput.outputPositiveLongGrouped` | `long`    | left-trimmed, output in 3-digit groups (value must be positive) |

(the "grouped" forms output digits in blocks of three, separated by a nominated separator character)

### Hexadecimal

| Function taking lambda | Parameter | Output                                                           |
|------------------------|-----------|------------------------------------------------------------------|
| `coOutputIntHex`       | `int`     | left-trimmed, hexadecimal                                        |
| `coOutputIntHexLC`     | `int`     | left-trimmed, hexadecimal (using lower-case alphabetics)         |
| `coOutputLongHex`      | `long`    | left-trimmed, hexadecimal                                        |
| `coOutputLongHexLC`    | `long`    | left-trimmed, hexadecimal (using lower-case alphabetics)         |
| `coOutput8Hex`         | `int`     | 8 digits left-padded, hexadecimal                                |
| `coOutput8HexLC`       | `int`     | 8 digits left-padded, hexadecimal (using lower-case alphabetics) |
| `coOutput4Hex`         | `int`     | 4 digits left-padded, hexadecimal                                |
| `coOutput4HexLC`       | `int`     | 4 digits left-padded, hexadecimal (using lower-case alphabetics) |
| `coOutput2Hex`         | `int`     | 2 digits left-padded, hexadecimal                                |
| `coOutput2HexLC`       | `int`     | 2 digits left-padded, hexadecimal (using lower-case alphabetics) |
| `coOutput1Hex`         | `int`     | 1 digit, hexadecimal                                             |
| `coOutput1HexLC`       | `int`     | 1 digit, hexadecimal (using lower-case alphabetics)              |

(the functions all have two variants, one which uses upper-case characters for the hexadecimal digits and one which uses
lower-case)

| Extension function         | Parameter | Output                                                           |
|----------------------------|-----------|------------------------------------------------------------------|
| `CoOutput.outputIntHex`    | `int`     | left-trimmed, hexadecimal                                        |
| `CoOutput.outputIntHexLC`  | `int`     | left-trimmed, hexadecimal (using lower-case alphabetics)         |
| `CoOutput.outputLongHex`   | `long`    | left-trimmed, hexadecimal                                        |
| `CoOutput.outputLongHexLC` | `long`    | left-trimmed, hexadecimal (using lower-case alphabetics)         |
| `CoOutput.output8Hex`      | `int`     | 8 digits left-padded, hexadecimal                                |
| `CoOutput.output8HexLC`    | `int`     | 8 digits left-padded, hexadecimal (using lower-case alphabetics) |
| `CoOutput.output4Hex`      | `int`     | 4 digits left-padded, hexadecimal                                |
| `CoOutput.output4HexLC`    | `int`     | 4 digits left-padded, hexadecimal (using lower-case alphabetics) |
| `CoOutput.output2Hex`      | `int`     | 2 digits left-padded, hexadecimal                                |
| `CoOutput.output2HexLC`    | `int`     | 2 digits left-padded, hexadecimal (using lower-case alphabetics) |
| `CoOutput.output1Hex`      | `int`     | 1 digit, hexadecimal                                             |
| `CoOutput.output1HexLC`    | `int`     | 1 digit, hexadecimal (using lower-case alphabetics)              |

(the functions all have two variants, one which uses upper-case characters for the hexadecimal digits and one which uses
lower-case)

## Dependency Specification

The latest version of the library is 2.3, and it may be obtained from the Maven Central repository.

### Maven
```xml
    <dependency>
      <groupId>net.pwall.util</groupId>
      <artifactId>co-int-output</artifactId>
      <version>2.3</version>
    </dependency>
```
### Gradle
```groovy
    implementation 'net.pwall.util:co-int-output:2.3'
```
### Gradle (kts)
```kotlin
    implementation("net.pwall.util:co-int-output:2.3")
```

Peter Wall

2023-07-24

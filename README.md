# co-int-output

[![Build Status](https://travis-ci.com/pwall567/co-int-output.svg?branch=main)](https://app.travis-ci.com/github/pwall567/co-int-output)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Kotlin](https://img.shields.io/static/v1?label=Kotlin&message=v1.6.10&color=7f52ff&logo=kotlin&logoColor=7f52ff)](https://github.com/JetBrains/kotlin/releases/tag/v1.6.10)
[![Maven Central](https://img.shields.io/maven-central/v/net.pwall.util/co-int-output?label=Maven%20Central)](https://search.maven.org/search?q=g:%22net.pwall.util%22%20AND%20a:%22co-int-output%22)

Non-blocking integer output functions

## Background

This is a Kotlin coroutine-aware version of the [`int-output`](https://github.com/pwall567/int-output) integer output
function library.
All of the `outputX` functions in that library are replicated here as `coOutputX`, taking a suspend function as a
parameter.
This brings the efficiencies (in particular, the avoidance of object creation) of the original library to non-blocking
output.

## Functions

There are functions to output decimal or hexadecimal.

### Decimal

Function                      | Parameter | Output
------------------------------|-----------|----------------------------------------------------------------
`coOutputInt`                 | `int`     | left-trimmed
`coOutputPositiveInt`         | `int`     | left-trimmed (value must be positive)
`coOutputUnsignedInt`         | `int`     | left-trimmed (value is treated as unsigned)
`coOutputLong`                | `long`    | left-trimmed
`coOutputPositiveLong`        | `long`    | left-trimmed (value must be positive)
`coOutputUnsignedLong`        | `long`    | left-trimmed (value is treated as unsigned)
`coOutput2Digits`             | `int`     | 2 digits left filled with zeros
`coOutput3Digits`             | `int`     | 3 digits left filled with zeros
`coOutputIntGrouped`          | `int`     | left-trimmed, output in 3-digit groups
`coOutputPositiveIntGrouped`  | `int`     | left-trimmed, output in 3-digit groups (value must be positive)
`coOutputLongGrouped`         | `long`    | left-trimmed, output in 3-digit groups
`coOutputPositiveLongGrouped` | `long`    | left-trimmed, output in 3-digit groups (value must be positive)
(the "grouped" forms output digits in blocks of three, separated by a nominated separator character)

### Hexadecimal

Function            | Parameter | Output
--------------------|-----------|-----------------------------------------------------------------
`coOutputIntHex`    | `int`     | left-trimmed, hexadecimal
`coOutputIntHexLC`  | `int`     | left-trimmed, hexadecimal (using lower-case alphabetics)
`coOutputLongHex`   | `long`    | left-trimmed, hexadecimal
`coOutputLongHexLC` | `long`    | left-trimmed, hexadecimal (using lower-case alphabetics)
`coOutput8Hex`      | `int`     | 8 digits left-padded, hexadecimal
`coOutput8HexLC`    | `int`     | 8 digits left-padded, hexadecimal (using lower-case alphabetics)
`coOutput4Hex`      | `int`     | 4 digits left-padded, hexadecimal
`coOutput4HexLC`    | `int`     | 4 digits left-padded, hexadecimal (using lower-case alphabetics)
`coOutput2Hex`      | `int`     | 2 digits left-padded, hexadecimal
`coOutput2HexLC`    | `int`     | 2 digits left-padded, hexadecimal (using lower-case alphabetics)
`coOutput1Hex`      | `int`     | 1 digit, hexadecimal
`coOutput1HexLC`    | `int`     | 1 digit, hexadecimal (using lower-case alphabetics)
(the functions all have two variants, one which uses upper-case characters for the hexadecimal digits and one which uses
lower-case)

## Dependency Specification

The latest version of the library is 1.0, and it may be obtained from the Maven Central repository.

### Maven
```xml
    <dependency>
      <groupId>net.pwall.util</groupId>
      <artifactId>co-int-output</artifactId>
      <version>1.0</version>
    </dependency>
```
### Gradle
```groovy
    implementation 'net.pwall.util:co-int-output:1.0'
```
### Gradle (kts)
```kotlin
    implementation("net.pwall.util:co-int-output:1.0")
```

Peter Wall

2022-05-31

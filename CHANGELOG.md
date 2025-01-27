# Change Log

The format is based on [Keep a Changelog](http://keepachangelog.com/).

## [Unreleased]
### Changed
- `pom.xml`: moved to `io.kstuff` (package amd Maven group)
- tests: converted to `should-test` library

## [2.7] - 2025-01-25
### Changed
- `pom.xml`: updated Kotlin version to 2.0.21, updated dependency version

## [2.6] - 2024-07-11
### Added
- `build.yml`, `deploy.yml`: converted project to GitHub Actions
### Changed
- `pom.xml`: updated Kotlin version to 1.9.24, updated dependency version
### Removed
- `.travis.yml`

## [2.5] - 2023-12-02
### Changed
- `CoIntOutput`: added `output1Digit` and `coOutput1Digit`
- `CoIntOutput`: added "Safe" versions of `output1Digit`, `coOutput1Digit`, `output2Digits`, `coOutput2Digits`,
  `output3Digits` and `coOutput3Digits`
- `pom.xml`: updated dependency version

## [2.4] - 2023-11-10
### Changed
- `CoIntOutput`: added `outputIntScaled` _etc._
- `pom.xml`: updated dependency version

## [2.3] - 2023-07-24
### Changed
- `pom.xml`: updated dependency version

## [2.2] - 2023-07-23
### Changed
- `pom.xml`: updated Kotlin version to 1.8.22

## [2.1] - 2023-04-24
### Added
- `CoOutputFlushable`: extension to `CoOutput` to allow `flush()`
### Changed
- `pom.xml`: updated Kotlin version to 1.7.21

## [2.0] - 2022-06-05
### Added
- `CoOutput`: typealias and extension functions
### Changed
- `CoIntOutput`: extended to include use of `CoOutput`

## [1.0] - 2022-05-31
### Added
- all files: initial versions

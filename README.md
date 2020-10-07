# Quizmokvack
## Architecture
An MVVM/MVI architecture where the UI has `ViewModel`s that observes the state of interactors and all state changes are delivered with partial state updates updating the interactors state object in a unidirectional way without side effects.

The app consists of three modules:

### An `app` module where you find:
* the UI
* `ViewModel`s
* Interactors
* Repositories

### A `data` module where you find:
* Network services

### A `domain` module where you find:
* Domain model objects mapped from the network model objects.
* Network service interfaces that the `data` module implements.

## Backend
The quiz game always provides 10 questions that ris andomly grabbed from a pool of 20 questions stored
in a Firebase Realtime Database and these questions are 20 random questions grabbed from Open Trivia Database](https://opentdb.com/api_config.php)
where I have modified it so that some questions have an `image_url` as well.

## Unit testing
There is unit tests that you can find in the `app` module. It's using `OkHttps` `MockWebServer` that
reads a copy of the REST API response from the test `resources` folder and like that you can unit test
everything from `ViewModel`s all the way down to the network services and this can run on a CI server
without any need of an emulator or device.

## Third party Dependencies
### RxJava & RxKotlin
All the communication between the different layers are using RxKotlin.

### Retrofit
To talk to the REST API.

### Dagger
For dependency injection.
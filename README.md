Decouple Android with Dagger and Otto
=====================================
![Otto Dagger](https://raw.github.com/myotive/decouple-android-example/master/images/Otto_Dagger.png)

This repository contains the sample code that accompanies my CodeMash 2016 presentation on Dagger 2 and Otto.

// todo: provide link to presentation slides

[Dagger](https://google.github.io/dagger/) is a dependency injection framework for Android currently being maintained by Google.

[Otto](https://square.github.io/otto/) is an event bus for Android written by Square.

# Screens
## Non Dagger Sample
![Non Dagger Sample](https://raw.github.com/myotive/decouple-android-example/master/images/non_dagger.gif)

This sample illustrates creating a RetroFit API (StarWarsAPI), performing a GET request on "/films" and displaying the results in a RecyclerView.


## Dagger Sample
This sample illustrates performs the same task as the Non Dagger Sample, but using Dagger 2 to inject the StarWarsAPI class.

This example also illustrates a dependent component, StarWarsFragmentComponent. This component depends on ApplicationComponent. I wanted to do this so I could show how you can sub-scope object graphs. When StarWarsFragmentComponent gets destroyed, the singleton object SearchResultAdapter will get cleaned up.


## Otto Sample
![Otto Sample](https://raw.github.com/myotive/decouple-android-example/master/images/otto_fragment.gif)

This example demonstrates a fragment communicating with a retrofit. Upon a successful call to the getFilm api, retrofit will post the success event to the bus, which gets handled in the fragment. If there is a network error, the event gets handled by the Activity.

This sample also shows how you can use the DeadEvent class to debug when there are no subscribers for an event.

# Building

To build, install and run a debug version, run this from the root of the project:

```./gradlew assembleDebug```

#Unit Tests

To run the unit tests for the application:

```./gradlew testDebugUnitTest```

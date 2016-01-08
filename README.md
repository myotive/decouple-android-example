Decouple Android with Dagger and Otto
=====================================
![Otto Dagger](https://raw.github.com/myotive/decouple-android-example/master/images/Otto_Dagger.png)

This repository contains the sample code that accompanies my CodeMash 2016 presentation on Dagger 2 and Otto.

[Here are my slides](https://docs.google.com/presentation/d/1Q5QvmWF2U7xh54rcTQdVx1jE_eRN-VRqZF5CWObJQ5s/edit?usp=sharing)

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

# References

### Dagger
* https://google.github.io/dagger/
* [Dagger 2: a new type of Dependency Injection](https://www.youtube.com/watch?v=oK_XtfXPkqw)
* [Dagger 2: A tutorial](https://www.youtube.com/watch?v=SKFB8u0-VA0)
* https://github.com/konmik/konmik.github.io/wiki/Snorkeling-with-Dagger-2
* https://www.youtube.com/watch?v=JNbz_rgdQ10
* http://slidenerd.com/2015/09/11/android-dependency-injection-with-android-annotations-and-dagger-2/
* https://publicobject.com/2014/11/15/dagger-2-has-components/
* http://code.tutsplus.com/tutorials/dependency-injection-with-dagger-2-on-android--cms-23345
* https://www.reddit.com/r/androiddev/comments/38bcgw/need_a_good_tutorial_on_dagger_2/
* http://www.slideshare.net/pustovit/anton-minashkin-dagger-2-light?qid=ffca4d97-401c-4ada-9790-57f8a5eaf5d4&v=qf1&b=&from_search=1
* http://www.slideshare.net/GlobalLogicUkraine/dagger-2-by-anton-minashkin?related=2
* https://engineering.circle.com/instrumentation-testing-with-dagger-mockito-and-espresso/
* http://tech.just-eat.com/2015/10/26/dependency-injection-on-android/
* http://www.future-processing.pl/blog/dependency-injection-with-dagger-2/
* http://fernandocejas.com/2015/04/11/tasting-dagger-2-on-android
* http://konmik.github.io/snorkeling-with-dagger-2.html
* http://siphon9.net/loune/2015/04/dagger-2-0-android-migration-tips/
* http://blog.gouline.net/2015/05/04/dagger-2-even-sharper-less-square

  Code Examples
* https://github.com/mgrzechocinski/dagger2-example
* https://github.com/cgruber/u2020/tree/dagger2
* https://github.com/frogermcs/DaggerExample

#### Otto
* https://square.github.io/otto/
* [Otto Event Bus in Android](https://www.youtube.com/watch?v=lVqBmGK3VuA)
* [Episode 2: Otto Event Bus](https://www.youtube.com/watch?v=GD_TrOuzkkQ)
* http://www.slideshare.net/petminuta/event-based-programming-45465844?qid=1d3b577f-387a-413d-9a0d-14aca5eaf759&v=default&b=&from_search=1
* https://guides.codepath.com/android/Communicating-with-an-Event-Bus
* [RXAndroid vs EventBus](https://github.com/futurice/android-best-practices/issues/12)
* https://www.reddit.com/r/androiddev/comments/2xsinq/reactive_programming_vs_event_bus/
* http://nerds.weddingpartyapp.com/tech/2014/12/24/implementing-an-event-bus-with-rxjava-rxbus/

#### Both Otto and Dagger
* http://www.infoq.com/presentations/Android-Design

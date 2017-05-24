# Skiffle-2.0

[<img src="https://play.google.com/intl/en_us/badges/images/badge_new.png">](https://play.google.com/store/apps/details?id=com.priyankvex.skiffle&hl=en)

## Description
Skiffle is the fastest way to discover and search music.
Powered by the powerful Spotify API it delivers a fast way to search for music. With offline support and super smooth UX and small size, it's a handy app to keep.

## Blog Post Of The Making
https://priyankvex.wordpress.com/2017/05/12/skiffle-android-app-to-discover-search-and-explore-music-the-making/

## Screenshots

![New Releases](http://i.imgur.com/3u2QGzq.png "New Releases") ![New Releases](http://i.imgur.com/Pb1771g.png "Search Results") ![New Releases](http://i.imgur.com/EuUf3b6.png "Track Details") ![New Releases](http://i.imgur.com/zbSQjP8.png "Artist Details") 

## Features for humans

1. Checkout new releases
2. Fastest search for artist, albums and tracks
3. Offline support
4. Save tracks and albums to favorites
5. Play track preview
6. Powered by Spotify API

## Features for geeks

1. Follows MVP architecture
2. Uses Dagger2 for dependency injection
3. Zero frame skips! i. super smooth UX
4. Uses RxJava2 for background thread handling
5. Effective OkHttp caching for reduced network calls and offline support.

## Libraries Used : 

1. Retrofit2
2. Dagger2
3. RxJava2
4. Green DAO
5. Retrofit2 Rxjava2 Adapter
6. Picasso OkHttp3 Downloader
7. Picasso
8. ButterKnife

Few  more that I am not mentioning. You can check the ````build.gradle```` file for more info.

## Build Instructions : 

1. Clone the project
2. Go to ````Android Studio -> Import Project````
3. Point to ````settings.gradle```` 
4. Click ````import````

## To-Do

1. Write tests
2. Manage state related issues (known bug) :bug: :bug:

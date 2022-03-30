## General

Used Firebase Realtime Database and Followed MVVM Architecture Pattern to build the app

<a id="raw-url" href="files/apk/BFST.apk?raw=true"><img src="https://raw.githubusercontent.com/nasim0x1/nasim0x1/main/image/download.svg"  width="180" height=auto>
</a>

## Specs / Open-source libraries:

- Minimum **SDK 21**, _but AppCompat is used all the way ;-)_
- [**Kotlin**](https://github.com/JetBrains/kotlin) all new modules starting from 2.5.3 will be written in **#Kotlin**.
- [**MVVM**](https://developer.android.com/jetpack/guide) the architecture pattern used in the app (Model-View-ViewModel), that incorporates the Android Architecture Components
- [**ViewModel**](https://developer.android.com/topic/libraries/architecture/viewmodel) for designed to store and manage UI-related data in a lifecycle conscious way.
- [**LiveData**](https://developer.android.com/topic/libraries/architecture/livedata) for observable data
- [**ViewBinding**](https://developer.android.com/topic/libraries/view-binding) for easily interacts with views

## Screenshots

|                           Create New Post                            |                            App Information                            |                            Delete a Post                             |
| :------------------------------------------------------------------: | :-----------------------------------------------------------------: | :------------------------------------------------------------------: |
| <img src="files/screenshots/1.gif" width=272 height=auto>  | <img src="files/screenshots/2.gif" width=272 height=auto> | <img src="files/screenshots/3.gif" width=272 height=auto>  |

## N.B: Apply Plugins and Dependencies

- Apply these plugins
```
apply plugin: 'kotlin-parcelize'
apply plugin: 'kotlin-kapt'
apply plugin: "androidx.navigation.safeargs.kotlin"
```

- Add these dependencies
```
implementation 'androidx.navigation:navigation-fragment-ktx:2.3.5'
implementation 'androidx.navigation:navigation-ui-ktx:2.3.5'

implementation "androidx.room:room-ktx:2.4.1"
kapt "androidx.room:room-compiler:2.4.1"
implementation "android.arch.persistence.room:runtime:1.1.1"
kapt "android.arch.persistence.room:compiler:1.1.1"
```

- Add this into your project build.gradle file
```
classpath "androidx.navigation:navigation-safe-args-gradle-plugin:2.3.5"
```
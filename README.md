# To Do List App

In my professional life, I've only ever worked on an Android app that had existed for years. I wanted to hone my Android skills by creating an app from scratch.

Note to recruiters: This is a project I am prepared to discuss and explain in order to illustrate my knowledge and judgment as a developer. 
I did not use GitHub Copilot or any LLMs to generate code for this project. 
I occasionally used ChatGPT to ask general questions when searching the Android docs and developer forums did not yield a relevant answer.

## Core Functionality
- Create, view, and delete lists with custom names.
- Create and mark as complete to-do list items. Delete to-do list items that are not yet marked as done.

To view a demo of this functionality, download the mp4 [here](https://github.com/armichaud/android_to_do_list_app/blob/main/demo.mp4).

## Implementation Details
- Jetpack Compose Material3 Functions to build UI
- Hilt+Dagger to manage injection of ViewModels and AppRepository (see _Assisted Injection_ below)
- Room to save lists to device storage
- ViewModels + Flow expose UI data for collection
- Splash Screen API to set custom theme for splash screen
- NavHost to control screen navigation

## Assisted Injection
While working on this, I ended up learning about an alpha feature of Hilt: Assisted Injection with ViewModels. See [this Medium article](https://medium.com/@alexander.michaud/hiltviewmodel-assisted-injection-with-compose-a800723165bf) I wrote about it for details.

## Future Goals
I'd like to demonstrate that I am also familiar with the legacy Adapter + ViewBindings pattern. I'd like to recreate the app in XML as a separate BuildVariant.

See also the Issues tab for tactical improvements I'd like to implement when I have the time.
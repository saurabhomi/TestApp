# DummyApp (Kotlin + Jetpack Compose + Navigation + MVI + Hilt + Clean )

DummyApp is a sample project that presents a modern approach to Android app
development.

The project tries to combine popular Android tools and to demonstrate best
development practices by utilizing up to date tech-stack like Compose, Kotlin
Flow and Hilt.

The sample app presents a modern Android application Architecture that is
scalable and maintainable through a MVI.

## Description

* UI
    * [Compose](https://developer.android.com/jetpack/compose) declarative UI
      framework
    * [Material3 design](https://developer.android.com/develop/ui/compose/designsystems/material3)

* Tech/Tools
    * [Kotlin](https://kotlinlang.org/) 100% coverage
    * [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html)
      and [Flow](https://developer.android.com/kotlin/flow) for async operations
    * [Hilt](https://dagger.dev/hilt/) for dependency
      injection (https://developer.android.com/training/dependency-injection/hilt-android)
    * [Jetpack](https://developer.android.com/jetpack)
        * [Compose](https://developer.android.com/jetpack/compose)
        * [Navigation](https://developer.android.com/topic/libraries/architecture/navigation/)
          for navigation between composable
        * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
          that stores, exposes and manages UI state
    * [Retrofit](https://square.github.io/retrofit/) for networking
    * [Coil](https://github.com/coil-kt/coil) for image loading

* Modern Architecture
    * Clean architecture (https://developer.android.com/topic/architecture)
    * (
      with [Navigation component](https://developer.android.com/guide/navigation/navigation-getting-started))
      that defines navigation graphs
    * MVI
    * [Android Architecture components](https://developer.android.com/topic/libraries/architecture) ([ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel), [Navigation](https://developer.android.com/jetpack/androidx/releases/navigation))
    * [Android KTX](https://developer.android.com/kotlin/ktx) - Jetpack Kotlin
      extensions

## Architecture

The project is layered with a View, Presentation, Data, Domain Model separation

Architecture layers:

* View - Composable screens that consume state, apply effects and delegate
  events.
*

ViewModel - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
that manages and reduces the state of the corresponding screen. Additionally, it
intercepts UI events and produces side-effects. The ViewModel lifecycle scope is
tied to the corresponding screen composable.

* Model - Repository classes that retrieve data. In a clean architecture
  context, one should use use-cases that tap into repositories.

![](https://i.imgur.com/UXwFbmv.png)

There are a three core components described:

* **State** - data class that holds the state content of the corresponding
  screen e.g. list of `User`, loading status etc. The state is exposed as a
  Compose runtime `MutableState` object from that perfectly matches the use-case
  of receiving continuous updates with initial value.

* **Event** - plain object that is sent through callbacks from the UI to the
  presentation layer. Events should reflect UI events caused by the user. Event
  updates are exposed as a `MutableSharedFlow` type which is similar
  to `StateFlow` and that behaves as in the absence of a subscriber, any posted
  event will be immediately dropped.

* **Effect** - plain object that signals one-time side-effect actions that
  should impact the UI e.g. triggering a navigation action, showing a Toast,
  SnackBar etc. Effects are exposed as `ChannelFlow` which behave as in each
  event is delivered to a single subscriber. An attempt to post an event without
  subscribers will suspend as soon as the channel buffer becomes full, waiting
  for a subscriber to appear.

Every screen/flow defines its own contract class that states all corresponding
core components described above: state content, events and effects.

### Modules

Modules are the collection of source files and build settings that allow you to
divide your project into discrete units of functionality.

- **App Module**

  `:app` module is
  an [com.android.application](https://developer.android.com/studio/projects/android-library),
  which is needed to create the app bundle. It contains dependency graph and UI
  related classes. It presents data to screen and handle user interactions.

- **Base Module**

  `:base` module contains only framework related base classes that is used in
  other modules

- **Common Module**

  `:common` module contains code and resources which are shared between other
  modules

- **Data Module**

  `:data` module contains implementation of repository and local - remote
  repository interface adapt

- **Domain Module**

  `:domain` module contains use cases and repository interface adapt

- **Remote Module**

  `:remote` module contains remote data source related classes

- **Presentation Module**

  `:presentation` module contains business logic

Each module has its own test.

### Tech Stack

- [Kotlin](https://kotlinlang.org)
- [Jetpack](https://developer.android.com/jetpack)
    * [Android KTX](https://developer.android.com/kotlin/ktx)
    * [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle)
    * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
    * [Navigation Component](https://developer.android.com/guide/navigation/navigation-getting-started)
- [Coroutines - Flow](https://kotlinlang.org/docs/reference/coroutines/flow.html)
    - [State Flow](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow)
    - [Shared Flow](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow)
    - [Channels](https://kotlinlang.org/docs/channels.html#channel-basics)
- [Dagger Hilt](https://dagger.dev/hilt/)
- [Retrofit](https://square.github.io/retrofit/)
- [OkHttp](https://github.com/square/okhttp)
- [KotlinX](https://github.com/Kotlin/kotlinx.serialization)
- [KotlinX Serialization Converter](https://github.com/JakeWharton/retrofit2-kotlinx-serialization-converter)
- [LeakCanary](https://square.github.io/leakcanary/)
- [Testing](https://developer.android.com/training/testing/fundamentals)
    * [MockK](https://mockk.io/)
    * [Junit4](https://junit.org/junit4/)
    * [Truth](https://github.com/google/truth)
    * [Turbine](https://github.com/cashapp/turbine)
    * [Fragment Testing](https://developer.android.com/guide/fragments/test)
    * [Navigation Testing](https://developer.android.com/guide/navigation/navigation-testing)
    * [Coroutine Test](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test)
    * [Dagger Hilt Testing](https://developer.android.com/training/dependency-injection/hilt-testing)

Apache License
Version 2.0, January 2004
http://www.apache.org/licenses/

TERMS AND CONDITIONS FOR USE, REPRODUCTION, AND DISTRIBUTION

1. Definitions.

   "License" shall mean the terms and conditions for use, reproduction,
   and distribution as defined by Sections 1 through 9 of this document.

   "Licensor" shall mean the copyright owner or entity authorized by
   the copyright owner that is granting the License.

   "Legal Entity" shall mean the union of the acting entity and all
   other entities that control, are controlled by, or are under common
   control with that entity. For the purposes of this definition,
   "control" means (i) the power, direct or indirect, to cause the
   direction or management of such entity, whether by contract or
   otherwise, or (ii) ownership of fifty percent (50%) or more of the
   outstanding shares, or (iii) beneficial ownership of such entity.

   "You" (or "Your") shall mean an individual or Legal Entity
   exercising permissions granted by this License.

   "Source" form shall mean the preferred form for making modifications,
   including but not limited to software source code, documentation
   source, and configuration files.

   "Object" form shall mean any form resulting from mechanical
   transformation or translation of a Source form, including but
   not limited to compiled object code, generated documentation,
   and conversions to other media types.

   "Work" shall mean the work of authorship, whether in Source or
   Object form, made available under the License, as indicated by a
   copyright notice that is included in or attached to the work
   (an example is provided in the Appendix below).

   "Derivative Works" shall mean any work, whether in Source or Object
   form, that is based on (or derived from) the Work and for which the
   editorial revisions, annotations, elaborations, or other modifications
   represent, as a whole, an original work of authorship. For the purposes
   of this License, Derivative Works shall not include works that remain
   separable from, or merely link (or bind by name) to the interfaces of,
   the Work and Derivative Works thereof.

   "Contribution" shall mean any work of authorship, including
   the original version of the Work and any modifications or additions
   to that Work or Derivative Works thereof, that is intentionally
   submitted to Licensor for inclusion in the Work by the copyright owner
   or by an individual or Legal Entity authorized to submit on behalf of
   the copyright owner. For the purposes of this definition, "submitted"
   means any form of electronic, verbal, or written communication sent
   to the Licensor or its representatives, including but not limited to
   communication on electronic mailing lists, source code control systems,
   and issue tracking systems that are managed by, or on behalf of, the
   Licensor for the purpose of discussing and improving the Work, but
   excluding communication that is conspicuously marked or otherwise
   designated in writing by the copyright owner as "Not a Contribution."

   "Contributor" shall mean Licensor and any individual or Legal Entity
   on behalf of whom a Contribution has been received by Licensor and
   subsequently incorporated within the Work.

2. Grant of Copyright License. Subject to the terms and conditions of
   this License, each Contributor hereby grants to You a perpetual,
   worldwide, non-exclusive, no-charge, royalty-free, irrevocable
   copyright license to reproduce, prepare Derivative Works of,
   publicly display, publicly perform, sublicense, and distribute the
   Work and such Derivative Works in Source or Object form.

3. Grant of Patent License. Subject to the terms and conditions of
   this License, each Contributor hereby grants to You a perpetual,
   worldwide, non-exclusive, no-charge, royalty-free, irrevocable
   (except as stated in this section) patent license to make, have made,
   use, offer to sell, sell, import, and otherwise transfer the Work,
   where such license applies only to those patent claims licensable
   by such Contributor that are necessarily infringed by their
   Contribution(s) alone or by combination of their Contribution(s)
   with the Work to which such Contribution(s) was submitted. If You
   institute patent litigation against any entity (including a
   cross-claim or counterclaim in a lawsuit) alleging that the Work
   or a Contribution incorporated within the Work constitutes direct
   or contributory patent infringement, then any patent licenses
   granted to You under this License for that Work shall terminate
   as of the date such litigation is filed.

4. Redistribution. You may reproduce and distribute copies of the
   Work or Derivative Works thereof in any medium, with or without
   modifications, and in Source or Object form, provided that You
   meet the following conditions:

   (a) You must give any other recipients of the Work or
   Derivative Works a copy of this License; and

   (b) You must cause any modified files to carry prominent notices
   stating that You changed the files; and

   (c) You must retain, in the Source form of any Derivative Works
   that You distribute, all copyright, patent, trademark, and
   attribution notices from the Source form of the Work,
   excluding those notices that do not pertain to any part of
   the Derivative Works; and

   (d) If the Work includes a "NOTICE" text file as part of its
   distribution, then any Derivative Works that You distribute must
   include a readable copy of the attribution notices contained
   within such NOTICE file, excluding those notices that do not
   pertain to any part of the Derivative Works, in at least one
   of the following places: within a NOTICE text file distributed
   as part of the Derivative Works; within the Source form or
   documentation, if provided along with the Derivative Works; or,
   within a display generated by the Derivative Works, if and
   wherever such third-party notices normally appear. The contents
   of the NOTICE file are for informational purposes only and
   do not modify the License. You may add Your own attribution
   notices within Derivative Works that You distribute, alongside
   or as an addendum to the NOTICE text from the Work, provided
   that such additional attribution notices cannot be construed
   as modifying the License.

   You may add Your own copyright statement to Your modifications and
   may provide additional or different license terms and conditions
   for use, reproduction, or distribution of Your modifications, or
   for any such Derivative Works as a whole, provided Your use,
   reproduction, and distribution of the Work otherwise complies with
   the conditions stated in this License.

5. Submission of Contributions. Unless You explicitly state otherwise,
   any Contribution intentionally submitted for inclusion in the Work
   by You to the Licensor shall be under the terms and conditions of
   this License, without any additional terms or conditions.
   Notwithstanding the above, nothing herein shall supersede or modify
   the terms of any separate license agreement you may have executed
   with Licensor regarding such Contributions.

6. Trademarks. This License does not grant permission to use the trade
   names, trademarks, service marks, or product names of the Licensor,
   except as required for reasonable and customary use in describing the
   origin of the Work and reproducing the content of the NOTICE file.

7. Disclaimer of Warranty. Unless required by applicable law or
   agreed to in writing, Licensor provides the Work (and each
   Contributor provides its Contributions) on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
   implied, including, without limitation, any warranties or conditions
   of TITLE, NON-INFRINGEMENT, MERCHANTABILITY, or FITNESS FOR A
   PARTICULAR PURPOSE. You are solely responsible for determining the
   appropriateness of using or redistributing the Work and assume any
   risks associated with Your exercise of permissions under this License.

8. Limitation of Liability. In no event and under no legal theory,
   whether in tort (including negligence), contract, or otherwise,
   unless required by applicable law (such as deliberate and grossly
   negligent acts) or agreed to in writing, shall any Contributor be
   liable to You for damages, including any direct, indirect, special,
   incidental, or consequential damages of any character arising as a
   result of this License or out of the use or inability to use the
   Work (including but not limited to damages for loss of goodwill,
   work stoppage, computer failure or malfunction, or any and all
   other commercial damages or losses), even if such Contributor
   has been advised of the possibility of such damages.

9. Accepting Warranty or Additional Liability. While redistributing
   the Work or Derivative Works thereof, You may choose to offer,
   and charge a fee for, acceptance of support, warranty, indemnity,
   or other liability obligations and/or rights consistent with this
   License. However, in accepting such obligations, You may act only
   on Your own behalf and on Your sole responsibility, not on behalf
   of any other Contributor, and only if You agree to indemnify,
   defend, and hold each Contributor harmless for any liability
   incurred by, or claims asserted against, such Contributor by reason
   of your accepting any such warranty or additional liability.

END OF TERMS AND CONDITIONS

APPENDIX: How to apply the Apache License to your work.

      To apply the Apache License to your work, attach the following
      boilerplate notice, with the fields enclosed by brackets "[]"
      replaced with your own identifying information. (Don't include
      the brackets!)  The text should be enclosed in the appropriate
      comment syntax for the file format. We also recommend that a
      file or class name and description of purpose be included on the
      same "printed page" as the copyright notice for easier
      identification within third-party archives.

Copyright (c) 2024 Saurabh Srivastava

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.


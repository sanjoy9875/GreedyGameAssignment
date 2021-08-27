# GreedyGameAssignment


Screens :

![Untitled design (6)](https://user-images.githubusercontent.com/75353031/131075087-6b0fd176-e229-4376-93bc-3d24fe51042b.png)


Descriptions :

{

all data for this app available on https://newsapi.org/

summary : 
  => First Screen have a list of articles getting this articles from API
  => Every articles have a read or save button save button will save the articles into our database and
     read button will navigate to another screen where you can read the whole article
  => In the first screen also have a search bar where you can search any articles by its title
  
  => In the first screen also have a read later button on the top right corner it will navigate you to a another
     page where all the saved articles you can see.

}


Used Dependency : 

plugins {

    id 'com.android.application'
    id 'kotlin-android'
    id 'kotlin-kapt'
    id 'kotlin-android-extensions'
}

dependencies {

    implementation "org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version"
    implementation 'androidx.core:core-ktx:1.6.0'
    implementation 'androidx.appcompat:appcompat:1.3.1'
    implementation 'com.google.android.material:material:1.4.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.0'
    testImplementation 'junit:junit:4.+'
    androidTestImplementation 'androidx.test.ext:junit:1.1.3'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'

    def room_version = "2.3.0"
    def arch_version = '2.2.0-alpha01'
    def retrofit_version = "2.6.1"
    def coroutines = "1.1.1"
    def kotlinCoroutineVersion = "1.1.1"
    implementation fileTree(dir: 'libs', include: ['*.jar'])


    // ViewModel and LiveData
    implementation "androidx.lifecycle:lifecycle-extensions:$arch_version"
    implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:$arch_version"
    implementation "androidx.lifecycle:lifecycle-livedata-ktx:$arch_version"
    implementation "androidx.lifecycle:lifecycle-runtime-ktx:$arch_version"

    //Coroutines
    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines"
    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinCoroutineVersion"

    //Room
    //implementation "androidx.room:room-runtime:$jetpack_version"
    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor "androidx.room:room-compiler:$room_version"
    // To use Kotlin annotation processing tool (kapt)
    kapt("androidx.room:room-compiler:$room_version")
    // To use Kotlin Symbolic Processing (KSP)
    //ksp("androidx.room:room-compiler:$room_version")
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$room_version")
    // optional - RxJava2 support for Room
    implementation "androidx.room:room-rxjava2:$room_version"
    // optional - RxJava3 support for Room
    implementation "androidx.room:room-rxjava3:$room_version"
    // optional - Guava support for Room, including Optional and ListenableFuture
    implementation "androidx.room:room-guava:$room_version"
    // optional - Test helpers
    testImplementation("androidx.room:room-testing:$room_version")

    // Retrofit & OkHttp
    implementation "com.squareup.retrofit2:retrofit:$retrofit_version"
    implementation "com.squareup.retrofit2:converter-gson:$retrofit_version"
    implementation "com.squareup.okhttp3:logging-interceptor:4.9.0"
    testImplementation "com.squareup.okhttp3:mockwebserver:4.9.0"

    //glide
    implementation 'com.github.bumptech.glide:glide:4.12.0'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.12.0'

}

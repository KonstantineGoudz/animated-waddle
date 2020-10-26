## An app Using NYT api to display news articles related to health topics

The app uses the NYT Semantic and Articles API to find concepts relating to health. The NYT Articles api is used to find articles for a specific concept.

#Setup

- option 1
update projecect or user `gradle.properties` with the `nytApiKey` key and the api key as the value 

- option 2 
replace the api key in the app build.gradle
[here](https://github.com/KonstantineGoudz/animated-waddle/blob/804e7d0671dcfbac9e5d4370fec9a32825c2342b/app/build.gradle#L46)
and 
[here](https://github.com/KonstantineGoudz/animated-waddle/blob/804e7d0671dcfbac9e5d4370fec9a32825c2342b/app/build.gradle#L41)

from
```groovy
    buildTypes {
        debug {
            minifyEnabled false
            buildConfigField 'String', "NYTApiKey", nytApiKey
        }
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
            buildConfigField 'String', "NYTApiKey", nytApiKey
        }
    }
```

to 

```groovy
    buildTypes {
        debug {
            minifyEnabled false
            buildConfigField 'String', "NYTApiKey", "the-api-key-here"
        }
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
            buildConfigField 'String', "NYTApiKey", "the-api-key-here"
        }
    }
```
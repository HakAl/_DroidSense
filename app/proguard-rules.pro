# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /home/alex/Documents/android-studio/android-sdk-linux/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# # # # # # # # # # # # # # # # # # # # # # # # ## # # # # # # # # # # # # # # # # # # # # # # # #
# # # # # # # # # # # # # # # # # # # #  Standard Android  # # # # # # # # # # # # # # # # # # # #
# # # # # # # # # # # # # # # # # # # # # # # # ## # # # # # # # # # # # # # # # # # # # # # # # #
-verbose
-dontpreverify
-repackageclasses ''
-allowaccessmodification
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-keepattributes *Annotation*
-keepattributes JavascriptInterface
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.support.v4.app.Fragment
-keep public class * extends android.app.Fragment
-keep public class * extends android.widget.ArrayAdapter
-keep public class android.webkit.WebViewClient
-keep public class * extends android.webkit.WebViewClient
-keepclasseswithmembers class * extends android.widget.ArrayAdapter

-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers class * extends android.content.Context {
   public void *(android.view.View);
   public void *(android.view.MenuItem);
}
-keepclassmembers class * implements android.os.Parcelable {
    static ** CREATOR;
}
-keepnames class * implements android.os.Parcelable {
    public static final ** CREATOR;
}
-keepclassmembers class **.R$* {
    public static <fields>;
}
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# # # # # # # # # # # # # # # # # # # # # # # # ## # # # # # # # # # # # # # # # # # # # # # # # #
# # # # # # # # # # # # # # # # # # # # # #   Dagger   # # # # # # # # # # # # # # # # # # # # # #
# # # # # # # # # # # # # # # # # # # # # # # # ## # # # # # # # # # # # # # # # # # # # # # # # #
-keepclassmembers,allowobfuscation class * {
    @javax.inject.* *;
    @dagger.* *;
    <init>();
}
-keep class **$$ModuleAdapter
-keep class **$$InjectAdapter
-keep class **$$StaticInjection
-keepnames class dagger.Lazy
-keepnames !abstract class com.jacmobile.sensorpanellite.**
-dontwarn dagger.**
-dontwarn com.squareup.**
-dontwarn com.google.common.**


# # # # # # # # # # # # # # # # # # # # # # # # ## # # # # # # # # # # # # # # # # # # # # # # # #
# # # # # # # # # # # # # # # # # # # # # Remove Logs  # # # # # # # # # # # # # # # # # # # # # #
# # # # # # # # # # # # # # # # # # # # # # # # ## # # # # # # # # # # # # # # # # # # # # # # # #
-assumenosideeffects class android.util.Log {
    public static *** e(...);
    public static *** w(...);
    public static *** wtf(...);
    public static *** d(...);
    public static *** v(...);
}

# # # # # # # # # # # # # # # # # # # # # # # # ## # # # # # # # # # # # # # # # # # # # # # # # #
# # # # # # # # # # # # # # # # # # # # # # #  Libs  # # # # # # # # # # # # # # # # # # # # # # #
# # # # # # # # # # # # # # # # # # # # # # # # ## # # # # # # # # # # # # # # # # # # # # # # # #
-keep class android.** {*;}
-keep class com.google.** { *;}
-keep interface com.google.** { *;}
-keep class com.android.support.v4.** { *; }
-keep class com.android.support.support-v4.** { *; }
-keep class com.android.support.support-v13.** { *; }
-keep class com.google.common.** { *; }
-keep class com.squareup.dagger.** { *; }
-keep class com.android.support.appcompat-v7.** { *; }
-keep class org.json.** { *; }
-keep class com.android.support.v4.content.** { *; }
-keep class com.androidplot.**
-keepclassmembers class com.androidplot.** { *; }




# # # # # # # # # # # # # # # # # # # # # # Constants  # # # # # # # # # # # # # # # # # # # # # #
-keepclassmembers public class * {
    public static ** valueOf(java.lang.String);
    public static ** valueOf(java.lang.Integer.TYPE);
}
###--------->>
###--------->> dunno if these are necessary
###--------->>
-keep class java.lang.reflect.Type { *; }
-keep class java.util.ArrayList { *; }
-keep class java.util.Collection { *; }
-keep class java.util.List { *; }

# # # # # # # # # # # # # # # # # # # Google Play Services # # # # # # # # # # # # # # # # # # # #
-keep class * extends java.util.ListResourceBundle {
    protected Object[][] getContents();
}

-keep public class com.google.android.gms.common.internal.safeparcel.SafeParcelable {
    public static final *** NULL;
}

-keepnames @com.google.android.gms.common.annotation.KeepName class *
-keepclassmembernames class * {
    @com.google.android.gms.common.annotation.KeepName *;
}
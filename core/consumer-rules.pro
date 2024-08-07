# Keep all classes and interfaces in the net.sqlcipher package
-keep,includedescriptorclasses class net.sqlcipher.** { *; }
-keep,includedescriptorclasses interface net.sqlcipher.** { *; }

##---------------Begin: ProGuard configuration for Gson ----------
# Gson uses generic type information stored in a class file when working with fields.
-keepattributes Signature

# For using Gson @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-dontwarn sun.misc.**
# Uncomment if needed
# -keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.examples.android.model.** { <fields>; }

# Prevent ProGuard from stripping interface information from TypeAdapter, TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * extends com.google.gson.TypeAdapter
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# Prevent ProGuard from stripping interface information from TypeAdapter, TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * extends com.google.gson.TypeAdapter
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# Keep annotations used in Dagger and Hilt
-keep class javax.inject.** { *; }
-keep class dagger.hilt.internal.** { *; }
-keep class dagger.hilt.android.internal.** { *; }
-keep class dagger.hilt.android.internal.managers.** { *; }

# Prevent R8 from leaving Data object members always null
-keepclassmembers,allowobfuscation class * {
    @com.google.gson.annotations.SerializedName <fields>;
}
##---------------Begin: proguard configuration for Retrofit ----------
# Retrofit does reflection on generic parameters. InnerClasses is required to use Signature and
# EnclosingMethod is required to use InnerClasses.

-keepattributes Signature, InnerClasses, EnclosingMethod

# Retrofit does reflection on method and parameter annotations.
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations

# Retain service method parameters when optimizing.
-keepclassmembers,allowshrinking,allowobfuscation interface * {
   @retrofit2.http.* <methods>;
}

# Ignore JSR 305 annotations for embedding nullability information.
-dontwarn javax.annotation.**

# Guarded by a NoClassDefFoundError try/catch and only used when on the classpath.
-dontwarn kotlin.Unit

# Top-level functions that can only be used by Kotlin.
-dontwarn retrofit2.KotlinExtensions
-dontwarn retrofit2.KotlinExtensions*

# With R8 full mode, it sees no subtypes of Retrofit interfaces since they are created with a Proxy
# and replaces all potential values with null. Explicitly keeping the interfaces prevents this.
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface <1>
-dontwarn kotlinx.**
##---------------Begin: ProGuard configuration for Glide ----------
# Keep Glide modules and their initialization methods
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep class * extends com.bumptech.glide.module.AppGlideModule {
    <init>(...);
}

# Keep Glide's ImageHeaderParser enum values and methods
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
    **[] $VALUES;
    public *;
}

# Keep specific internal Glide class
-keep class com.bumptech.glide.load.data.ParcelFileDescriptorRewinder$InternalRewinder {
    *** rewind();
}
##---------------Begin: ProGuard configuration for Room ----------
# Room uses reflection and annotations, so you need to keep these classes
-keep class androidx.room.** { *; }
-keepclassmembers class androidx.room.** { *; }
-dontwarn androidx.room.**

##---------------Begin: ProGuard configuration for Dagger-Hilt ----------
# Keep Hilt-related classes
-keep class hilt_aggregated_deps.** { *; }
-keep class dagger.hilt.android.internal.** { *; }
-keep class dagger.hilt.android.internal.managers.** { *; }
-keep class dagger.hilt.internal.** { *; }
-keep class dagger.hilt.internal.GeneratedComponentManager { *; }
# Keep Dagger classes and annotations
-keep class dagger.** { *; }
-keep class javax.inject.** { *; }
-keep class javax.annotation.** { *; }
-keep class * implements dagger.MembersInjector
-keep class * implements dagger.Component
-keep class * implements dagger.Module
-keep class * implements dagger.Provides
-keep class * implements dagger.Binds

##---------------Begin: ProGuard configuration for Kotlin Coroutines and Flow ----------
# Keep Kotlin Coroutines and Flow classes
-keep class kotlinx.coroutines.** { *; }
-keep class kotlinx.coroutines.flow.** { *; }
-keep class kotlinx.coroutines.internal.** { *; }
-keep class retrofit2.Retrofit { *; }
-keep class retrofit2.adapter.** { *; }
-keep class retrofit2.converter.gson.GsonConverterFactory { *; }

# Keep Kotlin coroutine continuation classes
-keep class kotlin.coroutines.** { *; }

# Keep classes for Dagger Hilt
-keep class com.example.core.** { *; }
-keep class com.example.projectsubmissionandroidexpert.** { *; }

# Keep the core data source classes
-keep class com.example.core.core.data.source.AgentsRepository { *; }
-keep class com.example.core.core.data.source.Resource$Error { *; }
-keep class com.example.core.core.data.source.Resource$Loading { *; }
-keep class com.example.core.core.data.source.Resource$Success { *; }
-keep class com.example.core.core.data.source.Resource { *; }
-keep class com.example.core.core.data.source.local.LocalDataSource { *; }
-keep class com.example.core.core.data.source.remote.RemoteDataSource { *; }
-keep class com.example.core.core.data.source.remote.network.Apiservice { *; }

# Keep the DI modules and generated classes
-keep class com.example.core.core.di.DatabaseModule { *; }
-keep class com.example.core.core.di.DatabaseModule_ProvideAgentDaoFactory { *; }
-keep class com.example.core.core.di.DatabaseModule_ProvideDatabaseFactory { *; }
-keep class com.example.core.core.di.NetworkModule { *; }
-keep class com.example.core.core.di.NetworkModule_ProvideApiServiceFactory { *; }
-keep class com.example.core.core.di.NetworkModule_ProvideOkHttpClientFactory { *; }
-keep class com.example.core.core.di.RepositoryModule { *; }
-keep class com.example.core.core.di.UseCaseModule { *; }
-keep class com.example.core.core.di.UseCaseModule_ProvideAgentUseCaseFactory { *; }

# Keep the domain model and use cases
-keep class com.example.core.core.domain.model.Agent { *; }
-keep class com.example.core.core.domain.repository.IAgentRepository { *; }
-keep class com.example.core.core.domain.usecase.AgentInteractor { *; }
-keep class com.example.core.core.domain.usecase.AgentUseCase { *; }

# Keep the UI Adapters
-keep class com.example.core.core.ui.AgentAdapter { *; }
-keep class com.example.core.core.ui.AgentOnlyFavoriteAdapter { *; }

# Keep utility classes
-keep class com.example.core.core.utils.AppExecutors { *; }

# Keep Hilt generated dependencies
-keep class hilt_aggregated_deps._com_example_core_core_di_DatabaseModule { *; }
-keep class hilt_aggregated_deps._com_example_core_core_di_NetworkModule { *; }
-keep class hilt_aggregated_deps._com_example_core_core_di_RepositoryModule { *; }
-keep class hilt_aggregated_deps._com_example_core_core_di_UseCaseModule { *; }

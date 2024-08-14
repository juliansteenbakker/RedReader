-dontobfuscate
-keepattributes LineNumberTable,SourceFile,RuntimeVisibleAnnotations,AnnotationDefault,InnerClasses,EnclosingMethod

-keepclassmembers class * extends org.quantumbadger.redreader.io.WritableObject {
	*;
}

-keepclassmembers class * extends org.quantumbadger.redreader.jsonwrap.JsonObject$JsonDeserializable {
	*;
}

-keepclassmembers class org.quantumbadger.redreader.R { *; }
-keepclassmembers class org.quantumbadger.redreader.R$xml {	*; }
-keepclassmembers class org.quantumbadger.redreader.R$string {	*; }

-keepclassmembers class com.github.luben.zstd.* {
	*;
}

-if @kotlinx.serialization.Serializable class **
{
    static **$* *;
}

-keepnames class <1>$$serializer { # -keepnames suffices; class is kept when serializer() is kept.
    static <1>$$serializer INSTANCE;
}

# Needed for instrumented tests for some reason
-keep class com.google.common.util.concurrent.ListenableFuture { *; }

-dontwarn com.google.android.exoplayer2.TracksInfo
-dontwarn org.bouncycastle.jsse.BCSSLParameters
-dontwarn org.bouncycastle.jsse.BCSSLSocket
-dontwarn org.bouncycastle.jsse.provider.BouncyCastleJsseProvider
-dontwarn org.conscrypt.Conscrypt$Version
-dontwarn org.conscrypt.Conscrypt
-dontwarn org.conscrypt.ConscryptHostnameVerifier
-dontwarn org.joda.convert.FromString
-dontwarn org.joda.convert.ToString
-dontwarn org.openjsse.javax.net.ssl.SSLParameters
-dontwarn org.openjsse.javax.net.ssl.SSLSocket
-dontwarn org.openjsse.net.ssl.OpenJSSE

@file:Suppress("UnstableApiUsage")

buildscript {
	repositories {
		mavenCentral()
		google()
	}
	dependencies {
		// TODO share this with the "plugins" block
		val rrKotlinVersion = "1.8.0"

		classpath("com.android.tools.build:gradle:7.3.1")
		classpath(kotlin("gradle-plugin", version = rrKotlinVersion))
		classpath(kotlin("serialization", version = rrKotlinVersion))
	}
}

plugins {
	id("com.android.application") version("7.3.1") apply(true)
	kotlin("android") version("1.8.0") apply(true)
	kotlin("plugin.serialization") version("1.8.0") apply(true)
	kotlin("plugin.parcelize") version("1.8.0") apply(true)

	// Make sure that you have the Google services Gradle plugin dependency
	id("com.google.gms.google-services") version "4.4.0" apply false

	// Add the dependency for the Crashlytics Gradle plugin
	id("com.google.firebase.crashlytics") version "2.9.9" apply false
    pmd
	checkstyle
}

dependencies {

	implementation("androidx.multidex:multidex:2.0.1")

	implementation(project(":redreader-common"))
	implementation(project(":redreader-datamodel"))

	coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.2.2")
	implementation("androidx.core:core-ktx:1.9.0")
	implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
	implementation("org.jetbrains.kotlin:kotlin-reflect:1.8.0") // TODO use constant
	implementation("androidx.annotation:annotation:1.5.0")
	implementation("androidx.appcompat:appcompat:1.6.0")
	implementation("androidx.recyclerview:recyclerview:1.2.1")
	implementation("com.google.android.flexbox:flexbox:3.0.0")
	implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
	implementation("androidx.preference:preference:1.2.0")
	implementation("com.google.android.material:material:1.9.0")
	implementation("androidx.constraintlayout:constraintlayout:2.1.4")
	implementation("androidx.fragment:fragment:1.5.5")

	implementation("com.fasterxml.jackson.core:jackson-core:2.14.2")
	implementation("org.apache.commons:commons-lang3:3.12.0")

	implementation("org.apache.commons:commons-text:1.10.0")

	implementation("com.squareup.okhttp3:okhttp:3.12.13")
	implementation("info.guardianproject.netcipher:netcipher:1.2.1")
	implementation("com.google.android.exoplayer:exoplayer-core:2.19.0")
	implementation("com.google.android.exoplayer:exoplayer-ui:2.19.0")
	implementation("com.github.luben:zstd-jni:1.5.1-1@aar")

	implementation("androidx.wear:wear:1.2.0")
	implementation("com.google.android.support:wearable:2.9.0")
	compileOnly("com.google.android.wearable:wearable:2.9.0")

	testImplementation("junit:junit:4.13.2")

	androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
	androidTestImplementation("androidx.test:rules:1.5.0")
	androidTestImplementation("androidx.test.espresso:espresso-contrib:3.5.1")

	// Import the BoM for the Firebase platform
	implementation(platform("com.google.firebase:firebase-bom:32.3.1"))

	// Add the dependencies for the Crashlytics and Analytics libraries
	// When using the BoM, you don't specify versions in Firebase library dependencies
	implementation("com.google.firebase:firebase-crashlytics-ktx")
	implementation("com.google.firebase:firebase-analytics-ktx")
}

android {
	compileSdk = 33
	buildToolsVersion = "33.0.1"
	ndkVersion = "23.1.7779620"
	namespace = "dev.steenbakker.wearreader"

	defaultConfig {
		applicationId = "dev.steenbakker.wearreader"
		minSdk = 23
		targetSdk = 33
		versionCode = 108
		versionName = "1.22"

		multiDexEnabled = true
		vectorDrawables.generatedDensities("mdpi", "hdpi", "xhdpi", "xxhdpi", "xxxhdpi")
		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
	}

	// Flag to tell aapt to keep the attribute ids around
	androidResources {
		additionalParameters("--no-version-vectors")
	}

	buildTypes.forEach {
		it.isMinifyEnabled = true
		it.isShrinkResources = false

		it.proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
	}

	compileOptions {
		encoding = "UTF-8"
		isCoreLibraryDesugaringEnabled = true
		sourceCompatibility = JavaVersion.VERSION_1_8
		targetCompatibility = JavaVersion.VERSION_1_8
	}

	lint {
		checkReleaseBuilds = false
		abortOnError = true
		warningsAsErrors = true

		error.add("DefaultLocale")

		baseline = file("config/lint/lint-baseline.xml")
		lintConfig = file("config/lint/lint.xml")
	}

	packagingOptions {
		excludes.add("META-INF/*")
	}

	testOptions {
		animationsDisabled = true
	}
}

pmd {
	toolVersion = "6.36.0"
}

tasks.register("pmd", Pmd::class) {
	dependsOn.add("assembleDebug")
	ruleSetFiles = files("${project.rootDir}/config/pmd/rules.xml")
	ruleSets = emptyList() // otherwise defaults clash with the list in rules.xml
	source("src/main/java/org/quantumbadger")
	include("**/*.java")
	isConsoleOutput = true
}

checkstyle {
	toolVersion = "8.35"
}

tasks.register("Checkstyle", Checkstyle::class) {
	source("src/main/java/org/quantumbadger")
	ignoreFailures = false
	isShowViolations = true
	include("**/*.java", "**/*.kt")
	classpath = files()
	maxWarnings = 0
	configFile = rootProject.file("${project.rootDir}/config/checkstyle/checkstyle.xml")
}

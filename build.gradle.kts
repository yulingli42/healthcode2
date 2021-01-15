plugins {
    java
    war
}

group = "com.health-code"
version = "1.0-SNAPSHOT"

java.targetCompatibility = JavaVersion.VERSION_1_8
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
    maven("https://maven.aliyun.com/repository/public")
    mavenCentral()
}

dependencies {
    implementation("com.zaxxer", "HikariCP", "3.4.5")
    runtimeOnly("mysql", "mysql-connector-java", "8.0.22")

    implementation("com.fasterxml.jackson.core", "jackson-databind", "2.12.0")
    implementation("com.fasterxml.jackson.datatype", "jackson-datatype-jsr310", "2.12.0")

    implementation("com.google.zxing", "core", "3.4.1")
    implementation("com.google.zxing", "javase", "3.4.1")

    implementation("org.apache.poi", "poi", "3.9")
    implementation("org.apache.poi", "poi-excelant", "3.9")
    implementation("org.apache.poi", "poi-scratchpad", "3.9")
    implementation("com.alibaba", "easyexcel", "1.1.2-beat1")

    implementation("org.jfree", "jfreechart", "1.5.2")
    implementation("com.itextpdf", "itextpdf", "5.2.0")
    implementation("com.itextpdf", "itext-asian", "5.2.0")


    implementation("cn.hutool", "hutool-all", "5.5.2")
    implementation("com.google.guava", "guava", "30.1-jre")
    implementation("org.apache.commons", "commons-collections4", "4.4")
    implementation("org.apache.commons", "commons-lang3", "3.11")
    implementation("commons-fileupload", "commons-fileupload", "1.4")
    implementation("org.jetbrains", "annotations", "20.1.0")

    implementation("org.apache.logging.log4j", "log4j-api", "2.14.0")
    implementation("org.apache.logging.log4j", "log4j-core", "2.14.0")
    implementation("org.slf4j", "slf4j-log4j12", "1.7.30")
    implementation("ch.qos.logback", "logback-classic", "1.2.3")

    implementation("org.apache.taglibs", "taglibs-standard-impl", "1.2.5")
    implementation("javax.servlet.jsp.jstl", "jstl", "1.2")
    implementation("javax.servlet.jsp.jstl", "javax.servlet.jsp.jstl-api", "1.2.2")
    implementation("javax.el", "javax.el-api", "3.0.0")
    providedCompile("javax.servlet", "javax.servlet-api", "4.0.1")
    providedCompile("javax.servlet.jsp", "javax.servlet.jsp-api", "2.3.3")

    testImplementation("org.junit.jupiter", "junit-jupiter-api", "5.7.0")
    testImplementation("org.junit.jupiter", "junit-jupiter-engine", "5.7.0")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}
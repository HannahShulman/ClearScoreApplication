package com.hanna.clearscore.application.test

@Target(AnnotationTarget.ANNOTATION_CLASS)
annotation class AllOpenAnnotation

@AllOpenAnnotation
@Target(AnnotationTarget.CLASS)
annotation class OpenForTesting
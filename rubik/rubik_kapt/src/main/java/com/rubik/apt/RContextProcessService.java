package com.rubik.apt;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

import com.google.auto.service.AutoService;

@SupportedAnnotationTypes({
        Constants.Annotations.R_EVENT,
        Constants.Annotations.R_EVENT_REPEATABLE,
        Constants.Annotations.R_EVENT_INSTANCE,
        Constants.Annotations.R_EVENT_INSTANCE_REPEATABLE,
        Constants.Annotations.R_EVENT_ASSIST,
        Constants.Annotations.R_EVENT_ASSIST_REPEATABLE,
        Constants.Annotations.R_ROUTE,
        Constants.Annotations.R_ROUTE_REPEATABLE,
        Constants.Annotations.R_ROUTE_FUNCTION,
        Constants.Annotations.R_ROUTE_FUNCTION_REPEATABLE,
        Constants.Annotations.R_ROUTE_PAGE,
        Constants.Annotations.R_ROUTE_PAGE_REPEATABLE,
        Constants.Annotations.R_ROUTE_INSTANCE,
        Constants.Annotations.R_ROUTE_INSTANCE_REPEATABLE,
        Constants.Annotations.R_ROUTE_ASSIST,
        Constants.Annotations.R_ROUTE_ASSIST_REPEATABLE,
        Constants.Annotations.R_VALUE
})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class RContextProcessService extends AbstractProcessor {

    private final RContextProcessor processor = new RContextProcessor();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        if (null != processingEnv) {
            super.init(processingEnv);
            processor.init(processingEnv);
        }
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (null != processingEnv) {
            return processor.process(annotations, processingEnv, roundEnv);
        }
        return true;
    }
}
